Feature: As a possible buyer
  I want to search and filter articles
  So I can find what I want to buy

  #just for the exercise, a test shouldn't have this structure
  Scenario: searching by keyword and ordering results by price
    Given the buyer is in the ebay page
    And the buyer searches for "shoes"
    When the buyer filters by Brand "PUMA"
    And the buyer filters by Size "10"
    And the buyer orders the results by ascendant price
    Then the first 5 results should be in ascendant price
    And the buyer can order the results by best match and see the first 5 results
    And the buyer can order the results by descendant price and see the first 5 results
