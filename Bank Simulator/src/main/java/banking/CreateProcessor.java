package banking;

import java.util.Locale;

public class CreateProcessor extends CommandProcessor {
    public static final String CREATE_SAVINGS = "savings";
    public static final String CREATE_CHECKING = "checking";
    public static final String CREATE_CD = "cd";

    public CreateProcessor(Bank bank) {
        super(bank);
    }

    public void create_process(String command) {

        String[] command_list = command.split(" ");
        String to_create = command_list[1].toLowerCase(Locale.ROOT);

        if (to_create.equals(CREATE_SAVINGS)) {
            savings = new Savings(command_list[2], command_list[3], "0");
            savings.create(bank, command_list[2], command_list[3], "0");
        }
        if (to_create.equals(CREATE_CHECKING)) {
            checking = new Checking(command_list[2], command_list[3], "0");
            checking.create(bank, command_list[2], command_list[3], "0");
        }
        if (to_create.equals(CREATE_CD)) {
            cd = new Cd(command_list[2], command_list[3], command_list[4]);
            cd.create(bank, command_list[2], command_list[3], command_list[4]);
        }

        update_account_history(command_list[2], command);
    }

}

