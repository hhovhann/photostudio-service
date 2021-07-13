# Software Document

## Software Environment

* Spring Boot 2.5.2
* Java 11
+ Maven 3.6.3

## Software Behaviour
System can be designed as a Spring Boot Web Application Rest API and support following endpoints:

```
   * CREATE ORDER WITHOUT DATE AND TIME
       POST /api/order/
       
       {
           firstName: "My First Name",
           lastName : "My First Name",
           email: "my_email@gmail.com",
           phone: "+39123456789",
           order_type: "REAL_ESTATE | FOOD"
       }
    
    * CREATE ORDER WITH DATE AND TIME
       POST /api/order/
       
       {
           firstName: "My First Name",
           lastName : "My First Name",
           email: "my_email@gmail.com",
           phone: "+39123456789",
           order_type: "REAL_ESTATE | FOOD",
           date_and_time: "dd-MM-yyyy HH:mm:ss"
       }
       
     * UPDATE ORDER WITH DATE AND TIME
        PUT /api/order//{order_id}
     
        { 
            date_and_time: "dd-MM-yyyy HH:mm:ss"
        }
     
     *  CANCEL ORDER
        DELETE /api/order/{order_id} 
       
     *  ASSIGN ORDER
        POST /api/order/{order_id}/photographer/{photographer_id}
     
     *  UNASSIGN ORDER
        DELETE /api/order/{order_id}/photographer/{photographer_id}
       
     *  Upload photo from photographer
        POST /api/order/photographer/{photographer_id}
        { 
            photoUrl: "https://etimg.etb2bimg.com/photo/83034773.cms"
        }
   ```



## Software Design and Diagram
Please check the [Photostudio Diagramm](design/photostudio.png)

# Comments Nice To have
Versioning support for rest api
Create and Delete Order can support bulk creation
Test coverage
DB rider integration
Swagger integration
Security Roles support for different players


# Questions
All questions plus the question about photo URL