// @flow

import {call, put} from 'redux-saga/effects';
import request from '../api';

/** The action for loading a user from the backend */
const LOAD_USER_ACTION = 'USERS/LOAD_USER';

/** The action for saving a user to the backend */
const SAVE_USER_ACTION = 'USERS/SAVE_USER';

/** The action for storing a user into the store */
const STORE_USER_ACTION = 'USERS/STORE_USER';

/** The type representing a user details */
export type User = {
    id: string,
    displayName: string,
    username: string,
    email?: string
};

/** The type representing the part of the state that we care about */
export type UsersState = {
    users: { [string]: User }
};

/** The type that is used for the Load User action */
type LoadUserAction = {
    type: string,
    payload: {
        userId: string,
    },
};

/** The type that is used for the Save User action */
type SaveUserAction = {
    type: string,
    payload: {
        user: User,
    },
    meta: {
        onSuccess: () => void,
        onError: (string) => void,
    },
};

/** the type that is used for the Store User action */
type StoreUserAction = {
    type: string,
    payload: {
        user: User,
    },
}

/** Action to indicate that we are loading a user */
export function loadUserAction(user: string): LoadUserAction {
    return {
        type: LOAD_USER_ACTION,
        payload: {
            userId: user
        }
    };
}

/** Action to indicate that we are loading a user */
export function saveUserAction(user: User, onSuccess: () => void, onError: (string) => void): SaveUserAction {
    return {
        type: SAVE_USER_ACTION,
        payload: {
            user: user
        },
        meta: {
            onSuccess: onSuccess,
            onError: onError
        }
    };
}

/** Mutation for updating the state with the user details provided */
export function storeUserMutation(state: UsersState, action: StoreUserAction) {
    const user = action.payload.user;

    state.users[user.id] = user;
}

/**
 * Selector to get the list of all users
 */
export function selectUsers(state: UsersState): { [string]: User } {
    return state.users;
}

/**
 * Selector to get a single user by ID
 */
export function selectUserById(state: UsersState, id: string): ?User {
    return selectUsers(state)[id];
}

/** The mutations for this sub-module */
export const mutations = {
    [STORE_USER_ACTION]: storeUserMutation
};

/** The sagas for this sub-module */
export const sagas = {
    [LOAD_USER_ACTION]: function*(action: LoadUserAction): Generator<any, any, any> {
        const user = yield call(request, {
            url: '/users/{userId}',
            path: {
                userId: action.payload.userId
            }
        });

        yield put({
            type: STORE_USER_ACTION,
            payload: {
                user: {
                    id: user.data.id,
                    username: user.data.username,
                    displayName: user.data.display_name,
                    email: user.data.email
                }
            }
        });
    },
    [SAVE_USER_ACTION]: function*(action: SaveUserAction): Generator<any, any, any> {
        try {
            const user = yield call(request, {
                method: 'PUT',
                url: '/users/{userId}',
                path: {
                    userId: action.payload.user.id
                },
                data: {
                    display_name: action.payload.user.displayName,
                    email: action.payload.user.email,
                    username: action.payload.user.username
                }
            });

            yield put({
                type: STORE_USER_ACTION,
                payload: {
                    user: {
                        id: user.data.id,
                        username: user.data.username,
                        displayName: user.data.display_name,
                        email: user.data.email
                    }
                }
            });

            action.meta.onSuccess();
        } catch (e) {
            action.meta.onError(e.response.data.type);
        }
    }
};

/** The actions for this sub-module */
export const actions = {
    saveUser: saveUserAction
};

/** The selectors for this sub-module */
export const selectors = {
    selectUsers: (state: UsersState) => () => selectUsers(state),
    selectUserById: (state: UsersState) => (id: string) => selectUserById(state, id)
};

/** Type describing what is exposed by this sub-module */
export type UsersModule = {
    selectUsers: () => { [string]: User },
    selectUserById: (string) => ?User,
    saveUser: (User, () => void, (string) => void) => void
};
