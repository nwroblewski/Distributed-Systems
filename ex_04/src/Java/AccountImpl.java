package Java;

import Java.Utils.BankClient.*;
import Java.Utils.Logger;
import com.zeroc.Ice.Current;

public class AccountImpl implements Account{


    private final Logger logger = new Logger(AccountImpl.class.getName());

    private Person person;
    private double income;
    private double balance;

    public AccountImpl(Person person, double income){
        this.person = person;
        this.income = income;
    }

    @Override
    public double getAccountBalance(Current current) {
        logger.log("Person with pesel " + person.pesel + " balance is: " + this.balance);
        return this.balance;
    }

    @Override
    public void depositMoney(double money, Current current) {
        logger.log("Person with pesel " + person.pesel + " deposited" + money);
        this.balance += money;
    }
}
