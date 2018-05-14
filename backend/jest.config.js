const path = require('path');

module.exports = {
    verbose: true,
    collectCoverage: true,
    collectCoverageFrom: [
        'src/**/*.js',
        '!src/index.js',
        '!src/config/*.js',
        '!src/server/index.js',
    ],
    clearMocks: true,
    roots: [
        'src',
    ],
    notify: true,
    setupFiles: [
        path.join(__dirname, 'src', 'testSetup.js'),
    ],
};
