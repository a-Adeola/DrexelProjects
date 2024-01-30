import java.awt.event.ActionEvent;

public class SecondOperand extends State {
    public SecondOperand(ClientHandler clientHandler, Expression expression) {
        super(clientHandler, expression);
    }

    public void handle(ActionEvent e){
        String command = e.getActionCommand();

        switch (command){
            case "C":
                currentExpression.reset();
                clientHandler.updateView("0");
                clientHandler.setState(new Start(clientHandler, currentExpression));
                break;
            case "*":
            case "/":
                clientHandler.updateView(currentExpression.getValue() + command);
                MDOperator multDivExpr = new MDOperator(command);
                multDivExpr.addLeftChild(currentExpression.getRoot());
                currentExpression.setRoot(multDivExpr);
                currentExpression.setValue(command);
                clientHandler.setState(new Wait(this.clientHandler, this.currentExpression));
                break;
            case "+":
            case "-":
                clientHandler.updateView(currentExpression.getValue() + command);
                AddSubExpr addSubExpr = new AddSubExpr(command);
                addSubExpr.addLeftChild(currentExpression.getRoot());
                currentExpression.setRoot(addSubExpr);
                currentExpression.setValue(command);
                clientHandler.setState(new Wait(this.clientHandler, this.currentExpression));
                break;
            case "=":
                clientHandler.calculate();
                clientHandler.setState(new Calculate(this.clientHandler, this.currentExpression));
                break;
            default:
                clientHandler.updateView(currentExpression.getValue() + command);
                currentExpression.setValue(currentExpression.getValue() + command);
                currentExpression.getRoot().setValue(currentExpression.getValue());
                clientHandler.setState(new SecondOperand(this.clientHandler, this.currentExpression));
        }
    }

    public Expression getExpression(){
        return this.currentExpression;
    }
}
