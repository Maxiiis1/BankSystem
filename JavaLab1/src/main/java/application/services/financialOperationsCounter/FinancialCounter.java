package application.services.financialOperationsCounter;

import application.buisnessLogic.bankAccounts.DepositAccount;
import application.contracts.bankAccounts.IBankAccount;
import application.models.bankAccounts.Account;
import application.models.money.Money;
import java.math.BigDecimal;

/**
 * Class makes commissions counting
 * and counts percent of current balance to add
 */
public class FinancialCounter {
    /**
     * lower value of start deposit,
     * higher value,
     * current percent that was accumulated
     * day of percent counting
     */
    private int lowerDeposit;
    private int higherDeposit;
    private Money percentOnBalance = new Money(new BigDecimal(0));
    private int percentDay = 0;

    public FinancialCounter(int higherDeposit, int lowerDeposit){
        this.higherDeposit = higherDeposit;
        this.lowerDeposit = lowerDeposit;
    }

    /**
     * Withdrawing commission from balance
     * @param account from witch subtract money commission
     * @param commission value of commission needed to subtract
     */
    public void countCommission(Account account, int commission){
        account.subtractMoney(new Money(BigDecimal.valueOf(commission)));
    }

    /**
     * Add percent of current balance to balance after 1 month
     * @param bankAccount account to witch add percents
     * @param percents percents added from 1 day
     * @return total percents from 1 month to add
     */
    public Money countPercentOnRemainBalance(IBankAccount bankAccount, Double percents){
        if (bankAccount instanceof DepositAccount account){
            if (account.getCurrentAccount().Money().moneyCount().compareTo(new BigDecimal(lowerDeposit)) <= 0){
                percentOnBalance.moneyCount().add(bankAccount.getCurrentAccount().Money().moneyCount().multiply(new BigDecimal(0.3)));
            }
            else if (account.getCurrentAccount().Money().moneyCount().compareTo(new BigDecimal(higherDeposit)) > 0) {
                percentOnBalance.moneyCount().add(bankAccount.getCurrentAccount().Money().moneyCount().multiply(new BigDecimal(0.5)));
            }
            else{
                percentOnBalance.moneyCount().add(bankAccount.getCurrentAccount().Money().moneyCount().multiply(new BigDecimal(0.4)));
            }
        }
        else{
            percentOnBalance.moneyCount().add(bankAccount.getCurrentAccount().Money().moneyCount().multiply(BigDecimal.valueOf(percents)));
        }
        if (percentDay == 30) {
            percentDay = 0;
            return percentOnBalance;
        }
        percentDay++;
        return new Money(new BigDecimal(0));
    }
}
