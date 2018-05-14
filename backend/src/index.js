// @flow
import bristol from 'bristol';
import promiseRetry from 'promise-retry';
import createServer from './server';
import migrate from './db/migrate';
import query from './db/query';

bristol.addTarget('console')
    .withFormatter('human');

async function getDatabaseVersion() {
    return promiseRetry((retry, number) => query('SELECT version() AS version')
        .catch((e) => {
            bristol.debug(`Attempt ${number} to get database version failed. Retrying.`, { e });
            retry();
        }));
}

/**
 * Start the server running
 */
async function start() {
    const databaseVersion = await getDatabaseVersion();
    bristol.info('Database version', { version: databaseVersion.rows[0].version });

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
