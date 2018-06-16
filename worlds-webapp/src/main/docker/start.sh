#!/bin/sh

cat << EOF > /usr/share/nginx/html/config.js
window.configuration = {
    REACT_APP_AUTH_URL: '$REACT_APP_AUTH_URL',
    REACT_APP_AUTH_CLIENT: '$REACT_APP_AUTH_CLIENT',
    REACT_APP_AUTH_CALLBACK: '$REACT_APP_AUTH_CALLBACK',
    REACT_APP_API_BASE: '$REACT_APP_API_BASE'
};
EOF

nginx -g 'daemon off;'
