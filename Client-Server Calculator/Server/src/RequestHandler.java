import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class RequestHandler extends Thread{
    private Socket clientSocket = null;
    public static ArrayList<String> operations = new ArrayList<String>();
    public static boolean printout = false;
    public static boolean asked = false;

    public RequestHandler(Socket client){
        this.clientSocket = client;
    }

    public void start(){
        try{
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);

            String equation = (String) ois.readObject();
            operations.add(equation);
            ois.close();
            inputStream.close();
            clientSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void printOperations() {
        if(printout){
            for (String operation : operations) {
                System.out.println(operation);
            }
            System.out.println("no. of successful operations: " + operations.size());

            System.out.println("-----------------------");
        }

    }

    public void request(){
        if(!asked){
            System.out.println("Do you want to know how many successful operations there are?\n");
            Scanner sc = new Scanner(System.in);

            String line = sc.nextLine();

            if(line.equalsIgnoreCase("yes") || line.equalsIgnoreCase("y")){
                printout = true;
            }
            asked = true;
        }
    }

}
