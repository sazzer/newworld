import path from 'path';
import routeParsePlugin from './routeParse';

export default [
    'blipp',
    'hapi-response-time',
    'inert',
    'vision',
    routeParsePlugin,
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
