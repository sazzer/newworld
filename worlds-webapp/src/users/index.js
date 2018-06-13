// @flow

import {createSagas} from "redux-box";
import type {CurrentUserState, CurrentUserModule} from "./currentUser";
import type {UsersState, UsersModule} from "./users";
import * as currentUser from './currentUser';
import * as users from './users';

type UserModuleState = CurrentUserState | UsersState;

/** The name of the module */
const MODULE_NAME = 'users';

/** The initial state of the module */
const state: UserModuleState = {
    users: {}
};

/** The set of actions that can be performed */
const actions = {
};

/** The set of mutations that are triggered in response to actions */
const mutations = {
    ...currentUser.mutations,
    ...users.mutations
};

/** The set of Sagas that are triggered in response to actions */
const sagas = {
    ...currentUser.sagas,
    ...users.sagas
};

/** The set of selectors that are used to get data out of the module */
const selectors = {
    ...currentUser.selectors,
    ...users.selectors
};

/** The actual module */
export const module = {
    name: MODULE_NAME,
    state,
    actions,
    mutations,
    sagas: createSagas(sagas),
    selectors
};

/** The type representing the Users module */
export type UserModule = UsersModule & CurrentUserModule;
