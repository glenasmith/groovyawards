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
package org.codehaus.groovy.grails.plugins.searchable.compass.config.mapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.plugins.searchable.compass.config.mapping.SearchableGrailsDomainClassMappingConfigurator;
import org.codehaus.groovy.grails.plugins.searchable.compass.config.mapping.CompassMappingXmlSearchableGrailsDomainClassMappingConfigurator;
import org.codehaus.groovy.grails.plugins.searchable.compass.config.mapping.SearchableClassPropertySearchableGrailsDomainClassMappingConfigurator;
import org.codehaus.groovy.grails.plugins.searchable.compass.mapping.SearchableCompassClassMappingXmlBuilder;
import org.codehaus.groovy.grails.plugins.searchable.compass.mapping.CompositeSearchableGrailsDomainClassCompassClassMapper;
import org.codehaus.groovy.grails.plugins.searchable.compass.mapping.SearchableGrailsDomainClassCompassClassMapperFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * @author Maurice Nicholson
 */
public abstract class SearchableGrailsDomainClassMappingConfiguratorFactory {
    private static final Log LOG = LogFactory.getLog(SearchableGrailsDomainClassMappingConfiguratorFactory.class);

    public static SearchableGrailsDomainClassMappingConfigurator[] getSearchableGrailsDomainClassMappingConfigurators(ResourceLoader resourceLoader, Map defaultFormats, List defaultExcludedProperties, SearchableCompassClassMappingXmlBuilder compassClassMappingXmlBuilder) {
        List configurators = new ArrayList();
        configurators.add(getXmlMappingConfigurator(resourceLoader));
        configurators.add(getSearchableClassPropertyMappingConfigurator(defaultFormats, defaultExcludedProperties, compassClassMappingXmlBuilder));
        SearchableGrailsDomainClassMappingConfigurator annotationsMappingConfigurator = getAnnotationsMappingConfigurator();
        if (annotationsMappingConfigurator != null) {
            configurators.add(annotationsMappingConfigurator);
        }

        return (SearchableGrailsDomainClassMappingConfigurator[]) configurators.toArray(new SearchableGrailsDomainClassMappingConfigurator[configurators.size()]);
    }

    public static SearchableGrailsDomainClassMappingConfigurator getAnnotationsMappingConfigurator() {
        try {
            return (SearchableGrailsDomainClassMappingConfigurator) ClassUtils.forName("org.codehaus.groovy.grails.plugins.searchable.compass.config.mapping.CompassAnnotationSearchableGrailsDomainClassMappingConfigurator").newInstance();
        } catch (Exception ex) {
            LOG.warn("CompassAnnotationSearchableGrailsDomainClassMappingConfigurator is unavailable");
        }
        return null;
    }

    public static CompassMappingXmlSearchableGrailsDomainClassMappingConfigurator getXmlMappingConfigurator(ResourceLoader resourceLoader) {
        CompassMappingXmlSearchableGrailsDomainClassMappingConfigurator mappingXmlMappingStrategy = new CompassMappingXmlSearchableGrailsDomainClassMappingConfigurator();
        mappingXmlMappingStrategy.setResourceLoader(resourceLoader);
        return mappingXmlMappingStrategy;
    }

    public static SearchableClassPropertySearchableGrailsDomainClassMappingConfigurator getSearchableClassPropertyMappingConfigurator(Map defaultFormats, List defaultExcludedProperties, SearchableCompassClassMappingXmlBuilder compassClassMappingXmlBuilder) {
        CompositeSearchableGrailsDomainClassCompassClassMapper classMapper = SearchableGrailsDomainClassCompassClassMapperFactory.getDefaultSearchableGrailsDomainClassCompassClassMapper(defaultExcludedProperties, defaultFormats);

        SearchableClassPropertySearchableGrailsDomainClassMappingConfigurator searchableClassPropertyMappingStrategy = new SearchableClassPropertySearchableGrailsDomainClassMappingConfigurator();
        searchableClassPropertyMappingStrategy.setCompassClassMappingXmlBuilder(compassClassMappingXmlBuilder);
        searchableClassPropertyMappingStrategy.setMappingDescriptionProviderManager(classMapper);
        return searchableClassPropertyMappingStrategy;
    }

}
