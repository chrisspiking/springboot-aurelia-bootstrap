# Spring Boot Aurelia Skeleton

This project is to be used as a skeleton for those that wish to create a Spring Boot application utilising Spring Security
and have the frontend implemented using Aurelia framework (www.aurelia.io).

Currently this skeleton isn't set up for things coming later like Aurelia bundling (when I get annoyed at page load times) 
and other things like SCSS, simple user registration/management/etc. Anything I can think of really to build this out to be a 
reusable starting block for people.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You will need:

* Gradle (if you don't want to use the wrapper in the project)
* NPM (7.6.0)
* TypeScript (2.1.x)
* JSPM (0.16.52) installed using NPM

```
Give examples
```

### Installing

A step by step series of examples that tell you have to get a development env running

Install Pre-Requisites
```
OSX: 
    brew install npm
    npm install -g jspm
    npm install -g typings
```

Clone the Repo

```
https://github.com/chrisspiking/springboot-aurelia-bootstrap.git
```

Build Java and Install JSPM packages and TypeScript Typings

```
From the git root dir:

    ./gradlew compileJava
    
    cd src/main/resources/public
    jspm install
    typings install
```

Run the Application from the git root dir

```
    ./gradlew bootRun
```

## Running the tests

```
    ./gradlew test
```

## Built With

* [Gradle](https://gradle.org/)
* [NPM](https://www.npmjs.com/)
* [JSPM](https://jspm.io/)
* [TypeScript](https://www.typescriptlang.org/)
* [Aurelia](https://aurelia.io/)

## Authors

* **Chris Spiking** - *Initial work* - [Chris Spiking](https://github.com/chrisspiking)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
