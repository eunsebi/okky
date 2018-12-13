grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.8
grails.project.source.level = 1.8
grails.project.war.file = "target/ROOT.war"


grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
//    test: [maxMemory: 768, minMemory: 64, debug: true, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
//    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    test: false,
    run: [maxMemory: 768, minMemory: 64, debug: false, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://repo.grails.org/grails/core"
        mavenRepo "http://repo.grails.org/grails/plugins"

        mavenRepo "https://raw.github.com/fernandezpablo85/scribe-java/mvn-repo"
        mavenRepo "http://repo.spring.io/milestone/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.29'
        // runtime 'org.postgresql:postgresql:9.3-1101-jdbc41'

        compile "org.springframework:spring-orm:$springVersion"
        compile "com.googlecode.owasp-java-html-sanitizer:owasp-java-html-sanitizer:20180219.1"
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.70"
	    //build "org.grails.plugins:tomcat:8.0.50"

        // plugins for the compile step
        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.8'
        compile ":asset-pipeline:2.5.7"
        compile ":mysql-connectorj:5.1.22.1"
        compile ":markdown:1.1.1"
        //compile ":recaptcha:1.0.0"
        compile ":pretty-time:2.1.3.Final-1.0.1"
        compile ":cookie:0.51"
        compile ":quartz:1.0.2"

        compile ":cache-headers:1.1.7"

        compile ":spring-security-core:2.0-RC4"
        compile ":spring-security-oauth:2.1.0-RC4"
        //compile ":spring-security-oauth-facebook:0.2"
        //compile ":spring-security-oauth-google:0.3.1"

        compile "org.grails.plugins:spring-mobile:1.1.3"

        compile ":random:0.2"

        compile ":mail:1.0.7"

        // plugins needed at runtime but not for compilation
        runtime ":hibernate4:4.3.10" // or ":hibernate:3.6.10.15"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"
        compile ":twitter-bootstrap:3.3.2.1"

        compile ':force-ssl:1.0.0'

        // Uncomment these to enable additional asset-pipeline capabilities
        //compile ":sass-asset-pipeline:1.7.4"
        //compile ":less-asset-pipeline:1.7.0"
        //compile ":coffee-asset-pipeline:1.7.0"
        //compile ":handlebars-asset-pipeline:1.3.0.3"
    }
}
