Feature: User Profile Management

  Background:
    Given a user exists with details:
      | Email         | test@example.com                                                                                                                                                             |
      | Username      | testuser                                                                                                                                                                     |
      | Display Name  | Test User                                                                                                                                                                    |
      # This Password Hash and Password Salt equate to the password "MyPassword"
      | Password Hash | GsZ+KUAgJgW6OYDAVbo8hzeSoWtRW7Q0uPgvk1OrA2Y=                                                                                                                                 |
      | Password Salt | RJMrJIln2gXN2SzZQ0YI3uqZXSLZBfGFj/nn7wTfwMXwfp/BLI7EKufDw7HejLXiSvByLjjpjRMGkrVqPjEyrJswDZa32/RSkWnvtlK4uIbcLMwyivvLWcVSqZP6Oqe+TDEz3HjpiZ6VCxB13QSfe4rYLtglGFr6JgnfFyK7duw= |
    And a user exists with details:
      | Email        | other@example.com |
      | Username     | otheruser         |
      | Display Name | Other User        |
    And I opened the home page
    And I log in with details:
      | Email Address | test@example.com |
      | Password      | MyPassword       |

  Scenario: Viewing a users profile
    When I view the users profile
    Then the users profile has details:
      | Email Address | test@example.com |
      | Username      | testuser         |
      | Display Name  | Test User        |

  Scenario Outline: Updating a users profile: <Comment>
    Given I view the users profile
    When I update the users profile to:
      | Email Address | <Email>        |
      | Username      | <Username>     |
      | Display Name  | <Display Name> |

    Then the users profile has details:
      | Email Address | <Email>        |
      | Username      | <Username>     |
      | Display Name  | <Display Name> |

    Examples:
      | Email            | Username | Display Name | Comment              |
      | test@example.com | testuser | Test User    | No Changes           |
      | new@example.com  | testuser | Test User    | Changed Email        |
      | test@eample.com  | newuser  | Test User    | Changed Username     |
      | test@example.com | testuser | New User     | Changed Display Name |
      | new@example.com  | newuser  | New User     | Changed Everything   |

  Scenario Outline: Updating a users profile - reload after: <Comment>
    Given I view the users profile
    When I update the users profile to:
      | Email Address | <Email>        |
      | Username      | <Username>     |
      | Display Name  | <Display Name> |
    And I opened the home page
    And I log in with details:
      | Email Address | <Email>    |
      | Password      | MyPassword |
    And I view the users profile

    Then the users profile has details:
      | Email Address | <Email>        |
      | Username      | <Username>     |
      | Display Name  | <Display Name> |

    Examples:
      | Email            | Username | Display Name | Comment              |
      | test@example.com | testuser | Test User    | No Changes           |
      | new@example.com  | testuser | Test User    | Changed Email        |
      | test@example.com | newuser  | Test User    | Changed Username     |
      | test@example.com | testuser | New User     | Changed Display Name |
      | new@example.com  | newuser  | New User     | Changed Everything   |

  Scenario: Updating a user to a duplicate email fails
    Given I view the users profile
    When I update the users profile to:
      | Email Address | other@example.com |
    Then updating the users profile failed with "That email address is already in use."

  Scenario: Updating a user to a duplicate username fails
    Given I view the users profile
    When I update the users profile to:
      | Username | otheruser |
    Then updating the users profile failed with "That username is already in use."
