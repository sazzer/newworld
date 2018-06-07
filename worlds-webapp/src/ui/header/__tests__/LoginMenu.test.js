// @flow

import React from 'react';
import {shallow} from 'enzyme';
import {LoginMenu} from '../LoginMenu';

/** Create the component to test */
function setup() {
    const onClickLogin = jest.fn();

    return {
        onClickLogin,
        element: shallow(<LoginMenu onClickLogin={onClickLogin()} />)
    };
}

describe('LoginMenu', () => {
    it('Renders correctly', () => {
        const { element } = setup();

        expect(element).toMatchSnapshot();
    });

    it('Reacts to being clicked', () => {
        const { onClickLogin, element } = setup();

        element.simulate('click');

        expect(onClickLogin).toHaveBeenCalledTimes(1);
    });
});
