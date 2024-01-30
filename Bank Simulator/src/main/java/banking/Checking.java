package banking;

public class Checking extends Account {

    Checking(String id, String apr, String balance) {
        super(id, apr, balance);
    }

    public void create(Bank bank, String id, String apr, String balance) {
        bank.accounts.put(id, new Checking(id, apr, balance));
    }

    @Override
    public String type() {
        return "Checking";
    }

    @Override
    public boolean withinMaximumDeposit(double amount) {
        double max_deposit = 1000;
        return (amount <= max_deposit && amount >= 0);
    }

}


