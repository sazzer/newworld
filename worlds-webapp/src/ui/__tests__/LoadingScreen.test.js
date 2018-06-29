// @flow

import React from 'react';
import {shallow} from 'enzyme';
import LoadingScreen from '../LoadingScreen';

/** Create the component to test */
function setup() {
    return {
        element: shallow(<LoadingScreen />)
    };
}

describe('LoadingScreen', () => {
    it('Renders correctly', () => {
        const { element } = setup();

        expect(element).toMatchSnapshot();
    });
});
