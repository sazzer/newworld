import convict from 'convict';
import manifest from './manifest';

const config = convict(manifest);
config.validate({
    allowed: 'strict',
});

export default config;
