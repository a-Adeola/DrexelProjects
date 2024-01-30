import java.io.*;
import java.util.ArrayList;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String saveDirPath = "Responses" + File.separator;
    private double grade=0;

    protected ArrayList<ArrayList> responses;

    Response(ArrayList responses){
        this.responses = responses;
    }

    //loads a Response from the Response directory using the given path
    public static Response deserialize(String path){
        File test = new File(path);

        if(!test.exists() || !test.isFile())
            throw new IllegalArgumentException(path + " is invalid");
        Response deserializedResponse = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            deserializedResponse = (Response) ois.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.exit(2);
        }
        finally{
            try {
                if(ois != null)
                    ois.close();
                if(fis != null)
                    fis.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return deserializedResponse;     //returns loaded response
    }

    public static boolean compare(String loadedAnswer, String loadedResponse){
        return (loadedAnswer.equalsIgnoreCase(loadedResponse));          //checks if the responses match
    }

                                  //return the response at a given index as a String with each input on a new line
    public String getResponseAt(int index){
        String toReturn = "";
        int i=0;
        while(i < responses.get(index).size()){
            toReturn += responses.get(index).get(i) + "\n";
            i+=1;
        }
        return toReturn;
    }

    public double getGrade(){
        return grade;
    }

    public void setGrade(double newGrade){
        grade = newGrade;
    }

    public void modify(int toModify, ArrayList<Object> newAns){
        responses.set(toModify,newAns);
    }

    public String type(){
        return "Response";
    }
}
