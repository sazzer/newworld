// @flow
import { By, WebElement } from 'selenium-webdriver';

/**
 * Helper to populate the fields on a form
 * @param form the form to populate
 * @param details the details to populate with
 * @param submit whether to submit the form afterwards or not
 */
export async function populateForm(form: WebElement, details: { [string] : any }, submit: boolean = true) {
    for (const fieldName of Object.keys(details)) {
        try {
            const field = await form.findElement(By.name(fieldName));
            await field.sendKeys(details[fieldName]);
            console.log(`Set field ${fieldName} to ${details[fieldName]}`);
        } catch (e) {
            console.log(`Not setting field ${fieldName}`);
        }
    }

    if (submit) {
        for (const fieldName of Object.keys(details)) {
            try {
                const field = await form.findElement(By.name(fieldName));
                await field.sendKeys('\n');
                break;
            } catch (e) {
                // Ignoring this
            }
        }

    }
}
