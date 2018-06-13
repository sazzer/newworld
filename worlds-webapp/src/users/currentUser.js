// @flow

import {put} from 'redux-saga/effects';
import {loadUserAction, selectUserById} from "./users";
import type {User, UsersState} from "./users";

/** The action for storing the current user */
const STORE_CURRENT_USER_ACTION = 'USERS/STORE_CURRENT_USER';

/** The type representing the part of the state that we care about */
export type CurrentUserState = {
    currentUser?: string
};

/** The type that is used for the Store Current User action */
type StoreCurrentUserAction = {
    type: string,
    currentUser: string
};

/** Action to indicate that we are storing the current user */
export function storeCurrentUserAction(user: string): StoreCurrentUserAction {
    return {
        type: STORE_CURRENT_USER_ACTION,
        currentUser: user
    };
}

/** Mutation for storing the current user ID into the redux store */
export function storeCurrentUserMutation(state: CurrentUserState, action: StoreCurrentUserAction) {
    state.currentUser = action.currentUser;
}

/**
 * Selector to get the ID of the current user
 */
export function selectCurrentUserId(state: CurrentUserState & UsersState): ?string {
    return state.currentUser;
}

/**
 * Selector to get the current user
 */
export function selectCurrentUser(state: CurrentUserState & UsersState): ?User {
    const currentUserId = selectCurrentUserId(state);
    let result;
    if (currentUserId) {
        result = selectUserById(state, currentUserId);
    }

    return result;
}

export function selectHasCurrentUser(state: CurrentUserState & UsersState): boolean {
    return selectCurrentUser(state) !== undefined;
}

/** The mutations for this sub-module */
export const mutations = {
    [STORE_CURRENT_USER_ACTION]: storeCurrentUserMutation
};

/** The sagas for this sub-module */
export const sagas = {
    [STORE_CURRENT_USER_ACTION]: function*(action: StoreCurrentUserAction): Generator<any, any, any> {
        yield put(loadUserAction(action.currentUser));
    }
};

export const selectors = {
    selectCurrentUserId: (state: CurrentUserState & UsersState) => () => selectCurrentUserId(state),
    selectCurrentUser: (state: CurrentUserState & UsersState) => () => selectCurrentUser(state),
    selectHasCurrentUser: (state: CurrentUserState & UsersState) => () => selectHasCurrentUser(state)
};

/** Type describing what is exposed by this sub-module */
export type CurrentUserModule = {
    selectCurrentUserId: () => ?string,
    selectCurrentUser: () => ?User,
    selectHasCurrentUser: () => boolean
}
