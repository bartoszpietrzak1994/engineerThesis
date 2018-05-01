Feature: Being able to login and register
  In order to register and then authenticate myself
  As an User
  I want to be able to register and login

Background:
  Given I am at login and register window

Scenario: I am able to register
  When I type Tomek as user name and password as password
  And I click Register button
  Then new user Tomek should be created

Scenario: I am able to login
  When I type Tomek as user name and password as password
  And I click Login button
  Then I should visit the main application window

Scenario: I am not able to register using existing user name
  Given the user Tomek is already registered
  When I type Tomek as user name and password as password
  And I click Register button
  Then I should see an error message
