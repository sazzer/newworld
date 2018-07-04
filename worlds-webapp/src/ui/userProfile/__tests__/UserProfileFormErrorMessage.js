// @flow

import React from 'react';
import {shallow} from 'enzyme';
import UserProfileFormErrorMessage from '../UserProfileFormErrorMessage';

/** Create the component to test */
function setup({errorCode}: {errorCode?: string} = {}) {
    return {
        element: shallow(<UserProfileFormErrorMessage errorCode={errorCode} />)
    };
}

describe('UserProfileFormErrorMessage', () => {
    it('Renders correctly with no error code', () => {
        const { element } = setup();

        expect(element).toMatchSnapshot();
    });
    it('Renders correctly with an unrecognized error code', () => {
        const { element } = setup({errorCode: 'unrecognized'});

        expect(element).toMatchSnapshot();
    });
    it('Renders correctly with a supported error code', () => {
        const { element } = setup({errorCode: 'tag:grahamcox.co.uk,2018,users/problems/duplicate-username'});

        expect(element).toMatchSnapshot();
    });
});
