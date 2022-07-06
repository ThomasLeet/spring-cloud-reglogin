#Install and Startup
- docker build -t account-service .
- docker run -d -p 8081:8081 --network internal_net --network external_net account-service:latest -e SPRING_PROFILES_ACTIVE=prod
- docker stop xxx (run -d 返回的containId)

