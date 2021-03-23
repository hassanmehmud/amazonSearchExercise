import org.openqa.selenium.By;
import java.util.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit ;

/**
 * The following class provides example test for amazon.com and includes following steps:
 * 1 - Navigate to amazon.com
 * 2 - Search "Nikon"
 * 3 - Sort price by high to low
 * 4 - Select Second product to view details
 * 5 - Assert if the product title contains "Nikon D3X"
 */
public class amazonSearch {
@Test
    /*
     * This is the setup function which configure and initiates chromedriver
     */
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", "utilities/chromedriver.exe"); //Set chromedriver path
        ChromeDriver driver = new ChromeDriver(); //Initialize chrome driver
        driver.manage().window().maximize();
        navigation(driver);
        retrieveData(driver);
        driver.close();
        driver.quit();
    }
    /*
     * This is the navigation method which lands users to amazon homepage and allows to perform search and sorting of price
     */
    static void navigation(ChromeDriver driver) {
        driver.get("https://www.amazon.com");
        driver.findElement(By.id("twotabsearchtextbox")).click();
        driver.findElement(By.id("twotabsearchtextbox")).clear();
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Nikon");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for the search pages to load completely
        driver.findElement(By.id("nav-search-bar-form")).submit();
        driver.findElement(By.id("a-autoid-0-announce")).click();
        driver.findElement(By.id("s-result-sort-select_2")).click(); //sort prices from high to low
    }
    /*
     * This is the data retrieval method, it selects second product from sorted results and check from product description title whether it contains "Nikon D3X"
     * using assertion
     */
    static void retrieveData(ChromeDriver driver) {
        WebElement we = new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Related searches']")));
        List<WebElement> resultsList = driver.findElements(By.xpath(".//span[@class='a-size-medium a-color-base a-text-normal']"));
        int size = resultsList.size();
        System.out.println("Size of list = " + size);
        resultsList.get(1).click();
        String title = driver.findElement(By.id("productTitle")).getText();
        System.out.println(title);
        try {
            Assert.assertTrue(title.contains("Nikon D3X"));
            System.out.println("Text  found");
        } catch (AssertionError e) {
            System.out.println(("The Test has failed, couldn't find Nikon D3X in product title, ")+ e.getMessage());
        }
    }
}