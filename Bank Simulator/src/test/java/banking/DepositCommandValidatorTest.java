package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandValidatorTest {
    public static final String ID = "12345678";
    public static final String APR = "1";
    public static final String BALANCE = "0";
    public static final String MIXED_DEPOSIT_STRING = "DePosiT 12345678 500";
    public static final String MISSING_DEPOSIT_STRING = "DePosiT 12345678";

    CommandValidator commandValidator;
    DepositValidator depositValidator;
    Bank bank;
    Savings savings;
    Checking checking;
    Cd cd;

    @BeforeEach
    void setup() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
        depositValidator = new DepositValidator(bank);
        savings = new Savings(ID, APR, BALANCE);
        checking = new Checking(ID, APR, BALANCE);
        cd = new Cd(ID, APR, BALANCE);
    }

    @Test
    void deposit_command_is_case_insensitive() {
        savings.create(bank, ID, APR, BALANCE);
        boolean actual = commandValidator.validate(MIXED_DEPOSIT_STRING);
        assertTrue(actual);
    }

    @Test
    void deposit_command_with_missing_parameter_is_invalid() {
        boolean actual = commandValidator.validate(MISSING_DEPOSIT_STRING);
        assertFalse(actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        checking.create(bank, ID, APR, BALANCE);
        boolean actual = commandValidator.validate("deposi 12345678 300");
        assertFalse(actual);
    }

    @Test
    void deposit_into_existing_account_is_valid() {
        checking.create(bank, ID, APR, BALANCE);
        boolean actual = commandValidator.is_existing_id(ID);
        assertTrue(actual);
    }

    @Test
    void cannot_deposit_into_account_that_does_not_exist() {
        boolean actual = commandValidator.validate(MIXED_DEPOSIT_STRING);
        assertFalse(actual);
    }

    @Test
    void deposit_into_checking_account_type_is_valid() {
        checking.create(bank, ID, APR, BALANCE);
        boolean actual = depositValidator.validate_deposit_type(ID);
        assertTrue(actual);
    }

    @Test
    void deposit_into_savings_account_type_is_valid() {
        savings.create(bank, ID, APR, BALANCE);
        boolean actual = depositValidator.validate_deposit_type(ID);
        assertTrue(actual);
    }

    @Test
    void cannot_deposit_into_account_type_not_checking_or_savings() {
        cd.create(bank, ID, APR, BALANCE);
        boolean actual = depositValidator.validate_deposit_type(ID);
        assertFalse(actual);
    }

    @Test
    void can_deposit_within_savings_account_type_deposit_limit() {
        savings.create(bank, ID, APR, BALANCE);
        boolean actual = depositValidator.validate_deposit_amount(ID, "2500");
        assertTrue(actual);
    }

    @Test
    void can_deposit_within_checking_account_type_deposit_limit() {
        checking.create(bank, ID, APR, BALANCE);
        boolean actual = depositValidator.validate_deposit_amount(ID, "1000");
        assertTrue(actual);
    }

    @Test
    void cannot_deposit_more_than_account_types_deposit_limit() {
        checking.create(bank, ID, APR, BALANCE);
        boolean actual = depositValidator.validate_deposit_amount(ID, "3000000");
        assertFalse(actual);
    }

    @Test
    void can_deposit_zero_amount_into_checking_account_type() {
        checking.create(bank, ID, APR, BALANCE);
        boolean actual = depositValidator.validate_deposit_amount(ID, "0");
        assertTrue(actual);
    }

    @Test
    void can_deposit_zero_amount_into_savings_account_type() {
        savings.create(bank, ID, APR, BALANCE);
        boolean actual = depositValidator.validate_deposit_amount(ID, "0");
        assertTrue(actual);
    }


    @Test
    void cannot_deposit_negative_amount() {
        savings.create(bank, ID, APR, "-200");
        boolean actual = depositValidator.validate_deposit_amount(ID, "-200");
        assertFalse(actual);
    }

    @Test
    void cannot_deposit_into_cd_account_type() {
        cd.create(bank, ID, APR, BALANCE);
        boolean actual = depositValidator.validate_deposit_amount(ID, "100");
        assertFalse(actual);
    }


}

