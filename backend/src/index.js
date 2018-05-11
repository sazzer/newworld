// @flow
import bristol from 'bristol';
import createServer from './server';


bristol.addTarget('console')
    .withFormatter('human');

createServer()
    .then(server => server.start())
    .then(() => {
        bristol.info('Server running');
    })
    .catch((error) => {
        bristol.error('Failed to start server', { error });
    });
