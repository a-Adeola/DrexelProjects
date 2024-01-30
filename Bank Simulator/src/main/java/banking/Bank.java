package banking;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bank {
    public Map<String, Account> accounts;


    Bank() {
        accounts = new LinkedHashMap<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public boolean accountExistsById(String id) {
        return accounts.get(id) != null;
    }

    public boolean accountTypeDeposits(String q_id) {
        return (accounts.get(q_id).allowsDeposit());
    }

    public boolean isDepositAmountInRange(String q_id, double i) {
        return accounts.get(q_id).withinMaximumDeposit(i);
    }

    public void accountDeposit(String q_id, String amt) {
        double amount = Double.parseDouble(amt);
        accounts.get(q_id).deposit(amount);
    }

    public void accountWithdrawal(String q_id, String amt) {
        double amount = Double.parseDouble(amt);
        accounts.get(q_id).withdraw(amount);
    }


}
