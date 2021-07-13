# Software General Comments

# Nice to have in future versions
-Test coverage
-DB rider integration could be used for unit tests (controller, repository layer)
-Bulk create/delete for OrderEntity, as we have for PhotographerEntity
-OpenAPI/Swagger more integration for API specification
-Basic authentication only supported, could be changed with other type of authentications in the future.
-Open for extensions with dockerization
-Can use mappers lib(model mapper) for toDto and toEntity functionalities
-Can be used lombok to provide builders getter setters etc, but decided not used that now
-Can have email and cell number validation

# Questions/Concerns/Decisions

-R1 - There weren't any info for Photographer and I used content data for photographer details
-R2 - Only update localDate but can be added also modify method which support other data modification in future
-R3 - Decided to design with orderId and photographerId to specify which order should be assigned to which photographer
NOTE: Decided to support one to many relations for OrderEntity -> PhotographerEntity
-R4 - Decided just keep photo URL not taking care for zip, but theoretically it will request with url from somewhere data then check in response that its zipped and then continue the business
-R5 - There were nothing about content. I used the photo url as a content to verify then that, now simply return verified true, after could be there verification business logic
NOTE: Could in future support ROLES (ADMIN, PHOTOGRAPHER, OPERATOR, etc.), and then have will Role OPERATOR, who only can call verify action.
-R6 - now delete the order with related photographer with specified ids, but in the future be just added new order state CANCELED and keep the record in the database with that state and have checking for all actions when need that status is not CANCELED to continue the business logic.
NOTE: Now I provided order id in path, because no more details. As soon as order will be canceled, that will be removed with existing associations.