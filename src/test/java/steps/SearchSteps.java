package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import pages.HomePage;
import pages.SearchPage;

public class SearchSteps {

    private HomePage homePage;
    private SearchPage searchPage;

    @Given("the user is in the ebay page")
    public void theUserIsInTheEbayPage() {
        homePage.openApplication();
        homePage.changeLanguageToEnglish();
    }

    @And("^the user searches for \"([^\"]*)\"$")
    public void theUserSearchFor(String keyword){
        homePage.searchByKeyword(keyword);
        searchPage.openApplication();
    }

    @When("^the user filters by (Brand) \"([^\"]*)\"$")
    public void theUserFiltersBy(String filterType,String filterValue) {
        searchPage.applyFilter(filterType,filterValue);
    }

    @And("^the user filters by (Size) \"([^\"]*)\"$")
    public void theUserFiltersUsingCarouselBy(String filterType,String filterValue) {
        searchPage.applyCarouselFilter(filterType,filterValue);
        searchPage.printTotalResults();
    }

}
