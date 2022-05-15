package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import webdrivermanager.DriverProvider;

import java.sql.Driver;

public class BaseTest {

  protected  void goToUrl(String url){
    this.getDriver().navigate().to(url);
  }

  protected void goBack(){
    this.getDriver().navigate().back();
  }

  protected String getUrl(){
    return this.getDriver().getCurrentUrl();
  }

  @BeforeTest
  protected WebDriver getDriver() {
    return DriverProvider.getDriver();
  }

  @AfterTest
  public void dispose() {
    DriverProvider.destroy();
  }
}
