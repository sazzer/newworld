// @flow
import bristol from 'bristol';
import createServer from './server';
import migrate from './db/migrate';

bristol.addTarget('console')
    .withFormatter('human');

/**
 * Start the server running
 */
async function start() {
    await migrate();

    const server = await createServer();
    try {
        server.start();
        bristol.info('Server running');
    } catch (error) {
        bristol.error('Failed to start server', { error });
    }
}

start();
