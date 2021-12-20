# Docomo code Excercise
## Context
Cucumber and Rest-Assured maven based automation framework to test an application that is a payment gateway which allows merchants to charge their users with a single integration.The framework from start is expecting the gate way appliaction to be running on the following port http://localhost:8088

## Requirements
1. JAVA 8
2. MAVEN
3. GIT

## Test cases been tested
1.API should expose the following endpoint POST /operations/{id}/refund

2.The "id" should be a valid uuid v4 (ex. d1e90d8f-11f7-41e0-92ff-235e2a85ab3b) to get 201 OK

3.With an invalid uuid you should get a 400

4.Only one concurrent refund operation (on the same transaction id) can be performed, so the resource should be blocked if another refund is being processed. Failing concurrencies should get a 423

## Project structure

src

--- test

       ----java
       
            *This is here where the Test runner class is located called CucucmberTestRunner.java used to run all the feature files*
            
         `----stepDefinitions
         
              *A Step Definition is a small piece of code with a pattern attached to it or in other words a Step Definition is a java method in a class with an annotation above it.*  
              
        ----resources
        
            *This is where the extent reporort property and extendt config file is located to customize the reporting.*
        
         ----features
                 *This is where the cucumber feature files are located same place where test data is been driven

## Run tests
1.Run this command ->(mvn clean install) from the terminal in this directory /DocomoDigital

2.The following Test can be ran by running CucumberTestRunner.java located in src->test->java which will run all the test and produce a report

3.Else the test can be ran by running the individul feature files located in src->test->resources -> features

4.Run maven command form terminal (`mvn clean install test -Dtest=**/**.java`) in this directory /DocomoDigital where POM is located

## Test execution Report

1.The report that is generated is a extent type report is located under -> target/cucumber-reports/report/CucumberExtent-Reports(Timestaped)/test-output/Spark/ExtentSpark.html

2.This report can be opened with any browser choice eg.chrome
