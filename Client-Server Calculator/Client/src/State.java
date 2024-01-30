import java.awt.event.ActionEvent;

public abstract class State {
    protected ClientHandler clientHandler;
    protected Expression currentExpression;

    public State(ClientHandler clientHandler, Expression expression){
        this.clientHandler = clientHandler;
        this.currentExpression = expression;
    }

    public abstract void handle(ActionEvent e);

    public abstract Expression getExpression();

}
