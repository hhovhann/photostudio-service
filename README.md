# Software Document

## Software Environment

* Spring Boot 2.5.2
* Java 11.0.8
+ Maven 3.6.3

## Software Behaviour
System designed as a Spring Boot Web Application. Provides Rest API with following endpoints:

- Order Endpoints
```
* Create order withoutthe date and time with user admin with role ADMIN
    POST /v1/api/order
       
    {
      "name": "Hayk",
      "surname": "Hovhannisyan",
      "email": "haik.hovhanisyan@gmail.com",
      "title": "title",
      "logisticInfo": "logistic info",
      "cell_number": "+39123456789",
      "photo_type": "REAL_ESTATE"
    }
    
* Create order with the date and time with user admin with role ADMIN
    {
      "name": "Hayk",
      "surname": "Hovhannisyan",
      "email": "haik.hovhanisyan@gmail.com",
      "title": "title",
      "logisticInfo": "logistic info",
      "localDateTime": "2021-07-13T19:02:16.990Z",
      "cell_number": "+39123456789",
      "photo_type": "REAL_ESTATE"
    }
       
* Update order with the date and time with user admin with role ADMIN
   PATCH /v1/api/order/{order_id}

   { 
       date_and_time: "dd-MM-yyyy HH:mm:ss"
   }
  
*  Assign order to the photographer with user admin with role ADMIN
   PATCH /v1/api/order/{order_id}/photographer/{photographer_id}
               
*  Upload photo from photographerEntity with user admin or photographer with roles ADMIN and PHOTOGRAPHER
   PATCH /v1/api/order/file/{order_id}
   BODY Request Param: Upload the zip file
          
*  Verify photo content 
   PATCH /v1/api/order/image/{order_id} with user operator with roles ADMIN and OPERATOR
       
*  Cancel the order with user admin with role ADMIN
   DELETE /v1/api/order/{order_id}/photographer/{photographer_id}
```   

- Photographer Endpoints
```     
*  Add photographers with user photographer with role PHOTOGRAPHER
   POST /v1/api/photographers
    [
      {
        "name": "Hayk",
        "surname": "Hovhannisyan",
        "email": "haik.hovhanisyan@gmail.com",
        "cell_number": "+39123456789",
      }
    ] 
```

## Security Support
There is support for basic authentication now with inmemory `role: ADMIN username:password admin:admin`, `role: OPERATOR username:password operator:operator`, `role: PHOTOGRAPHER username:password photographer:photographer`

## Software Run
Can run application with bach command from project root `./scripts/run.sh`
Can run the application from the IDEA itself

## Software Design and Diagram
Please check the [Photostudio Diagramm](design/photostudio.png)

# Nice To have
- Versioning support for rest api
- Create and Delete Order can support bulk creation
- Test coverage
- DB rider integration for tests
- OpenAPI/Swagger integration for API specification
- Current software support basic authentication, which can be extended in the future versions.