import java.awt.event.ActionEvent;
import java.util.Objects;

public class Start extends State {

    public Start(ClientHandler clientHandler, Expression expression) {

        super(clientHandler, expression);

        if(expression != null){
            clientHandler.setState(new FirstOperand(this.clientHandler, this.currentExpression));
        }
    }

    public void handle(ActionEvent e){
        clientHandler.updateView("0");
        String command = e.getActionCommand();
        currentExpression = new AtomExpr(command);
        currentExpression.reset();

        if (!Objects.equals(command, "C") && !Objects.equals(command, "*") && !Objects.equals(command, "/") && !Objects.equals(command, "+") && !Objects.equals(command, "-") && !Objects.equals(command, "=")){
            currentExpression.setRoot(new AtomExpr(command));
            currentExpression.setValue(command);
            clientHandler.updateView(command);
            clientHandler.setState(new FirstOperand(this.clientHandler, this.currentExpression));
        }

        //else stay in this same state
    }

    public Expression getExpression(){
        return this.currentExpression;
    }
}
