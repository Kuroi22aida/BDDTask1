package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    int currentBalance1;
    int currentBalance2;
    int balanceAfterReplenishment;
    int balanceAfterWithdrawal;
    int sum;
    DashboardPage dashboardPage;

    @BeforeEach
    void Setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        currentBalance1 = dashboardPage.getBalance(dashboardPage.card1);
        currentBalance2 = dashboardPage.getBalance(dashboardPage.card2);
    }


    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        sum = 1000;
        var topUpPage = dashboardPage.clickTopUp(dashboardPage.card2);
        var cardNum = DataHelper.getFirstCard().getNumber();
        var dashboardPage2 = topUpPage.successfulReplenishment(Integer.toString(sum), cardNum);
        balanceAfterReplenishment = dashboardPage2.getBalance(dashboardPage2.card2);
        balanceAfterWithdrawal = dashboardPage2.getBalance(dashboardPage2.card1);
        assertEquals(currentBalance1 - sum, balanceAfterWithdrawal);
        assertEquals(currentBalance2 + sum, balanceAfterReplenishment);
    }


}

