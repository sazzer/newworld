// @flow

import Resource from '../resource';

describe('Resource', () => {
    describe('No links', () => {
        const resource = new Resource({
            name: 'Graham',
            age: 36
        });

        it('Returns the expected data', () => {
            expect(resource.data).toEqual({
                name: 'Graham',
                age: 36
            });
        });

        it('Has no self link', () => {
            expect(resource.getLink('self')).toBeUndefined();
        });

        it('Has no self links list', () => {
            expect(resource.getLinks('self')).toHaveLength(0);
        });

        it('Has no links', () => {
            expect(resource.links).toEqual({});
        });
    });

    describe('Simple links', () => {
        const resource = new Resource({
            name: 'Graham',
            age: 36,
            _links: {
                self: {
                    href: '/api/users/123',
                    templated: false,
                    type: 'application/hal+json'
                }
            }
        });

        it('Returns the expected data', () => {
            expect(resource.data).toEqual({
                name: 'Graham',
                age: 36
            });
        });

        it('Has a self link', () => {
            expect(resource.getLink('self')).toEqual({
                href: '/api/users/123',
                templated: false,
                type: 'application/hal+json'
            });
        });

        it('Has no unknown link', () => {
            expect(resource.getLink('unknown')).toBeUndefined();
        });

        it('Has 1 self links list', () => {
            expect(resource.getLinks('self')).toHaveLength(1);
            expect(resource.getLinks('self')).toContainEqual({
                href: '/api/users/123',
                templated: false,
                type: 'application/hal+json'
            });
        });

        it('Has no unknown links list', () => {
            expect(resource.getLinks('unknown')).toHaveLength(0);
        });

        it('Has the correct links', () => {
            expect(resource.links).toEqual({
                self: [
                    {
                        href: '/api/users/123',
                        templated: false,
                        type: 'application/hal+json'
                    }
                ]
            });
        });
    });

    describe('Tag links', () => {
        const resource = new Resource({
            name: 'Graham',
            age: 36,
            _links: {
                'tag:grahamcox.co.uk,2018,links/users/password': {
                    href: '/api/users/123',
                    templated: false,
                    type: 'application/hal+json'
                }
            }
        });

        it('Returns the expected data', () => {
            expect(resource.data).toEqual({
                name: 'Graham',
                age: 36
            });
        });

        it('Has a password link', () => {
            expect(resource.getLink('tag:grahamcox.co.uk,2018,links/users/password')).toEqual({
                href: '/api/users/123',
                templated: false,
                type: 'application/hal+json'
            });
        });

        it('Has no unknown link', () => {
            expect(resource.getLink('unknown')).toBeUndefined();
        });

        it('Has 1 password links list', () => {
            expect(resource.getLinks('tag:grahamcox.co.uk,2018,links/users/password')).toHaveLength(1);
            expect(resource.getLinks('tag:grahamcox.co.uk,2018,links/users/password')).toContainEqual({
                href: '/api/users/123',
                templated: false,
                type: 'application/hal+json'
            });
        });

        it('Has no unknown links list', () => {
            expect(resource.getLinks('unknown')).toHaveLength(0);
        });

        it('Has the correct links', () => {
            expect(resource.links).toEqual({
                'tag:grahamcox.co.uk,2018,links/users/password': [
                    {
                        href: '/api/users/123',
                        templated: false,
                        type: 'application/hal+json'
                    }
                ]
            });
        });
    });

    describe('Repeated links', () => {
        const resource = new Resource({
            name: 'Graham',
            age: 36,
            _links: {
                article: [{
                    href: '/api/articles/1',
                    templated: false,
                    type: 'application/hal+json',
                    name: '1'
                }, {
                    href: '/api/articles/2',
                    templated: false,
                    type: 'application/hal+json',
                    name: '2'
                }]
            }
        });

        it('Returns the expected data', () => {
            expect(resource.data).toEqual({
                name: 'Graham',
                age: 36
            });
        });

        it('Fails to get an article link without a name', () => {
            expect(() => {
                resource.getLink('article')
            }).toThrow('Link relation is not unique, and no name provided');
        });

        it('Has an article link named 1', () => {
            expect(resource.getLink('article', '1')).toEqual({
                href: '/api/articles/1',
                templated: false,
                type: 'application/hal+json',
                name: '1'
            });
        });

        it('Has no article link named 0', () => {
            expect(resource.getLink('article', '0')).toBeUndefined();
        });

        it('Has 2 article links list', () => {
            expect(resource.getLinks('article')).toHaveLength(2);
            expect(resource.getLinks('article')).toContainEqual({
                href: '/api/articles/1',
                templated: false,
                type: 'application/hal+json',
                name: '1'
            });
            expect(resource.getLinks('article')).toContainEqual({
                href: '/api/articles/2',
                templated: false,
                type: 'application/hal+json',
                name: '2'
            });
        });

        it('Has the correct links', () => {
            expect(resource.links).toEqual({
                article: [
                    {
                        href: '/api/articles/1',
                        templated: false,
                        type: 'application/hal+json',
                        name: '1'
                    }, {
                        href: '/api/articles/2',
                        templated: false,
                        type: 'application/hal+json',
                        name: '2'
                    }
                ]
            });
        });
    });
});
