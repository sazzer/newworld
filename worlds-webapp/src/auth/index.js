// @flow

import {createSagas} from "redux-box";
import type {StartAuthModule, StartAuthModuleState} from "./startAuth";
import * as startAuth from './startAuth';
import type {AccessTokenModuleState, AccessTokenModule} from "./accessToken";
import * as accessToken from './accessToken';
import * as finishAuth from './finishAuth';

type AuthModuleState = StartAuthModuleState & AccessTokenModuleState;

/** The name of the module */
const MODULE_NAME = 'auth';

/** The initial state of the module */
const state: AuthModuleState = {
};

/** The set of actions that can be performed */
const actions = {
    ...startAuth.actions,
    ...accessToken.actions
};

/** The set of mutations that are triggered in response to actions */
const mutations = {
    ...startAuth.mutations,
    ...accessToken.mutations
};

/** The set of Sagas that are triggered in response to actions */
const sagas = {
    ...startAuth.sagas,
    ...finishAuth.sagas,
    ...accessToken.sagas
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

/** Flow type to represent the Auth module */
export type AuthModule = StartAuthModule & AccessTokenModule;
