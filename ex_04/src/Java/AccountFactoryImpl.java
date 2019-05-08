package Java;


import Java.Utils.BankClient.*;
import Java.Utils.Logger;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;

public class AccountFactoryImpl implements AccountFactory {


    private final Logger logger = new Logger(AccountFactoryImpl.class.getName());
    double premiumAccountMinIncome = 8000.0;

    @Override
    public AccountPrx createAccount(Person person, Money declaredIncome, Current current) throws InvalidPeselException{
        if(!isPeselValid(person.pesel)) throw new InvalidPeselException("Invalid pesel number format.");
        if(declaredIncome.amount > premiumAccountMinIncome){
            logger.log("New premium account created with Pesel: " + person.pesel);
            return PremiumAccountPrx.uncheckedCast(current.adapter.add(
                    new PremiumAccountImpl(person,declaredIncome),new Identity(person.pesel,"BankClient")));
        }

        logger.log("New regular account created with Pesel: " + person.pesel);
        return AccountPrx.uncheckedCast(current.adapter.add(
                new AccountImpl(person,declaredIncome), new Identity(person.pesel,"BankClient")));
    }

    private boolean isPeselValid(String pesel){
        if(!pesel.matches("\\d+") || pesel.length() != 11){
            return false;
        }
        return true;
    }
}
