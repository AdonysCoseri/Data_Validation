package data_comparision;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {
    private static final Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();

    public static WebDriver getBrowser(String browserName){
        WebDriver driver = null;

        switch(browserName){
            case "Chrome":
                driver = drivers.get("Chrome");
                if(driver == null){
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
                    driver = new ChromeDriver();
                    drivers.put("Chrome", driver);
                    driver.manage().window().maximize();
                }
                break;
            case "Firefox":
                driver = drivers.get("Firefox");
                if(driver == null) {
                    System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
                    driver = new FirefoxDriver();
                    drivers.put("Firefox", driver);
                    driver.manage().window().maximize();
                }
                break;
            case "Edge":
                driver = drivers.get("Edge");
                if(driver == null) {
                    System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver");

                    EdgeDriverService service = EdgeDriverService.createDefaultService();
                    driver = new EdgeDriver(service);
                    drivers.put("Edge", driver);
                    driver.manage().window().maximize();
                }
        }
        return driver;
    }

    public static void closeAllDriver() {
        for(String key : drivers.keySet()) {
            drivers.get(key).close();
            //drivers.get(key).quit();
        }
    }
}
