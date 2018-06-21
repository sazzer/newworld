// @flow

import { WebElement, By, error } from 'selenium-webdriver';

/** Selector for finding the Login Menu option */
const LOGIN_MENU_SELECTOR = By.css('[data-test="loginMenu"]');


/** Selector for finding the Logged In User display */
const LOGGED_IN_USER_SELECTOR = By.css('[data-test="loggedInUser"]');

/**
 * Page Model representing the header bar
 */
export default class HeaderBarPageModel {
    /** The root element of the header bar */
    _rootElement: WebElement;

    /**
     * Construct the page model
     * @param element The web driver that represents the page model
     */
    constructor(element: WebElement) {
        this._rootElement = element;
    }

    /**
     * Check if we are logged in or not
     * @return {Promise<void>}
     */
    async isLoggedIn() {
        try {
            await this._rootElement.findElement(LOGIN_MENU_SELECTOR);
            return false;
        } catch (e) {
            if (e instanceof error.NoSuchElementError) {
                return true;
            } else {
                throw e;
            }
        }
    }

    /**
     * Get the display name of the logged in user
     */
    async loggedInUser() {
        const loggedInUser = await this._rootElement.findElement(LOGGED_IN_USER_SELECTOR);
        return loggedInUser.getText();
    }

    /**
     * Start the login process
     */
    async startLogin() {
        const loginLink = await this._rootElement.findElement(LOGIN_MENU_SELECTOR);
        await loginLink.click();
    }
}
