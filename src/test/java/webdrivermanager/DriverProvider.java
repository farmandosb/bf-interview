package webdrivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverProvider {

  private static WebDriver driver;

  public static WebDriver getDriver() {
    if (driver == null) {
      initDriver();
    }
    return driver;
  }

  private static void initDriver() {
    io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
    ChromeOptions opts = new ChromeOptions();
    opts.addArguments("--start-maximized");
    driver = new ChromeDriver(opts);
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
    driver.manage().window().maximize();
  }

  public static void destroy() {
    if(driver != null){
      try {
        driver.close();
        driver.quit();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
