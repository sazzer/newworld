#!/bin/bash

set -e

API_BASE=https://newworld-cd.herokuapp.com
UI_BASE=https://newworld-cd.surge.sh

pushd worlds-service
heroku container:login
heroku container:push web -a newworld-cd --arg JAR_FILE=./target/worlds-service-0.0.1-SNAPSHOT.jar
heroku container:release web -a newworld-cd
popd 

pushd worlds-webapp/build
cat << EOF > config.js
window.configuration = {
    REACT_APP_AUTH_URL: '${API_BASE}/openid/authorize',
    REACT_APP_AUTH_CLIENT: '4B81F3F0-99DD-4306-995E-BFF70C3BBF7B',
    REACT_APP_AUTH_CALLBACK: '${UI_BASE}/auth/callback.html',
    REACT_APP_API_BASE: '${API_BASE}/api'
};
EOF
cp index.html 200.html
surge --domain newworld-cd.surge.sh .
popd 
