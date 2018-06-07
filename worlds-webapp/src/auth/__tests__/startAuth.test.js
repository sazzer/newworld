// @flow

import * as testSubject from '../startAuth';

describe('startAuthAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.startAuthAction();

        it('Has 3 keys', () => {
            expect(Object.keys(result)).toHaveLength(3);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('AUTH/START_AUTH');
        });
        it('Has a nonce', () => {
            expect(result.nonce).toBeDefined();
        });
        it('Has a state value', () => {
            expect(result.state).toBeDefined();
        });
    });
});

describe('startAuthMutation', () => {
    describe('Specifying new values', () => {
        const state = {};
        testSubject.startAuthMutation(state, {state: 'MyState', nonce: 'MyNonce'});

        it('Has 2 keys', () => {
           expect(Object.keys(state)).toHaveLength(2);
        });
        it('Has the right state', () => {
            expect(state.state).toBe('MyState');
        });
        it('Has the right nonce', () => {
            expect(state.nonce).toBe('MyNonce');
        });
    });
});
