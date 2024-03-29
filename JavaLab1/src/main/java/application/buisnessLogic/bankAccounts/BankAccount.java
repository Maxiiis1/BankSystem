package application.buisnessLogic.bankAccounts;

import application.buisnessLogic.bankAccounts.accountsHistory.AccountSnapshotsHistory;
import application.contracts.bankAccounts.AccountOperationResult;
import application.contracts.bankAccounts.IBankAccount;
import application.contracts.users.IUser;
import application.models.bankAccounts.Account;
import application.models.money.Money;
import application.services.financialOperationsCounter.FinancialCounter;
import lombok.AllArgsConstructor;

/**
 * abstract Bank account class for three types of accounts
 * with checkBalance and undoTransaction realization
 */
@AllArgsConstructor
public abstract class BankAccount implements IBankAccount {
    /**
     * There are current account, user,
     * snaphots of account to undo transactions
     */
    protected Account currentBankAccount;
    protected IUser currentUser;
    protected AccountSnapshotsHistory snapshotsHistory;

    /**
     * Allows to see balance
     * @return current balance
     */
    @Override
    public AccountOperationResult checkBalance() {
        return new AccountOperationResult.SuccessCheckingBalance(currentBankAccount.Money());
    }

    /**
     * Undo previous money transaction
     * @return result of undo transaction
     */
    @Override
    public AccountOperationResult undoTransaction() {
        if (snapshotsHistory.getLastAccountState() instanceof AccountOperationResult.SuccessCheckingAccountHistory state){
            currentBankAccount = state.accountPreviousState().restore();
            return new AccountOperationResult.Success();
        }

        return new AccountOperationResult.OperationError("Impossible to undo transaction!");

    }
    @Override
    public IUser getCurrentUser() {
        return currentUser;
    }
    public Account getCurrentAccount() {
        return currentBankAccount;
    }

    /**
     * Withdrawing money from account
     * @param money value to withdraw
     * @return operation result
     */
    @Override
    public abstract AccountOperationResult withdrawMoney(Money money);

    /**
     * Adding money from account
     * @param money value to add
     * @return operation result
     */
    @Override
    public abstract AccountOperationResult addMoney(Money money);
}
