// @flow

import createServer from '..';

describe('Hapi Healthchecks', () => {
    let server;

    beforeEach(async () => {
        server = await createServer();
    });

    test('Hapi Healthchecks respond correctly', async () => {
        const response = await server.inject({
            method: 'GET',
            url: '/health',
        });

        expect.assertions(1);
        expect(response.statusCode).toBe(200);
    });
});
