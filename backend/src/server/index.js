// @flow
import path from 'path';
import Glue from 'glue';
import Nunjucks from 'nunjucks';
import manifest from './manifest';
import config from '../config';

/**
 * Create the server to use.
 * This does everything necessary to set up the server short of actually starting it listening
 */
export default async function createServer() {
    const server = await Glue.compose({
        server: {
            port: config.get('http.port'),
        },
        register: {
            plugins: manifest,
        },
    });

    server.views({
        engines: {
            html: {
                compile: (src, options) => {

                    const template = Nunjucks.compile(src, options.environment);

                    return context => template.render(context);
                },

                prepare: (options, next) => {
                    options.compileOptions.environment = // eslint-disable-line no-param-reassign
                        Nunjucks.configure(options.path, { watch: false });
                    return next();
                }
            }
        },
        relativeTo: path.join(__dirname, '..', '..'),
        path: 'templates',
    });

    return server;
}
