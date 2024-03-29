package application.contracts.bankAccounts;

import application.contracts.users.IUser;
import application.models.bankAccounts.Account;
import application.models.money.Money;

public interface IBankAccount {
    IUser getCurrentUser();
    Account getCurrentAccount();

    AccountOperationResult withdrawMoney(Money money);
    AccountOperationResult addMoney(Money money);
    AccountOperationResult checkBalance();
    AccountOperationResult undoTransaction();
}
