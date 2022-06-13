# custom-microservices-project

Simple microservices POC based on the Spring Cloud and Java 18, to try microservices architecture patterns and technologies.

Project structure:
- **apigw** - _spring cloud gateway_
- **clients** - _feign clients common library, for communication between services_
- **config-server** - _spring cloud config server, for storing services properties in the one place_
- **consumer** - _consumer service_
- **eureka-server** - _discovery service, for simple communication between services_
- **fraud** - _service for checking if the consumer is fraudster_

To run docker containers, just run from the root:
`docker compose up`

## HOWTO

### Docker

- add a docker for postgresql - https://github.com/dtimchenko/custom-microservices-project/commit/5f17c642ec2277707e532a94a03a5df968403964
- add a docker-compose for all microservices - https://github.com/dtimchenko/custom-microservices-project/commit/39a93cc67dd5630aedd84afc4ed5b0713a56ef27

#### How to create a docker image
- add a dockerfile to the project
- `docker build --tag=<repository-name> .` - to build an image
- `docker push <repository-name>` - push an image to the dockerhub

### UPDATE SERVICE PROPERTIES DYNAMICALLY

To update service properties dynamically (without service redeploy) you need:
- update properties on config-server repo (https://github.com/dtimchenko/temp-repo.git/config-server/src/main/resources/applications/)
- POST http://localhost:8010/actuator/busrefresh (where localhost:8010 is a config-server address)

### GENERAL
- Init maven parent pom - https://github.com/dtimchenko/custom-microservices-project/commit/b5d15058b41bde97fd809fb7e39e3f48dd5deb43
- Add a docker-compose for postgresql - https://github.com/dtimchenko/custom-microservices-project/commit/5f17c642ec2277707e532a94a03a5df968403964

### EUREKA
- Add Eureka Server - https://github.com/dtimchenko/custom-microservices-project/commit/e615453423bcb8b4326f0dc93fa4413fd5d8e549 
- Add Eureka Clients - https://github.com/dtimchenko/custom-microservices-project/commit/27f3273c53bdba149c7e5dc040c8cf382db17575
- Add Eureka Clients RestTemplate Communication - https://github.com/dtimchenko/custom-microservices-project/commit/e6b61fcbfa9dd67ea1e260f50191cdcaaca9626a
- Add Eureka Clients Feign Communication - https://github.com/dtimchenko/custom-microservices-project/commit/4c894dcf2d9b13c089df0ebe9cadee3b24f4ff85

### CIRCUIT BREAKER
- Add hystrix Circuit Breaker - https://github.com/dtimchenko/custom-microservices-project/commit/8a78055c87140edf80f39945b5e4766e6e84be42
- Add resilience4j Circuit Breaker - https://github.com/dtimchenko/custom-microservices-project/commit/3f266435014ba94c79af166f20964ea5404ec2bc
- Add resilience4j Retry - https://github.com/dtimchenko/custom-microservices-project/commit/47fb688aec682ecf350590fa0c6e4ceffc7eafbb

### SPRING WEB
- Add application/xml API support - https://github.com/dtimchenko/custom-microservices-project/commit/4c894dcf2d9b13c089df0ebe9cadee3b24f4ff85
- Add Request Validation - https://github.com/dtimchenko/custom-microservices-project/commit/301886c3d5863319f1478faa2dc19edea89c4ea4

### GATEWAY
- Add API Gateway - https://github.com/dtimchenko/custom-microservices-project/commit/dd4b2bf1b945e502a396321fd94be9c8735df816
- Add Manual Route for API Gateway - https://github.com/dtimchenko/custom-microservices-project/commit/f2c1aa71e92c0cf7c2216e2058b7434e05968b22
- Add Spring Gateway LoadBalancer - https://github.com/dtimchenko/custom-microservices-project/commit/9791ca86f585bee408707ba98173034bed011297
- Add Gateway Authorization filter - https://github.com/dtimchenko/custom-microservices-project/commit/144531f83c5ca2ecc919b4abdc587101e1915272
- Add Gateway Global Post/Pre Filters - https://github.com/dtimchenko/custom-microservices-project/commit/22f448c14c22c20a666e69442f03b16ed6fa9f68

### SPRING SECURITY
- Add Spring Security - https://github.com/dtimchenko/custom-microservices-project/commit/da4211d999f0cd68002335439cc569d10714bc19
- Add Spring Security Password Encryption - https://github.com/dtimchenko/custom-microservices-project/commit/3fd5d13029b661f05df8b77e416c8dbf6fc48f34
- Add Spring Security JWT - https://github.com/dtimchenko/custom-microservices-project/commit/1b3504a5e6d847ead3825d4aa638beee7ad796ae
- Add a JWT refresh_token - https://github.com/dtimchenko/custom-microservices-project/commit/28ab1ba9de5bfdc632ff774b3911e3954b6453a1

### CONFIG SERVER
- Add a Spring Cloud Config Server - https://github.com/dtimchenko/custom-microservices-project/commit/fbf1b88fb8f834a7705b329d2f77d514dc80e18a
- Add Config Server Shared Properties - https://github.com/dtimchenko/custom-microservices-project/commit/e669f370c7615f436f7febe3f33bff635ee94c6b

### ZIPKIN TRACING
- Add zipkin and spring sleuth tracing - https://github.com/dtimchenko/custom-microservices-project/commit/3401c18099864ace8e30431f325596cc86e3f7b0

### SPRING CLOUD BUS
- Update services properties dynamically - https://github.com/dtimchenko/custom-microservices-project/commit/31574d0d1f97345eb1fd02674de438daac12f65f

### ELK CENTRALIZED LOGGING
- Add ELK logging with Filebeats - https://github.com/dtimchenko/custom-microservices-project/commit/0f2646e2bcb16f75dfc86e4a448eef3d53f23763
- Add Kafka buffer to ELK logs - https://github.com/dtimchenko/custom-microservices-project/commit/81203e61caaced7c68d3804cd4e776105e64d5e7

### CENTRALIZED MONITORING
- Add monitoring with: Micrometer, Prometheus and Grafana - https://github.com/dtimchenko/custom-microservices-project/commit/45091411e6a29a5c0cc0eb61434b629c46c3f5ff

### PROPERTIES ENCRYPTION/DECRYPTION
- Add Properties SYMMETRIC encryption and decryption - https://github.com/dtimchenko/custom-microservices-project/commit/0795f4049a64c766f7f9ab1d9677caee9148d9ee

to encrypt a property just send a POST to http://localhost:8010/encrypt (where http://localhost:8010 is the config-server's endpoint)