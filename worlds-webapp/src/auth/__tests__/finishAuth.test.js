// @flow

import * as testSubject from '../finishAuth';

describe('finishAuthAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.finishAuthAction({
            access_token: 'accessToken',
            token_type: 'Bearer',
            state: 'state',
            expires_in: 3600
        });

        it('Has 5 keys', () => {
            expect(Object.keys(result)).toHaveLength(5);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('AUTH/FINISH_AUTH');
        });
        it('Has an access_token', () => {
            expect(result.access_token).toBeDefined();
        });
        it('Has aa token_type', () => {
            expect(result.token_type).toBeDefined();
        });
        it('Has an expires_in', () => {
            expect(result.expires_in).toBeDefined();
        });
        it('Has a state value', () => {
            expect(result.state).toBeDefined();
        });
    });
});
