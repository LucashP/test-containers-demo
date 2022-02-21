#/bin/bash

docker run --name some-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root-password -e MYSQL_DATABASE=my-database -e MYSQL_USER=mysql-user -e MYSQL_PASSWORD=mysql-password -d mysql:8.0.25
