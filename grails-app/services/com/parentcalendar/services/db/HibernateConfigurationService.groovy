package com.parentcalendar.services.db

import org.hibernate.cfg.AnnotationConfiguration
import org.hibernate.cfg.Configuration
import org.springframework.stereotype.Component

@Deprecated
@Component
class HibernateConfigurationService {

  def grailsApplication

  public Configuration getHibernateConfiguration() {

    def config = new AnnotationConfiguration()
            .setProperty("hibernate.dialect", grailsApplication.config.db.hibernate.dialect as String)
            .setProperty("hibernate.connection.driver_class", grailsApplication.config.db.hibernate.driverClass as String)
            .setProperty("hibernate.connection.url", grailsApplication.config.db.hibernate.url as String)
            .setProperty("hibernate.connection.username", grailsApplication.config.db.hibernate.username as String)
            .setProperty("hibernate.connection.password", grailsApplication.config.db.hibernate.password as String)
            .setProperty("hibernate.connection.requireSSL", grailsApplication.config.db.hibernate.requireSSL as String)
            //.setProperty("hibernate.connection.pool_size", grailsApplication.config.db.hibernate.poolsize as String)
            //.setProperty("hibernate.c3p0.min_size", grailsApplication.config.db.hibernate.c3p0.minsize as String)
            //.setProperty("hibernate.c3p0.max_size", grailsApplication.config.db.hibernate.c3p0.maxsize as String)
            //.setProperty("hibernate.c3p0.timeout", grailsApplication.config.db.hibernate.c3p0.timeout as String)
            //.setProperty("hibernate.c3p0.max_statements", grailsApplication.config.db.hibernate.c3p0.maxstatements as String)
            //.setProperty("hibernate.c3p0.idle_test_period", grailsApplication.config.db.hibernate.c3p0.testperiod as String)
            .setProperty("hibernate.show_sql", "true")


          /*
        <!-- org.hibernate.HibernateException: No CurrentSessionContext configured! -->
        <property name="hibernate.current_session_context_class">thread</property>

                        */

    grailsApplication.config.domainClasses.each { domainClass ->
        config.addAnnotatedClass(Class.forName(domainClass))
    }

    config
  }
}
