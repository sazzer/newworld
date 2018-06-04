Feature: Logging in

  Background:
    Given a user exists with details:
      | Email         | test@example.com                                                                                                                                                             |
      # This Password Hash and Password Salt equate to the password "MyPassword"
      | Password Hash | GsZ+KUAgJgW6OYDAVbo8hzeSoWtRW7Q0uPgvk1OrA2Y=                                                                                                                                 |
      | Password Salt | RJMrJIln2gXN2SzZQ0YI3uqZXSLZBfGFj/nn7wTfwMXwfp/BLI7EKufDw7HejLXiSvByLjjpjRMGkrVqPjEyrJswDZa32/RSkWnvtlK4uIbcLMwyivvLWcVSqZP6Oqe+TDEz3HjpiZ6VCxB13QSfe4rYLtglGFr6JgnfFyK7duw= |

  Scenario: When I enter a known email address
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

  Scenario: When I log in with an unknown email
    When I log in with parameters:
      | response_type | id_token token         |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | unknown@example.com    |
      | password      | Incorrect              |
    Then I get an OK response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/login-unknownEmail.html.snapshot"

  Scenario: When I log in with an incorrect password
    When I log in with parameters:
      | response_type | id_token token         |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
      | password      | Incorrect              |
    Then I get an OK response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/login-invalidPassword.html.snapshot"

  Scenario: When I log in with no password
    When I log in with parameters:
      | response_type | id_token token         |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
    Then I get an OK response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/login-missingPassword.html.snapshot"

  @ignore
  Scenario: When I successfully log in
    When I log in with parameters:
      | response_type | id_token token         |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
      | password      | MyPassword             |
    Then I get a See Other response
    And I am redirected to the callback address:
      | URL Scheme            | http            |
      | URL Host              | www.example.com |
      | Query String Present  | No              |
      | Access Token Fragment | Present         |
      | Token Type Fragment   | Bearer          |
      | Expires In Fragment   | 31536000        |
      | State Fragment        | MyState         |
