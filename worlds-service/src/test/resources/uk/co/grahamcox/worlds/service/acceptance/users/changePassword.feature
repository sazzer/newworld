Feature: Changing a users password

  Background:
    Given a user exists with details:
      | ID            | 00000000-0000-0000-0000-000000000001                                                                                                                                         |
      | Version       | 00000000-0000-0000-0000-000000000002                                                                                                                                         |
      | Created       | 2018-05-23T09:33:00Z                                                                                                                                                         |
      | Updated       | 2018-05-23T09:34:00Z                                                                                                                                                         |
      | Email         | test@example.com                                                                                                                                                             |
      | Username      | testuser                                                                                                                                                                     |
      | Display Name  | Test User                                                                                                                                                                    |
      # This Password Hash and Password Salt equate to the password "MyPassword"
      | Password Hash | GsZ+KUAgJgW6OYDAVbo8hzeSoWtRW7Q0uPgvk1OrA2Y=                                                                                                                                 |
      | Password Salt | RJMrJIln2gXN2SzZQ0YI3uqZXSLZBfGFj/nn7wTfwMXwfp/BLI7EKufDw7HejLXiSvByLjjpjRMGkrVqPjEyrJswDZa32/RSkWnvtlK4uIbcLMwyivvLWcVSqZP6Oqe+TDEz3HjpiZ6VCxB13QSfe4rYLtglGFr6JgnfFyK7duw= |

  Scenario: Change a users password when not authenticated
    When I change the password of the user with ID "00000000-0000-0000-0000-000000000001" from "MyPassword" to "NewPassword"
    Then I get an Unauthorized response

  Scenario: Change a users password when authenticated as a different user
    Given I have authenticated as user "00000000-0000-0000-0000-000000000002"
    When I change the password of the user with ID "00000000-0000-0000-0000-000000000001" from "MyPassword" to "NewPassword"
    Then I get a Forbidden response

  Scenario: Change a users password with the wrong old password
    Given I have authenticated as user "00000000-0000-0000-0000-000000000001"
    When I change the password of the user with ID "00000000-0000-0000-0000-000000000001" from "WrondPassword" to "NewPassword"
    Then I get a Conflict response
    And I get a problem response of:
      | Type   | tag:grahamcox.co.uk,2018,users/problems/invalid-password |
      | Title  | Invalid Password                                         |
      | Status | 409                                                      |

  Scenario: Successfully change a users password
    Given I have authenticated as user "00000000-0000-0000-0000-000000000001"
    When I change the password of the user with ID "00000000-0000-0000-0000-000000000001" from "MyPassword" to "NewPassword"
    Then I get a No Content response
