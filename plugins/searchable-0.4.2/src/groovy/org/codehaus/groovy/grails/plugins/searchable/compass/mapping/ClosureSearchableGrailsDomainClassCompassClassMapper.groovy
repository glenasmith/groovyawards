/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.plugins.searchable.compass.mapping

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.plugins.searchable.SearchableUtils
import org.codehaus.groovy.grails.plugins.searchable.compass.SearchableCompassUtils
import org.codehaus.groovy.grails.plugins.searchable.util.GrailsDomainClassUtils
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

/**
*
*
* @author Maurice Nicholson
*/
class ClosureSearchableGrailsDomainClassCompassClassMapper extends AbstractSearchableGrailsDomainClassCompassClassMapper implements SearchableGrailsDomainClassCompassClassMapper {
    static final Log log = LogFactory.getLog(ClosureSearchableGrailsDomainClassCompassClassMapper);
    static final SEARCHABLE_PROPERTY_OPTIONS = ['accessor', 'analyzer', 'boost', 'converter', 'excludeFromAll', 'format', 'index', 'managedId', 'managedIdIndex', 'propertyConverter', 'reverse', 'store', 'termVector']
    static final SEARCHABLE_REFERENCE_OPTIONS = ['accessor', 'cascade', 'converter', 'refAlias', 'refComponentAlias']
    static final SEARCHABLE_COMPONENT_OPTIONS = ['accessor', 'cascade', 'converter', 'maxDepth', 'override', 'refAlias']
    static final SEARCHABLE_REFERENCE_MAPPING_OPTIONS = SEARCHABLE_REFERENCE_OPTIONS + ["reference", "component"]
    static final SEARCHABLE_COMPONENT_MAPPING_OPTIONS = SEARCHABLE_COMPONENT_OPTIONS + ["reference", "component"]

    GrailsDomainClass grailsDomainClass;
    Class mappedClass;
    GrailsDomainClassProperty[] mappableProperties;
    Collection searchableGrailsDomainClasses
    Object only;
    Object except;
    List mappedProperties;
    String alias;
    List constantMetadatas;

    /**
     * Get the property mappings for the given GrailsDomainClass
     * @param grailsDomainClass the Grails domain class
     * @param searchableGrailsDomainClasses a collection of searchable GrailsDomainClass instances
     * @param searchableValue the searchable value: true|false|Map|Closure
     * @param excludedProperties a List of properties NOT to map; may be ignored by impl
     * @return a List of CompassClassPropertyMapping
     */
    List getCompassClassPropertyMappings(GrailsDomainClass grailsDomainClass, Collection searchableGrailsDomainClasses, Object searchableValue, List excludedProperties) {
        assert searchableValue instanceof Closure
        internalGetCompassClassPropertyMappings(grailsDomainClass, searchableGrailsDomainClasses, (Closure) searchableValue, excludedProperties, null)
        return mappedProperties
    }

    private List internalGetCompassClassPropertyMappings(GrailsDomainClass grailsDomainClass, Collection searchableGrailsDomainClasses, Closure closure, List excludedProperties, List inheritedPropertyMappings) {
        init(grailsDomainClass, searchableGrailsDomainClasses)

        // Build user-defined specific mappings
        closure = (Closure) closure.clone()
        closure.setDelegate(this)
        closure.call()

        // Merge inherited parent mappings?
        if (inheritedPropertyMappings) {
            SearchableGrailsDomainClassCompassMappingUtils.mergePropertyMappings(mappedProperties, inheritedPropertyMappings)
        }

        // Default any remaining mappable properties
        if (only && except) throw new IllegalArgumentException("Both 'only' and 'except' were used in '${mappedClass.getName()}.searchable': provide one or neither but not both")
        def mapValue = only ? [only: only] : except ? [except: except] : true
        this.mappableProperties = SearchableGrailsDomainClassCompassMappingUtils.getMappableProperties(grailsDomainClass, mapValue, searchableGrailsDomainClasses, excludedProperties, getDomainClassPropertyMappingStrategyFactory())

        for (property in mappableProperties) {
            if (!mappedProperties.any { it.propertyName == property.name }) {
                mappedProperties << super.getDefaultPropertyMapping(property, searchableGrailsDomainClasses)
            }
        }
        return mappedProperties
    }

