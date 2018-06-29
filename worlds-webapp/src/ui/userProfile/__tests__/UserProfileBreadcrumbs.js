// @flow

import React from 'react';
import {shallow} from 'enzyme';
import UserProfileBreadcrumbs  from '../UserProfileBreadcrumbs';

/** Create the component to test */
function setup({user}) {
    return {
        element: shallow(<UserProfileBreadcrumbs user={user} />)
    };
}

describe('UserProfileBreadcrumbs', () => {
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
