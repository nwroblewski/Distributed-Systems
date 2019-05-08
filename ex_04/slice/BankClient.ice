module BankClient{


    enum AccountType { REGULAR, PREMIUM };

    enum Currency { PLN, USD, EUR, AUD, CZK, DKK };
    
    exception InvalidPeselException {
        string reason;
    };

    exception InvalidDateFormatException{
        string reason;
    };
    
    exception UnsupportedCurrencyException{
        string reason;
    };
    
    struct Person {
        string name;
        string surname;
        string pesel;
    };

    struct Date {
        short day;
        short month;
        short year;
    };

    struct Money {
        Currency currency;
        double amount;
    };

    struct Credit {
        Money cost;
        Date beginDate;
        Date endDate;
    };

    struct CreditInfo {
        Money localCost;
        Money creditCost;
    };

    interface Account {
        double getAccountBalance();
        void depositMoney(Money money);
    };

    interface PremiumAccount extends Account{
        CreditInfo takeCredit(Credit credit) throws UnsupportedCurrencyException, InvalidDateFormatException;
    };

    //implementation of this class will return the key for account.
    interface AccountFactory {
        Account* createAccount(Person person,Money declaredIncome) throws InvalidPeselException;
    };

};
