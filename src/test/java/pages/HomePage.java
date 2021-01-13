package pages;

import helpers.DriverSetUp;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.WhenPageOpens;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("https://www.ebay.com/")
public class HomePage extends AbstractPage {

    @FindBy(xpath = "//input[@class='gh-tb ui-autocomplete-input']")
    private WebElement searchInput;
    @FindBy(xpath = "//input[@id='gh-btn']")
    private WebElement searchButton;
    @FindBy(xpath = "//*[@id='gh-eb-Geo-a-default']//span")
    private WebElement currentLanguageButton;
    @FindBy(xpath = "//*[@class='gh-menu']//*[@class='gh-submenu gh-eb-o']//li[@lang='en-US']")
    private WebElement englishLanguageOption;

    public void searchByKeyword(String keyword){
        int v = 10;
        for(int i=0;i<v;i++){
            System.out.println("cualquier cosa");
        }
        searchInput.sendKeys(keyword);
        searchButton.click();
    }

    public void changeLanguageToEnglish(){
        if(!currentLanguageButton.getText().contains("English")){
            currentLanguageButton.click();
            englishLanguageOption.click();
        }
    }

    @Override
    public void openApplication() {
        this.setDriver(DriverSetUp.getWebDriver());
        open();
    }

    @WhenPageOpens
    protected void waitForPageToBeReady() {
        element(searchInput).waitUntilVisible();
    }
}
