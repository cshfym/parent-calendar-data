package com.parentcalendar.services.db

import org.hibernate.cfg.AnnotationConfiguration
import org.hibernate.cfg.Configuration
import org.springframework.stereotype.Component


@Component
class HibernateConfigurationService {

  def grailsApplication

  // private static final String DOMAIN_CLASS_CONFIG = "grails-app/conf/com.parentcalendar.services.db.DomainClassConfig.groovy"

  public Configuration getHibernateConfiguration() {

    def config = new AnnotationConfiguration()
            .setProperty("hibernate.dialect", grailsApplication.config.db.hibernate.dialect as String)
            .setProperty("hibernate.connection.driver_class", grailsApplication.config.db.hibernate.driverClass as String)
            .setProperty("hibernate.connection.url", grailsApplication.config.db.hibernate.url as String)
            .setProperty("hibernate.connection.username", grailsApplication.config.db.hibernate.username as String)
            .setProperty("hibernate.connection.password", grailsApplication.config.db.hibernate.password as String)
            .setProperty("hibernate.connection.requireSSL", grailsApplication.config.db.hibernate.requireSSL as String)

    // def domains = new ConfigSlurper().parse(new File(DOMAIN_CLASS_CONFIG).toURL())

    grailsApplication.config.domainClasses.each { domainClass ->
        config.addAnnotatedClass(domainClass)
    }

    config
  }
}
