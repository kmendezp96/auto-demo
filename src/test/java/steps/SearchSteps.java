package steps;

import static org.junit.Assert.assertThat;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.CoreMatchers;
import pages.HomePage;
import pages.SearchPage;

public class SearchSteps {

    private HomePage homePage;
    private SearchPage searchPage;

    @Given("the buyer is in the ebay page")
    public void theUserIsInTheEbayPage() {
        homePage.openApplication();
        homePage.changeLanguageToEnglish();
    }

    @And("^the buyer searches for \"([^\"]*)\"$")
    public void theUserSearchFor(String keyword){
        homePage.searchByKeyword(keyword);
        searchPage.openApplication();
    }

    @When("^the buyer filters by (Brand) \"([^\"]*)\"$")
    public void theUserFiltersBy(String filterType,String filterValue) {
        searchPage.applyFilter(filterType,filterValue);
    }

    @And("^the buyer filters by (Size) \"([^\"]*)\"$")
    public void theUserFiltersUsingCarouselBy(String filterType,String filterValue) {
        searchPage.applyCarouselFilter(filterType,filterValue);
        searchPage.printTotalResults();
    }

    @And("^the buyer orders the results by ascendant price$")
    public void theUserOrdersTheResultsByAscendantPrice() {
        searchPage.orderResultsByAscendantPrice();
    }

    @Then("^the first (\\d+) results should be in descendant price$")
    public void theFirstResultsShouldBeInAscendantPrice(int elementsToAssert) {
        assertThat("the elements should be ordered in ascendant price",
                searchPage.areResultsOrderedByAscendantPrice(elementsToAssert),
                CoreMatchers.is(true));
        System.out.println("\n in ascendant price:");
        searchPage.printFirstLimitedProductsWithPrice(elementsToAssert);
    }

    @And("^the buyer can order the results by best match and see the first (\\d+) results$")
    public void theUserCanOrderTheResultsByBestMatchAndSeeTheFirstResults(int elementsToPrint) {
        searchPage.orderResultsByBestMatch();
        System.out.println("\n in best match order:");
        searchPage.printFirstLimitedProducts(elementsToPrint);
    }

    @And("^the buyer can order the results by descendant price and see the first (\\d+) results$")
    public void theUserCanOrderTheResultsByDescendantPriceAndSeeTheFirstResults(int elementsToPrint) {
        searchPage.orderResultsByDescendantPrice();
        System.out.println("\n in descendant price:");
        searchPage.printFirstLimitedProductsWithPrice(elementsToPrint);
    }
}
