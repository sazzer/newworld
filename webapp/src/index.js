// @flow
import 'semantic-ui-css/semantic.min.css';

import React from 'react';
import ReactDOM from 'react-dom';
import { ConnectedRouter as Router } from 'react-router-redux';
import { Provider } from 'react-redux';
import { store, history } from './redux'
import App from './App';
import registerServiceWorker from './registerServiceWorker';

/**
 * The wrapper around the main application to set everything up
 * @return {*} the main application
 */
const AppWrapper = () => (
    <Provider store={store}>
        <Router history={history}>
            <App />
        </Router>
    </Provider>
);

ReactDOM.render(<AppWrapper />, document.getElementById('root'));
registerServiceWorker();
