package data_comparision;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"},
        features = {"src/test/resources/features/1_LogIn.feature","src/test/resources/features/2_Clients.feature"},
        glue = {"data_comparision.StepDef"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
