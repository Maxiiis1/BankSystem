package tests;

import application.buisnessLogic.bankAccounts.DebitAccount;
import application.buisnessLogic.bankAccounts.accountProxy.AccountAccessChecker;
import application.buisnessLogic.bankAccounts.accountsHistory.AccountSnapshotsHistory;
import application.buisnessLogic.banks.CentralBank;
import application.buisnessLogic.users.User;
import application.contracts.bankAccounts.AccountOperationResult;
import application.contracts.bankAccounts.IBankAccount;
import application.contracts.banks.BankOperationResult;
import application.contracts.banks.IBank;
import application.contracts.banks.ICentralBank;
import application.contracts.users.IUser;
import application.models.bankAccounts.Account;
import application.models.money.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TransactionTests {
    ICentralBank centralBank = new CentralBank();
    IBank bank = centralBank.createBank("Sberbank", 100, 0.1);

    @Test
    public void testMakeTransaction() {
        IUser user = new User();
        user.login("Alex", "Alexeev", "Lomo 9", "4567658");

        IUser user2 = new User();
        user.login("Matvey", "Matveev", "Lomo 8", "123456");

        IBankAccount accountA = new DebitAccount(new Account(new Money(new BigDecimal(100)), "4608"),
                user,
                new AccountSnapshotsHistory());
        bank.registerBankAccountToSystem(accountA);

        IBankAccount accountM = new DebitAccount(new Account(new Money(new BigDecimal(100)), "1234"),
                user2,
                new AccountSnapshotsHistory());
        bank.registerBankAccountToSystem(accountM);

        bank.sendMoney(accountA, bank, "1234", new Money(new BigDecimal(50)));

        AccountOperationResult result =  accountM.checkBalance();
        if (result instanceof AccountOperationResult.SuccessCheckingBalance res){
            assertEquals(new BigDecimal(150), res.money().moneyCount());
        }
        else{
            assertFalse(false);
        }
    }

    @Test
    public void testMakeTransactionNoPassportInfo() {
        IUser user = new User();
        user.login("Alex", "Alexeev", null, null);

        IUser user2 = new User();
        user.login("Matvey", "Matveev", "Lom", "123456");

        IBankAccount account = new DebitAccount(new Account(new Money(new BigDecimal(100)), "4608"),
                user,
                new AccountSnapshotsHistory());
        IBankAccount accountA = new AccountAccessChecker(account);
        bank.registerBankAccountToSystem(accountA);

        IBankAccount accountM = new DebitAccount(new Account(new Money(new BigDecimal(100)), "1234"),
                user2,
                new AccountSnapshotsHistory());
        bank.registerBankAccountToSystem(accountM);

        BankOperationResult result = bank.sendMoney(accountA, bank, "1234", new Money(new BigDecimal(50)));

        if (result instanceof BankOperationResult.OperationError res){
            assertEquals(res.problem(), "Enter your account information in full!");
        }
        else{
            assertFalse(false);
        }
    }

}
