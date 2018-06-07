// @flow

import {createStore} from 'redux-box';
import {routerMiddleware, routerReducer} from 'react-router-redux';
import createHistory from 'history/createBrowserHistory';
import {module as authModule} from './auth';

export const history = createHistory();

const reduxBoxConfig = {
    middlewares: [
        routerMiddleware(history)
    ],
    reducers: {
        routing: routerReducer
    }
};

export const store = createStore([
    authModule
], reduxBoxConfig);
