package tests;
import application.buisnessLogic.bankAccounts.DebitAccount;
import application.buisnessLogic.bankAccounts.accountsHistory.AccountSnapshotsHistory;
import application.buisnessLogic.banks.CentralBank;
import application.buisnessLogic.users.User;
import application.contracts.bankAccounts.AccountOperationResult;
import application.contracts.bankAccounts.IBankAccount;
import application.contracts.banks.IBank;
import application.contracts.banks.ICentralBank;
import application.contracts.users.IUser;
import application.models.bankAccounts.Account;
import application.models.money.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CheckBalanceTests {

    ICentralBank centralBank = new CentralBank();
    IBank bank = centralBank.createBank("Sberbank", 100, 0.1);

    @Test
    public void testChekBalance() {
        IUser user = new User();
        user.login("Alex", "Alexeev", "Lomo 9", "4567658");

        IBankAccount account = new DebitAccount(new Account(new Money(new BigDecimal(100)), "4608"),
                user,
                new AccountSnapshotsHistory());
        bank.registerBankAccountToSystem(account);

        AccountOperationResult result =  account.checkBalance();
        if (result instanceof AccountOperationResult.SuccessCheckingBalance res){
            assertEquals(new BigDecimal(100), res.money().moneyCount());
        }
        else{
            assertFalse(false);
        }
    }

    @Test
    public void testAddMoney() {
        IUser user = new User();
        user.login("Alex", "Alexeev", "Lomo 9", "4567658");

        IBankAccount account = new DebitAccount(new Account(new Money(new BigDecimal(100)), "4608"),
                user,
                new AccountSnapshotsHistory());
        bank.registerBankAccountToSystem(account);

        account.addMoney(new Money(new BigDecimal(20)));

        AccountOperationResult result =  account.checkBalance();
        if (result instanceof AccountOperationResult.SuccessCheckingBalance res){
            assertEquals(new BigDecimal(120), res.money().moneyCount());
        }
        else{
            assertFalse(false);
        }
    }

    @Test
    public void testWithdrawMoney() {
        IUser user = new User();
        user.login("Alex", "Alexeev", "Lomo 9", "4567658");

        IBankAccount account = new DebitAccount(new Account(new Money(new BigDecimal(100)), "4608"),
                user,
                new AccountSnapshotsHistory());
        bank.registerBankAccountToSystem(account);

        account.withdrawMoney(new Money(new BigDecimal(20)));

        AccountOperationResult result =  account.checkBalance();
        if (result instanceof AccountOperationResult.SuccessCheckingBalance res){
            assertEquals(new BigDecimal(80), res.money().moneyCount());
        }
        else{
            assertFalse(false);
        }
    }
}
