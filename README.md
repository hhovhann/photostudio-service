# Software Document

## Software Environment

* Spring Boot 2.5.2
* Java 11.0.8
+ Maven 3.6.3

## Software Behaviour
System can be designed as a Spring Boot Web Application Rest API and support following endpoints:

```
   * CREATE ORDER WITHOUT DATE AND TIME
       POST /api/orderEntity/
       
       {
           firstName: "My First Name",
           lastName : "My First Name",
           email: "my_email@gmail.com",
           phone: "+39123456789",
           order_type: "REAL_ESTATE | FOOD"
       }
    
    * CREATE ORDER WITH DATE AND TIME
       POST /api/orderEntity/
       
       {
           firstName: "My First Name",
           lastName : "My First Name",
           email: "my_email@gmail.com",
           phone: "+39123456789",
           order_type: "REAL_ESTATE | FOOD",
           date_and_time: "dd-MM-yyyy HH:mm:ss"
       }
       
     * UPDATE ORDER WITH DATE AND TIME
        PUT /api/orderEntity//{order_id}
     
        { 
            date_and_time: "dd-MM-yyyy HH:mm:ss"
        }
     
     *  CANCEL ORDER
        DELETE /api/orderEntity/{order_id} 
       
     *  ASSIGN ORDER
        POST /api/orderEntity/{order_id}/photographerEntity/{photographer_id}
     
     *  UNASSIGN ORDER
        DELETE /api/orderEntity/{order_id}/photographerEntity/{photographer_id}
       
     *  Upload photo from photographerEntity
        POST /api/orderEntity/photographerEntity/{photographer_id}
        { 
            photoUrl: "https://etimg.etb2bimg.com/photo/83034773.cms"
        }
   ```

## Security Support

## Software Design and Diagram
Please check the [Photostudio Diagramm](design/photostudio.png)

# Nice To have
-Versioning support for rest api
-Create and Delete Order can support bulk creation
-Test coverage
-DB rider integration for tests
-OpenAPI/Swagger integration for API specification
-Current software support basic authentication with username:passowrd {admin:admin} which can be extended in the future versions.



# Questions
All questions asked by email + the questions about photo URL, zip