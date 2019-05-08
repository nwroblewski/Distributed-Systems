package Java;

import Java.Utils.BankClient.*;
import Java.Utils.Logger;
import com.zeroc.Ice.Current;

public class PremiumAccountImpl extends AccountImpl implements PremiumAccount {


    private final Logger logger = new Logger(PremiumAccountImpl.class.getName());

    public PremiumAccountImpl(Person person, Money income){
        super(person,income);
    }

    @Override
    public CreditInfo takeCredit(Credit credit, Current current) throws InvalidDateFormatException, UnsupportedCurrencyException {
        return null;
    }

    //TODO calculate credit cost based on income
    private double calcCreditCost(){
        return 0.0;
    }
}
