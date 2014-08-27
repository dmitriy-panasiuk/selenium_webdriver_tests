package com.sdl.selenium.pages;

import org.openqa.selenium.*;
import org.testng.annotations.*;
import org.testng.Assert;
import java.util.List;

public class LC_test extends TestBase {

    @BeforeClass
    public void before() throws InterruptedException{
        driver.get(baseUrl);
        Thread.sleep(1000);

        navigateToReach();
    }

    @AfterMethod
    public void afterTest() throws InterruptedException {
        resetToLandingPage();
    }

    @Test
    public void firstTest() throws InterruptedException {
        Assert.assertEquals(driver.getTitle().contains("SDL"), true);

        navigateToPub();
        navigateToTopic();
        pressEdit();
        setFocus();
        goToInsertTab();
        pressInsertImage();
        selectAndInsertImage();
        goToHomeTab();
        pressFinishEditing();

        WebElement frame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(frame);

        Assert.assertEquals(isElementPresent(By.xpath(".//*[@id='GUID-31AD7404-DB60-484D-9EC7-D039C6A25C44__" +
                "GUID-507078E1-FCC6-43C0-AA19-54351409B73D']/img")), true, "Image is not found");

    }

    @Test
    public void secondTest() throws InterruptedException {
        navigateToPub();
        navigateToTopic();
        pressAddCommentButton();
        saveComment("Comment text");

        WebElement frame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(frame);
        WebElement comment = driver.findElement(By.xpath(".//*[@id='xform_footer']/div/div[3]/div/div/table/tbody/tr/td[2]/p"));
        Assert.assertEquals(comment.getText(), "Comment text");
    }

    @Test
    public void thirdTest() throws InterruptedException {
        navigateToPub();
        pressEditMapButton();
        setFocus2();
        goToTOCTab();
        pressInsertExistingPage();
        selectAndInsertExistingPage();
        goToHomeTab();
        pressFinishEditing();

        WebElement iFrame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(iFrame);
        Assert.assertTrue(isElementPresent(By.xpath(".//*[@id='toc_div']/ul/div/li[contains(.,'Career')]")), "Topic was not added!");

    }

    public void navigateToPub() {
        WebElement iFrame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(iFrame);
        //WebElement pub = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/div/div[2]/div/div[4]/div[4]/div/div[2]"));

        WebElement pub = driver.findElement(By.xpath(".//*[@id='publistWidget']/div[4]/div[5]/div[1]/div[2]"));
        System.out.println(pub.getText());
        while (true) {
            try {
                pub.click();
            } catch (StaleElementReferenceException e) {
                break;
            } catch (ElementNotVisibleException e) {
            }
        }
        driver.switchTo().defaultContent();
    }

    public void navigateToTopic() {
        WebElement iFrame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(iFrame);
        WebElement topic = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div/div[2]/div/div/ul/div/li[3]/div/div/div[2]"));
        topic.click();
        topic.click();
        topic.click();
        driver.switchTo().defaultContent();
    }

    public void pressEdit() throws InterruptedException {
        WebElement iFrame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(iFrame);
        Thread.sleep(1000);
        WebElement edit = driver.findElement(By.xpath(".//*[@id='EditTopic']/div"));

        while (true) {
            try {
                edit.click();
            } catch (StaleElementReferenceException e) {
                break;
            } catch (ElementNotVisibleException e) {
                break;
            }
        }
        driver.switchTo().defaultContent();
    }

    public void setFocus() {
        WebElement iFrame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(iFrame);
        WebElement iFrame2 = driver.findElement(By.id("lceditor"));
        driver.switchTo().frame(iFrame2);
        WebElement iFrame3 = driver.findElement(By.className("iframe"));
        driver.switchTo().frame(iFrame3);
        //works in IE, does not in FF
        //WebElement emptyPara = driver.findElement(By.xpath("/html/body/div/div/div/xopusviewroot/div/div/div/div[4]/textnode[2]"));
        //works in FF, does not work in IE
        //WebElement emptyPara = driver.findElement(By.xpath(".//*[@id='xopus-xsl-root']/div/div/div[4]"));
        //System.out.println(driver.getPageSource());
        //WebElement emptyPara = driver.findElement(By.xpath(".//*[@class='xopus-xsl-root']"));
        WebElement emptyPara = driver.findElement(By.className("xopus-xsl-root"));
        WebElement para2 = emptyPara.findElement(By.xpath("//div/div/div[4]"));
        para2.click();
        driver.switchTo().defaultContent();
    }

