package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Home extends BasePageObject {
  public List<WebElement> menuItems = this.getContainer().findElements(By.cssSelector("ul#menu-main-menu>li>a"));
  public List<WebElement> pageItems = this.getContainer().findElements(By.cssSelector("div.insights-pages a.page-item"));
  public WebElement btnAcceptCookies = this.getContainer().findElement(By.xpath("//a[@id='hs-eu-confirmation-button']"));

  public Home() {
    super(By.cssSelector("body.home"));
  }

  public void focusOnMenuItemByText(String text) {
    this.hover(this.getElementByText(menuItems, text));
  }

  public void clickPageItemByText(String text) {
    this.getElementByText(pageItems, text).click();
  }
}
