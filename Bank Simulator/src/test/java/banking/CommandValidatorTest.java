package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {
    public static final String ID = "12345678";
    public static final String APR = "1";
    public static final String BALANCE = "0";

    CommandValidator commandValidator;
    Bank bank;
    Savings savings;
    Checking checking;
    Cd cd;

    @BeforeEach
    void setup() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
        savings = new Savings(ID, APR, BALANCE);
        checking = new Checking(ID, APR, BALANCE);
        cd = new Cd(ID, APR, BALANCE);
    }

    @Test
    void id_shorter_than_8_digits_is_invalid() {
        savings.create(bank, "123456", APR, BALANCE);
        boolean actual = commandValidator.validate_id("123456");
        assertFalse(actual);
    }

    @Test
    void id_longer_than_8_digits_is_invalid() {
        savings.create(bank, "123456789", APR, BALANCE);
        boolean actual = commandValidator.validate_id("123456789");
        assertFalse(actual);
    }

    @Test
    void id_with_8_digits_is_valid() {
        savings.create(bank, "12345678", APR, BALANCE);
        boolean actual = commandValidator.validate_id("12345678");
        assertTrue(actual);
    }

    @Test
    void negative_apr_is_invalid() {
        String negative_apr = "-5";
        boolean actual = commandValidator.validate_apr(negative_apr);
        assertFalse(actual);
    }

    @Test
    void zero_apr_is_valid() {
        String zero_apr = "0";
        boolean actual = commandValidator.validate_apr(zero_apr);
        assertTrue(actual);
    }

    @Test
    void apr_greater_than_10_is_invalid() {
        String large_apr = "50";
        boolean actual = commandValidator.validate_apr(large_apr);
        assertFalse(actual);
    }

    @Test
    void apr_between_0_and_10_is_valid() {
        boolean actual = commandValidator.validate_apr(APR);
        assertTrue(actual);
    }

    @Test
    void apr_of_10_is_valid() {
        boolean actual = commandValidator.validate_apr("10");
        assertTrue(actual);
    }

}
