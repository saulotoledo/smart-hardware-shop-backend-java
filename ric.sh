#!/bin/sh

echo "Run In Container (RIC)\n"

docker exec -it smart-hardware-shop-app "$@"
