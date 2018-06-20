import { Then } from 'cucumber';
import expect from 'unexpected';

Then('I am not logged in', async function() {
    const headerBar = await this.applicationPageModel.getHeaderBar();
    const loginMenuPresent = await headerBar.isLoggedIn();

    expect(loginMenuPresent, 'to be true');
});
