@wip
Feature: Getting a User by ID

  Scenario: Get an Unknown user
    When I get the user with ID "00000000-0000-0000-0000-000000000000"
    Then I get a Not Found response
    And I get a problem response of:
      | Type   | tag:grahamcox.co.uk,2018,users/problems/user-not-found |
      | Title  | User not found                                         |
      | Status | 404                                                    |

  @ignore
  Scenario: Get a known user
    Given a user exists with details:
      | ID           | 00000000-0000-0000-0000-000000000001 |
      | Version      | 00000000-0000-0000-0000-000000000002 |
      | Created      | 2018-05-23T09:33:00Z                 |
      | Updated      | 2018-05-23T09:34:00Z                 |
      | Email        | test@example.com                     |
      | Display Name | Test User                            |
    When I get the user with ID "00000000-0000-0000-0000-000000000001"
    Then I get an OK response
    And I get a user with details:
      | ID            | 00000000-0000-0000-0000-000000000001 |
      | ETag          | 00000000-0000-0000-0000-000000000002 |
      | Last Modified | 2018-05-23T09:34:00Z                 |
      | Email         | test@example.com                     |
      | Display Name  | Test User                            |
