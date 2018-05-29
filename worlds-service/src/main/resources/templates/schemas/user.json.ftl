{
    "$id": "/schemas/user.schema.json",
    "$schema": "http://json-schema.org/draft-07/hyper-schema#",
    "type": "object",
    "required": [
        "display_name",
        "email",
        "password"
    ],
    "properties": {
        "id":{
            "$ref": "#/definitions/id"
        },
        "display_name": {
            "type": "string"
        },
        "email": {
            "type": "string"
        },
        "username": {
            "type": "string"
        },
        "password": {
            "type": "string",
            "writeOnly": true
        }
    },
    "links": [
        {
            "rel": "self",
            "href": "/api/users/{id}",
            "templateRequired": [
                "id"
            ],
            "targetSchema": {
                "$ref": "#"
            }
        }
    ],
    "definitions": {
        "id": {
            "type": "string",
            "readOnly": true
        }
    }
}
