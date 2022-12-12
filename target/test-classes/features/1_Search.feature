Feature: Search for emag on google and click on the site
  Scenario Outline: Search
    Given I open google and search for <site> <googleLink>
    And click enter
    Then i click on the correct link
    Then <site> should be displayed
    Examples:
      | site |googleLink|
      |emag.ro |  https://www.google.com/ |