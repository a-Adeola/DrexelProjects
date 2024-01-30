import java.awt.event.ActionEvent;

public class Calculate extends State {
    public Calculate(ClientHandler clientHandler, Expression expression) {
        super(clientHandler, expression);
    }

    public void handle(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "C":
            case "+":
            case "*":
            case "/":
            case "-":
                currentExpression.reset();
                clientHandler.updateView("0");
                clientHandler.setState(new Start(clientHandler, null));
                break;
            case "=":
                this.clientHandler.updateView("ERROR");
                this.clientHandler.setState(new Error(this.clientHandler, this.currentExpression));
                break;
            default:
                //ready for new calculation if digit is entered
                currentExpression.setRoot(new AtomExpr(command));
                currentExpression.setValue(command);
                clientHandler.updateView(command);
                clientHandler.setState(new FirstOperand(this.clientHandler, this.currentExpression));
        }
    }

    public Expression getExpression(){
        return this.currentExpression;
    }
}
