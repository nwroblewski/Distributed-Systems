package Java;


import Java.Utils.BankClient.*;
import Java.Utils.Logger;
import com.zeroc.Ice.Current;

public class AccountFactoryImpl implements AccountFactory {


    private final Logger logger = new Logger(AccountFactoryImpl.class.getName());
    double premiumAccountMinIncome = 8000.0;

    @Override
    public AccountPrx createAccount(Person person, double declaredIncome, Current current) throws InvalidPeselException{
        logger.log("New user created. Pesel: " + person.pesel);

    }

}
