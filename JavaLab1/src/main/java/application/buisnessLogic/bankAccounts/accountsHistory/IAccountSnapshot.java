package application.buisnessLogic.bankAccounts.accountsHistory;

import application.contracts.bankAccounts.AccountOperationResult;
import application.models.bankAccounts.Account;

public interface IAccountSnapshot {
    Account restore();
}
