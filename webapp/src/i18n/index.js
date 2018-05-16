// @flow

import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import defaultTranslations from './messages.json';

i18n
    .use(LanguageDetector)
    .init({
        resources: {
            dev: {
                worlds: defaultTranslations
            }
        },

        // have a common namespace used around the full app
        ns: ['space'],
        defaultNS: 'worlds',

        debug: true,

        interpolation: {
            escapeValue: false
        },

        parseMissingKeyHandler: (key) => `!!${key}!!`
    });

export default i18n;
