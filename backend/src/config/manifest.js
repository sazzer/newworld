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
    db: {
        host: {
            doc: 'The database host to connect to',
            env: 'POSTGRES_HOST',
            default: '',
        },
        port: {
            doc: 'The database port to connect to',
            format: 'port',
            env: 'POSTGRES_PORT',
            default: 5432,
        },
        db: {
            doc: 'The database to connect to',
            env: 'POSTGRES_DB',
            default: '',
        },
        user: {
            doc: 'The database user to connect as',
            env: 'POSTGRES_USER',
            default: '',
        },
        password: {
            doc: 'The database password to connect with',
            env: 'POSTGRES_PASSWORD',
            default: '',
        },
    },
};
