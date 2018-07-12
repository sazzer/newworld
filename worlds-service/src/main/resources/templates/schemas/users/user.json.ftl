{
    "$id": "/schemas/users/user.json",
    "$schema": "http://json-schema.org/draft-07/hyper-schema#",
    "type": "object",
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
    "required": [
        "username",
        "display_name"
    ],
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
                "$ref": "#",
                "required": [
                    "username",
                    "display_name",
                    "email"
                ]
            },
            "targetHints": {
                "http": {
                    "methods": ["GET", "PUT"]
                }
            }
        }, {
            "rel": "tag:grahamcox.co,uk,2018,users/password",
            "href": "/api/users/{id}/password",
            "templateRequired": [
                "id"
            ],
            "submissionSchema": {
                "properties": {
                    "old_password": {
                        "type": "string"
                    },
                    "new_password": {
                        "type": "string"
                    }
                },
                "required": [
                    "old_password",
                    "new_password"
                ]
            },
            "targetHints": {
                "http": {
                    "methods": ["PUT"]
                }
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
