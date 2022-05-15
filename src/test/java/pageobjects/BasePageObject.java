package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdrivermanager.DriverProvider;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;

public class BasePageObject {
  protected WebDriver driver;
  protected By locator;

  private WebElement container;

  public BasePageObject(By locator) {
    this.driver = DriverProvider.getDriver();
    this.locator = locator;
  }

  protected WebElement getContainer() {
    try {
      if (this.container == null) {
        return this.driver.findElement(this.locator);
      }
      return container;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean hasLoaded() {

    try {
      return !this.getContainer().isDisplayed();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  protected WebElement getElementByText(List<WebElement> elements, String text) {
    WebElement item = elements.stream().filter(p -> p.getText().equalsIgnoreCase(text)).findAny().orElse(null);
    return item;
  }

  protected void hover(WebElement element) {
    new Actions(this.driver).moveToElement(element).perform();
    this.waitUntil(() -> element.isDisplayed(), 10);
  }

  protected void focus(WebElement element) {
    if (!this.isCurrentlyVisible(element)) {
      this.scrollIntoView(element);
    }
    JavascriptExecutor jse = (JavascriptExecutor) this.driver;
    String script = "arguments[0].focus();";
    jse.executeScript(script, element);
  }

  protected void jsClick(WebElement element) {
    JavascriptExecutor jse = (JavascriptExecutor) this.driver;
    String script = "arguments[0].click();";
    jse.executeScript(script, element);
  }


  public boolean isClickable(By locator) {
    WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
    WebElement element = wait.until(
        ExpectedConditions.elementToBeClickable(locator));
    //wait.until(ExpectedConditions.attributeContains(element,"",""));

    return element.isDisplayed();
  }

  public WebElement waitForElementToBeVisible(By locator) {
    WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public boolean waitUntil(Callable<Boolean> func, long timeout) {
    try {
      WebDriverWait waiter = new WebDriverWait(this.driver, Duration.ofSeconds(timeout));
      return waiter.until(x -> {
        try {
          return func.call();
        } catch (Exception e) {
          return false;
        }
      });
    } catch (Exception e) {
      return false;
    }
  }

  private boolean isCurrentlyVisible(WebElement element) {
    try {
      Dimension shellSize = this.driver.findElement(By.tagName("body")).getSize();
      Point shellLocation = this.driver.findElement(By.tagName("body")).getLocation();
      WebElement parentDiv = this.getParent(element).findElement(By.xpath("./ancestor::div"));

      if (parentDiv != null) {
        shellSize = parentDiv.getSize();
        shellLocation = parentDiv.getLocation();
      }
      Point location = element.getLocation();
      boolean isInParentRectX = (location.x >= shellLocation.x)
          & (location.x <= (shellSize.width + shellLocation.x));

      boolean isInParentRectY = (location.y >= shellLocation.y)
          & (location.y <= (shellSize.height + shellLocation.y));

      return (element.isDisplayed() & isInParentRectX & isInParentRectY);
    } catch (Exception e) {
      return false;
    }
  }

  public void scrollIntoView(WebElement element) {

    JavascriptExecutor jse = (JavascriptExecutor) this.driver;
    String script = "arguments[0].scrollIntoView(true);";
    jse.executeScript(script, element);
  }

  public WebElement getParent(WebElement child) {
    if (child.getTagName().equalsIgnoreCase("body")
        || child.getTagName().equalsIgnoreCase("html")) {
      return null;
    }

    return child.findElement(By.xpath("./.."));
  }
}
