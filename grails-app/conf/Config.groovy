// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

// External configuration
grails.config.locations = [
  "file:/etc/grails/pc-config.groovy"
]

// Hibernate domain classes
domainClasses = [
  com.parentcalendar.domain.User.class
]

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

grails.plugin.databasesession.deleteInvalidSessions = true
grails.plugin.databasesession.enabled = true

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
def appName = "${appName}"
log4j = {
  appenders {
    rollingFile name:"errorLog", maxFileSize:"1MB", maxBackupIndex: 10, file:"/var/log/parent-calendar/${appName}-error.log", 'append':true, threshold:org.apache.log4j.Level.ERROR
    rollingFile name:"warnLog", maxFileSize:"1MB", maxBackupIndex: 10, file:"/var/log/parent-calendar/${appName}-warn.log", 'append':true, threshold:org.apache.log4j.Level.WARN
    rollingFile name:"debugLog", maxFileSize:"10MB", maxBackupIndex: 10, file:"/var/log/parent-calendar/${appName}-debug.log", 'append':true, threshold:org.apache.log4j.Level.INFO
    console name:"stdout", threshold:org.apache.log4j.Level.WARN
  }

  root {
    info "debugLog", "warnLog", "errorLog", "stdout"
  }

  /*
  error  'org.codehaus.groovy.grails.web.servlet',        // controllers
         'org.codehaus.groovy.grails.web.pages',          // GSP
         'org.codehaus.groovy.grails.web.sitemesh',       // layouts
         'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
         'org.codehaus.groovy.grails.web.mapping',        // URL mapping
         'org.codehaus.groovy.grails.commons',            // core / classloading
         'org.codehaus.groovy.grails.plugins',            // plugins
         'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
         'org.springframework',
         'org.hibernate',
         'net.sf.ehcache.hibernate'

  warn 'org.apache.catalina'
  */
}

// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line 
// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside null
                scriptlet = 'none' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
remove this line */
