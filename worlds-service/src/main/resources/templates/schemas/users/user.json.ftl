{
    "$id": "/schemas/user.schema.json",
    "$schema": "http://json-schema.org/draft-07/hyper-schema#",
    "type": "object",
    "required": [
        "username",
        "display_name"
    ],
    "properties": {
        "id":{
            "$ref": "#/definitions/id"
        },
        "display_name": {
            "type": "string"
        },
        "email": {
            "type": "string",
            "format": "email"
        },
        "username": {
            "type": "string"
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
            },
            "submissionSchema": {
                "required": [
                    "username",
                    "display_name",
                    "email"
                ]
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
