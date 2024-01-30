import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception{

        ServerSocket server = new ServerSocket(9085);

        while(true){
            Socket client = server.accept();

            RequestHandler handler = new RequestHandler(client);
            handler.request();
            handler.start();

            handler.printOperations();
        }
    }
}
