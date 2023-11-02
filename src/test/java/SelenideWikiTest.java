import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SelenideWikiTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = false;
        Configuration.timeout = 5000; // default 4000
    }

    @Test
    void selenideTest() {
        open("https://github.com");
        $("[placeholder='Search or jump to...']").click();
        $("#query-builder-test").setValue("selenide").pressEnter();
        $$x("//div[@data-testid='results-list']//a/span").first().click();
        $("#repository-container-header").shouldHave(text("selenide / selenide"));
        $("#wiki-tab").click();
        $("#wiki-pages-box input").setValue("SoftAssertion");
        $$(".wiki-rightbar ul").shouldHave(itemWithText("SoftAssertions"));
        $$(".wiki-rightbar ul").first().$(byText("SoftAssertions")).click();

        String textExample = String.join(
                "\n"+ "@ExtendWith({SoftAssertsExtension.class})\n"
                        + "class Tests {\n"
                        + "@Test\n" + "void test() {\n"
                        + "Configuration.assertionMode = SOFT;\n"
                        + "open(\"page.html\");\n"
                        +"\n"
                        + "$(\"#first\").should(visible).click();\n"
                        + "$(\"#second\").should(visible).click();\n"
                        + "}\n"
                        + "}"           );
        $$("#user-content-3-using-junit5-extend-test-class").shouldHave(texts(textExample));
    }

    @AfterEach
    void clearAll() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        closeWebDriver();
    }
}