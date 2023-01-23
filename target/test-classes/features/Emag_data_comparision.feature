Feature: Emag data comparision
  Scenario Outline: Navigation_Smartphone
    Given I hover on phone main menu
    When I open phones page
    Then phonesPage is displayed
    Then I select the <category> option
    When I select brand
    Then first result should be a apple device
    And I read the excel data <category>
    Then I read UI data
    Then I compare the data
    Then I generate the report
    Examples:
      | category |
      |smartphone|




