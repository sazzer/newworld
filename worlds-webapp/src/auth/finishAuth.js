// @flow

import {put} from 'redux-saga/effects';
import jwtDecode from 'jwt-decode';
import {storeAccessTokenAction} from "./accessToken";
import {storeCurrentUserAction} from "../users/currentUser";

/** The action for finishing authentication */
const FINISH_AUTH_ACTION = 'AUTH/FINISH_AUTH';

/** The type that represents the payload received from authentication */
type FinishAuthPayload = {
    access_token: string,
    token_type: string,
    expires_in: number,
    state: string,
    id_token: string
}

/** The type that is used for the Finish Auth action */
type FinishAuthAction = {
    type: string,
    payload: FinishAuthPayload
};

/** Action to indicate that we are finishing authentication */
export function finishAuthAction(params: FinishAuthPayload): FinishAuthAction {
    return {
        type: FINISH_AUTH_ACTION,
        payload: params
    };
}

/** The sagas for this sub-module */
export const sagas = {
    [FINISH_AUTH_ACTION]: function*(action: FinishAuthAction): Generator<any, any, any> {
        yield put(storeAccessTokenAction(action.payload.access_token, action.payload.token_type, action.payload.expires_in));

        const idToken = jwtDecode(action.payload.id_token);
        yield put(storeCurrentUserAction(idToken.sub));
    }
};

