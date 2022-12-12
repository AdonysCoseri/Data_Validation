package data_comparision.StepDef;

import data_comparision.BrowserFactory;
import data_comparision.pages.Emag;
import io.cucumber.core.backend.ScenarioScoped;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class EmagStepDef {
    WebDriver driver;
    private final String BROWSER = "Chrome";
    @Given("I hover on phone main menu")
    public void i_hover_on_phone_main_menu() {
        driver = BrowserFactory.getBrowser(BROWSER);
        Emag emag = new Emag(driver);
        emag.HoverOverMainMenu();
    }
    @When("I open phones page")
    public void i_open_phones_page() throws InterruptedException {
        Emag emag = new Emag(driver);
        emag.HoverOverSubMenuClick();
    }
    @Then("phonesPage is displayed")
    public void phones_page_is_displayed() {
        Emag emag = new Emag(driver);
        emag.PhonesPageIsDisplayed();
    }
    @Then("^I select the (.*) option$")
    public void i_select_the_type_option(String type) {
        Emag emag = new Emag(driver);
        emag.SelectPhoneCategory(type);
    }
    @When("I select brand")
    public void I_select_brand() throws InterruptedException {
        Emag emag = new Emag(driver);
        emag.Select_DeselectAppleBrand();
    }
    @Then("first result should be a apple device")
    public void first_result_should_be_a_apple_device(){
        Emag emag = new Emag(driver);
        emag.AssertFisrtResultIsApple();

    }
    @And("I read the data")
    public void I_read_the_data() throws IOException {
        Emag emag = new Emag(driver);
        System.out.println(emag.ReadUIData());
    }
}
