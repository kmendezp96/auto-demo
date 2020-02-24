Feature: As a possible buyer
  I want to search and filter articles
  So I can find what I want to buy

  Scenario: search by keyword and ordering results by price
    Given the user is in the ebay page
    And the user searches for "shoes"
    When the user filters by Brand "PUMA"
    And the user filters by Size "10"

