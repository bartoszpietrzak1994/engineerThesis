Feature: Being able to see basic user controls
  In order to gain access to basic application's features
  As an User
  I want to be able to access basic user controls

Background:
  Given I am a logged in user
  And a random album is added to collection
  And I visit the main application window

Scenario: I am able to see my album collection
  When I visit the main application window
  Then I should be able to see my album collection

Scenario: I am able to add album data
  When I click Add Album button
  Then the Add Album window should appear

Scenario: I am able to rate an album from collection
  When I choose the first album from my collection
  And I click Rate button
  Then I the Rate Album window should appear

Scenario: I am able to receive music recommendations based on albums from my collection
  When I click Get Recommendations button
  Then I should receive the list of albums related to these from my collection

Scenario: I am able to see details of an album from my collection
  When I select an album from my collection
  And I click Details button
  Then I the Album Details window should appear