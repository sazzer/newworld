Feature: Starting OpenID Authorization

  Scenario Outline: Starting OpenID Authorization with unsupported response type <Response Type>
    When I start OpenID Authorization with parameters:
      | response_type | <Response Type>        |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
    Then I get a Bad Request response
    And I get an OpenID Authorization error message of "<Error Message>"

    Examples:
      | Response Type       | Error Message                                                              |
      | code                | The response_type parameter was not a supported value: code                |
      | token               | The response_type parameter was not a supported value: token               |
      | code+id_token       | The response_type parameter was not a supported value: code id_token       |
      | code+token          | The response_type parameter was not a supported value: code token          |
      | code+id_token+token | The response_type parameter was not a supported value: code id_token token |
      | other               | The response_type parameter was not a supported value: other               |

  Scenario: Starting OpenID authorization with missing response type
    When I start OpenID Authorization with parameters:
      | scope        | openid                 |
      | client_id    | MyClientId             |
      | redirect_uri | http://www.example.com |
      | state        | MyState                |
      | nonce        | MyNonce                |
    Then I get a Bad Request response
    And I get an OpenID Authorization error message of "A required parameter was missing: response_type"

  Scenario Outline: Starting OpenID authorization with missing required parameters: <Comment>
    When I start OpenID Authorization with parameters:
      | response_type | <Response Type> |
      | scope         | <Scope>         |
      | client_id     | <Client Id>     |
      | redirect_uri  | <Redirect URI>  |
      | state         | State           |
      | nonce         | <Nonce>         |
    Then I get a Bad Request response
    And I get an OpenID Authorization error message of "<Error Message>"

    Examples: id_token
      | Response Type | Scope | Client Id | Redirect URI          | Nonce | Error Message                                  | Comment                                                       |
      | id_token      |       | ClientId  | http://www.google.com | Nonce | A required parameter was missing: scope        | Missing parameter "scope" for response type "id_token"        |
      | id_token      | Scope |           | http://www.google.com | Nonce | A required parameter was missing: client_id    | Missing parameter "client_id" for response type "id_token"    |
      | id_token      | Scope | ClientId  |                       | Nonce | A required parameter was missing: redirect_uri | Missing parameter "redirect_uri" for response type "id_token" |
      | id_token      | Scope | ClientId  | http://www.google.com |       | A required parameter was missing: nonce        | Missing parameter "nonce" for response type "id_token"        |

    Examples: id_token token
      | Response Type  | Scope | Client Id | Redirect URI          | Nonce | Error Message                                  | Comment                                                             |
      | id_token+token |       | ClientId  | http://www.google.com | Nonce | A required parameter was missing: scope        | Missing parameter "scope" for response type "id_token token"        |
      | id_token+token | Scope |           | http://www.google.com | Nonce | A required parameter was missing: client_id    | Missing parameter "client_id" for response type "id_token token"    |
      | id_token+token | Scope | ClientId  |                       | Nonce | A required parameter was missing: redirect_uri | Missing parameter "redirect_uri" for response type "id_token token" |
      | id_token+token | Scope | ClientId  | http://www.google.com |       | A required parameter was missing: nonce        | Missing parameter "nonce" for response type "id_token token"        |

  Scenario Outline: Starting OpenID authorization with valid details: <Comment>
    When I start OpenID Authorization with parameters:
      | response_type | <Response Type>        |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | <State>                |
      | nonce         | MyNonce                |
    Then I get an OK response
    And I get an OpenID Email entry screen with details:
      | response_type | <Returned Response Type> |
      | scope         | openid                   |
      | client_id     | MyClientId               |
      | redirect_uri  | http://www.example.com   |
      | state         | <State>                  |
      | nonce         | MyNonce                  |

    Examples: id_token
      | Response Type | Returned Response Type | State | Comment                |
      | id_token      | id_token               | State | id_token with state    |
      | id_token      | id_token               |       | id_token without state |

    Examples: id_token token
      | Response Type  | Returned Response Type | State | Comment                      |
      | id_token+token | id_token token         | State | id_token+token with state    |
      | id_token+token | id_token token         |       | id_token+token without state |
