// @flow

import axios from 'axios';
import type { AxiosXHRConfig, $AxiosXHR as AxiosXHR } from 'axios';
import { Before } from 'cucumber';
import config from '../config';

/** The requester to use to make API calls */
const requester = axios.create({
    baseURL: config.get('api'),
    timeout: 1000,
});

let lastResponse: ?Promise<AxiosXHR<any, any>>;

/**
 * Make a request to the API Server and get the response back
 * @param {object} requestConfig The configuration to pass to Axios
 */
function request<Req>(requestConfig: AxiosXHRConfig<Req, any>): Promise<AxiosXHR<Req, any>> {
    lastResponse = requester.request(requestConfig);
    return lastResponse;
}
export default request;

/**
 * Get the last response from the API calls, if there is one
 */
export function getLastResponse<Res>(): ?Promise<AxiosXHR<any, Res>> {
    return lastResponse;
}

Before(() => {
    lastResponse = undefined;
});
