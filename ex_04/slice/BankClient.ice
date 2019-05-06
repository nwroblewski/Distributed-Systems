module BankClient{

    exception InvalidPeselException {
        string reason;
    };

    exception InvalidDateFormatException{
        string reason;
    };
    
    exception UnsupportedCurrencyException{
        string reason;
    };
    
    enum AccountType {
        REGULAR = 0,
        PREMIUM = 1
    };

    enum Currency {
        PLN = 0,
        USD = 1,
        EUR = 2,
        AUD = 3,
        CZK = 4,
        DKK = 5
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

    struct Credit {
        double cost;
        Currency currency;
        Date beginDate;
        Date endDate;
    };

    struct CreditInfo {
        Currency localCurrency;
        Currency creditCurrency;
        double localCurrencyCost;
        double creditCurrencyCost;
    };

    interface Account {
        double getAccountBalance();
        void depositMoney(double money);
    };

    interface PremiumAccount extends Account{
        CreditInfo takeCredit(Credit credit) throws UnsupportedCurrencyException, InvalidDateFormatException;
    };

    interface AccountFactory {
        Account* createAccount(Person person,double declaredIncome) throws InvalidPeselException;
    };

};
