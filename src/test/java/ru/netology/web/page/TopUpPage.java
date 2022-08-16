package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;


public class TopUpPage {
    private SelenideElement sumField = $("[data-test-id=amount] input");
    private SelenideElement cardField = $("span[data-test-id=from] input");
    private SelenideElement topUpButton = $("button[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id = error-notification]");

    public DashboardPage successfulReplenishment(String sum, String cardNum) {
        sumField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        sumField.setValue(sum);
        cardField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        cardField.setValue(cardNum);
        topUpButton.click();
        return new DashboardPage();
    }

    public void failedReplenishment(String sum, String cardNum) {
        sumField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        sumField.setValue(sum);
        errorNotification.shouldBe(Condition.visible);
    }
}
