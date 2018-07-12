Feature: Changing the users password

  Background:
    Given a user exists with details:
      | Email         | test@example.com                                                                                                                                                             |
      | Username      | testuser                                                                                                                                                                     |
      | Display Name  | Test User                                                                                                                                                                    |
      # This Password Hash and Password Salt equate to the password "MyPassword"
      | Password Hash | GsZ+KUAgJgW6OYDAVbo8hzeSoWtRW7Q0uPgvk1OrA2Y=                                                                                                                                 |
      | Password Salt | RJMrJIln2gXN2SzZQ0YI3uqZXSLZBfGFj/nn7wTfwMXwfp/BLI7EKufDw7HejLXiSvByLjjpjRMGkrVqPjEyrJswDZa32/RSkWnvtlK4uIbcLMwyivvLWcVSqZP6Oqe+TDEz3HjpiZ6VCxB13QSfe4rYLtglGFr6JgnfFyK7duw= |
    And I opened the home page
    And I log in with details:
      | Email Address | test@example.com |
      | Password      | MyPassword       |

  Scenario: Successfully change password
    Given I view the users profile
    When I update the users password:
      | Old Password    | MyPassword  |
      | New Password    | NewPassword |
      | Repeat Password | NewPassword |
    Then the password is updated successfully

  Scenario: Successfully change password and log in with it
    Given I view the users profile
    When I update the users password:
      | Old Password    | MyPassword  |
      | New Password    | NewPassword |
      | Repeat Password | NewPassword |
    And I opened the home page
    And I log in with details:
      | Email Address | test@example.com |
      | Password      | NewPassword      |
    Then I am logged in as "Test User"

  Scenario: Fail to change password because the old password was wrong
    Given I view the users profile
    When I update the users password:
      | Old Password    | Wrong       |
      | New Password    | NewPassword |
      | Repeat Password | NewPassword |
    Then updating the users password failed with "Incorrect old password."

  @manual
  Scenario: Fail to change password because the new passwords don't match
    Given I view the users profile
    When I update the users password:
      | Old Password    | MyPassword  |
      | New Password    | NewPassword |
      | Repeat Password | NewPa55word |
    Then updating the users password failed with "The new passwords do not match."

