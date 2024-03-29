package application.buisnessLogic.bankAccounts;

import application.buisnessLogic.bankAccounts.accountsHistory.AccountSnapshotsHistory;
import application.contracts.bankAccounts.AccountOperationResult;
import application.contracts.users.IUser;
import application.models.bankAccounts.Account;
import application.models.money.Money;
import application.services.financialOperationsCounter.FinancialCounter;

public class DebitAccount extends BankAccount implements PercentReceivable{
    private FinancialCounter financialCounter = new FinancialCounter(100000, 50000);


    public DebitAccount(Account currentBankAccount, IUser currentUser, AccountSnapshotsHistory history) {
        super(currentBankAccount, currentUser, history);
        snapshotsHistory.addSnapshot(currentBankAccount);
    }

    @Override
    public AccountOperationResult withdrawMoney(Money money) {
        if (currentBankAccount.Money().moneyCount().compareTo(money.moneyCount()) <= 0){
            return new AccountOperationResult.OperationError("Not enough money!");
        }
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
    public void receivePercent(Double percent) {
        currentBankAccount = currentBankAccount.addMoney(financialCounter.countPercentOnRemainBalance(this, percent));
    }
}
