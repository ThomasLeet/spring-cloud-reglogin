#Install and Startup
- docker build -t gateway .
- docker run -d -p 8080:8080 --network internal_net --network external_net gateway:latest  -e SPRING_PROFILES_ACTIVE=prod
- docker stop xxx (run -d 返回的containId)

