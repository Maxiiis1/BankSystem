package application.contracts.banks;

import application.buisnessLogic.banks.notificationStrategy.IUserNotificationStrategy;
import application.contracts.bankAccounts.IBankAccount;
import application.contracts.users.IUser;
import application.models.money.Money;

public interface IBank {
    void sendNotificationToUsers();
    void chargeCommissionAndPercents();
    void setUserNotificationStrategy(IUserNotificationStrategy strategy);
    void setPercentBalance(Double percent);
    void setCommissionValue(int commission);
    void subscribeOnNotification(IUser user);
    void registerBankAccountToSystem(IBankAccount bankAccount);
    BankOperationResult sendMoney(IBankAccount moneySender, IBank bank, String accountNumber, Money money);
    BankOperationResult getMoney(String accountNumber, Money money);
}
