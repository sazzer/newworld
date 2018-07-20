// @flow

import type Link from './link';

/** Representation of a single link in a HAL resource */
export type HalLink = {
    href: string,
    templated: boolean,
    type?: string,
    name?: string
};

/** Representation of a collection of links in a HAL resource */
export type HalLinks = Array<HalLink>;

/** Representation of a HAL payload */
export type HalPayload = {
    [string] : any,
    _links?: {
        [string] : HalLink | HalLinks
    },
    _embedded?: {
        [string] : any
    }
};

/**
 * Representation of a resource as loaded from the server
 */
export default class Resource {
    /** The actual resource data */
    _data: { [string] : any };

    /** The resource links */
    _links: { [string] : HalLinks };

    /**
     * Construct the resource
     * @param payload the payload
     */
    constructor(payload: HalPayload) {
        const { _links, _embedded, ...data} = payload;

        this._links = {};
        if (_links) {
            Object.keys(_links)
                .forEach(key => {
                    const links = _links[key];
                    this._links[key] = [].concat.apply([], [links]);
                });
        }

        this._data = data;
    }

    /**
     * Get the actual data that represents the resource
     */
    get data(): { [string] : any } {
        return this._data;
    }

    /**
     * Get the entire set of links
     */
    get links(): { [string] : Array<Link> } {
        return this._links;
    }

    /**
     * Get the first/only link that has the given relation
     * @param rel the relation of the link
     * @param name The name of the link, if needed to disambiguate it
     */
    getLink(rel: string, name?: string): ?Link {
        const links = this.getLinks(rel);

        let result;
        if (links.length > 1) {
            if (name === undefined) {
                throw new Error('Link relation is not unique, and no name provided');
            } else {
                result = links.find(link => link.name === name)
            }
        } else if (links.length === 1) {
            result = links[0];
        }

        return result;
    }

    /**
     * Get the array of links that have the given relation
     * @param rel the relation of the links
     */
    getLinks(rel: string): Array<Link> {
        return this._links[rel] || [];
    }
}
