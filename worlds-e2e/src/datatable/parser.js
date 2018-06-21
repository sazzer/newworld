/**
 * Helper to parse a data table into a consistent usable form
 */
export function datatableParser({fields}) {
    return function(datatable) {
        const result = {};
        const data = datatable.rowsHash();

        Object.keys(fields).forEach((key) => {
            const fieldConfig = fields[key];
            const value = data[fieldConfig.label];

            result[key] = value;
        });

        return result;
    }
}
