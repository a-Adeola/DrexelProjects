package banking;

public class CommandProcessor {
    public static final String CREATE = "create";
    public static final String DEPOSIT = "deposit";
    public static final String WITHDRAW = "withdraw";
    public static final String TRANSFER = "transfer";
    public static final String PASS = "pass";

    Bank bank;
    Savings savings;
    Checking checking;
    Cd cd;
    CreateProcessor createProcessor;
    DepositProcessor depositProcessor;
    WithdrawProcessor withdrawProcessor;
    TransferProcessor transferProcessor;
    PassTimeProcessor passTimeProcessor;


    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        createProcessor = new CreateProcessor(bank);
        depositProcessor = new DepositProcessor(bank);
        withdrawProcessor = new WithdrawProcessor(bank);
        transferProcessor = new TransferProcessor(bank);
        passTimeProcessor = new PassTimeProcessor(bank);


        String[] actual = command.split(" ");

        if (actual[0].equalsIgnoreCase(CREATE)) {
            createProcessor.create_process(command);
        }

        if (actual[0].equalsIgnoreCase(DEPOSIT)) {
            depositProcessor.deposit_process(command);
        }

        if (actual[0].equalsIgnoreCase(WITHDRAW)) {
            withdrawProcessor.withdraw_process(command);
        }

        if (actual[0].equalsIgnoreCase(TRANSFER)) {
            transferProcessor.transfer_process(command);
        }

        if (actual[0].equalsIgnoreCase(PASS)) {
            passTimeProcessor.pass_time_process(actual);
        }

    }

    public void update_account_history(String q_id, String command) {
        bank.update_history(q_id, command);
    }
}
