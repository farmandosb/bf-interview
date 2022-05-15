package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Post extends BasePageObject {
  public Post() {
    super(By.cssSelector("main.post-page-template"));
  }

  public WebElement getTitle() {
    return this.getContainer().findElement(By.cssSelector("h1.post-title"));
  }

  public WebElement getInputSubscribe()  {
    return this.getContainer().findElement(By.cssSelector("div.form-newsletter__fields input[name='Email']"));
  }
  public WebElement getBtnSubscribe()  {
    return this.getContainer().findElement(By.id("form-newsletter-blog-submit-btn"));
  }

  public WebElement getSubscriptionSuccessMsg()  {
    this.waitUntil(()->
        !this.getContainer().findElement(By.cssSelector("div.mc4wp-response")).getText().equalsIgnoreCase(""),5);
    return this.getContainer().findElement(By.cssSelector("div.mc4wp-response"));
  }

  public void subscribeByEmail(String email){
    getInputSubscribe().sendKeys(email);
    getBtnSubscribe().click();
  }

}
