// @flow

import React from 'react';
import {shallow} from 'enzyme';
import {UserMenu} from '../UserMenu';

/** Create the component to test */
function setup({hasCurrentUser} = {hasCurrentUser: false}) {
    return {
        element: shallow(<UserMenu hasCurrentUser={hasCurrentUser} />)
    };
}

describe('UserMenu', () => {
    it('Renders correctly when no current user is present', () => {
        const { element } = setup({hasCurrentUser: false});

        expect(element).toMatchSnapshot();
    });

    it('Renders correctly when a current user is present', () => {
        const { element } = setup({hasCurrentUser: true});

        expect(element).toMatchSnapshot();
    });

});
