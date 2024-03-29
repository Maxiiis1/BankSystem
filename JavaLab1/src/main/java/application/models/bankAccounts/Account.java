package application.models.bankAccounts;

import application.models.money.Money;

import java.math.BigDecimal;

/**
 * Account in Bank record
 * @param Money current money value
 * @param number account number
 */
public record Account(Money Money, String number) {
    public Account addMoney(Money moneyToAdd) {
        BigDecimal money = this.Money.moneyCount().add(moneyToAdd.moneyCount());
        return new Account(new Money(money), this.number);
    }

    public Account subtractMoney(Money moneyToSub) {
        BigDecimal money = this.Money.moneyCount().subtract(moneyToSub.moneyCount());
        return new Account(new Money(money), this.number);
    }
}
