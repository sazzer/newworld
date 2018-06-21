import { Given, When, Before, BeforeAll, AfterAll } from 'cucumber';
import { By, until } from 'selenium-webdriver';
import { openBrowser} from "./webdriver";
import ApplicationPageModel from './applicationPageModel';

let browser;

BeforeAll(async () => {
    console.log('Opening browser');
    browser = await openBrowser();
});

Before(function () {
    this.browser = browser;
    this.applicationPageModel = new ApplicationPageModel(browser);
});

AfterAll(async () => {
    console.log('Quitting browser');
    await browser.quit()
});

async function openHomePage() {
    const url = process.env.WEB_URL;
    console.log(`Opening page to ${url}`);
    await browser.get(url);
    await browser.wait(until.elementLocated(By.id('App')));
}

Given('I opened the home page', openHomePage);
When('I open the home page', openHomePage);
