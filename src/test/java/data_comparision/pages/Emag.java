package data_comparision.pages;


import io.cucumber.java.sl.Ce;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Emag {

    private WebDriver driver;
    private static final List<String> excel_data_smartphones = new ArrayList<>();
    private static final List<String> excel_data_nromalPhones = new ArrayList<>();
    private static final List<String> excel_data = new ArrayList<>();
    private static final List<String> ui_data = new ArrayList<>();
    private final String smart="smartphone";
    private final String old = "telefon clasic";
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
    public List<String> ReadExcelData(String typeofPhone) throws IOException {

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
        for (int i = 0 ; i<excel_data.size() ; i++){
            if(excel_data.get(i).equals(smart)){
                excel_data_smartphones.add(excel_data.get(i+1));
                excel_data_smartphones.add(excel_data.get(i+2));
            }
        }
        for (int i = 0 ; i<excel_data.size() ; i++){
            if(excel_data.get(i).equals(old)){
                excel_data_nromalPhones.add(excel_data.get(i+1));
                excel_data_nromalPhones.add(excel_data.get(i+2));
            }
        }
        if(typeofPhone.equals(smart))
         return excel_data_smartphones;
        else
            return excel_data_nromalPhones;
    }
    public static List<String> mergeLists(@NotNull List<String> list1, List<String> list2) {
        List<String> mergedList = new ArrayList<>();

        for (int i = 0; i < list1.size(); i++) {
            mergedList.add(list1.get(i));
            mergedList.add(list2.get(i));
        }

        return mergedList;
    }
    public List<String> ReadUIData()  {
        List<String> stringPhoneNames= new ArrayList<>();
        List<String> stringPhonePrices= new ArrayList<>();
        for (WebElement e:allPhoneNames) {
            stringPhoneNames.add(e.getText());
        }
        for (WebElement e:allPhonePrices) {
            stringPhonePrices.add(e.getText());
        }
        return mergeLists(stringPhoneNames,stringPhonePrices);
    }
   public List<String> Compare(List<String> uiData, @NotNull List<String> excelData )  {

       List<String> diffList = new ArrayList<>();
       Iterator<String> it1 = excelData.iterator();
       while (it1.hasNext()) {
           String name1 = it1.next();
           String price1 = it1.next();
           Iterator<String> it2 = uiData.iterator();
           while (it2.hasNext()) {
               String name2 = it2.next();
               String price2 = it2.next();
               if (name1.equals(name2)) {
                   double price1Value = Double.parseDouble(price1.replaceAll(".{4}$", "")
                           .replace(".","").replace(",","."));
                   double price2Value = Double.parseDouble(price2.replaceAll(".{4}$", "")
                           .replace(".","").replace(",","."));
                   double diff = price1Value - price2Value;
                   //String diffString = String.format("%s : Pret UI: %s - Pret Exel %s = %.2f", name1, price1, price2, diff);
                   diffList.add(name1);
                   diffList.add(price1);
                   diffList.add(price2);
                   diffList.add(String.valueOf(diff));
               }
               else
                   diffList.add(name1+" Nu a fost gasit in excel");
           }
       }
       return diffList;
   }
    public void GenerateExelReport(@NotNull List<String> comparedList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("FoundSheet");
        XSSFSheet sheet2 = workbook.createSheet("Not found sheet");
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        int rowNumFound = 1;
        int CellFound = 0;
        int rowNumNotFound = 1;
        Row header = sheet.createRow(0);
        Row headerNotFound = sheet2.createRow(0);
        Cell nameCellNotFound = headerNotFound.createCell(0);
        Cell reasonNotFound = headerNotFound.createCell(1);
        Cell nameCell = header.createCell(0);
        Cell uiPriceCell = header.createCell(1);
        Cell excelPrice  = header.createCell(2);
        Cell diffCell = header.createCell(3);
        nameCellNotFound.setCellStyle(style);
        nameCellNotFound.setCellValue("NUME");
        reasonNotFound.setCellStyle(style);
        reasonNotFound.setCellValue("MOTIV");
        nameCell.setCellStyle(style);
        nameCell.setCellValue("NUME");
        uiPriceCell.setCellStyle(style);
        uiPriceCell.setCellValue("PRET UI");
        excelPrice.setCellStyle(style);
        excelPrice.setCellValue("PRET EXCEL");
        diffCell.setCellStyle(style);
        diffCell.setCellValue("DIFERENTA");
        Row row = sheet.createRow(rowNumFound);
        for (String item : comparedList) {
            if(item.contains("Nu a fost gasit")){
                int index = item.indexOf("Nu")-1;
                String name = item.substring(0, index);
                String reason = item.substring(index + 1);
                Row row2 = sheet2.createRow(rowNumNotFound);
                Cell cell2 = row2.createCell(0);
                cell2.setCellValue(name);
                Cell cell3 = row2.createCell(1);
                cell3.setCellValue(reason);
                rowNumNotFound++;
                sheet2.autoSizeColumn(0);
                sheet2.autoSizeColumn(1);
                }
            else {
                Cell cell = row.createCell(CellFound);
                cell.setCellValue(item);
                CellFound++;
                if (CellFound % 4 == 0) {
                    rowNumFound++;
                     CellFound = 0;
                     row = sheet.createRow(rowNumFound);
                }
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                }
        }

        FileOutputStream fileOut = new FileOutputStream("Report.xlsx");
        workbook.write(fileOut);
        fileOut.close();
    }
}


