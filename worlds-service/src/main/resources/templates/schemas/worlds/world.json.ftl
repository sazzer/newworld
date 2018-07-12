{
    "$id": "/schemas/worlds/world.json",
    "$schema": "http://json-schema.org/draft-07/hyper-schema#",
    "type": "object",
    "properties": {
        "id":{
            "$ref": "#/definitions/id"
        },
        "name": {
            "type": "string"
        },
        "display_name": {
            "type": "string"
        },
        "description": {
            "type": "string"
        },
        "owner": {
            "$ref": "#/definitions/owner"
        }
    },
    "required": [
        "name"
    ],
    "links": [
        {
            "rel": "self",
            "href": "/api/worlds/{id}",
            "templateRequired": [
                "id"
            ],
            "targetSchema": {
                "$ref": "#"
            }
        }, {
            "rel": "tag:grahamcox.co,uk,2018,worlds/owner",
            "href": "/api/users/{owner}",
            "templateRequired": [
                "owner"
            ],
            "targetSchema": {
                "$ref": "../users/user.json"
            }
        }
    ],
    "definitions": {
        "id": {
            "type": "string",
            "readOnly": true
        },
        "owner": {
            "type": "string",
            "readOnly": true
        }
    }
}
