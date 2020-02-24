package pages;

import helpers.DriverSetUp;
import net.thucydides.core.pages.PageObject;

public class AbstractPage extends PageObject {

    public void openApplication() {
        setDriver(DriverSetUp.getWebDriver());
    }
}
