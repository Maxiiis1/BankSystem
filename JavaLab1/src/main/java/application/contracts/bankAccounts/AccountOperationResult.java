package application.contracts.bankAccounts;

import application.buisnessLogic.bankAccounts.accountsHistory.IAccountSnapshot;
import application.models.money.Money;

public sealed interface AccountOperationResult permits
        AccountOperationResult.Success,
        AccountOperationResult.SuccessCheckingBalance,
        AccountOperationResult.SuccessCheckingAccountHistory,
        AccountOperationResult.OperationError {

    record Success() implements AccountOperationResult { }
    record SuccessCheckingBalance(Money money) implements AccountOperationResult {}
    record SuccessCheckingAccountHistory(IAccountSnapshot accountPreviousState) implements AccountOperationResult {}
    record OperationError(String problem) implements AccountOperationResult { }
}
