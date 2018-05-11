// @flow
import bristol from 'bristol';
import config from './config';

bristol.addTarget('console')
    .withFormatter('human');

const msg: string = config.get('env');

bristol.warn('Starting', { msg });
