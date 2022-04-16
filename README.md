# custom-microservices-project

Simple microservices POC

To run docker containers, just run from the root:
`docker compose up`


HOW TO:
- Init maven parent pom - https://github.com/dtimchenko/custom-microservices-project/commit/b5d15058b41bde97fd809fb7e39e3f48dd5deb43
- Add a docker-compose for postgresql - https://github.com/dtimchenko/custom-microservices-project/commit/5f17c642ec2277707e532a94a03a5df968403964

HOW TO EUREKA:
- Add Eureka Server - https://github.com/dtimchenko/custom-microservices-project/commit/e615453423bcb8b4326f0dc93fa4413fd5d8e549 
- Add Eureka Clients - https://github.com/dtimchenko/custom-microservices-project/commit/27f3273c53bdba149c7e5dc040c8cf382db17575
- Add Eureka Clients Communication - https://github.com/dtimchenko/custom-microservices-project/commit/e6b61fcbfa9dd67ea1e260f50191cdcaaca9626a
- Add OpenFeign Client - https://github.com/dtimchenko/custom-microservices-project/commit/4c894dcf2d9b13c089df0ebe9cadee3b24f4ff85

SPRING WEB:
- Add application/xml API support - https://github.com/dtimchenko/custom-microservices-project/commit/4c894dcf2d9b13c089df0ebe9cadee3b24f4ff85
- Add Request Validation - https://github.com/dtimchenko/custom-microservices-project/commit/301886c3d5863319f1478faa2dc19edea89c4ea4

HOW TO GATEWAY:
- Add API Gateway - https://github.com/dtimchenko/custom-microservices-project/commit/dd4b2bf1b945e502a396321fd94be9c8735df816
- Add Manual Route for API Gateway - https://github.com/dtimchenko/custom-microservices-project/commit/f2c1aa71e92c0cf7c2216e2058b7434e05968b22
- Add Spring Gateway LoadBalancer - https://github.com/dtimchenko/custom-microservices-project/commit/9791ca86f585bee408707ba98173034bed011297
- Add Gateway Authorization filter - https://github.com/dtimchenko/custom-microservices-project/commit/144531f83c5ca2ecc919b4abdc587101e1915272
- Add Gateway Global Post/Pre Filters - https://github.com/dtimchenko/custom-microservices-project/commit/22f448c14c22c20a666e69442f03b16ed6fa9f68

HOW TO SPRING SECURITY:
- Add Spring Security - https://github.com/dtimchenko/custom-microservices-project/commit/da4211d999f0cd68002335439cc569d10714bc19
- Add Spring Security Password Encryption - https://github.com/dtimchenko/custom-microservices-project/commit/3fd5d13029b661f05df8b77e416c8dbf6fc48f34
- Add Spring Security JWT - https://github.com/dtimchenko/custom-microservices-project/commit/1b3504a5e6d847ead3825d4aa638beee7ad796ae
- Add a JWT refresh_token - https://github.com/dtimchenko/custom-microservices-project/commit/28ab1ba9de5bfdc632ff774b3911e3954b6453a1