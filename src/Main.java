package src;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        setUp();
    }

    private static void setUp() {
        String chromeDriverPath = System.getProperty("os.name").toLowerCase().contains("mac") ? "chromeDriver/chromedriver_mac" : "chromeDriver/chromedriver_win.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        String EXTENSION_PATH = "extension/Ronin-Wallet.crx";
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File(EXTENSION_PATH));

        ChromeDriver driver = new ChromeDriver(options);
        driver.get("chrome-extension://fnjhmkhhmkbjkkabndcnnogagogbneec/popup.html#/welcome");

        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Get Started')]"))).click();

        try {
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text() = \"I'm new. Let's get set up!\"]"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text() = 'Allow tracking']"))).click();

            WebElement enterPass = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/form/label[1]/div/div/span/input"));
            enterPass.sendKeys("123456789");
            WebElement confrimPass = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/form/label[2]/div/div/span/input"));
            confrimPass.sendKeys("123456789");
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Create Wallet']"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), ' Reveal Seed Phrase')]"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Copy to Clipboard']"))).click();

            String copiedText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);

            String[] words = copiedText.split(" ");
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(words));

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Confirm Seed Phrase']"))).click();

            WebElement key1 = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/div[2]/div[1]/label[1]/span"));
            String number1 = key1.getText().substring(1);

            String[] arr = new String[arrayList.size()];
            arr = arrayList.toArray(arr);

            // Kiểm tra nếu số được lấy từ WebElement key1 là số nguyên hợp lệ
            if (isNumeric(number1)) {
                int index = Integer.parseInt(number1) - 1; // Trừ đi 1 để lấy index tương ứng trong mảng
                if (index >= 0 && index < arr.length) {
                    String value = arr[index]; // Lấy giá trị tương ứng trong mảng
                    WebElement inputKey1 = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/div[2]/div[1]/label[1]/div/div/span/input"));
                    inputKey1.sendKeys(value); // Nhập giá trị vào input
                }
            }

            WebElement key2 = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/div[2]/div[1]/label[2]/span"));
            String number2 = key2.getText().substring(1);

            if (isNumeric(number2)) {
                int index = Integer.parseInt(number2) - 1;
                if (index >= 0 && index < arr.length) {
                    String value = arr[index];
                    WebElement inputKey2 = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/div[2]/div[1]/label[2]/div/div/span/input"));
                    inputKey2.sendKeys(value);
                }
            }

            WebElement key3 = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/div[2]/div[2]/label[1]/span"));
            String number3 = key3.getText().substring(1);

            if (isNumeric(number3)) {
                int index = Integer.parseInt(number3) - 1;
                if (index >= 0 && index < arr.length) {
                    String value = arr[index];
                    WebElement inputKey3 = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/div[2]/div[2]/label[1]/div/div/span/input"));
                    inputKey3.sendKeys(value);
                }
            }

            WebElement key4 = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/div[2]/div[2]/label[2]/span"));
            String number4 = key4.getText().substring(1);
            if (isNumeric(number4)) {
                int index = Integer.parseInt(number4) - 1;
                if (index >= 0 && index < arr.length) {
                    String value = arr[index];
                    WebElement inputKey4 = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div[2]/div[2]/div[2]/label[2]/div/div/span/input"));
                    inputKey4.sendKeys(value);
                }
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text() = 'Continue']"))).click();
        } catch (
                Exception e) {
            System.out.println("Can't switch to the window");
        }
        driver.close();

        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }
        driver.get("chrome-extension://fnjhmkhhmkbjkkabndcnnogagogbneec/popup.html#/welcome");

        WebElement inputPass = driver.findElement(By.id("password-input"));

        Actions actions = new Actions(driver);
        actions.sendKeys(inputPass, "123456789").sendKeys(Keys.ENTER).build().perform();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div/div/div/div[1]/div[1]/button"))).click();

        for (int i = 0; i < 2000; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text() = 'Create Account']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text() = 'Create']"))).click();
        }
    }
}