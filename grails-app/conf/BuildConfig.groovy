grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
grails.project.war.file = "target/${appName}.war"

def hibernateVersion = "3.6.10.3"
def springVersion = "3.2.8.RELEASE"
def tomcatJdbcVersion = "8.0.15"
def jedisVersion = "2.0.0"

grails.project.dependency.resolver = "maven"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
        excludes "org.springframework"
    }
    log "debug" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        mavenRepo "http://iron-io.github.com/maven/repository"
    }

    dependencies {

        compile "org.springframework:spring-orm:${springVersion}"
        //compile "org.hibernate:hibernate-entitymanager:${hibernateVersion}"
        //compile "org.hibernate:hibernate-annotations:${hibernateVersion}"
        compile "org.apache.tomcat:tomcat-jdbc:${tomcatJdbcVersion}"

        compile "redis.clients:jedis:${jedisVersion}"

        runtime "mysql:mysql-connector-java:5.1.6"

        runtime 'javax.xml.bind:jaxb-api:2.2.12'

        test "junit:junit:4.11"
    }

    plugins {
        compile ":hibernate:$hibernateVersion"

        runtime ":jquery:1.7.2"
        runtime ":resources:1.1.6"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:8.0.15"
        // runtime ":database-migration:1.1"
        // compile ":database-session:1.2.1" // Sticky sessions.
        // compile ":webxml:1.4.1"

        //compile (":heroku:1.0.1") {
        //    excludes "database-session"
        //}
        // compile ':cache:1.0.0'
        compile ':cloud-support:1.0.11'


    }
}
