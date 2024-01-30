package banking;

public class CreateValidator extends CommandValidator {

    public static final String CD_TYPE = "cd";
    Cd cd;

    CreateValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] arguments = command.split(" ");
        boolean cd_val = false;
        boolean arg_create;
        boolean arg_id;
        boolean arg_apr;

        if (arguments.length < 4 || arguments.length > 5) {
            return false;
        }
        arg_apr = validate_apr(arguments[3]);
        arg_id = validate_id_creation(arguments[2]);
        arg_create = validate_type(arguments[1]);

        if (arguments.length == 4) {
            cd_val = !(arguments[1].equalsIgnoreCase(CD_TYPE));
        }

        if (arguments.length == 5) {
            cd_val = validate_cd_creation(arguments[1], arguments[4]);
        }

        return (cd_val && arg_apr && arg_create && arg_id);
    }

    public boolean validate_id_creation(String command) {
        return (!(bank.accountExistsById(command)) && command.length() == 8);
    }

    public boolean validate_cd_creation(String type, String amt) {
        cd = new Cd("12345678", "1", "0");
        return (type.equalsIgnoreCase(CD_TYPE) && cd.openingBalance(Double.parseDouble(amt)));
    }

}
