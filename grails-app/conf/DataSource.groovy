dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
    //driverClassName = "com.p6spy.engine.spy.P6SpyDriver" // use this driver to enable p6spy logging
	username = "sa"
	password = ""
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
            driverClassName = "org.postgresql.Driver"
            //driverClassName = "com.p6spy.engine.spy.P6SpyDriver"

            url = "jdbc:postgresql://localhost/groovyawards"
            username = "glen"
	        password = "password"
            // url = "jdbc:hsqldb:file:devDB;shutdown=true"
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:file:prodDb;shutdown=true"
		}
	}
}
