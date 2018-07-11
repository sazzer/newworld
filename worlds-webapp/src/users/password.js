// @flow

import {call} from 'redux-saga/effects';
import request from '../api';

/** The action for changing a password */
const CHANGE_PASSWORD_ACTION = 'USERS/CHANGE_PASSWORD';

/** The type that is used for the Change Password action */
type ChangePasswordAction = {
    type: string,
    payload: {
        userId: string,
        oldPassword: string,
        newPassword: string
    },
    meta: {
        onSuccess: () => void,
        onError: (string) => void,
    },
};

/** Action to indicate that we are changing a password */
export function changePasswordAction(user: string,
                                     oldPassword: string,
                                     newPassword: string,
                                     onSuccess: () => void,
                                     onError: (string) => void): ChangePasswordAction {
    return {
        type: CHANGE_PASSWORD_ACTION,
        payload: {
            userId: user,
            oldPassword: oldPassword,
            newPassword: newPassword
        },
        meta: {
            onSuccess: onSuccess,
            onError: onError
        }
    };
}

/** The sagas for this sub-module */
export const sagas = {
    [CHANGE_PASSWORD_ACTION]: function*(action: ChangePasswordAction): Generator<any, any, any> {
        try {
            yield call(request, {
                method: 'PUT',
                url: '/users/{userId}/password',
                path: {
                    userId: action.payload.userId
                },
                data: {
                    old_password: action.payload.oldPassword,
                    new_password: action.payload.newPassword
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
    changePassword: changePasswordAction
};

/** Type describing what is exposed by this sub-module */
export type PasswordModule = {
    changePassword: (string, string, string, () => void, (string) => void) => void
};