    /**
     * Get the CompassMappingDescription  for the given GrailsDomainClass and "searchable" value
     * @param grailsDomainClass the Grails domain class
     * @param searchableGrailsDomainClasses a collection of searchable GrailsDomainClass instances
     * @param searchableValue the searchable value: Closure in this case
     * @param excludedProperties a List of excluded properties NOT to map; ignored in this impl
     * @return the CompassMappingDescription
     */
    CompassClassMapping getCompassClassMapping(GrailsDomainClass grailsDomainClass, Collection searchableGrailsDomainClasses, Object closure, List excludedProperties) {
        // Get inherited parent mappings
        List parentMappedProperties = getInheritedPropertyMappings(grailsDomainClass, searchableGrailsDomainClasses, excludedProperties);
        // Get property mapping for this class
        mappedProperties = internalGetCompassClassPropertyMappings(grailsDomainClass, searchableGrailsDomainClasses, (Closure) closure, excludedProperties, parentMappedProperties)
        def classMapping = SearchableGrailsDomainClassCompassMappingUtils.buildCompassClassMapping(grailsDomainClass, searchableGrailsDomainClasses, mappedProperties, alias)
        for (constant in constantMetadatas) {
            classMapping.addConstantMetaData(constant.name, constant.attributes, constant.value)
        }
        return classMapping
    }

