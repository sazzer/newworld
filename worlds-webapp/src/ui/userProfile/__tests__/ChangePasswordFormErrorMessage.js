// @flow

import React from 'react';
import {shallow} from 'enzyme';
import ChangePasswordFormErrorMessage from '../ChangePasswordFormErrorMessage';

/** Create the component to test */
function setup({errorCode}: {errorCode?: string} = {}) {
    return {
        element: shallow(<ChangePasswordFormErrorMessage errorCode={errorCode} />)
    };
}

describe('ChangePasswordFormErrorMessage', () => {
    it('Renders correctly with no error code', () => {
        const { element } = setup();

        expect(element).toMatchSnapshot();
    });
    it('Renders correctly with an unrecognized error code', () => {
        const { element } = setup({errorCode: 'unrecognized'});

        expect(element).toMatchSnapshot();
    });
    it('Renders correctly with a special code of passwordMismatch', () => {
        const { element } = setup({errorCode: 'passwordMismatch'});

        expect(element).toMatchSnapshot();
    });
    it('Renders correctly with an expected code', () => {
        const { element } = setup({errorCode: 'tag:grahamcox.co.uk,2018,users/problems/invalid-password'});

        expect(element).toMatchSnapshot();
    });
});
