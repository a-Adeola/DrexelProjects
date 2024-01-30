package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandValidatorTest {
    public static final String ID = "12345678";
    public static final String APR = "1";
    public static final String BALANCE = "0";
    public static final String MIXED_SAVINGS_CASES = "cReAtE savIngs 12345678 0.6";
    public static final String MIXED_CD_CASES = "cReAtE CD 12345678 0.6 1000";
    public static final String MISSING_CD_CASES = "cReAtE cd 12345678 0.6";
    public static final String MISSING_SAVINGS_CASES = "cReAtE savIngs 12345678";

    CommandValidator commandValidator;
    CreateValidator createValidator;
    Bank bank;
    Savings savings;
    Checking checking;
    Cd cd;

    @BeforeEach
    void setup() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
        createValidator = new CreateValidator(bank);
        cd = new Cd(ID, APR, BALANCE);
        savings = new Savings(ID, APR, BALANCE);
        checking = new Checking(ID, APR, BALANCE);

    }

    @Test
    void creation_command_is_case_insensitive() {
        boolean actual = commandValidator.validate(MIXED_SAVINGS_CASES);
        assertTrue(actual);
    }

    @Test
    void creation_command_with_missing_parameter_is_invalid() {
        boolean actual = commandValidator.validate(MISSING_SAVINGS_CASES);
        assertFalse(actual);
    }

    @Test
    void typo_in_create_command_is_invalid() {
        boolean actual = commandValidator.validate("creat savings 12345678 0.4");
        assertFalse(actual);
    }

    @Test
    void create_cd_command_is_case_insensitive() {
        boolean actual = commandValidator.validate(MIXED_CD_CASES);
        assertTrue(actual);
    }

    @Test
    void creation_command_with_missing_parameter_is_invalid_for_cd() {
        boolean actual = commandValidator.validate(MISSING_CD_CASES);
        assertFalse(actual);
    }

    @Test
    void create_account_type_not_savings_or_checking_or_cd_is_invalid() {
        boolean actual = commandValidator.validate("create current 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void valid_id_in_create_command() {
        boolean actual = createValidator.validate_id_creation(ID);
        assertTrue(actual);
    }

    @Test
    void duplicate_id_is_invalid_during_creation() {
        checking.create(bank, ID, APR, BALANCE);
        boolean actual = createValidator.validate_id_creation(ID);
        assertFalse(actual);
    }

    @Test
    void can_only_create_type_savings_checking_or_cd() {
        boolean actual = commandValidator.validate("create sleep 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void savings_account_opened_with_no_balance_is_valid() {
        boolean actual = commandValidator.validate("create savings 12345678 1");
        assertTrue(actual);
    }

    @Test
    void checking_account_opened_with_no_balance_is_valid() {
        boolean actual = commandValidator.validate("create checking 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void savings_or_checking_account_opened_with_a_balance_is_invalid() {
        boolean actual = commandValidator.validate("create checking 12345678 0.6 90");
        assertFalse(actual);
    }

    @Test
    void cd_account_opened_with_negative_balance_is_invalid() {
        boolean actual = commandValidator.validate("create cd 12345678 0.6 -5");
        assertFalse(actual);
    }

    @Test
    void cd_account_created_with_balance_between_1000_and_10000_is_valid() {
        boolean actual = commandValidator.validate("create cd 12345678 0.6 2000");
        assertTrue(actual);
    }

    @Test
    void cd_account_created_with_balance_more_than_10000_is_invalid() {
        boolean actual = commandValidator.validate("create cd 12345678 10 200000");
        assertFalse(actual);
    }

    @Test
    void cd_account_created_with_zero_balance_is_invalid() {
        boolean actual = commandValidator.validate("create cd 12345678 0 0");
        assertFalse(actual);
    }


}
