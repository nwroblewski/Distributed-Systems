package Java;

import Java.Utils.BankClient.*;
import Java.Utils.Logger;
import com.zeroc.Ice.Current;

import java.util.regex.Pattern;

public class AccountImpl implements Account{


    private final Logger logger = new Logger(AccountImpl.class.getName());

    private Person person;
    private Money income;
    private double balance;
    private String password;

    public AccountImpl(Person person, Money income){
        this.person = person;
        this.income = income;
    }

    @Override
    public double getAccountBalance(Current current) {
        logger.log("Person with pesel " + person.pesel + " balance is: " + this.balance);
        return this.balance;
    }

    @Override
    public void depositMoney(Money money, Current current){
        logger.log("Person with pesel " + person.pesel + " deposited" + money);
        //TODO CURRENCY EXCHANGE HERE WHEN MAKING DEPOSIT
        this.balance += money.amount;
    }
}
