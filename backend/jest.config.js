module.exports = {
    verbose: true,
    collectCoverage: true,
    collectCoverageFrom: [
        'src/**/*.js',
        '!src/index.js',
        '!src/config/*.js',
    ],
    clearMocks: true,
    roots: [
        'src',
    ],
    notify: true,
};
