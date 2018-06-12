// @flow

import {call, put} from 'redux-saga/effects';
import request from '../api';

/** The action for loading a user from the backend */
const LOAD_USER_ACTION = 'USERS/LOAD_USER';

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
    userId: string
};

/** the type that is used for the Store User action */
type StoreUserAction = {
    type: string,
    user: User
}

/** Action to indicate that we are loading a user */
export function loadUserAction(user: string): LoadUserAction {
    return {
        type: LOAD_USER_ACTION,
        userId: user
    };
}

/** Mutation for updating the state with the user details provided */
export function storeUserMutation(state: UsersState, action: StoreUserAction) {
    const user = action.user;

    state.users[user.id] = user;
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
                userId: action.userId
            }
        });

        yield put({
            type: STORE_USER_ACTION,
            user: {
                id: user.data.id,
                username: user.data.username,
                displayName: user.data.display_name,
                email: user.data.email
            }
        });
    }
};

