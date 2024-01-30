package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandProcessorTest {
    public static final String create_input = "create savings 12345678 1.0";
    public static final String ID_1 = "12345678";
    public static final String APR = "1.0";


    Bank bank;
    CommandProcessor commandProcessor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void create_command_adds_an_account_to_bank() {
        commandProcessor.process(create_input);
        assertTrue(bank.accountExistsById(ID_1));
    }

    @Test
    void create_command_adds_correct_apr_to_account() {
        commandProcessor.process(create_input);
        String actual = Double.toString(bank.getAccounts().get(ID_1).getApr());
        assertEquals(APR, actual);
    }

    @Test
    void create_command_creates_correct_account_type() {
        commandProcessor.process(create_input);
        assertEquals("Savings", bank.getAccounts().get(ID_1).type());

    }

    @Test
    void savings_account_is_created_with_zero_balance() {
        commandProcessor.process("create savings 12345678 0.5");
        assertEquals(0, bank.getAccounts().get(ID_1).getBalance());
    }
}
