// @flow

/** The action for storing the access token */
const STORE_ACCESS_TOKEN_ACTION = 'AUTH/STORE_ACCESS_TOKEN';

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

/** The type that is used for storing the access token action */
type StoreAccessTokenAction = {
    type: string,
    accessToken: AccessToken
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

/** Mutation for storing the access token into the redux store */
export function storeAccessTokenMutation(state: AccessTokenModuleState, action: StoreAccessTokenAction) {
    state.accessToken = action.accessToken;
}

/** The mutations for this sub-module */
export const mutations = {
    [STORE_ACCESS_TOKEN_ACTION]: storeAccessTokenMutation
};
