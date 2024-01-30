package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    public static final String ID = "12345678";
    public static final String APR = "1.0";
    public static final String BALANCE = "0.0";
    Cd cd;
    Savings savings;
    Checking checking;


    @BeforeEach
    void setUp() {
        cd = new Cd(ID, APR, BALANCE);
        savings = new Savings(ID, APR, BALANCE);
        checking = new Checking(ID, APR, BALANCE);
    }


    @Test
    void account_initially_has_no_money() {
        assertEquals(BALANCE, Double.toString(savings.getBalance()));
    }

    @Test
    void deposit_updates_balance() {
        savings.deposit(100);
        assertEquals(100.00, savings.getBalance());
    }

    @Test
    void withdrawal_updates_balance() {
        checking.deposit(100);
        checking.withdraw(50);
        assertEquals(50, checking.getBalance());
    }

    @Test
    void withdrawal_can_not_go_below_zero() {
        savings.deposit(100);
        savings.withdraw(200);
        assertEquals(0, savings.getBalance());
    }

    @Test
    void withdrawal_of_same_amount_in_account_sets_balance_to_zero() {
        savings.deposit(100);
        savings.withdraw(100);
        assertEquals(0, savings.getBalance());
    }

}
