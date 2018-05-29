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
    And the response matches snapshot "<Snapshot>"

    Examples:
      | Response Type       | Snapshot                                                                                                             |
      | code                | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/unsupportedResponseType-code.html.snapshot                |
      | token               | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/unsupportedResponseType-token.html.snapshot               |
      | code+id_token       | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/unsupportedResponseType-code-id_token.html.snapshot       |
      | code+token          | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/unsupportedResponseType-code-token.html.snapshot          |
      | code+id_token+token | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/unsupportedResponseType-code-id_token-token.html.snapshot |
      | other               | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/unsupportedResponseType-other.html.snapshot               |

  Scenario: Starting OpenID authorization with missing response type
    When I start OpenID Authorization with parameters:
      | scope        | openid                 |
      | client_id    | MyClientId             |
      | redirect_uri | http://www.example.com |
      | state        | MyState                |
      | nonce        | MyNonce                |
    Then I get a Bad Request response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/missingResponseType.html.snapshot"

  Scenario Outline: Starting OpenID authorization with missing required parameters: <Comment>
    When I start OpenID Authorization with parameters:
      | response_type | <Response Type> |
      | scope         | <Scope>         |
      | client_id     | <Client Id>     |
      | redirect_uri  | <Redirect URI>  |
      | state         | State           |
      | nonce         | <Nonce>         |
    Then I get a Bad Request response
    And the response matches snapshot "<Snapshot>"

    Examples: id_token
      | Response Type | Scope | Client Id | Redirect URI          | Nonce | Snapshot                                                                                                       | Comment                                                       |
      | id_token      |       | ClientId  | http://www.google.com | Nonce | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/missingParameter-id_token-scope.html.snapshot       | Missing parameter "scope" for response type "id_token"        |
      | id_token      | Scope |           | http://www.google.com | Nonce | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/missingParameter-id_token-clientId.html.snapshot    | Missing parameter "client_id" for response type "id_token"    |
      | id_token      | Scope | ClientId  |                       | Nonce | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/missingParameter-id_token-redirectUri.html.snapshot | Missing parameter "redirect_uri" for response type "id_token" |
      | id_token      | Scope | ClientId  | http://www.google.com |       | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/missingParameter-id_token-nonce.html.snapshot       | Missing parameter "nonce" for response type "id_token"        |

    Examples: id_token token
      | Response Type  | Scope | Client Id | Redirect URI          | Nonce | Snapshot                                                                                                             | Comment                                                             |
      | id_token+token |       | ClientId  | http://www.google.com | Nonce | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/missingParameter-id_token-token-scope.html.snapshot       | Missing parameter "scope" for response type "id_token token"        |
      | id_token+token | Scope |           | http://www.google.com | Nonce | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/missingParameter-id_token-token-clientId.html.snapshot    | Missing parameter "client_id" for response type "id_token token"    |
      | id_token+token | Scope | ClientId  |                       | Nonce | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/missingParameter-id_token-token-redirectUri.html.snapshot | Missing parameter "redirect_uri" for response type "id_token token" |
      | id_token+token | Scope | ClientId  | http://www.google.com |       | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/missingParameter-id_token-token-nonce.html.snapshot       | Missing parameter "nonce" for response type "id_token token"        |

  Scenario Outline: Starting OpenID authorization with valid details: <Comment>
    When I start OpenID Authorization with parameters:
      | response_type | <Response Type>        |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | <State>                |
      | nonce         | MyNonce                |
    Then I get an OK response
    And the response matches snapshot "<Snapshot>"

    Examples: id_token
      | Response Type | State | Snapshot                                                                                                  | Comment                |
      | id_token      | State | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/emailEntry-id_token-withState.html.snapshot    | id_token with state    |
      | id_token      |       | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/emailEntry-id_token-withoutState.html.snapshot | id_token without state |

    Examples: id_token token
      | Response Type  | State | Snapshot                                                                                                        | Comment                      |
      | id_token+token | State | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/emailEntry-id_token-token-withState.html.snapshot    | id_token+token with state    |
      | id_token+token |       | uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/emailEntry-id_token-token-withoutState.html.snapshot | id_token+token without state |