    public void goToInsertTab() {
        WebElement iFrame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(iFrame);
        WebElement iFrame2 = driver.findElement(By.id("lceditor"));
        driver.switchTo().frame(iFrame2);
        //works in IE, does not in FF
        //WebElement insertTab = driver.findElement(By.id("InsertTab"));
        WebElement insertTab = driver.findElement(By.xpath(".//*[@id='InsertTab']/span"));

        System.out.println(insertTab.getText());
        insertTab.click();
    }

    public void pressInsertImage () {
        WebElement imageButton = driver.findElement(By.xpath("//div[@title='Image']"));
        imageButton.click();
    }

    public void selectAndInsertImage() {
        List<WebElement> buttons = driver.findElements(By.xpath("//tr//div[@class='title']"));
        buttons.get(1).click();
        buttons = driver.findElements(By.xpath("//tr/td[2]//div[@class='title']"));
        buttons.get(1).click();
        WebElement insertButton = driver.findElement(By.className("InsertImageActionCommand-actionButton"));
        insertButton.click();
    }

    public void goToHomeTab() {
        WebElement insertTab = driver.findElement(By.xpath(".//*[@id='HomeTab']/span"));

        insertTab.click();
    }

    public void pressFinishEditing() {
        WebElement finishEditingButton = driver.findElement(By.id("FinishEditingButton"));
        finishEditingButton.click();
        driver.switchTo().defaultContent();
    }

    public void navigateToReach() {
        WebElement reachButton = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div[2]/div[2]/ul/li/ul/li[2]"));
        reachButton.click();
    }

    public void resetToLandingPage() throws InterruptedException {
        driver.switchTo().defaultContent();
        WebElement frame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(frame);
        WebElement homeIcon = driver.findElement(By.className("contextBreadcrumbHome"));
        while (true) {
            try {
                homeIcon.click();
                } catch (StaleElementReferenceException e) {
                    break;
                } catch (ElementNotVisibleException e) {
            }
        }
        driver.switchTo().defaultContent();
    }

    public void pressAddCommentButton() throws InterruptedException {
        WebElement frame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(frame);
        Thread.sleep(1000);
        WebElement addCommentButton = driver.findElement(By.id("AddComment"));
        addCommentButton.click();
        addCommentButton.click();
        addCommentButton.click();
        driver.switchTo().defaultContent();
    }

    public void saveComment(String text) {
        WebElement frame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(frame);
        WebElement commentText = driver.findElement(By.id("comment-desc"));
        commentText.sendKeys(text);
        WebElement postButton = driver.findElement(By.className("saveComment"));
        postButton.click();
        driver.switchTo().defaultContent();
    }

    public void pressEditMapButton() {
        WebElement iFrame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(iFrame);
        WebElement editMapButton = driver.findElement(By.id("EditMap"));
        while (true) {
            try {
                editMapButton.click();
            } catch (StaleElementReferenceException e) {
                break;
            } catch (ElementNotVisibleException e) {
                break;
            }
        }
        driver.switchTo().defaultContent();
    }

    public void setFocus2() {
        WebElement iFrame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(iFrame);
        WebElement iFrame2 = driver.findElement(By.id("lceditor"));
        driver.switchTo().frame(iFrame2);
        WebElement iFrame3 = driver.findElement(By.className("iframe"));
        driver.switchTo().frame(iFrame3);
        WebElement lastTopicRef = driver.findElement(By.className("xopus-xsl-root"));
        List<WebElement> last2 = lastTopicRef.findElements(By.xpath(".//div[contains(.,'Big')]"));
        last2.get(1).click();
        driver.switchTo().defaultContent();
    }

    public void goToTOCTab() {
        WebElement iFrame = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/iframe"));
        driver.switchTo().frame(iFrame);
        WebElement iFrame2 = driver.findElement(By.id("lceditor"));
        driver.switchTo().frame(iFrame2);
        WebElement insertTab = driver.findElement(By.id("TableOfContentTab"));
        insertTab.click();
    }

    public void pressInsertExistingPage() {
        WebElement imageButton = driver.findElement(By.xpath("//div[@title='Insert Existing Page']"));
        imageButton.click();
    }

    public void selectAndInsertExistingPage() {
        List<WebElement> buttons = driver.findElements(By.xpath("//tr//div[@class='title']"));
        buttons.get(1).click();
        buttons = driver.findElements(By.xpath("//tr/td[2]//div[@class='title']"));
        buttons.get(1).click();
        WebElement insertButton = driver.findElement(By.className("InsertExistingPageCommand-actionButton"));
        insertButton.click();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
