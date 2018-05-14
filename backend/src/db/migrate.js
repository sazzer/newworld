// @flow

import path from 'path';
import Postgrator from 'postgrator';
import bristol from 'bristol';
import config from '../config';

/**
 * Migrate the database to the latest version as defined in the migrations files
 */
export default async function migrate() {
    const postgrator = new Postgrator({
        migrationDirectory: path.join(__dirname, '..', '..', 'migrations'),
        driver: 'pg',
        host: config.get('db.host'),
        port: config.get('db.port'),
        database: config.get('db.db'),
        username: config.get('db.user'),
        password: config.get('db.password'),
    });

    postgrator.on('validation-started', migration => bristol.info('Validation started', { migration }));
    postgrator.on('validation-finished', migration => bristol.info('Validation finished', { migration }));
    postgrator.on('migration-started', migration => bristol.info('Migration started', { migration }));
    postgrator.on('migration-finished', migration => bristol.info('Migration finished', { migration }));

    return postgrator.migrate();
}
