package application.buisnessLogic.banks;

import application.contracts.bankAccounts.AccountOperationResult;
import application.contracts.banks.BankOperationResult;
import application.contracts.banks.IBank;
import application.contracts.banks.ICentralBank;
import application.models.bankAccounts.Notification;
import application.models.money.Money;

import java.util.ArrayList;

/**
 * Realization of central bank which is director of all banks
 */
public class CentralBank implements ICentralBank {
    private ArrayList<IBank> banks = new ArrayList<>();
    private Notification notification;

    public void setNotification(Notification newNotification){
        this.notification = newNotification;
    }

    /**
     * Allows to creat new bank and registrate it in Central bank
     * @param commission commission that will be in bank
     * @param percent percents that will be in bank
     * @return new bank
     */
    @Override
    public IBank createBank(String name, int commission, Double percent) {
        IBank bank = new Bank(name, this, commission, percent);
        banks.add(bank);
        return bank;
    }

    /**
     * Sending current notifications to banks added in central bank system
     */
    @Override
    public void sendNotificationToBanks() {
        for (var bank: banks) {
            bank.chargeCommissionAndPercents();
        }
    }

    /**
     * Making all money transactions between two accs
     * @param money to send
     * @param bank sender
     * @param accountNumber account whom to send money
     * @return result of transaction
     */
    @Override
    public BankOperationResult makeMoneyTransaction(Money money, IBank bank, String accountNumber) {
        if (banks.contains(bank)){
            return bank.getMoney(accountNumber, money);
        }

        return new BankOperationResult.OperationError("Your bank is not registered in central bank!");
    }
}
