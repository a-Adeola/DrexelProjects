package banking;

public class Cd extends Account {

    Cd(String id, String apr, String bal) {
        super(id, apr, bal);
    }

    public void create(Bank bank, String id, String apr, String balance) {
        bank.accounts.put(id, new Cd(id, apr, balance));
    }

    @Override
    public void deposit(double amount) {
    }

    @Override
    public void withdraw(double amount) {
        balance = 0;
    }

    @Override
    public String type() {
        return "Cd";
    }

    @Override
    public boolean openingBalance(double i) {
        return (i >= 1000 && i <= 10000);
    }

    @Override
    public boolean allowsDeposit() {
        return false;
    }

}
