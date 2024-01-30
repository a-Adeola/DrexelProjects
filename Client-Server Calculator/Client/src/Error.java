import java.awt.event.ActionEvent;

public class Error extends State{

    public Error(ClientHandler clientHandler, Expression expression){
        super(clientHandler, expression);
        this.clientHandler.updateView("ERROR");
    }

    public void handle(ActionEvent e){
        String command = e.getActionCommand();

        if(command.equalsIgnoreCase("C")){
            this.clientHandler.updateView("0");
            currentExpression.reset();
            this.clientHandler.setState(new Start(clientHandler, this.currentExpression));
        } else {
            this.clientHandler.setState(new Error(this.clientHandler, this.currentExpression));
            this.clientHandler.updateView("ERROR");
        }
    }

    public Expression getExpression(){
        return this.currentExpression;
    }
}
