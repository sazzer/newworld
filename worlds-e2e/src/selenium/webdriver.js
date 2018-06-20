// @flow

import { Builder, ThenableWebDriver } from 'selenium-webdriver';

/**
 * Open a new web browser to control
 */
export function openBrowser(): Promise<ThenableWebDriver> {
    const driver = new Builder()
        .forBrowser('chrome')
        .build();

    return driver;
}
