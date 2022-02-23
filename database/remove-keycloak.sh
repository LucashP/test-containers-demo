#/bin/bash

docker stop some-keycloak
docker rm some-keycloak
docker stop some-mysql
docker rm some-mysql
docker network rm keycloak-network