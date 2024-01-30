import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientHandler implements ActionListener {
    protected View view;
    protected State state;

    public ClientHandler(){
        state = new Start(this, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (state instanceof Error){
            System.out.println("Error state. Reset (C)");
        }

        state.handle(e);
    }

    public void calculate(){
        CalculationVisitor calculateVisitor = new CalculationVisitor();
        String result = state.getExpression().accept(calculateVisitor);

        String sendOut = printout();
        sendOut+= "=" + result;
        System.out.println(sendOut);

        try {
            send(sendOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String printout(){
        PrintVisitor printVisitor = new PrintVisitor();
        String printout = state.getExpression().accept(printVisitor);
        return printout;
    }

    public void setCalculator(View newView) {
        this.view = newView;
    }

    public void updateView(String update){
        this.view.updateResult(update);
    }

    public void setState(State newState){
        this.state = newState;
    }

    public void send(String output) throws IOException {
        try {
            CalcClient.sendToServer(output);
        } catch (IOException e) {
            System.out.println("failed to send to server");
            e.printStackTrace();
        }
    }

}
