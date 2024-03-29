package application.buisnessLogic.banks;

import application.buisnessLogic.bankAccounts.CommissionReceivable;
import application.buisnessLogic.bankAccounts.PercentReceivable;
import application.buisnessLogic.banks.notificationStrategy.IUserNotificationStrategy;
import application.buisnessLogic.banks.notificationStrategy.LocalUserNotification;
import application.contracts.bankAccounts.AccountOperationResult;
import application.contracts.bankAccounts.IBankAccount;
import application.contracts.banks.BankOperationResult;
import application.contracts.banks.IBank;
import application.contracts.banks.ICentralBank;
import application.contracts.users.IUser;
import application.models.bankAccounts.Notification;
import application.models.money.Money;
import java.util.ArrayList;
import java.util.List;

/**
 * Realization of bank
 */
public class Bank implements IBank {
    private int commissionForUsing;
    private Double percentBalance;
    private final ArrayList<IUser> users = new ArrayList<>();
    private List<IBankAccount> accounts;
    private Notification notification;
    private IUserNotificationStrategy userNotificationStrategy = new LocalUserNotification();
    private String name;
    private ICentralBank centralBank;

    public Bank(String name, ICentralBank centralBank, int commissionForUsing, Double percentBalance){
        this.name = name;
        this.centralBank = centralBank;
        this.commissionForUsing = commissionForUsing;
        this.percentBalance = percentBalance;
        this.notification = new Notification("");
        this.accounts = new ArrayList<>();
    }

    /**
     * Send money to another account throughout central bank
     * @param moneySender who sends
     * @param accountNumber whom to send
     * @param money value to send
     * @param bank bank of user whom we send money
     * @return operation result
     */
    @Override
    public  BankOperationResult sendMoney(IBankAccount moneySender, IBank bank, String accountNumber, Money money) {
        if (accounts.contains(moneySender)){
            if (moneySender.withdrawMoney(money) instanceof AccountOperationResult.OperationError result){
                return new BankOperationResult.OperationError(result.problem());
            }

            return centralBank.makeMoneyTransaction(money, bank, accountNumber);
        }

        return new BankOperationResult.OperationError("Register your bank account to system!");
    }

    /**
     * Gets money from central bank and sends to account
     * @param accountNumber whom to send from central bank
     * @param money value to send
     * @return operation result
     */
    @Override
    public BankOperationResult getMoney(String accountNumber, Money money) {
        for (var account: accounts) {
            if (account.getCurrentAccount().number() == accountNumber){
                account.addMoney(money);

                return new BankOperationResult.Success();
            }
        }

        return new BankOperationResult.OperationError("No account with similar account number!");
    }

    /**
     * Charge commission and percents on all accounts of current bank
     */
    @Override
    public void chargeCommissionAndPercents() {
        for (var account: accounts) {
            if (account instanceof CommissionReceivable commissionReceivable){
                commissionReceivable.receiveCommission(commissionForUsing);
            } else if (account instanceof PercentReceivable percentReceivable) {
                percentReceivable.receivePercent(percentBalance);
            }
        }
    }

    /**
     * Sets way of sending notifications to users
     * @param strategy way of sending notifications
     */
    public void setUserNotificationStrategy(IUserNotificationStrategy strategy){
        this.userNotificationStrategy = strategy;
    }

    @Override
    public void setPercentBalance(Double percent) {
        this.percentBalance = percent;
        notification = new Notification("Percent on balance was changed!");
    }

    @Override
    public void setCommissionValue(int commission) {
        this.commissionForUsing = commission;
        notification = new Notification("Commission was changed!");
    }

    @Override
    public void subscribeOnNotification(IUser user) {
        users.add(user);
    }

    @Override
    public void registerBankAccountToSystem(IBankAccount bankAccount) {
        accounts.add(bankAccount);
    }

    @Override
    public void sendNotificationToUsers() {
        userNotificationStrategy.execute(users, notification);
    }
}
