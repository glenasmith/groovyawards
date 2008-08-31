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
package org.codehaus.groovy.grails.plugins.searchable.compass.config.mapping

import org.springframework.core.JdkVersion

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.plugins.searchable.test.domain.annotated.*
import org.codehaus.groovy.grails.plugins.searchable.compass.config.CompassXmlConfigurationSearchableCompassConfigurator

import org.compass.core.config.*
import org.compass.annotations.config.CompassAnnotationsConfiguration

/**
*
*
* @author Maurice Nicholson
*/
class CompassAnnotationSearchableGrailsDomainClassMappingConfiguratorTests extends GroovyTestCase {
    SearchableGrailsDomainClassMappingConfigurator strategy

    void setUp() {
        assert JdkVersion.isAtLeastJava15()
        strategy = new CompassAnnotationSearchableGrailsDomainClassMappingConfigurator()
    }

    void tearDown() {
        strategy = null
    }

    void testIsMappedByWhenNotAnnotated() {
        // Should not fail, just return
        assert strategy.isMappedBy([
            getClazz: { Object.class } // not important
        ] as GrailsDomainClass) == false

        assert strategy.isMappedBy([
            getClazz: { Other.class } // not important
        ] as GrailsDomainClass) == false
    }

    void testIsMappedByWhenAnnotated() {
        // Should not fail, just return
        assert strategy.isMappedBy([
            getClazz: { AnnotatedSearchable.class }
        ] as GrailsDomainClass) == true
    }

    void testConfigureWithoutCompassXml() {
        def config = new MyAnnotationCompassConfiguration()
        strategy.configureMappings(config, [:], [[getClazz: { AnnotatedSearchable.class }] as GrailsDomainClass])
        assert config.addedClass == AnnotatedSearchable.class
    }

    void testConfigureWithCompassXml() {
        def config = new MyAnnotationCompassConfiguration()
        strategy.configureMappings(config, [(CompassXmlConfigurationSearchableCompassConfigurator.CONFIGURED): true], [[getClazz: { AnnotatedSearchable.class }] as GrailsDomainClass])
        assert config.addedClass == null
    }
}

class MyAnnotationCompassConfiguration extends CompassAnnotationsConfiguration {
    Class addedClass
    CompassConfiguration addClass(Class clazz) {
        addedClass = clazz
        this
    }
}