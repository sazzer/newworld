Feature: Health Checks

  Scenario: Checking the health of the system
    When I check the health of the system
    Then I get an OK response
