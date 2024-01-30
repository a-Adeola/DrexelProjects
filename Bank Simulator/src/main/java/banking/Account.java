package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

abstract class Account {

    public boolean withdrawn_month;
    public int total_time = 0;
    public List<String> transaction_history;
    protected double balance;
    protected double apr;
    protected String id;
    DecimalFormat decimalFormat;

    Account(String id, String apr, String bal) {
        decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        this.balance = Double.parseDouble(decimalFormat.format(Double.parseDouble(bal)));
        this.apr = Double.parseDouble(apr);
        this.id = id;
        transaction_history = new ArrayList<String>();
    }

    public boolean openingBalance(double i) {
        return (i == 0);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
        } else {
            balance = 0;
        }
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public String type() {
        return "Savings";
    }

    public double getApr() {
        return apr;
    }

    double getBalance() {
        balance = Double.parseDouble(decimalFormat.format(balance));
        return balance;
    }

    public String getId() {
        return id;
    }

    public boolean withinMaximumDeposit(double amount) {
        return false;
    }


    public boolean allowsDeposit() {
        return true;
    }


}
