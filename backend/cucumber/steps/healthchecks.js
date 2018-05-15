import { When, Then } from 'cucumber';
import test from 'unexpected';
import request, { getLastResponse } from './requester';

When('I get the health of the system', () => {
    request('/health');
});

Then('the system is healthy', async () => {
    const response = await getLastResponse();

    test(response.status, 'to equal', 200);
});
