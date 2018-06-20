import { When, Before, BeforeAll, AfterAll } from 'cucumber';
import { By, until } from 'selenium-webdriver';
import { openBrowser} from "./webdriver";
import ApplicationPageModel from './applicationPageModel';

let browser;

BeforeAll(async () => {
    console.log('Opening browser');
    browser = await openBrowser();
});

Before(function () {
    this.applicationPageModel = new ApplicationPageModel(browser);
});

AfterAll(async () => {
    console.log('Quitting browser');
    await browser.quit()
});


When('I open the home page', async () => {
    await browser.get('http://localhost:3000');
    await browser.wait(until.elementLocated(By.id('App')));
});
