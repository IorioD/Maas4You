# Maas4You
 JAVA application to show a possible implementation of a management system for smart mobility following MaaS approach.
The application is a Client-Server witn an MVC for the architecture and a DTO for data transfer.

The following Use Case diagram shows users and implemented iteractions.

![Alt text](src/main/resources/templates/Use_Case.jpg "UC diagram")

This application uses the following technologies:
1. JAVA SpringBoot Framework
2. Keycloak as IAM management system
3. HashiCorp Vault as secure credential storage system
4. NginX as secure connection filtering and load balancer
5. OpenSSL as self certificate issuer
6. Docker

The following Deployment diagram shows how the application is deployed with docker

![Alt text](src/main/resources/templates/Deployment_Diagram.jpg "Deployment diagram")

After the docker-compose has been completed, it is possible to execute the command `docker-compose up` to create the containers as follows:
1. All containers start except `maas4you` and `maas4you_proxy` because the application depends on the secret contained in Vault and the proxy depends on the application itself.
2. Visit the address https://localhost:9443/vault to unseal the vault with the three keys fragment and the token.
3. The `maas4you` and `maas4you_proxy` containers can be started correctly.
4. If it’s the FIRST START, visit https://localhost:8443/auth to configure the application's Keycloak realm.
5. Visit https://localhost:8443/maas4you to access the application.
6. To visit the MailHog page and interact with its UI, visit http://localhost:8025/

### 1. Application index and login (via keycloak) pages:

![Alt text](src/main/resources/templates/01_Index.png "Index")

![Alt text](src/main/resources/templates/02_Login.png "Login")

### 2. Terms and conditions are displayed after login (with opt-in and out choices):

![Alt text](src/main/resources/templates/03_Terms.png "Terms")

### 3. Main page with interactive map:

![Alt text](src/main/resources/templates/Home.png "Home")

### 4. User Trip list with add, delete and edit functions:

![Alt text](src/main/resources/templates/04_Trips.png "User Trips")

![Alt text](src/main/resources/templates/05_AddTrip.png "User Add Trip")

![Alt text](src/main/resources/templates/05_EditTrip.png "User Edit Trip")

The cost of the trip is automatically evaluated by the app once the user provides the mean of transport that intends to use.

### 5. The admin can have a full overview of the trips planned:

![Alt text](src/main/resources/templates/06_AdminSummary.png "Admin Summary")
