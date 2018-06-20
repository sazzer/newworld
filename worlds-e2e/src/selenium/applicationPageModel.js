// @flow

import { WebDriver, By, until } from 'selenium-webdriver';
import HeaderBarPageModel from './headerBarPageModel';

/** Locator for the application header bar */
const HEADER_BAR_LOCATOR = By.css('[data-test="headerBar"]');

/**
 * Page Model representing the entire application
 */
export default class ApplicationPageModel {
    /** The web driver representing the application */
    _webDriver: WebDriver;

    /**
     * Construct the page model
     * @param webdriver The web driver that represents the page
     */
    constructor(webdriver: WebDriver) {
        this._webDriver = webdriver;
    }

    /**
     * Get the header bar page model
     * @return {HeaderBarPageModel} the header bar page model
     */
    async getHeaderBar(): Promise<HeaderBarPageModel> {
        await this._webDriver.wait(until.elementLocated(HEADER_BAR_LOCATOR));
        const headerBarElement = await this._webDriver.findElement(HEADER_BAR_LOCATOR);
        return new HeaderBarPageModel(headerBarElement);
    }
}
