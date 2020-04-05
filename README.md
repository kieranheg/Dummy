**PROJECT REQUIREMENTS**
1. Java 1.8
1. Lombok (Intellij Plugin)

**Build and Test**

* Clean, build and run unit & integration tests

    ``` ./gradlew clean build itest ```

* Run app locally
    
    ``` SPRING_PROFILES_ACTIVE=local ./gradlew bootRun ```
    
* Use postman to hit endpoint with request parameters 
    * http://localhost:8080/order/{order_id}