    Object invokeMethod(String name, Object args) {
        // special cases
        if ("alias".equals(name)) {
            if (args.size() != 1) {
                throw new IllegalArgumentException("'${mappedClass.getName()}.${property.name}' declares an 'alias': there should be just one argument following 'alias' (the alias), but the arguments were ${args}")
            }
            alias = args[0]
            return
        }
        if ("constant".equals(name)) {
            if (args.size() != 1) {
                throw new IllegalArgumentException("'${mappedClass.getName()}.${property.name}' declares an '${name}': there should be just one argument following '${name}', but the arguments were ${args}")
            }
            def constant = args[0]
            if (!constant.name || !(constant.value || constant.values)) {
                log.WARN("${mappedClass.name}.searchable defines a constant but this feature has been reimplented! You probably need to change the syntax from \"foo: 'bar'\" to \"name: 'foo', value: 'bar'\". Ignoring this constant for now since it doesn't provide 'name' + 'value(s)' attributes. See http://grails.org/Searchable+Plugin+-+Mapping#SearchablePlugin-Mapping-constant for details")
                return
            }
            def value = constant.value ? constant.value : constant.values
            if (!(value instanceof List)) {
                value = [value]
            }
            def attributes = new HashMap(constant)
            attributes.remove("name")
            attributes.remove("values")
            attributes.remove("value")
            constantMetadatas << [name: constant.name, attributes: attributes, value: value]
            return
        }

        def property = grailsDomainClass.getProperties().find { it.name == name }
        if (!property) throw new IllegalArgumentException("Unable to find property '${name}' used in '${mappedClass.getName()}.searchable'. The names of method invocations in the closure must match class properties (like GORM's 'constraints' closure)")

        if (!mappableProperties.any { it == property }) {
            throw new IllegalArgumentException("Unable to map '${mappedClass.getName()}.${property.name}'. It does not appear to a suitable 'searchable property' (normally simple types like Strings, Dates, Numbers, etc), 'searchable reference' (normally another domain class) or 'searchable component' (normally another domain class defined as a component, using the 'embedded' declaration). Is it a derived property (a getter method with no equivalent field) defined with 'def'? Try defining it with a more specific return type")
        }

        // Get default mapping
        def defaultMapping = super.getDefaultPropertyMapping(property, searchableGrailsDomainClasses)
        assert defaultMapping, "Expected property ${property} to be mappable using default rules"

        // validate args
        args = args ? args[0] : [:]
        if (defaultMapping.property) {
            def invalidOptions = args.keySet() - SEARCHABLE_PROPERTY_OPTIONS 
            if (invalidOptions) {
                throw new IllegalArgumentException("One or more invalid options were defined in '${mappedClass.getName()}.searchable' for property '${name}'. " +
                    "'${mappedClass.getName()}.${name}' is assumed to be a 'searchable property', meaning you can only define the options allowed " +
                    "for searchable properties. The invalid options are: [${invalidOptions.join(', ')}]. Supported options for 'searchable properties' are [${SEARCHABLE_PROPERTY_OPTIONS.join(', ')}]")
            }
            mappedProperties << CompassClassPropertyMapping.getPropertyInstance(name, args)
            return
        }

        // Arg check
        def defaultTypeReference = defaultMapping.reference
        def validOptions = defaultTypeReference ? SEARCHABLE_REFERENCE_MAPPING_OPTIONS : SEARCHABLE_COMPONENT_MAPPING_OPTIONS
        def invalidOptions = args.keySet() - validOptions
        if (invalidOptions) {
            throw new IllegalArgumentException("One or more invalid options were defined in '${mappedClass.getName()}.searchable' for property '${name}'. " +
                (defaultTypeReference ? "'${mappedClass.getName()}.${name}' can either be implicitly defined as a 'searchable reference', in which case you can use the options for searchable reference directly " +
                    "- eg, 'user(cascade: \"create,delete\")' - or you can define it as a 'searchable reference' or 'searchable component' explicity and use their options within nested maps " +
                    " - eg, 'user(reference: [cascade: \"create,delete\"])' or 'user(component: [maxDepth : 3])'. Supported options are [${SEARCHABLE_REFERENCE_MAPPING_OPTIONS.join(', ')}]. " :
                    "'${mappedClass.getName()}.${name}' is implicity a 'searchable component'. Supported options are [${SEARCHABLE_COMPONENT_OPTIONS.join(', ')}]. ") +
                "The invalid options are: [${invalidOptions.join(', ')}]."
            )
        }

        def referenceOptions
        def componentOptions
        boolean implicitReference = true
        boolean implicitComponent = false
        def component = false
        def reference = false
        if (defaultTypeReference) {
            assert defaultMapping.reference
            if (!args.reference && !args.component) {
                // eg "comments(cascade: true)" - assumed to be reference
                referenceOptions = new HashMap(defaultMapping.attributes)
                referenceOptions.putAll(args)
                reference = true
            }
            if (args.reference) {
                // eg "comments(reference: true)" - explicit reference type or
                // eg "comments(reference: [cascase: 'all'])" - explicit reference options
                referenceOptions = new HashMap(defaultMapping.attributes)
                if (args.reference != true) {
                    referenceOptions.putAll(args.reference)
                }
                implicitReference = false
                reference = true
            }
            if (args.component) {
                // eg "comments(component: true)" - explicit component type or
                // eg "comments(component: [cascase: 'all'])" - explicit component options
                componentOptions = new HashMap(defaultMapping.attributes)
                if (args.component != true) {
                    componentOptions.putAll(args.component)
                }
                component = true
            }
        } else {
            reference = false
            component = false
            implicitComponent = true
            implicitReference = false
            assert defaultMapping.component
            if (!args.reference && !args.component) {
                // eg "comments(cascade: true)" - assumed to be component
                componentOptions = new HashMap(defaultMapping.attributes)
                componentOptions.putAll(args)
                component = true
            }
            if (args.reference != null) {
                if (args.reference != false) {
                    reference = true
                    referenceOptions = new HashMap(defaultMapping.attributes)
                    if (args.reference instanceof Map) {
                        referenceOptions.putAll(args.reference)
                    }
                }
            }
            if (args.component != null) {
                if (args.component != false) {
                    component = true
                    componentOptions = new HashMap(defaultMapping.attributes)
                    if (args.component instanceof Map) {
                        componentOptions.putAll(args.component)
                    }
                }
            }
        }

        if (!reference && !component) {
            return
        }

        // Check for invalid options
        if (component) {
            invalidOptions = componentOptions.keySet() - SEARCHABLE_COMPONENT_OPTIONS
            if (invalidOptions) {
                throw new IllegalArgumentException("One or more invalid options were defined in '${mappedClass.getName()}.searchable' for property '${name}'. " +
                    "'${mappedClass.getName()}.${name}' is ${implicitComponent ? 'implicitly' : 'defined to be'} a 'searchable component', meaning you can only define the options allowed " +
                    "for searchable components. The invalid options are: [${invalidOptions.join(', ')}]. Supported options for 'searchable components' are [${SEARCHABLE_COMPONENT_OPTIONS.join(', ')}]")
            }
            mappedProperties << CompassClassPropertyMapping.getComponentInstance(name, defaultMapping.propertyType, componentOptions)
        }
        if (reference) {
            invalidOptions = referenceOptions.keySet() - SEARCHABLE_REFERENCE_OPTIONS
            if (invalidOptions) {
                throw new IllegalArgumentException("One or more invalid options were defined in '${mappedClass.getName()}.searchable' for property '${name}'. " +
                    "'${mappedClass.getName()}.${name}' is ${implicitReference ? 'implicitly' : 'declared to be'} a 'searchable reference', meaning you can only define the options allowed " +
                    "for searchable references. The invalid options are: [${invalidOptions.join(', ')}]. Supported options for 'searchable references' are [${SEARCHABLE_REFERENCE_OPTIONS.join(', ')}]")
            }
            mappedProperties << CompassClassPropertyMapping.getReferenceInstance(name, defaultMapping.propertyType, referenceOptions)
        }
    }

    /**
     * Does the implementation handle th given "searchable" value type?
     * @param searchableValue a searchable value
     * @return true for Closure for this class
     */
    public boolean handlesSearchableValue(Object searchableValue) {
        searchableValue instanceof Closure
    }

    /**
     * Init
     */
    private init(GrailsDomainClass grailsDomainClass, Collection searchableGrailsDomainClasses) {
        this.grailsDomainClass = grailsDomainClass
        this.mappedClass = grailsDomainClass.clazz
        this.mappableProperties = SearchableGrailsDomainClassCompassMappingUtils.getMappableProperties(grailsDomainClass, true, searchableGrailsDomainClasses, excludedProperties, getDomainClassPropertyMappingStrategyFactory())
        this.searchableGrailsDomainClasses = searchableGrailsDomainClasses
        this.only = null
        this.except = null
        this.mappedProperties = []
        this.alias = null;
        this.constantMetadatas = []
    }
}