package application.buisnessLogic.bankAccounts.accountProxy;

import application.contracts.bankAccounts.AccountOperationResult;
import application.contracts.bankAccounts.IBankAccount;
import application.contracts.users.IUser;
import application.models.bankAccounts.Account;
import application.models.money.Money;
import application.services.verificationChain.AddressVerificationHandler;
import application.services.verificationChain.IVerificationHandler;
import application.services.verificationChain.PassportVerificationHandler;

/**
 * Proxy of account to check extra info before transactions of unsafe account
 */
public class AccountAccessChecker implements IBankAccount {
    private IBankAccount bankAccount;
    private IVerificationHandler verificator;

    public AccountAccessChecker(IBankAccount bankAccount){
        this.bankAccount = bankAccount;
        verificator = new AddressVerificationHandler()
                .setNext(new PassportVerificationHandler());
    }

    /**
     * Checking that all extra info like passport and address was presented and call account withdrawing money method
     * @return checking result
     */
    @Override
    public AccountOperationResult withdrawMoney(Money money) {
        if (verificator.verify(bankAccount.getCurrentUser())){
            return new AccountOperationResult.OperationError("Enter your account information in full!");
        }
        return bankAccount.withdrawMoney(money);
    }

    /**
     * Checking that all extra info like passport and address was presented and call account adding money method
     * @return checking result
     */
    @Override
    public AccountOperationResult addMoney(Money money) {
        if (verificator.verify(bankAccount.getCurrentUser())){
            return new AccountOperationResult.OperationError("Enter your account information in full!");
        }
        return bankAccount.addMoney(money);
    }

    @Override
    public AccountOperationResult checkBalance() {
        return bankAccount.checkBalance();
    }

    @Override
    public AccountOperationResult undoTransaction() {
        return bankAccount.undoTransaction();
    }
    @Override
    public IUser getCurrentUser() {
        return bankAccount.getCurrentUser();
    }

    @Override
    public Account getCurrentAccount() {
        return bankAccount.getCurrentAccount();
    }
}
