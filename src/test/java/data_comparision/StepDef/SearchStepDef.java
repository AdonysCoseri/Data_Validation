package data_comparision.StepDef;

import data_comparision.BrowserFactory;
import data_comparision.pages.Google_search;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

public class SearchStepDef {
    WebDriver driver;
    private final String BROWSER = "Chrome";
    @Given("^I open google and search for (.*) (.*)$")
    public void i_open_google_and_search_for_dada(String searchTerm, String link) {
       driver = BrowserFactory.getBrowser(BROWSER);
       Google_search googleSearch = new Google_search(driver);
       driver.get(link);
       googleSearch.OpenGoogleAndSearch(searchTerm,link);
    }
    @And("click enter")
    public void click_enter() {
        Google_search googleSearch = new Google_search(driver);
        googleSearch.ClickEnter();
    }
    @Then("^i click on the correct link$")
    public void i_click_on_the_correct_link() {
        Google_search googleSearch = new Google_search(driver);
        googleSearch.ClickLink();
    }
    @Then("^(.*) should be displayed$")
    public void dada_should_be_displayed(String site) {
        Google_search googleSearch = new Google_search(driver);
        googleSearch.AssertPageIsCorrect(site);
    }

}
