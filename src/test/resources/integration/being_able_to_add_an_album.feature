Feature: Being able to add an album
  In order to maintain my music collection
  As an User
  I want to add albums to my collection

Background:
  Given the user Tomek is already registered
  And I am at login and register window
  And I type Tomek as user name and password as password
  And I click Login button
  And I visit the main application window
  And I click Add Album button

Scenario: I am able to add new album
  When I fill album details with valid random data
  And I click Add button
  Then new album should be added

Scenario: I am not able to add new album with no title
  When I fill artist field with AC/DC
  And I fill release date field with 2018-06-05
  And I click Add button
  Then new album should not be added
  And I should see an error message

Scenario: I am not able to add new album with no artist
    When I fill title field with Random album
    And I fill release date field with 2018-06-05
    And I click Add button
    Then new album should not be added
    And I should see an error message

