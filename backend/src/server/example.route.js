export default [
    {
        method: 'GET',
        path: '/example',
        handler: (request, h) => {
            return h.view('example', {
                name: 'Graham',
            });
        },
    },
];
