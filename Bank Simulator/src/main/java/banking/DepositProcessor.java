package banking;

public class DepositProcessor extends CommandProcessor {

    public DepositProcessor(Bank bank) {
        super(bank);
    }

    public void deposit_process(String command) {
        String[] command_list = command.split(" ");
        bank.accountDeposit(command_list[1], command_list[2]);

        update_account_history(command_list[1], command);
    }
}
