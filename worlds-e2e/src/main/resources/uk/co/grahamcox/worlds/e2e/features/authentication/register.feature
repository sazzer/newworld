Feature: User Registration

  Background:
    Given I opened the home page

  Scenario: Successfully register a new user
    When I register a user with details:
      | Email Address    | test@example.com |
      | Password         | password         |
      | Confirm Password | password         |
      | Username         | testing          |
      | Display Name     | Test User        |
    Then I am logged in as "Test User"
