Feature: Account Registration

  Scenario: When I enter an unknown email address
    When I authenticate with parameters:
      | response_type | id_token               |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
    Then I get an OK response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/startRegister.html.snapshot"
    And no user now exists with email address "test@example.com"

  Scenario: When I register with an unsupported response type
    When I register a new user with parameters:
      | response_type | code                   |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
    Then I get a Bad Request response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/register-unsupportedResponseType.html.snapshot"
    And no user now exists with email address "test@example.com"

  Scenario: When I register without filling in the form
    When I register a new user with parameters:
      | response_type | id_token               |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
    Then I get an OK response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/register-blankForm.html.snapshot"
    And no user now exists with email address "test@example.com"

  Scenario: When I register with mismatched passwords
    When I register a new user with parameters:
      | response_type | id_token               |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
      | password      | MyPassword             |
      | password2     | MyPassword1            |
      | username      | MyUsername             |
    Then I get an OK response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/register-mismatchedPasswords.html.snapshot"
    And no user now exists with email address "test@example.com"

  Scenario: When I register a duplicate email
    Given a user exists with details:
      | Email | test@example.com |
    When I register a new user with parameters:
      | response_type | id_token               |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
      | password      | MyPassword             |
      | password2     | MyPassword             |
      | username      | MyUsername             |
    Then I get an OK response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/register-duplicateEmail.html.snapshot"

  Scenario: When I register a duplicate username
    Given a user exists with details:
      | Username | MyUsername |
    When I register a new user with parameters:
      | response_type | id_token               |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
      | password      | MyPassword             |
      | password2     | MyPassword             |
      | username      | MyUsername             |
    Then I get an OK response
    And the response matches snapshot "uk/co/grahamcox/worlds/service/acceptance/openid/snapshots/register-duplicateUsername.html.snapshot"
    And no user now exists with email address "test@example.com"

  Scenario: When I successfully register a new user
    When I register a new user with parameters:
      | response_type | id_token token         |
      | scope         | openid                 |
      | client_id     | MyClientId             |
      | redirect_uri  | http://www.example.com |
      | state         | MyState                |
      | nonce         | MyNonce                |
      | email         | test@example.com       |
      | password      | MyPassword             |
      | password2     | MyPassword             |
      | username      | MyUsername             |
    Then I get a See Other response
    And a user now exists with email address "test@example.com"
    And I am redirected to the callback address:
      | URL Scheme            | http            |
      | URL Host              | www.example.com |
      | Query String Present  | No              |
      | Access Token Fragment | Present         |
      | Token Type Fragment   | Bearer          |
      | Expires In Fragment   | 31536000        |
      | State Fragment        | MyState         |
