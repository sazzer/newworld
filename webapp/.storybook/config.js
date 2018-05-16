import 'semantic-ui-css/semantic.min.css';
import '@storybook/addon-console';

import React from 'react';
import {I18nextProvider, translate} from 'react-i18next';
import {addDecorator, configure, setAddon} from '@storybook/react';
import {withConsole} from '@storybook/addon-console';
import JSXAddon from 'storybook-addon-jsx';
import {Segment} from 'semantic-ui-react';
import i18n from "../src/i18n";

addDecorator((story) => {
    const StoryWrapper = () => {
        return (
            <div className="ui">
                <Segment>
                    {story()}
                </Segment>
            </div>
        );
    };

    const TranslatedStoryWrapper = translate(['worlds'], {wait: true})(StoryWrapper);

    return (
        <I18nextProvider i18n={i18n}>
            <TranslatedStoryWrapper />
        </I18nextProvider>
    );
});

addDecorator((storyFn, context) => withConsole()(storyFn)(context));

setAddon(JSXAddon);

configure(() => {
    const req = require.context('../src', true, /\.stories\.js$/);
    req.keys().forEach((filename) => req(filename));
}, module);
