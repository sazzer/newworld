export default {
    api: {
        doc: 'The Base URI of the API to test',
        default: '',
        env: 'API_URL',
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
