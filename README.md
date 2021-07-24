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
    POST /v1/api/orders
       
    [
        {
          "name": "Hayk1",
          "surname": "Hovhannisyan1",
          "email": "haik.hovhanisyan1@gmail.com",
          "title": "title1",
          "logisticInfo": "logistic info1",
          "cell_number": "+39123456789",
          "photo_type": "REAL_ESTATE"
        },
        {
          "name": "Hayk2",
          "surname": "Hovhannisyan2",
          "email": "haik.hovhanisyan2@gmail.com",
          "title": "title2",
          "logisticInfo": "logistic info 2",
          "cell_number": "+39987654321",
          "photo_type": "EVENTS"
        }
    ]
    
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
   PATCH /v1/api/orders/{order_id}

   { 
       date_and_time: "dd-MM-yyyy HH:mm:ss"
   }
  
*  Assign order to the photographer with user admin with role ADMIN
   PATCH /v1/api/orders/{order_id}/photographers/{photographer_id}
               
*  Upload photo from photographerEntity with user admin or photographer with roles ADMIN and PHOTOGRAPHER
   PATCH /v1/api/orders/file/{order_id}
   BODY Request Param: Upload the zip file
          
*  Verify photo content 
   PATCH /v1/api/orders/image/{order_id} with user operator with roles ADMIN and OPERATOR
       
*  Cancel the order with user admin with role ADMIN
   DELETE /v1/api/orders/{order_ids}
   DELETE /v1/api/orders/1,2,3,4,5
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
There is support for basic authentication now with inmemory

- `role: ADMIN username:password admin:admin`
- `role: OPERATOR username:password operator:operator`
- `role: PHOTOGRAPHER username:password photographer:photographer`

## Software Run
- Run application with bach command from project root `./scripts/run.sh`
- Run the application from the IDEA itself

## Software Design and Diagram
Please check the [Photostudio Diagramm](design/photostudio.png)

# Nice To have
- High test coverage
- Current software support basic authentication, which can be extended in the future versions.