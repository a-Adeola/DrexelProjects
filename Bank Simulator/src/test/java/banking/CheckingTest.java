package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckingTest {

    public static final String ID = "12345678";
    public static final String APR = "1.0";
    public static final String BALANCE = "0.0";
    public static final String TYPE = "Checking";
    Checking checking;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(ID, APR, BALANCE);
    }

    @Test
    void checking_account_has_type() {
        assertEquals(TYPE, checking.type());
    }

    @Test
    void checking_account_has_apr() {
        assertEquals(APR, Double.toString(checking.getApr()));
    }

    @Test
    void savings_account_has_id() {
        assertEquals(ID, checking.getId());
    }

    @Test
    void checking_account_has_balance() {
        assertEquals(BALANCE, Double.toString(checking.getBalance()));
    }

    @Test
    void checking_account_designated_opening_balance_is_zero() {
        boolean actual = checking.openingBalance(0);
        assertTrue(actual);
    }

    @Test
    void checking_account_opening_balance_must_be_zero() {
        boolean actual = checking.openingBalance(200);
        assertFalse(actual);
    }

    @Test
    void successfully_create_checking_account() {
        checking.create(bank, ID, APR, BALANCE);
        assertEquals(ID, bank.getAccounts().get(ID).getId());
    }


}

