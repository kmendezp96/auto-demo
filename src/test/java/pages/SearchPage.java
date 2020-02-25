package pages;

import helpers.DriverSetUp;

import net.thucydides.core.annotations.WhenPageOpens;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;

public class SearchPage extends AbstractPage {

    private final String filterListXpath = "//h3[contains(text(),'%s')]/../../*[@class='x-refine__group']//li";

    private final By primaryPriceSelector = By
            .xpath(".//span[@class='s-item__price']");
    private final By shippingPriceSelector = By
            .xpath(".//span[@class='s-item__shipping s-item__logisticsCost']");
    private final By productTitleSelector = By
            .xpath(".//h3[contains(@class,'s-item__title')]");
    private final By resultsSelector = By
            .xpath("//li[contains(@id,'srp-river-results-listing')]");

    @FindBy(xpath = "//ul[@class='srp-results srp-grid clearfix']")
    private WebElement resultsContainer;
    @FindBy(xpath = "//*[@class='x-carousel noToggleShowBtn']//li[@class='srp-carousel-list__item--large-items']")
    private List<WebElement> carouselFiltersList;
    @FindBy(xpath = "//*[@class='srp-controls__count-heading']")
    private WebElement totalResultsText;
    @FindBy(xpath = "//*[@id='w9']")
    private WebElement orderButton;
    @FindBy(xpath = "//*[@id='w9']//li")
    private List<WebElement> orderResultsOptions;

    /*
    * This method should work for every filter type, however when we try to click
    * on the Size filter with this method it does click the element but ebay doesn't apply the filter.
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

    /*
    * This method applies filters on the carousel above the results.
     */
    public void applyCarouselFilter(String filterType, String filterValue){
        for(int i=0;i<carouselFiltersList.size();i++){
            if ((carouselFiltersList.get(i).getText().contains(filterValue+"\n"))&&
                    (carouselFiltersList.get(i).getText().contains(filterType))){
                carouselFiltersList.get(i).click();
            }
        }
    }

    public void orderResultsByAscendantPrice(){
        orderResultsBy("lowest");
    }

    public void orderResultsByBestMatch(){
        orderResultsBy("Best Match");
    }

    public void orderResultsByDescendantPrice(){
        orderResultsBy("highest");
    }

    public void printTotalResults(){
        System.out.println(totalResultsText.getText());
    }

    /*
    * Price includes both price and shipping price, so we should add them to know the real value of the product,
    * also there are some products that have a range of price, in this case ebay takes the cheaper price for this
    * order.
    * Shipping could be free as well, we should take that in account, that's why the shipping price is 0 by default.
     */
    public boolean areResultsOrderedByAscendantPrice(int elementsToAssert){
        List<WebElement> firstResults = DriverSetUp.getWebDriver()
                .findElements(resultsSelector)
                .stream().limit(elementsToAssert).collect(Collectors.toList());
        List<Double> prices = new LinkedList<>();
        for (WebElement result:firstResults){
            double shippingPrice = 0.0;
            double price = Double.parseDouble(result.
                    findElement(primaryPriceSelector).getText()
                    .split("\\$")[1].split(" ")[0].replace(",",""));
            String[] tempShippingPriceText = result.findElement(shippingPriceSelector).getText().split("\\$");
            if(tempShippingPriceText.length>1){
                shippingPrice = Double.parseDouble(tempShippingPriceText[1]
                        .split(" ")[0].replace(",",""));
            }
            prices.add(price+shippingPrice);
        }
        return isListOrdered(prices);
    }

    public void printFirstLimitedProducts(int elementsToPrint){
        List<WebElement> firstResults = DriverSetUp.getWebDriver()
                .findElements(resultsSelector).stream().limit(elementsToPrint).collect(Collectors.toList());
        for (WebElement result:firstResults){
            System.out.println(result.findElement(productTitleSelector).getText());
        }
    }
    public void printFirstLimitedProductsWithPrice(int elementsToPrint){
        List<WebElement> firstResults = DriverSetUp.getWebDriver()
                .findElements(resultsSelector).stream().limit(elementsToPrint).collect(Collectors.toList());
        for (WebElement result:firstResults){
            double shippingPrice = 0.0;
            double price = Double.parseDouble(result.
                    findElement(primaryPriceSelector).getText()
                    .split("\\$")[1].split(" ")[0].replace(",",""));
            String[] tempShippingPriceText = result.findElement(shippingPriceSelector).getText().split("\\$");
            if(tempShippingPriceText.length>1){
                shippingPrice = Double.parseDouble(tempShippingPriceText[1]
                        .split(" ")[0].replace(",",""));
                }
            System.out.println(result.findElement(productTitleSelector).getText()+" price:"+
                    (price+shippingPrice));
        }
    }

    private void orderResultsBy(String query){
        new Actions(DriverSetUp.getWebDriver())
                .moveToElement(orderButton)
                .build()
                .perform();
        orderResultsOptions.stream().filter(filter -> filter.getText().contains(query))
                .collect(Collectors.toList()).get(0).click();
    }

    private boolean isListOrdered(List<Double> list){
        boolean isOrdered = true;
        for(int i=0;i<list.size()-1;i++){
            isOrdered = isOrdered && (list.get(i)<=list.get(i+1));
        }
        return isOrdered;
    }

    @WhenPageOpens
    protected void waitForPageToBeReady() {
        element(resultsContainer).waitUntilVisible();
    }

}
