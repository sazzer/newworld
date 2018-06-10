// @flow

import {createSagas} from "redux-box";
import type {CurrentUserState} from "./currentUser";
import * as currentUser from './currentUser';

type UserModuleState = CurrentUserState;

/** The name of the module */
const MODULE_NAME = 'users';

/** The initial state of the module */
const state: UserModuleState = {
};

/** The set of actions that can be performed */
const actions = {
};

/** The set of mutations that are triggered in response to actions */
const mutations = {
    ...currentUser.mutations
};

/** The set of Sagas that are triggered in response to actions */
const sagas = {
    ...currentUser.sagas
};

/** The set of selectors that are used to get data out of the module */
const selectors = {
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
