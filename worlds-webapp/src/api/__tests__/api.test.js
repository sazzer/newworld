// @flow

import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import testSubject from '..';

describe('request', () => {
    const mockAxios = new MockAdapter(axios);

    describe('Simple request to static URL', () => {
        mockAxios.onGet('/url')
            .reply(200, { answer: 42 });

        let result;

        beforeAll(async () => {
            result = await testSubject({
                url: '/url'
            });
        });

        it('Returns a 200 OK', () => {
            expect(result.status).toEqual(200);
        });
        it('Returns the correct payload', () => {
            expect(result.data).toEqual({ answer: 42 });
        });
    });
    describe('Request to parameterized URL', () => {
        mockAxios.onGet('/url/abc/123')
            .reply(200, { answer: 42 });

        let result;

        beforeAll(async () => {
            result = await testSubject({
                url: '/url/{str}/{num}',
                path: {
                    str: 'abc',
                    num: '123'
                }
            });
        });

        it('Returns a 200 OK', () => {
            expect(result.status).toEqual(200);
        });
        it('Returns the correct payload', () => {
            expect(result.data).toEqual({ answer: 42 });
        });

    });
    describe('POST request to static URL', () => {
        mockAxios.onPost('/url')
            .reply(200, { answer: 42 });

        let result;

        beforeAll(async () => {
            result = await testSubject({
                url: '/url',
                method: 'POST'
            });
        });

        it('Returns a 200 OK', () => {
            expect(result.status).toEqual(200);
        });
        it('Returns the correct payload', () => {
            expect(result.data).toEqual({ answer: 42 });
        });
    });
});
