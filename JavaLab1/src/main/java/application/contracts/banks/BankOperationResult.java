package application.contracts.banks;

import application.contracts.bankAccounts.AccountOperationResult;

public sealed interface BankOperationResult permits
        BankOperationResult.Success,
        BankOperationResult.OperationError {

    record Success() implements BankOperationResult { }

    record OperationError(String problem) implements BankOperationResult { }

}
