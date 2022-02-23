#/bin/bash

docker network create keycloak-network
docker run --name some-mysql --net keycloak-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=keycloak -e MYSQL_USER=keycloak -e MYSQL_PASSWORD=password -p 3307:3306 mysql:5.7
docker run --name some-keycloak --net keycloak-network -e DB_VENDOR=MYSQL -e DB_ADDR=some-mysql -e DB_PORT=3306 -e DB_DATABASE=keycloak -e DB_USER=keycloak -e DB_PASSWORD=password -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=password -e MYSQL_PORT_3306_TCP_ADDR=some-mysql -e MYSQL_PORT_3306_TCP_PORT=3306 -p 8888:8080 jboss/keycloak:3.4.3.Final