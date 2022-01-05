#!/usr/bin/env bash
/bin/bash ./mvnw clean package -DskipTests
cp target/*.jar ./docker/bot.jar
docker build -t pmajewski/wodbot ./docker/.
rm ./docker/bot.jar
rm -r ./target