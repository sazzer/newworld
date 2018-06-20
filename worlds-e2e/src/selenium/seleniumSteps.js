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
    const url = process.env.WEB_URL;
    console.log(`Opening page to ${url}`);
    await browser.get(url);
    await browser.wait(until.elementLocated(By.id('App')));
});
