package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class Blog extends BasePageObject {
  public Blog() {
    super(By.cssSelector("div.blog-layout__list"));
  }

  private String btnLoadMoreCssLocator = "button.btn-load-more-posts";
  private String articlesCssLocator = "div.posts-list > article";

  private String articleTitleLinkLocator = "h2 > a";

  private String articlesAuthorsCssLocator = "h2 > a";

  public List<WebElement> getArticles() {
    return this.driver.findElements(By.cssSelector(articlesCssLocator));
  }

  public List<WebElement> getArticlesTitles() {
    return this.getArticles().stream().map(a -> a.findElement(By.cssSelector(articleTitleLinkLocator))).collect(Collectors.toList());
  }

  public List<WebElement> getArticleAuthors() {
    return this.getArticles().stream().map(a -> a.findElement(By.cssSelector(articlesAuthorsCssLocator))).collect(Collectors.toList());
  }

  public int getTotalPost() {
    String resultsText = this.getContainer()
        .findElement(By.cssSelector("div.results"))
        .getText();
    String totalPosts = this.extractSubString(resultsText, "(", ")");
    return Integer.parseInt(totalPosts);
  }

  public void reloadPosts() {
    WebElement btn = this.driver.findElement(By.cssSelector(btnLoadMoreCssLocator));
    this.waitUntil(() -> this.isClickable(By.cssSelector(btnLoadMoreCssLocator)), 15);
    this.focus(btn);
    this.hover(btn);
    this.waitUntil(() -> !btn.getAttribute("class").contains("wait-disable"), 15);
    try {
      btn.click();
    } catch (ElementNotInteractableException e) {
      this.jsClick(btn);
    }
  }

  public WebElement reloadPostsUntilFindByTitle(String title) {
    int totalPost = this.getTotalPost();
    int postsLoaded = this.getArticles().size();
    WebElement postFound = this.getArticlesTitles().stream().filter(t -> t.getText().equalsIgnoreCase(title)).findAny().orElse(null);

    while (postFound == null && (totalPost > postsLoaded)) {
      this.reloadPosts();
      postsLoaded = this.getArticles().size();
      postFound = this.getArticlesTitles().stream().filter(t -> t.getText().equalsIgnoreCase(title)).findAny().orElse(null);
    }
    return postFound;
  }

  public String extractSubString(String text, String firstChar, String secondChar) {
    text = text.substring(text.indexOf(firstChar) + 1);
    text = text.substring(0, text.indexOf(secondChar));
    return text;
  }

  public void printAllPostsTitlesAndLinks() {
    List<WebElement> titles = this.getArticlesTitles();
    for (WebElement t : titles) {
      String formatted = String.format("Title: %-60s, Link: %-100s", t.getText(), t.getAttribute("href"));
      System.out.println(formatted);
    }
  }
}
