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

    Examples:
      | Response Type       |
      | code                |
      | token               |
      | code id_token       |
      | code token          |
      | code id_token token |
      | other               |
