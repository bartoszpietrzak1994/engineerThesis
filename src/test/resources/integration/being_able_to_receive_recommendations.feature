Feature: Being able to have similar artists recommended
  In order to be aware of unknown artists
  As an User
  I want to be able to have similar artists recommended

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

Scenario: I am able to receive list of similar artists
  When I click Get Recommendations button
  Then I should see recommendations window
  And there should be 5 recommendations