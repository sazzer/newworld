Feature: Updating a User

  Background:
    Given a user exists with details:
      | ID           | 00000000-0000-0000-0000-000000000001 |
      | Version      | 00000000-0000-0000-0000-000000000002 |
      | Created      | 2018-05-23T09:33:00Z                 |
      | Updated      | 2018-05-23T09:34:00Z                 |
      | Email        | test@example.com                     |
      | Username     | testuser                             |
      | Display Name | Test User                            |
    And a user exists with details:
      | Email        | test2@example.com |
      | Username     | testuser2         |
      | Display Name | Test User 2       |

  Scenario: Update a user when not authenticated
    When I update the user with ID "00000000-0000-0000-0000-000000000001" to have details:
      | Email        | updated@example.com |
      | Username     | updateduser         |
      | Display Name | Updated User        |
    Then I get an Unauthorized response

  Scenario: Update a user with missing required fields - Email Address missing
    Given I have authenticated as user "00000000-0000-0000-0000-000000000001"
    When I update the user with ID "00000000-0000-0000-0000-000000000001" to have details:
      | Username     | updateduser  |
      | Display Name | Updated User |
    Then I get a Bad Request response
    And I get a problem response of:
      | Type   | tag:grahamcox.co.uk,2018,problems/missing-parameter |
      | Title  | Required parameter was not present                  |
      | Status | 400                                                 |

  Scenario: Update a user with missing required fields - Username missing
    Given I have authenticated as user "00000000-0000-0000-0000-000000000001"
    When I update the user with ID "00000000-0000-0000-0000-000000000001" to have details:
      | Email        | updated@example.com |
      | Display Name | Updated User        |
    Then I get a Bad Request response
    And I get a problem response of:
      | Type   | tag:grahamcox.co.uk,2018,problems/missing-parameter |
      | Title  | Required parameter was not present                  |
      | Status | 400                                                 |

  Scenario: Update a user with missing required fields - Display Name missing
    Given I have authenticated as user "00000000-0000-0000-0000-000000000001"
    When I update the user with ID "00000000-0000-0000-0000-000000000001" to have details:
      | Username | updateduser         |
      | Email    | updated@example.com |
    Then I get a Bad Request response
    And I get a problem response of:
      | Type   | tag:grahamcox.co.uk,2018,problems/missing-parameter |
      | Title  | Required parameter was not present                  |
      | Status | 400                                                 |

  Scenario: Update a user with a duplicate username
    Given I have authenticated as user "00000000-0000-0000-0000-000000000001"
    When I update the user with ID "00000000-0000-0000-0000-000000000001" to have details:
      | Email        | updated@example.com |
      | Username     | testuser2           |
      | Display Name | Updated User        |
    Then I get a Conflict response
    And I get a problem response of:
      | Type   | tag:grahamcox.co.uk,2018,users/problems/duplicate-username |
      | Title  | The specified username was not unique                      |
      | Status | 409                                                        |

  Scenario Outline: Successfully update a user: <Comment>
    Given I have authenticated as user "00000000-0000-0000-0000-000000000001"
    When I update the user with ID "00000000-0000-0000-0000-000000000001" to have details:
      | Email        | <Email>        |
      | Username     | <Username>     |
      | Display Name | <Display Name> |
    Then I get an OK response
    And I get a user with details:
      | ID           | 00000000-0000-0000-0000-000000000001 |
      | Email        | <Email>                              |
      | Username     | <Username>                           |
      | Display Name | <Display Name>                       |

    Examples:
      | Email               | Username    | Display Name | Comment             |
      | updated@example.com | testuser    | Test User    | Change Email        |
      | test@example.com    | updateduser | Test User    | Change Username     |
      | test@example.com    | testuser    | Updated User | Change Display Name |
      | updated@example.com | updateduser | Updated User | Change all fields   |

  Scenario: Re-retrieve a user after a successful update
    Given I have authenticated as user "00000000-0000-0000-0000-000000000001"
    When I update the user with ID "00000000-0000-0000-0000-000000000001" to have details:
      | Email        | updated@example.com |
      | Username     | updateduser         |
      | Display Name | Updated User        |
    And I get an OK response
    And I get the user with ID "00000000-0000-0000-0000-000000000001"
    Then I get an OK response
    And I get a user with details:
      | ID           | 00000000-0000-0000-0000-000000000001 |
      | Email        | updated@example.com                  |
      | Username     | updateduser                          |
      | Display Name | Updated User                         |
