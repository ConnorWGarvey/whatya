WhatYammer
==========

A Gaelyk application (Groovy for Google App Engine) that compares popularity of terms in Yammer

Configuration
-------------

Yammer OAuth requires application keys. Go to the [Yammer Developer Center](http://developer.yammer.com) and get an API key. Then, create the file `war/WEB-INF/groovy/AuthTokens.groovy` with static string properties `CONSUMER_KEY` and `CONSUMER_SECRET`.

Build Instructions
------------------

WhatYammer is built with [Gradle](http://www.gradle.org). Run `./gradlew tasks` for a list of tasks.
