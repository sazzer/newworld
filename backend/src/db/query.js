// @flow

import { Pool } from 'pg';
import config from '../config';

/** The actual connection pool to use */
const pool = new Pool({
    host: config.get('db.host'),
    port: config.get('db.port'),
    database: config.get('db.db'),
    user: config.get('db.user'),
    password: config.get('db.password'),
});

/**
 * Actually send a query to the database and get the response back
 * @param {string} sql the query to send
 * @param {Array<any>} binds the binds for the query
 */
export default async function query(sql: string, binds: Array<any> = []) {
    return pool.query(sql, binds);
}
