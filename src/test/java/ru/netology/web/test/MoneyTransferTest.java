package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    int amountToAddForTest = -200;

    @Test
    void shouldCheckTransactionIsOkFromSecondToFirst() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceOfFirstCardBefore = DashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardBefore = DashboardPage.getCurrentBalanceOfSecondCard();
        val rechargingPage = dashboardPage.chooseFirstCardToRecharge();
        val cardInfo = DataHelper.getSecondCardInformation();
        TransferPage.rechargeCard(cardInfo);
        val balanceAfterTransactionOnRecharged = DataHelper.checkBalanceOfRechargeCard(balanceOfFirstCardBefore, amountToAddForTest);
        val balanceAfterTransaction = DataHelper.checkBalanceOfWriteOffCard(balanceOfSecondCardBefore, amountToAddForTest);
        val balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransaction, balanceOfSecondCardAfter);
    }

    @Test
    void shouldCheckTransactionIsOkFromFirstToSecond() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceOfFirstCardBefore = DashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardBefore = DashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.chooseSecondCardToRecharge();
        val cardInfo = DataHelper.getFirstCardInformation();
        TransferPage.rechargeCard(cardInfo);
        val balanceAfterTransactionOnRecharged = DataHelper.checkBalanceOfRechargeCard(balanceOfSecondCardBefore, amountToAddForTest);
        val balanceAfterTransaction = DataHelper.checkBalanceOfWriteOffCard(balanceOfFirstCardBefore, amountToAddForTest);
        val balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        val balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
        assertEquals(balanceAfterTransactionOnRecharged, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransaction, balanceOfSecondCardAfter);
    }
}