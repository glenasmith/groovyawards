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
package org.codehaus.groovy.grails.plugins.searchable.compass.mapping;

import org.springframework.util.Assert;
import org.compass.core.Compass;
import org.compass.core.mapping.CompassMapping;
import org.compass.core.mapping.AliasMapping;
import org.compass.core.mapping.osem.ClassMapping;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.util.ClassUtils;
import org.compass.core.util.FieldInvoker;
import org.compass.core.spi.InternalCompass;
import org.codehaus.groovy.grails.plugins.searchable.util.GrailsDomainClassUtils;
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * @author Maurice Nicholson
 */
public class CompassMappingUtils {
    private static final Log LOG = LogFactory.getLog(CompassMappingUtils.class);

    /**
     * Provide a default alias for the given Class. This is the Class's short-name
     *
     * @param clazz the class
     * @return the Compass alias
     */
    public static String getDefaultAlias(Class clazz) {
        Assert.notNull(clazz, "clazz cannot be null");
        return ClassUtils.getShortName(clazz);
    }

    /**
     * Get the alias used to map the class in Compass
     * @param compass Compass
     * @param clazz the class whose alias to look up
     * @return the alias
     */
    public static String getMappingAlias(Compass compass, Class clazz) {
        return ((InternalCompass) compass).getMapping().findRootMappingByClass(clazz).getAlias();
    }

    /**
     * Get the mapping aliases for the given user-defined domain classes any
     * @param compass Compass instance
     * @param clazzes the user-defined domain classes
     * @return the Compass aliases for the hierarchy
     */
    public static String[] getMappingAliases(Compass compass, Collection clazzes) {
        Set aliases = new HashSet();
        for (Iterator iter = clazzes.iterator(); iter.hasNext(); ) {
            Class clazz = (Class) iter.next();
            aliases.add(getMappingAlias(compass, clazz));
        }
        return (String[]) aliases.toArray(new String[aliases.size()]);
    }

