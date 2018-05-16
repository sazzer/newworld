// @flow

import axios from 'axios';
import type { AxiosXHRConfig, $AxiosXHR as AxiosXHR } from 'axios';

const httpClient = axios.create({
    baseURL: process.env.REACT_APP_API_URL || window.API_URL_BASE,
    timeout: 1000
});

export default function request<Req>(requestConfig: AxiosXHRConfig<Req, any>): Promise<AxiosXHR<Req, any>> {
    return httpClient.request(requestConfig);
}
