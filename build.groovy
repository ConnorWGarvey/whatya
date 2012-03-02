#! /usr/bin/env groovy

ant = new AntBuilder()
webinf = 'war/WEB-INF'
lib = "${webinf}/lib"
gaeHome = System.getenv('APPENGINE_HOME')

void copyAppEngineJars() {
  ant.delete(dir:lib, failonerror:false) {
    include(name:'appengine-api-*.jar')
  }
  ant.copy(todir:lib) {
    fileset(dir:"${gaeHome}/lib/user") {
      include(name:'appengine-api-*.jar')
    }
  }
}

def ensureAppEngineIsInstalled() {
  if(!gaeHome) {
    println 'Please set the "APPENGINE_HOME" environment variable, pointing to your GAE SDK.'
    System.exit(1)
  }
}

ant.sequential {
  mkdir dir:"${webinf}/classes"
  ensureAppEngineIsInstalled()
  copyAppEngineJars()

  taskdef name: "groovyc", classname: "org.codehaus.groovy.ant.Groovyc"
  
  groovyc srcdir: "src", destdir: "${webinf}/classes", {
    classpath {
      fileset dir: "${webinf}/lib", {
        include name: "*.jar"
      }
      fileset dir: "${gaeHome}/lib/", {
        include name: "**/*.jar"
      }
      pathelement path: "${webinf}/classes"
    }
    javac source: "1.5", target: "1.5", debug: "on"
  }
}

