package banking;

public class DepositValidator extends CommandValidator {


    DepositValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] arguments = command.split(" ");
        boolean arg_deposit;
        boolean arg_id;
        boolean arg_amount;

        if (arguments.length != 3) {
            return false;
        }

        arg_id = is_existing_id(arguments[1]);
        if (!arg_id) {
            return false;
        }
        arg_deposit = validate_deposit_type(arguments[1]);
        arg_amount = validate_deposit_amount(arguments[1], arguments[2]);


        return (arg_deposit && arg_amount);
    }


    public boolean validate_deposit_amount(String q_id, String amt) {
        double amount = Double.parseDouble(amt);
        return (bank.isDepositAmountInRange(q_id, amount));
    }

}