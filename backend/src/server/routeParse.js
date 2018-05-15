// @flow

export type ParsedRoute = {
    id?: string,
    params: any,
    paramNames: Array<String>,
    paramsArray: Array<any>,
    path: string
};

/**
 * Parse the given Method and URL into some Route details, including the ID of the route and the bind parameters
 * @param {Hapi.Server} server The Hapi Server to use
 * @param {string} method The HTTP Method to use
 * @param {string} url The HTTP URL to use
 */
function parseServerRoute(server: any, method: string, url: string): ?ParsedRoute {
    const route = server._core.router.route(method.toLowerCase(), url);
    console.log(route);

    if (route && route.route.method === method.toLowerCase()) {
        return {
            id: route.route.settings.id,
            params: route.params,
            paramNames: route.route.params,
            paramsArray: route.paramsArray,
            path: route.route.path,
        };
    }
}

export default {
    name: 'route-parse',
    version: '1.0.0',
    register: function register(server: any) {
        server.method('parseRoute', (method: string, url: string) => parseServerRoute(server, method, url));
    },
};
