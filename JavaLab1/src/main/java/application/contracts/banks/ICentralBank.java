package application.contracts.banks;

import application.models.money.Money;

public interface ICentralBank {
    IBank createBank(String name, int commission, Double percent);
    void sendNotificationToBanks();
    BankOperationResult makeMoneyTransaction(Money money, IBank bank, String accountNumber);
}
