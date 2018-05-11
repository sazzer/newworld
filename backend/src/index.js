// @flow
import config from './config';

const msg: string = config.get('env');

console.log(msg);
