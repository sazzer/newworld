Feature: Authentication

  Scenario: Registering a new user
    Given I opened the home page
    When I register a new user with details:
      | Email Address | test@example.com |
      | Password      | testPassword     |
      | Username      | testuser         |
      | Display Name  | Test User        |
    Then I am logged in as "Test User"
