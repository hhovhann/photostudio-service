# Software General Comments

# Nice To have
-Create and Delete Order can support bulk creation
-Test coverage
-OpenAPI/Swagger more integration for API specification
-Basic authentication only supported, could be changed with other type of authentications in the future.
-DB rider integration for tests
-Open for extensions with dockerization
-Can use mappers for toDto and toEntity functionalities
-Can have email and cell number validation

# Questions/Concerns
-R1 - There weren't any info for Photographer and I used content data for photographer details
-R2 - Only update localDate but can be added also modify method which support other data modification in future
-R3 - Decided to design with orderId and photographerId to specify which order should be assigned to which photographer
NOTE: Decided to support one to many relations for OrderEntity -> PhotographerEntity
-R4 - Decided just keep photo URL not taking care for zip, but theoretically it will request with url from somewhere data then check in response that its zipped and then continue the business
-R5 - There were nothing about content. I used the photo url as a content to verify then that, now simply return verified true, after could be there verification business logic
NOTE: Could in future support ROLES (ADMIN, PHOTOGRAPHER, OPERATOR, etc.), and then have will Role OPERATOR, who only can call verify action.
-R6 - now delete the order with related photographer with specified ids, but in the future be just added new order state CANCELED and keep the record in the database with that state and have checking for all actions when need that status is not CANCELED to continue the business logic.
NOTE: Now I provided order id in path and photographer id in body, Because can not sure what support we should expecting.
We can try to delete the order which have a associated photographer, which mean we need to remove photographer before.
I simply checking now if order contains or not photographer in case if photographer exist