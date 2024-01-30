package abstractedInputOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHelper {
    // If file is null, read from System.in
    public static String readStr(String file) {
        BufferedReader in = null;
        String line = "-1";
        try{
            if(file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            // Verify the input exists
            while(line == null || line.length() == 0){
                Out.getInstance().say("Please enter valid input of at least 1 char");
                line = in.readLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return line;
    }

    // If file is null, read from System.in
    public static int readInt(String file) {
        BufferedReader in = null;
        String line = "-1";
        try{
            if(file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            while(line == null || line.length() == 0 || !isInt(line)) {
                Out.getInstance().say("Please enter a valid int");
                line = in.readLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return Integer.parseInt(line);
    }

    public static String readDate(String file) {
        BufferedReader in = null;
        String line = "-1";
        try{
            if(file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            // Verify the input exists
            while(line == null || line.length() != 10 || !(isYear(line.substring(0,4))) || !(isMonth(line.substring(5,7))) || !(isDay(line.substring(8,10))) || line.charAt(4) != '-' || line.charAt(7) != '-'){
                Out.getInstance().say("Please enter a valid date in the format YYYY-MM-DD");
                line = in.readLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return line;
    }

    // If file is null, read from System.in
    public static double readDouble(String file) {
        BufferedReader in = null;
        String line = "-1";
        try{
            if(file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            while(line == null || line.length() == 0 || !isDouble(line)) {
                Out.getInstance().say("Please enter a valid double");
                line = in.readLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return Double.parseDouble(line);
    }


    // Returns true if the input can be parsed to an int, else false
    private static boolean isInt(String num) {
        try {
            Integer.parseInt(num);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    // Returns true if the input can be parsed to a double, else false
    private static boolean isDouble(String num) {
        try {
            Double.parseDouble(num);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    private static boolean isMonth(String num) {
        boolean isInteger = isInt(num);
        if(isInteger){
            return (Integer.parseInt(num) <= 12 && Integer.parseInt(num) > 0);
        }
        else {
            return false;
        }

    }

    private static boolean isDay(String num) {
        boolean isInteger = isInt(num);
        if(isInteger){
            return (Integer.parseInt(num) <= 31 && Integer.parseInt(num) > 0);
        }
        else {
            return false;
        }
    }

    private static boolean isYear(String num) {
        boolean isInteger = isInt(num);
        if(isInteger){
            return (Integer.parseInt(num) > 0);
        }
        else {
            return false;
        }
    }

}
