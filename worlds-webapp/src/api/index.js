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

/**
 * Make a request to the API server for the given details
 * @param config the details of the request to make
 * @return {AxiosPromise<any>}
 */
export default function request(config: Request): Promise<Response> {
    const realUrl = urlTemplate.parse(config.url)
        .expand(config.path);

    return axios.request({
        baseURL: process.env.REACT_APP_API_BASE,
        timeout: 10000,
        url: realUrl,
        method: config.method || 'GET',
        headers: config.headers,
        params: config.params,
        data: config.data
    });
}
