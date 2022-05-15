package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.Blog;
import pageobjects.Home;
import pageobjects.Post;

public class PostsTests extends BaseTest {

  private By btnAcceptCookies = By.cssSelector("a#hs-eu-confirmation-button");

  @Test
  public void Test() {
    // Step 1
    this.goToUrl("https://blankfactor.com/");

    // Step 2
    Home home = new Home();
    Assert.assertTrue(home.btnAcceptCookies.isDisplayed());
    home.btnAcceptCookies.click();
    home.focusOnMenuItemByText("Insights");
    home.clickPageItemByText("Blog");

    // Step 3
    Blog blog = new Blog();
    String title = "Why Fintech in Latin America Is Having a Boom";
    WebElement postLink = blog.reloadPostsUntilFindByTitle(title);
    postLink.click();

    // Step 4
    Post post = new Post();
    post.getTitle().getText().trim().equalsIgnoreCase(title);
    String expectedUrl = "https://blankfactor.com/insights/blog/fintech-in-latin-america/";
    Assert.assertEquals(this.getUrl(), expectedUrl);

    // Step 5
    post.subscribeByEmail("automatedtest@blankfactor.com");
    String expectedSubscriptionSuccedsMsg = "Thank you for subscribing! Stay tuned.";
    String currentSubscriptionMsg = post.getSubscriptionSuccessMsg().getText().trim();
    Assert.assertTrue(currentSubscriptionMsg.equalsIgnoreCase(expectedSubscriptionSuccedsMsg));

    // Step 6
    this.goBack();
    blog.printAllPostsTitlesAndLinks();
  }
}
