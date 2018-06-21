import { When, Then } from 'cucumber';
import expect from 'unexpected';
import { By, WebElement, until } from 'selenium-webdriver';
import { datatableParser } from "../datatable/parser";
import { populateForm } from "../selenium/formUtils";

const UserRegistrationParser = datatableParser({
    fields: {
        email: {
            label: 'Email Address'
        },
        username: {
            label: 'Username'
        },
        display_name: {
            label: 'Display Name'
        },
        password: {
            label: 'Password'
        },
        password2: {
            label: 'Password'
        }
    }
});

When('I register a new user with details:', async function (dataTable) {
    const parsed = UserRegistrationParser(dataTable);

    const headerBar = await this.applicationPageModel.getHeaderBar();
    await headerBar.startLogin();
    await this.browser.sleep(100);

    // This is a special case in that it interacts with Selenium directly instead of through a Page Model
    // Note as well that this code for changing windows assumes exactly two will be present.
    const windowHandles = await this.browser.getAllWindowHandles();
    const currentWindowHandle = await this.browser.getWindowHandle();
    const loginWindowHandle = windowHandles.filter((h) => h !== currentWindowHandle)[0];
    console.log(`Current Window: ${currentWindowHandle}, Login Window: ${loginWindowHandle}`);
    await this.browser.switchTo().window(loginWindowHandle);

    this.browser.wait(until.elementLocated(By.css('form')), 1000);
    const startAuthForm: WebElement = await this.browser.findElement(By.css('form'));
    await populateForm(startAuthForm, parsed);

    const registerForm: WebElement = await this.browser.findElement(By.css('form'));
    const formType = await registerForm.getAttribute('data-test');
    expect(formType, 'to equal', 'registerForm');
    await populateForm(registerForm, parsed);

    this.browser.wait(async () => {
        const windowHandles = await this.browser.getAllWindowHandles();
        return !windowHandles.includes(loginWindowHandle);
    }, 1000);

    await this.browser.switchTo().window(currentWindowHandle);
});

Then('I am not logged in', async function() {
    const headerBar = await this.applicationPageModel.getHeaderBar();
    const loginMenuPresent = await headerBar.isLoggedIn();

    expect(loginMenuPresent, 'to be false');
});

Then('I am logged in as {string}', async function(displayName) {
    const headerBar = await this.applicationPageModel.getHeaderBar();
    const loginMenuPresent = await headerBar.isLoggedIn();
    expect(loginMenuPresent, 'to be true');

    const loggedInUser = await headerBar.loggedInUser();
    expect(loggedInUser, 'to equal', displayName);
});
