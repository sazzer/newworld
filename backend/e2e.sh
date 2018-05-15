#!/bin/sh

docker-compose -f docker-compose.cucumber.yml down
docker-compose -f docker-compose.cucumber.yml rm -f
docker-compose -f docker-compose.cucumber.yml up --build --exit-code-from worlds-e2e-cucumber
