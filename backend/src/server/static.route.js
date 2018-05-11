import path from 'path';

export default [
    {
        method: 'GET',
        path: '/static/{param*}',
        handler: {
            directory: {
                path: path.join(__dirname, '..', '..', 'static'),
                index: false,
                listing: false,
                showHidden: false,
            },
        },
    },
];
