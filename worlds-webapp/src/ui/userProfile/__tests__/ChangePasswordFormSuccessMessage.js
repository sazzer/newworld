// @flow

import React from 'react';
import {shallow} from 'enzyme';
import ChangePasswordFormSuccessMessage from '../ChangePasswordFormSuccessMessage';

/** Create the component to test */
function setup() {
    return {
        element: shallow(<ChangePasswordFormSuccessMessage />)
    };
}

describe('ChangePasswordFormSuccessMessage', () => {
    it('Renders correctly', () => {
        const { element } = setup();

        expect(element).toMatchSnapshot();
    });
});
