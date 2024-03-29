package application.buisnessLogic.bankAccounts;

import application.buisnessLogic.bankAccounts.accountsHistory.AccountSnapshotsHistory;
import application.contracts.bankAccounts.AccountOperationResult;
import application.contracts.users.IUser;
import application.models.bankAccounts.Account;
import application.models.money.Money;
import application.services.financialOperationsCounter.FinancialCounter;

/**
 * Realisation of bank account on deposit base, receive percents on balance
 */
public class DepositAccount extends BankAccount implements PercentReceivable {
    private FinancialCounter financialCounter = new FinancialCounter(100000, 50000);

    public DepositAccount(Account currentBankAccount, IUser currentUser, AccountSnapshotsHistory history) {
        super(currentBankAccount, currentUser, history);
        snapshotsHistory.addSnapshot(currentBankAccount);
    }

    @Override
    public AccountOperationResult withdrawMoney(Money money) {
        currentBankAccount = currentBankAccount.subtractMoney(money);
        snapshotsHistory.addSnapshot(currentBankAccount);
        return new AccountOperationResult.Success();
    }
    @Override
    public AccountOperationResult addMoney(Money money) {
        currentBankAccount = currentBankAccount.addMoney(money);
        snapshotsHistory.addSnapshot(currentBankAccount);
        return new AccountOperationResult.Success();
    }

    /**
     * Add percents to account
     * @param percent value to receive as percent of current balance
     */
    @Override
    public void receivePercent(Double percent) {
        currentBankAccount = currentBankAccount.addMoney(financialCounter.countPercentOnRemainBalance(this, percent));
    }
}
