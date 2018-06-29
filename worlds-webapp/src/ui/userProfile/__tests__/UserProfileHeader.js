// @flow

import React from 'react';
import {shallow} from 'enzyme';
import UserProfileHeader  from '../UserProfileHeader';

/** Create the component to test */
function setup({user}) {
    return {
        element: shallow(<UserProfileHeader user={user} />)
    };
}

describe('UserProfileHeader', () => {
    it('Renders correctly', () => {
        const user = {
            id: 'id',
            displayName: 'Test User',
            username: 'testuser'
        };

        const { element } = setup({user});

        expect(element).toMatchSnapshot();
    });
});
