// @flow

import * as testSubject from '../accessToken';

describe('storeAccessTokenAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.storeAccessTokenAction('accessToken', 'Bearer', 3600);

        it('Has 2 keys', () => {
            expect(Object.keys(result)).toHaveLength(2);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('AUTH/STORE_ACCESS_TOKEN');
        });
        it('Has an access token', () => {
            expect(result.accessToken).toEqual({
                token: 'accessToken',
                type: 'Bearer',
                expires: 3600
            });
        });
    });
});

describe('storeAccessTokenMutation', () => {
    describe('Specifying new values', () => {
        const state = {};
        testSubject.storeAccessTokenMutation(state, {type: '', accessToken: {token: 'accessToken', type: 'Bearer', expires: 3600}});

        it('Has 1 key', () => {
           expect(Object.keys(state)).toHaveLength(1);
        });
        it('Has the right access token', () => {
            expect(state.accessToken).toEqual({
                token: 'accessToken',
                type: 'Bearer',
                expires: 3600
            });
        });
    });
});
