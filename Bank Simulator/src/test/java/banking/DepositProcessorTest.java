package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositProcessorTest {
    public static final String deposit_input = "deposit 12345678 100";
    public static final String ID_1 = "12345678";
    public static final String APR = "1.0";
    public static final String ZERO = "0";


    Bank bank;
    CommandProcessor commandProcessor;
    Checking checking;


    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
        checking = new Checking(ID_1, APR, ZERO);
    }

    @Test
    void deposit_command_updates_accounts_balance_with_no_money() {
        checking.create(bank, ID_1, APR, ZERO);
        commandProcessor.process(deposit_input);
        assertEquals(100, bank.getAccounts().get(ID_1).getBalance());

    }

    @Test
    void deposit_command_updates_account_balance_with_existing_money() {
        checking.create(bank, ID_1, APR, ZERO);
        commandProcessor.process(deposit_input);
        commandProcessor.process(deposit_input);
        assertEquals(200, bank.getAccounts().get(ID_1).getBalance());
    }
}
