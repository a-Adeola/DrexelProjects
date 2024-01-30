package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CDTest {

    public static final String ID = "12345678";
    public static final String APR = "1.0";
    public static final String BALANCE = "0.0";
    public static final String TYPE = "Cd";
    Cd cd;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        cd = new Cd(ID, APR, BALANCE);
    }

    @Test
    void cd_account_has_type() {
        assertEquals(TYPE, cd.type());
    }

    @Test
    void savings_account_has_id() {
        assertEquals(ID, cd.getId());
    }

    @Test
    void cd_account_has_apr() {
        assertEquals(APR, Double.toString(cd.getApr()));
    }

    @Test
    void successfully_create_cd_account() {
        cd.create(bank, ID, APR, BALANCE);
        assertEquals(ID, bank.getAccounts().get(ID).getId());
    }

    @Test
    void cd_opening_balance_cannot_be_below_1000() {
        boolean actual = cd.openingBalance(0);
        assertFalse(actual);
    }

    @Test
    void cd_opening_balance_cannot_be_more_than_10000() {
        boolean actual = cd.openingBalance(1000000);
        assertFalse(actual);
    }

    @Test
    void cd_account_can_be_opened_with_1000_balance() {
        boolean actual = cd.openingBalance(1000);
        assertTrue(actual);
    }

    @Test
    void cd_account_can_be_opened_with_10000_balance() {
        boolean actual = cd.openingBalance(10000);
        assertTrue(actual);
    }
}
