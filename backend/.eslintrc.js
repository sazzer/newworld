module.exports = {
    "extends": [
        "airbnb-base",
        "plugin:flowtype/recommended",
        "plugin:jest/recommended",
    ],
    "parser": "babel-eslint",
    "plugins": [
        "flowtype",
        "jest",
    ],
    "env": {
        "jest/globals": true,
    },
    "rules": {
        "indent": ["error", 4],
    }
};
