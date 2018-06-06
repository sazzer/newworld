import React from 'react';
import ReactDOM from 'react-dom';
import {I18nextProvider, translate} from 'react-i18next';
import 'semantic-ui-css/semantic.min.css';
import i18n from './i18n';
import App from './ui/App';
import registerServiceWorker from './registerServiceWorker';

/**
 * The contents of the app, wrapped in the translations layer
 */
const TranslatedAppContents = translate(['worlds'], {wait: true})(App);


/**
 * The wrapper around the main application to set everything up
 * @return {*} the main application
 */
const AppWrapper = () => (
    <I18nextProvider i18n={ i18n }>
        <TranslatedAppContents />
    </I18nextProvider>
);

ReactDOM.render(<AppWrapper />, document.getElementById('root'));
registerServiceWorker();
