package pages;

import helpers.DriverSetUp;

import net.thucydides.core.annotations.WhenPageOpens;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;

public class SearchPage extends AbstractPage {

    private final String filterListXpath = "//h3[contains(text(),'%s')]/../../*[@class='x-refine__group']//li";
    private final String carouselFilterHeaderXpath = ".//h3[contains(text(),'%s')]";

    @FindBy(xpath = "//ul[@class='srp-results srp-grid clearfix']")
    private WebElement resultsContainer;
    @FindBy(xpath = "//*[@class='x-carousel noToggleShowBtn']//li[@class='srp-carousel-list__item--large-items']")
    private List<WebElement> carouselFiltersList;
    @FindBy(xpath = "//*[@class='srp-controls__count-heading']")
    private WebElement totalResultsText;

    /*
    * This method should work for every filter type, however when we try to click
    * on the Size filter with this method it does click the element but ebay doesn't apply the filter.
    * Also we could use forEach in the stream and click there instead of collecting the elements as a list.
    * But in that way the stream could lose reference to the element before making the click.
    * */
    public void applyFilter(String filterType, String filterValue){
        DriverSetUp.getWebDriver().findElements(By
                .xpath(String.format(filterListXpath,filterType)))
                .stream().filter(filter -> filter.getText().contains(filterValue+"\n"))
                .collect(Collectors.toList()).get(0).click();

        await().atMost(Duration.ONE_MINUTE)
                .pollInterval(Duration.ONE_SECOND)
                .until(() -> DriverSetUp.getWebDriver().findElements(By
                        .xpath(String.format(filterListXpath,filterType))).size()<2);
    }

    public void applyCarouselFilter(String filterType, String filterValue){
        for(int i=0;i<carouselFiltersList.size();i++){
            if ((carouselFiltersList.get(i).getText().contains(filterValue+"\n"))&&
                    (carouselFiltersList.get(i).getText().contains(filterType))){
                carouselFiltersList.get(i).click();
            }
        }
    }

    public void printTotalResults(){
        System.out.println(totalResultsText.getText());
    }

    @WhenPageOpens
    protected void waitForPageToBeReady() {
        element(resultsContainer).waitUntilVisible();
    }

}
