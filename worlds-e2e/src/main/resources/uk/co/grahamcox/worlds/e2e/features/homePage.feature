Feature: Opening the home page

  Scenario: Opening the home page defaults to not being logged in
    When I open the home page
    Then I am not logged in
