package banking;

public class Savings extends Account {

    Savings(String id, String apr, String balance) {
        super(id, apr, balance);

    }

    public void create(Bank bank, String id, String apr, String balance) {
        bank.accounts.put(id, new Savings(id, apr, balance));
    }

    @Override
    public boolean withinMaximumDeposit(double amount) {
        double max_deposit = 2500;
        return (amount <= max_deposit && amount >= 0);
    }


}
