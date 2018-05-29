Feature: Logging in

  Scenario: When I enter a known email address
    Given a user exists with details:
      | Email        | test@example.com                     |
    When I authenticate with parameters:
      | response_type | id_token               |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
    Then I get an OK response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/startLogin.html.snapshot"
