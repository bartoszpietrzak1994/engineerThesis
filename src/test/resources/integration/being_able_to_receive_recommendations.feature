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

Scenario: I am able to receive list of similar artists
  When I click Get Recommendations button
  And there should see 5 recommendations