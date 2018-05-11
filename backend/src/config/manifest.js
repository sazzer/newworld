export default {
    env: {
        doc: 'The application environment.',
        format: ['production', 'development', 'test'],
        default: 'development',
        env: 'NODE_ENV',
    },
    http: {
        port: {
            doc: 'The port to listen on',
            format: 'port',
            default: 3000,
            env: 'PORT',
        },
    },
};
