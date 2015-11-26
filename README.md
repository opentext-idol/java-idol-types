# java-idol-types

Java Client for DOM parsing of ACI responses in accordance with XML schemas.
The parsing method used is JAXB.
Note that while this is performant, it should not be used where responses are allowed to be very large.

Information can be found on the project homepage [here](http://hpautonomy.github.io/java-idol-types)

[![Build Status](https://travis-ci.org/hpautonomy/java-idol-types.svg?branch=master)](https://travis-ci.org/hpautonomy/java-idol-types)

## Usage

java-idol-types is available from the central Maven repository.

    <dependency>
        <groupId>com.hp.autonomy.frontend</groupId>
        <artifactId>idol-types</artifactId>
        <version>0.1.0-SNAPSHOT</version>
    </dependency>

## License
Copyright 2015 Hewlett-Packard Development Company, L.P.

Licensed under the MIT License (the "License"); you may not use this project except in compliance with the License.
