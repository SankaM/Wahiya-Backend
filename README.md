# wahiya
eDoctor backend

# Docker
sudo docker build --tag wahiya:0.0.1 .

sudo docker images


sudo docker run -d -e DATABASE_SERVER=jdbc:h2:mem:testdb -p 3005:3005 wahiya:0.0.1

sudo docker container ps

gradlew bootJar

# H2
http://localhost:3005/h2-console
