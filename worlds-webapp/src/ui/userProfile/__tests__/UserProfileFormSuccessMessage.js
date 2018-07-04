// @flow

import React from 'react';
import {shallow} from 'enzyme';
import UserProfileFormSuccessMessage from '../UserProfileFormSuccessMessage';

/** Create the component to test */
function setup() {
    return {
        element: shallow(<UserProfileFormSuccessMessage />)
    };
}

describe('UserProfileFormSuccessMessage', () => {
    it('Renders correctly', () => {
        const { element } = setup();

        expect(element).toMatchSnapshot();
    });
});
