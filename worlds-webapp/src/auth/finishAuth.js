// @flow

import {put} from 'redux-saga/effects';
import {storeAccessTokenAction} from "./accessToken";

/** The action for finishing authentication */
const FINISH_AUTH_ACTION = 'AUTH/FINISH_AUTH';

/** The type that is used for the Finish Auth action */
type FinishAuthAction = {
    access_token: string,
    token_type: string,
    expires_in: number,
    state: string
};

/** Action to indicate that we are finishing authentication */
export function finishAuthAction(params: FinishAuthAction) {
    return {
        type: FINISH_AUTH_ACTION,
        ...params
    };
}

/** The sagas for this sub-module */
export const sagas = {
    [FINISH_AUTH_ACTION]: function*(action: FinishAuthAction): Generator<any, any, any> {
        yield put(storeAccessTokenAction(action.access_token, action.token_type, action.expires_in));
    }
};

