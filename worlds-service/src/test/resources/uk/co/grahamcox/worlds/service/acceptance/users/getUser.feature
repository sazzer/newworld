@wip
Feature: Getting a User by ID

  Background:
    Given a user exists with details:
      | ID           | 00000000-0000-0000-0000-000000000001 |
      | Version      | 00000000-0000-0000-0000-000000000002 |
      | Created      | 2018-05-23T09:33:00Z                 |
      | Updated      | 2018-05-23T09:34:00Z                 |
      | Email        | test@example.com                     |
      | Username     | testuser                             |
      | Display Name | Test User                            |

  Scenario: Get an Unknown user
    Given I have authenticated as user "00000000-0000-0000-0000-000000000000"
    When I get the user with ID "00000000-0000-0000-0000-000000000000"
    Then I get a Not Found response
    And I get a problem response of:
      | Type   | tag:grahamcox.co.uk,2018,users/problems/user-not-found |
      | Title  | User not found                                         |
      | Status | 404                                                    |

  Scenario: Get a known user when not authenticated
    When I get the user with ID "00000000-0000-0000-0000-000000000001"
    Then I get an Unauthorized response

  @ignore
  Scenario: Get a known user when authenticated as a different user
    Given I have authenticated as user "00000000-0000-0000-0000-000000000002"
    When I get the user with ID "00000000-0000-0000-0000-000000000001"
    Then I get an Forbidden response

  Scenario: Get a known user when authenticated
    Given I have authenticated as user "00000000-0000-0000-0000-000000000001"
    When I get the user with ID "00000000-0000-0000-0000-000000000001"
    Then I get an OK response
    And I get a user with details:
      | ID            | 00000000-0000-0000-0000-000000000001   |
      | ETag          | "00000000-0000-0000-0000-000000000002" |
      | Last Modified | Wed, 23 May 2018 09:34:00 GMT          |
      | Email         | test@example.com                       |
      | Username      | testuser                               |
      | Display Name  | Test User                              |
