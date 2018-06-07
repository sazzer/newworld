// @flow

import buildUrl from 'build-url';
import uuid from "uuid/v4";

/** The action for starting authentication */
const START_AUTH_ACTION = 'AUTH/START_AUTH';

/** The type representing the part of the state that we care about */
export type StartAuthModuleState = {
    state?: string,
    nonce?: string
};

/** The signature of the Start Auth sub-module */
export type StartAuthModule = {
    startAuth: () => void
}

/** The type that is used for the Start Auth action */
type StartAuthAction = {
    state: string,
    nonce: string
};

/**
 * Start authentication by opening a new window to the appropriate URL
 * @param state The state to use for the auth flow
 * @param nonce The nonce to use for the auth flow
 */
export function startAuth(state: string, nonce: string) {
    const url = process.env.REACT_APP_AUTH_URL;
    const clientId = process.env.REACT_APP_AUTH_CLIENT;
    const callback = process.env.REACT_APP_AUTH_CALLBACK;

    const realUrl = buildUrl(url, {
        queryParams: {
            response_type: 'id_token token',
            scope: 'openid',
            redirect_uri: callback,
            client_id: clientId,
            nonce,
            state
        }
    });

    window.open(realUrl,
        'worldsAuth',
        'dependent'
    );
}

/** Action to indicate that we are starting authentication */
export function startAuthAction() {
    return {
        type: START_AUTH_ACTION,
        state: uuid(),
        nonce: uuid()
    };
}

/** Mutation for storing the state and nonce we used for starting authentication into the redux store */
export function startAuthMutation(state: StartAuthModuleState, action: StartAuthAction) {
    state.state = action.state;
    state.nonce = action.nonce;
}

/** The actions for this sub-module */
export const actions = {
    startAuth: startAuthAction
};

/** The mutations for this sub-module */
export const mutations = {
    [START_AUTH_ACTION]: startAuthMutation
};

/** The sagas for this sub-module */
export const sagas = {
    [START_AUTH_ACTION]: function*(action: StartAuthAction): Generator<void, void, StartAuthAction> {
        startAuth(action.state, action.nonce);
    }
};

