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
package org.codehaus.groovy.grails.plugins.searchable.compass.search;

import org.apache.commons.collections.MapUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.Compass;
import org.compass.core.lucene.engine.queryparser.CompassQueryParser;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Maurice Nicholson
 */
public class DefaultStringQuerySearchableCompassQueryBuilder extends AbstractSearchableCompassQueryBuilder implements SearchableCompassQueryBuilder {
    private static final String[] ANALYZER_NAMES = new String[] {"analyzer"};
    private static final String[] PARSER_NAMES = new String[] {"parser", "queryParser"};
    private static final String[] DEFAULT_PROPERTY_NAMES = new String[] {"defaultProperty", "defaultSearchProperty"};
    private static final String[] PROPERTIES_NAMES = new String[] {"properties"};
    private static final String[] USE_AND_DEFAULT_OPERATOR_NAMES = new String[] {"andDefaultOperator", "useAndDefaultOperator"};

    public DefaultStringQuerySearchableCompassQueryBuilder(Compass compass) {
        super(compass);
    }

    public CompassQuery buildQuery(GrailsApplication grailsApplication, CompassSession compassSession, Map options, Object query) {
        Assert.notNull(query, "query cannot be null");
        Assert.isInstanceOf(String.class, query, "query must be a String but is [" + query.getClass().getName() + "]");

        String analyzer = (String) getOption(ANALYZER_NAMES, options);
        String parser = (String) getOption(PARSER_NAMES, options);
        String defaultSearchProperty = (String) getOption(DEFAULT_PROPERTY_NAMES, options);
        Collection properties = (Collection) getOption(PROPERTIES_NAMES, options);
        Boolean useAndDefaultOperator = (Boolean) getOption(USE_AND_DEFAULT_OPERATOR_NAMES, options);
        Boolean escape = MapUtils.getBoolean(options, "escape", Boolean.FALSE);

        Assert.isTrue(!(properties != null && defaultSearchProperty != null), "The " + DefaultGroovyMethods.join(DEFAULT_PROPERTY_NAMES, "/") + " and " + DefaultGroovyMethods.join(PROPERTIES_NAMES, "/") + " options cannot be combined");

        String queryString = (String) query;
        if (escape.booleanValue()) {
            queryString = CompassQueryParser.escape(queryString);
        }

        CompassQueryBuilder compassQueryBuilder = compassSession.queryBuilder();
        CompassQueryBuilder.ToCompassQuery stringBuilder;
        if (properties != null && !properties.isEmpty()) {
            stringBuilder = compassQueryBuilder.multiPropertyQueryString(queryString);
            for (Iterator iter = properties.iterator(); iter.hasNext(); ) {
                ((CompassQueryBuilder.CompassMultiPropertyQueryStringBuilder) stringBuilder).add((String) iter.next());
            }
        } else {
            stringBuilder = compassQueryBuilder.queryString(queryString);
        }

        if (analyzer != null) {
            InvokerHelper.invokeMethod(stringBuilder, "setAnalyzer", analyzer);
        }
        if (parser != null) {
            InvokerHelper.invokeMethod(stringBuilder, "setQueryParser", parser);
        }
        if (defaultSearchProperty != null) {
            InvokerHelper.invokeMethod(stringBuilder, "setDefaultSearchProperty", defaultSearchProperty);
        }
        if (useAndDefaultOperator != null && useAndDefaultOperator.booleanValue()) {
            InvokerHelper.invokeMethod(stringBuilder, "useAndDefaultOperator", null);
        }
        return stringBuilder.toQuery();
    }

    private Object getOption(String[] names, Map options) {
        Object value = null;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (options.containsKey(name)) {
                if (value != null) {
                    throw new IllegalArgumentException("More than one equivalent option specified for names: [" + DefaultGroovyMethods.join(names, ", ") + "]");
                }
                value = options.get(name);
            }
        }
        return value;
    }
}
