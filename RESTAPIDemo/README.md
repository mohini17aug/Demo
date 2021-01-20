# This project perform HTTP operation POST,GET and DELETE.
1. Run test.java.demo.Demo.java
2. Log file will be created under test.resources with name log.log


# Approach:
Using input File, Creating an object of RESTTestDefinition class which consist all the required inputs to perform any http method. i.e. endPoint, request headers, request body if required, url parameters if required, expected response code and expected response body.

Using this RESTTestDefinition obj, Creating an object of REST Action class, which is responsible for firing http method i.e. GET, POST or DELETE. After each http method, RESTTestDefinition object is updated with actual response code and actual response body. 

Again an object of RESTValidation class has been created using RESTTestDefinition Object which now contains actual response code, actual response body, expected response code and expected response body. Using these, we can validate response status and response body.

