#/bin/bash

docker run -p 6379:6379 --name some-redis -d redis:6.2