    /**
     * Resolve aliases between mappings
     * Note this method is destructive in the sense that it modifies the passed in mappings
     */
    public static void resolveAliases(List classMappings, Collection grailsDomainClasses, CompassConfiguration compassConfiguration) {
        // resolve aliases, faking plugin class mappings for those classes already mapped by annotations/xml
        List tempClassMappings = new ArrayList(classMappings);
        try {
            CompassMapping mapping = (CompassMapping) new FieldInvoker(CompassConfiguration.class, "mapping").prepare().get(compassConfiguration);
            AliasMapping[] mappings = mapping.getMappings();
            for (int i = 0; i < mappings.length; i++) {
                AliasMapping am = mappings[i];
                if (am instanceof ClassMapping) {
                    ClassMapping cm = (ClassMapping) am;
                    CompassClassMapping dummyMapping = new CompassClassMapping();
                    dummyMapping.setAlias(cm.getAlias());
                    dummyMapping.setMappedClass(cm.getClazz());
                    tempClassMappings.add(dummyMapping);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to get Compass mapping: " + e);
        }

        resolveAliases(tempClassMappings, grailsDomainClasses);
    }

    /**
     * Resolve aliases between mappings
     * Note this method is destructive in the sense that it modifies the passed in mappings
     */
    public static void resolveAliases(List classMappings, Collection grailsDomainClasses) {
        // set defaults for those classes without explicit aliases and collect aliases
        Map mappingByClass = new HashMap();
        Map mappingsByAlias = new HashMap();
        for (Iterator iter = classMappings.iterator(); iter.hasNext(); ) {
            CompassClassMapping classMapping = (CompassClassMapping) iter.next();
            if (classMapping.getAlias() == null) {
                classMapping.setAlias(getDefaultAlias(classMapping.getMappedClass()));
            }
            mappingByClass.put(classMapping.getMappedClass(), classMapping);
            List mappings = (List) mappingsByAlias.get(classMapping.getAlias());
            if (mappings == null) {
                mappings = new ArrayList();
                mappingsByAlias.put(classMapping.getAlias(), mappings);
            }
            mappings.add(classMapping);
        }

        // override duplicate inherited aliases
        for (Iterator iter = mappingsByAlias.keySet().iterator(); iter.hasNext(); ) {
            List mappings = (List) mappingsByAlias.get(iter.next());
            if (mappings.size() == 1) {
                continue;
            }
            CompassClassMapping parentMapping = null;
            for (Iterator miter = mappings.iterator(); miter.hasNext(); ) {
                CompassClassMapping classMapping = (CompassClassMapping) miter.next();
                if (classMapping.getMappedClassSuperClass() == null) {
                    parentMapping = classMapping;
                    break;
                }
            }
            mappings.remove(parentMapping);
            for (Iterator miter = mappings.iterator(); miter.hasNext(); ) {
                CompassClassMapping classMapping = (CompassClassMapping) miter.next();
                LOG.debug("Overriding duplicated alias [" + classMapping.getAlias() + "] for class [" + classMapping.getMappedClass().getName() + "] with default alias. (Aliases must be unique - maybe this was inherited from a superclass?)");
                classMapping.setAlias(getDefaultAlias(classMapping.getMappedClass()));
            }
        }

        // resolve property ref aliases
        for (Iterator iter = classMappings.iterator(); iter.hasNext(); ) {
            CompassClassMapping classMapping = (CompassClassMapping) iter.next();
            Class mappedClass = classMapping.getMappedClass();
            for (Iterator piter = classMapping.getPropertyMappings().iterator(); piter.hasNext(); ) {
                CompassClassPropertyMapping propertyMapping = (CompassClassPropertyMapping) piter.next();
                if ((propertyMapping.isComponent() || propertyMapping.isReference()) && !propertyMapping.hasAttribute("refAlias")) {
                    Set aliases = new HashSet();
                    Class clazz = propertyMapping.getPropertyType();
                    aliases.add(((CompassClassMapping) mappingByClass.get(clazz)).getAlias());
                    GrailsDomainClassProperty domainClassProperty = GrailsDomainClassUtils.getGrailsDomainClassProperty(grailsDomainClasses, mappedClass, propertyMapping.getPropertyName());
                    Collection clazzes = GrailsDomainClassUtils.getClazzes(domainClassProperty.getReferencedDomainClass().getSubClasses());
                    for (Iterator citer = clazzes.iterator(); citer.hasNext(); ) {
                        CompassClassMapping mapping = (CompassClassMapping) mappingByClass.get(citer.next());
                        if (mapping != null) {
                            aliases.add(mapping.getAlias());
                        }
                    }
                    propertyMapping.setAttrbute("refAlias", DefaultGroovyMethods.join(aliases, ", "));
                }
            }
        }

        // resolve extend aliases
        for (Iterator iter = classMappings.iterator(); iter.hasNext(); ) {
            CompassClassMapping classMapping = (CompassClassMapping) iter.next();
            Class mappedClassSuperClass = classMapping.getMappedClassSuperClass();
            if (mappedClassSuperClass != null && classMapping.getExtend() == null) {
                CompassClassMapping mapping = (CompassClassMapping) mappingByClass.get(mappedClassSuperClass);
                classMapping.setExtend(mapping.getAlias());
            }
        }
    }

    /**
     * Sets the sub-index name to be the same as the short-class-name for each CompassClassMapping,
     * if not already defined
     * @param classMappings a List of CompassClassMapping
     */
    public static void resolveSubIndexes(List classMappings) {
        for (Iterator iter = classMappings.iterator(); iter.hasNext(); ) {
            CompassClassMapping classMapping = (CompassClassMapping) iter.next();
            Assert.notNull(classMapping.getMappedClass(), "Expected mappedClass to be defined for CompassClassMapping [" + classMapping + "] but was null");
            if (classMapping.getSubIndex() == null) {
                String subIndex = ClassUtils.getShortName(classMapping.getMappedClass()).toLowerCase();
                classMapping.setSubIndex(subIndex);
            }
        }
    }
}
