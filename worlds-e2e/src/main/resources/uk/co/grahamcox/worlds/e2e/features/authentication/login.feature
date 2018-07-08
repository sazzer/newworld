Feature: User Registration

  Background:
    Given a user exists with details:
      | Email         | test@example.com                                                                                                                                                             |
      | Display Name  | Test User                                                                                                                                                                    |
      # This Password Hash and Password Salt equate to the password "MyPassword"
      | Password Hash | GsZ+KUAgJgW6OYDAVbo8hzeSoWtRW7Q0uPgvk1OrA2Y=                                                                                                                                 |
      | Password Salt | RJMrJIln2gXN2SzZQ0YI3uqZXSLZBfGFj/nn7wTfwMXwfp/BLI7EKufDw7HejLXiSvByLjjpjRMGkrVqPjEyrJswDZa32/RSkWnvtlK4uIbcLMwyivvLWcVSqZP6Oqe+TDEz3HjpiZ6VCxB13QSfe4rYLtglGFr6JgnfFyK7duw= |
    And I opened the home page

  Scenario: Successfully register a new user
    When I log in with details:
      | Email Address    | test@example.com |
      | Password         | MyPassword       |
    Then I am logged in as "Test User"
