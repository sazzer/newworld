// @flow

import React from 'react';
import {shallow} from 'enzyme';
import { MissingAccessToken } from '../HasAccessToken';

/** Create the component to test */
function setup() {
    return {
        element: shallow(<MissingAccessToken />)
    };
}

describe('MissingAccessToken', () => {
    it('Renders correctly', () => {
        const { element } = setup();

        expect(element).toMatchSnapshot();
    });
});
