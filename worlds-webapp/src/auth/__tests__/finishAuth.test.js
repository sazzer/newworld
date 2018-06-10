// @flow

import * as testSubject from '../finishAuth';

describe('finishAuthAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.finishAuthAction({
            access_token: 'accessToken',
            token_type: 'Bearer',
            state: 'state',
            expires_in: 3600,
            id_token: 'idToken'
        });

        it('Has 2 keys', () => {
            expect(Object.keys(result)).toHaveLength(2);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('AUTH/FINISH_AUTH');
        });
        it('Has the payload', () => {
            expect(result.payload).toEqual({
                access_token: 'accessToken',
                token_type: 'Bearer',
                state: 'state',
                expires_in: 3600,
                id_token: 'idToken'
            });
        })
    });
});
