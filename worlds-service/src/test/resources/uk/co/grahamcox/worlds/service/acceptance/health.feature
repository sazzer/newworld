Feature: Health Checks

  Scenario: Checking the health of the system
    When I get the health of the system
    Then the system is healthy
