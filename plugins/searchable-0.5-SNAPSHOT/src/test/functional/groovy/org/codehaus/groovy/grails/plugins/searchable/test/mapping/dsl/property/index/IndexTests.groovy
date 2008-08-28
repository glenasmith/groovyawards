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
package org.codehaus.groovy.grails.plugins.searchable.test.mapping.dsl.property.index

import org.codehaus.groovy.grails.plugins.searchable.test.SearchableFunctionalTestCase

/**
 * @author Maurice Nicholson
 */
class IndexTests extends SearchableFunctionalTestCase {

    public getDomainClasses() {
        return [No, Tokenized, UnTokenized]
    }

    void testNo() {
        new No(id: 1l, value: "this is the value").index()
        assert searchableService.searchTop("value") == null
    }

    void testTokenized() {
        new Tokenized(id: 1l, value: "this is the value").index()
        assert searchableService.searchTop("value")
    }

    void testUnTokenized() {
        new UnTokenized(id: 1l, value: "this is the value").index()
        assert searchableService.searchTop("value") == null
        assert searchableService.searchTop {
            term('value', "this is the value") // when index = un_tokenized, the value(s) are stored verbatim as a single term
        }
    }
}
