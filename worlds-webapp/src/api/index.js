// @flow

import axios from 'axios';
import urlTemplate from 'url-template';

/** Type representing the request to the server */
export type Request = {
    url: string,
    method?: string,
    headers?: { [string] : any },
    params?: { [string] : any },
    path?: { [string] : any },
    data?: any,
};

/** Type representing a response from the server */
export type Response = {
    data: any,
    status: number,
    headers: { [string] : any }
};

/** The access token to use. May be undefined. */
let accessToken: ?string;

/**
 * Make a request to the API server for the given details
 * @param config the details of the request to make
 * @return {AxiosPromise<any>}
 */
export default function request(config: Request): Promise<Response> {
    const realUrl = urlTemplate.parse(config.url)
        .expand(config.path);

    const headers = config.headers || {};
    if (accessToken) {
        headers['Authorization'] = `Bearer ${accessToken}`;
    }

    return axios.request({
        baseURL: process.env.REACT_APP_API_BASE || window.configuration.REACT_APP_API_BASE,
        timeout: 10000,
        url: realUrl,
        method: config.method || 'GET',
        headers: headers,
        params: config.params,
        data: config.data
    });
}

/**
 * Set the Access Token to use for all future requests
 * @param newAccessToken the access token to use
 */
export function setAccessToken(newAccessToken?: string) {
    accessToken = newAccessToken;
}
