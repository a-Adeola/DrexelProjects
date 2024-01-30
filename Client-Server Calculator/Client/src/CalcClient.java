import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CalcClient {
    public static void main(String[] args){

        ClientHandler clientHandler = new ClientHandler();
        View view = new View();
        view.attachListener(clientHandler);
        clientHandler.setCalculator(view);
    }

    public static void sendToServer(String output) throws IOException {
        Socket socket = new Socket("localhost", 9085);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);

        oos.writeObject(output);
        oos.flush();
    }
}
