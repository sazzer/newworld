// @flow

import {put} from 'redux-saga/effects';
import {setAccessToken} from "../api";
import {clearCurrentUserAction} from "../users/currentUser";

/** The action for storing the access token */
const STORE_ACCESS_TOKEN_ACTION = 'AUTH/STORE_ACCESS_TOKEN';

/** The action for clearing the access token */
const CLEAR_ACCESS_TOKEN_ACTION = 'AUTH/CLEAR_ACCESS_TOKEN';

/** Type representing an access token */
export type AccessToken = {
    token: string,
    type: string,
    expires: number
}

/** The type representing the part of the state that we care about */
export type AccessTokenModuleState = {
    accessToken?: AccessToken
};

/** The signature of the Access Token sub-module */
export type AccessTokenModule = {
    logout: () => void,
    selectHasAccessToken: () => boolean
}

/** The type that is used for storing the access token action */
type StoreAccessTokenAction = {
    type: string,
    accessToken: AccessToken
};

type ClearAccessTokenAction = {
    type: string,
};

/** Action to indicate that we are finishing authentication */
export function storeAccessTokenAction(accessToken: string, type: string, expiresIn: number): StoreAccessTokenAction {
    return {
        type: STORE_ACCESS_TOKEN_ACTION,
        accessToken: {
            token: accessToken,
            type: type,
            expires: expiresIn
        }
    };
}

/** Action to indicate that we are clearing the access token */
export function clearAccessTokenAction(): ClearAccessTokenAction {
    return {
        type: CLEAR_ACCESS_TOKEN_ACTION
    };
}

/** Mutation for storing the access token into the redux store */
export function storeAccessTokenMutation(state: AccessTokenModuleState, action: StoreAccessTokenAction) {
    state.accessToken = action.accessToken;
}

/** Mutation for clearing the access token from the redux store */
export function clearAccessTokenMutation(state: AccessTokenModuleState, action: ClearAccessTokenAction) {
    delete state.accessToken;
}

/** Selector to see if we've got an access token available */
export function selectHasAccessToken(state: AccessTokenModuleState): boolean {
    return state.accessToken !== undefined;
}

/** The actions for this sub-module */
export const actions = {
    logout: clearAccessTokenAction
};

/** The mutations for this sub-module */
export const mutations = {
    [STORE_ACCESS_TOKEN_ACTION]: storeAccessTokenMutation,
    [CLEAR_ACCESS_TOKEN_ACTION]: clearAccessTokenMutation,
};

/** The sagas for this sub-module */
export const sagas = {
    [STORE_ACCESS_TOKEN_ACTION]: function(action: StoreAccessTokenAction) {
        setAccessToken(action.accessToken.token);
    },
    [CLEAR_ACCESS_TOKEN_ACTION]: function*(): Generator<any, any, any> {
        setAccessToken(undefined);

        yield put(clearCurrentUserAction());
    }
};

export const selectors = {
    selectHasAccessToken: (state: AccessTokenModuleState) => () => selectHasAccessToken(state)
};
