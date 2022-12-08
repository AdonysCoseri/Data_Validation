package data_comparision.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Google_search {
    private WebDriver driver;
    private String URL = "https://www.google.com/";
    public Google_search(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    public WebDriver getDriver() {return this.driver;}

    @FindBy(xpath = "//input[contains(@title,'Cau')]")
    private WebElement searchBar;
    @FindBy(xpath = "//a[contains(@href,'https://www.emag') and  h3 ]")
    private WebElement siteLink;
    @FindBy(xpath = "//button/div[contains(normalize-space(),'Accept')]")
    private WebElement acceptCookies;
    public void OpenGoogleAndSearch(String seachTerm,String link){
        //driver.get(link);
        acceptCookies.click();
        searchBar.sendKeys(seachTerm);
    }
    public void ClickEnter(){
        searchBar.sendKeys(Keys.ENTER);
    }
    public void ClickLink(){
        siteLink.click();
    }
    public void AssertPageIsCorrect(String link){
        assert driver.getCurrentUrl().contains(link);
    }
}
