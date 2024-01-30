package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SavingsTest {
    public static final String ID = "12345678";
    public static final String APR = "1.0";
    public static final String BALANCE = "0.0";
    public static final String TYPE = "Savings";
    Savings savings;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        savings = new Savings(ID, APR, BALANCE);
    }

    @Test
    void savings_account_has_type() {
        assertEquals(TYPE, savings.type());
    }

    @Test
    void savings_account_has_apr() {
        assertEquals(APR, Double.toString(savings.getApr()));
    }

    @Test
    void savings_account_has_id() {
        assertEquals(ID, savings.getId());
    }

    @Test
    void savings_account_has_balance() {
        assertEquals(BALANCE, Double.toString(savings.getBalance()));
    }

    @Test
    void savings_account_designated_opening_balance_is_zero() {
        boolean actual = savings.openingBalance(0);
        assertTrue(actual);
    }

    @Test
    void savings_account_opening_balance_must_be_zero() {
        boolean actual = savings.openingBalance(200);
        assertFalse(actual);
    }

    @Test
    void successfully_create_savings_account() {
        savings.create(bank, ID, APR, BALANCE);
        assertEquals(ID, bank.getAccounts().get(ID).getId());
    }


}
