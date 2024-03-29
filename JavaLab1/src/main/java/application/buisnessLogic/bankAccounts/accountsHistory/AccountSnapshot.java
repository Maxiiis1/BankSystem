package application.buisnessLogic.bankAccounts.accountsHistory;

import application.contracts.bankAccounts.AccountOperationResult;
import application.models.bankAccounts.Account;
import lombok.AllArgsConstructor;

/**
 * Snapshot that store bank account state
 */
@AllArgsConstructor
public class AccountSnapshot implements IAccountSnapshot{
    private Account bankAccount;

    /**
     * restore bank account state
     * @return account state
     */
    @Override
    public Account restore() {
        return bankAccount;
    }
}
