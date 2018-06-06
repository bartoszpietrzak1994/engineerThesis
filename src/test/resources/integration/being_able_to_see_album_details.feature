Feature: Being able to see album details
  In order to see more detailed album information
  As an User
  I want to be able to have details displayed in separate window

Background:
  Given the user Tomek is already registered
  And I am at login and register window
  And I type Tomek as user name and password as password
  And I click Login button
  And I visit the main application window
  And I click Add Album button
  And I fill artist field with AC/DC
  And I fill title field with Random album
  And I fill release date field with 2018-06-05
  And I click Add button

Scenario: Being able to see album details
  When I choose the first album from my collection
  And I click Details button
  Then I should visit album details window
  And artist field should be filled with AC/DC
  And title field should be filled with Random
  And release date field should be filled with 2018-06-05

