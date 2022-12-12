package data_comparision.pages;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Emag {

    private WebDriver driver;
    private static final List<String> excel_data = new ArrayList<>();
    private static final List<String> ui_data = new ArrayList<>();
    public Emag(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//li[@data-id=1]")
    private WebElement telefoaneMainMenu;
    @FindBy(xpath = "//a[contains(normalize-space(),'Telefoane mobile') and contains(@data-id,'4835')]")
    private WebElement megaMenuTelefoane;
    @FindBy(xpath = "//a[contains(@data-name,'Sma') ]")
    private WebElement smartPhone;
    @FindBy(xpath = "//a[contains(@data-name,'Tele') ]")
    private WebElement telefonClasic;
    @FindBy(xpath = "//a[contains(@data-name,'Apple') ]")
    private WebElement appleBrand;
    @FindBy(xpath = "//div[@data-position='1']//div[@class='card-v2-info']//img")
    private WebElement firstElementIsApple;
    @FindBy(xpath = "//a[@data-zone='title']")
    private List<WebElement> allPhoneNames;
    @FindBy(xpath = "//p[@class='product-new-price']")
    private List<WebElement> allPhonePrices;

    public void HoverOverMainMenu(){
        Actions action = new Actions(driver);
        action.moveToElement(telefoaneMainMenu).perform();
    }
    public void HoverOverSubMenuClick() throws InterruptedException {
        Actions action = new Actions(driver);
        Thread.sleep(200);
        action.moveToElement(megaMenuTelefoane).perform();
        megaMenuTelefoane.click();
    }
    public void PhonesPageIsDisplayed(){
        assert driver.getCurrentUrl().contains("telefoane-mobile");
    }
    public void SelectPhoneCategory(String type){
        switch (type){
            case "smartphone":
                smartPhone.click();
            case "oldphone":
                telefonClasic.click();
        }

    }
    public void Select_DeselectAppleBrand() throws InterruptedException {
        appleBrand.click();
        Thread.sleep(5000);
    }
    public void AssertFisrtResultIsApple(){
        assert firstElementIsApple.getAttribute("alt").contains("Apple");

    }
    public List<String> ReadExcelData() throws IOException {

        String path = "/home/adonyscoseri/Documents/Initial_price.xlsx";
        File file = new File(path);
        FileInputStream inputdata = new FileInputStream(file);
        XSSFWorkbook workbook= new XSSFWorkbook(inputdata);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        int rowcount = sheet.getLastRowNum()-sheet.getFirstRowNum();
        for(int i = 1 ; i<=rowcount ; i++){
            int cellCount = sheet.getRow(i).getLastCellNum();
            for(int j = 0; j<cellCount; j++){
                excel_data.add(sheet.getRow(i).getCell(j).toString());
            }
        }
        return excel_data;
    }
    public List<String> ReadUIData() {
        List<String> allPhonesStringList = new ArrayList<>();
        for (WebElement x: allPhoneNames) {
            for (WebElement e: allPhonePrices) {
                allPhonesStringList.add(x.getText());
                allPhonesStringList.add(e.getText());
            }
        }
        return allPhonesStringList;
    }
   public void CompareAndGenerate() throws IOException {
        List<String> excelData = ReadExcelData();
        List<String> uiData = ReadUIData();

   }

}


