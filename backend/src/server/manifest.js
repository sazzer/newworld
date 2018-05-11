import path from 'path';

export default [
    'blipp',
    'hapi-response-time',
    'inert',
    'vision',
    {
        plugin: 'hapi-alive',
        options: {
            path: '/health',
            healthCheck: async () => true,
        },
    },
    {
        plugin: 'hapi-router',
        options: {
            routes: '**/*.route.js',
            cwd: path.join(__dirname, '..'),
        },
    },
];
