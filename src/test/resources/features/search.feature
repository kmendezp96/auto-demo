Feature: As a possible buyer
  I want to search and filter articles
  So I can find what I want to buy

  #just for the exercise, a test shouldn't have this structure
  Scenario: search by keyword and ordering results by price
    Given the user is in the ebay page
    And the user searches for "shoes"
    When the user filters by Brand "PUMA"
    And the user filters by Size "10"
    And the user orders the results by ascendant price
    Then the first 5 results should be in ascendant price
    And the user can order the results by best match and see the first 5 results
    And the user can order the results by descendant price and see the first 5 results

