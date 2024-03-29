package application.buisnessLogic.bankAccounts;

import application.buisnessLogic.bankAccounts.accountsHistory.AccountSnapshotsHistory;
import application.contracts.bankAccounts.AccountOperationResult;
import application.contracts.users.IUser;
import application.models.bankAccounts.Account;
import application.models.money.Money;
import application.services.financialOperationsCounter.FinancialCounter;

public class CreditAccount extends BankAccount implements CommissionReceivable {
    private FinancialCounter financialCounter;
    private final int higherDeposit = 100000;
    private final int lowerDeposit = 50000;
    public CreditAccount(Account currentBankAccount, IUser currentUser, AccountSnapshotsHistory history) {
        super(currentBankAccount, currentUser, history);
        snapshotsHistory.addSnapshot(currentBankAccount);
        financialCounter  = new FinancialCounter(higherDeposit, lowerDeposit);
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

    @Override
    public void receiveCommission(int commission) {
        financialCounter.countCommission(currentBankAccount, commission);
    }
}
