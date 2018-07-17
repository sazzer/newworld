@wip
Feature: Getting a World by ID

  Background:
    Given a user exists with details:
      | ID           | 00000000-0000-0000-0000-000000000001 |
      | Version      | 00000000-0000-0000-0000-000000000002 |
      | Created      | 2018-05-23T09:33:00Z                 |
      | Updated      | 2018-05-23T09:34:00Z                 |
      | Email        | test@example.com                     |
      | Username     | testuser                             |
      | Display Name | Test User                            |
    And a world exists with details:
      | ID           | 00000000-0000-0000-0001-000000000001 |
      | Version      | 00000000-0000-0000-0001-000000000002 |
      | Created      | 2018-05-23T09:33:00Z                 |
      | Updated      | 2018-05-23T09:34:00Z                 |
      | Name         | testworld                            |
      | Display Name | Test World                           |
      | Description  | This is a test work                  |
      | Owner        | 00000000-0000-0000-0000-000000000001 |

  Scenario: Get an Unknown user
    When I get the world with ID "00000000-0000-0000-0001-000000000000"
    Then I get a Not Found response
    And I get a problem response of:
      | Type   | tag:grahamcox.co.uk,2018,users/problems/world-not-found |
      | Title  | World not found                                         |
      | Status | 404                                                     |

  Scenario: Get a known world
    When I get the world with ID "00000000-0000-0000-0001-000000000001"
    Then I get an OK response
    And I get a world with details:
      | ID            | 00000000-0000-0000-0001-000000000001   |
      | ETag          | "00000000-0000-0000-0001-000000000002" |
      | Last Modified | Wed, 23 May 2018 09:34:00 GMT          |
      | Name          | testworld                              |
      | Display Name  | Test World                             |
      | Description   | This is a test work                    |
      | Owner         | 00000000-0000-0000-0000-000000000001   |
