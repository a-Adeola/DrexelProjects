import java.awt.event.ActionEvent;

public class Wait extends State {
    public Wait(ClientHandler clientHandler, Expression expression) {
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
            case "+":
            case "/":
            case "-":
            case "=":
                clientHandler.setState(new Error(this.clientHandler, this.currentExpression));
                break;
            default:
                clientHandler.updateView(currentExpression.getValue() + command);
                AtomExpr newAtom = new AtomExpr(command);
                if(currentExpression.getRoot().getValue().equals("*")){
                    currentExpression.getRoot().addRightChild(newAtom);
                }else if (currentExpression.getRoot().getValue().equals("/")){
                    if(command.equals("0")){
                        clientHandler.setState(new Error(this.clientHandler, this.currentExpression));
                        break;
                    } else {
                        currentExpression.getRoot().addRightChild(newAtom);
                    }
                }
                else {
                    newAtom.addLeftChild(currentExpression.getRoot());
                    currentExpression.setRoot(newAtom);
                    currentExpression.setValue(command);
                }
                clientHandler.setState(new SecondOperand(this.clientHandler, this.currentExpression));
                break;
        }
    }

    public Expression getExpression(){
        return this.currentExpression;
    }
}
