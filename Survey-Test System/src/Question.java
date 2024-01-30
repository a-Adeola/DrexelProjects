import abstractedInputOutput.In;
import abstractedInputOutput.Out;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public String prompt;
    protected Response response = new Response(new ArrayList<>());
    protected ArrayList<ArrayList> allResponses = new ArrayList<ArrayList>();
    int numInputs = 1;


    Question(String prompt){
        this.prompt = prompt;
    }

    public void display() {
        System.out.print(prompt + "\n");
    }

    public String type(){
        return "question";
    }

    public void modify(){

        Out.getInstance().say("Enter your new prompt:");
        String new_prompt = In.getInstance().readStr();

        prompt = new_prompt;
    }

    public ArrayList<Object> take() {
        ArrayList<Object> userInput = new ArrayList<>();
        String input = In.getInstance().readStr();
        userInput.add(input);
        response = new Response(userInput);

        return userInput;   //returns a list of input given by the user
    }

    public Response getResponses(){
        return response;
    }

    //asks the user for the number of inputs a question can have
    public void getUserInputNum() {
        Out.getInstance().say("How many responses can the user enter?");
        numInputs = In.getInstance().readInt();
        while(numInputs < 1 ){
            Out.getInstance().say("The number of responses must be more than 0!");
            numInputs = In.getInstance().readInt();
        }
    }

    //puts all given responses in a new list and prints out each element and its number of occurrences
    public void tabulate() {
        ArrayList<String> allElements = new ArrayList<>();
        for (int i = 0; i < allResponses.size(); i++) {
            int count = 0;
            while(count < allResponses.get(i).size()){
                allElements.add(String.valueOf(allResponses.get(i).get(count)));
                count+=1;
            }
        }
                             //copy elements of allElements array to a hashmap
        Map<String, Integer> occurrence = new HashMap<>();
        for (String e: allElements)
        {
            Integer count = occurrence.get(e);
            if (count == null) {
                count = 0;
            }
            occurrence.put(e, count + 1);
        }
                              //loop to print every element and its number of occurrences
        for (Map.Entry<String, Integer> entry: occurrence.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    //adds the given user responses to a list storing the responses
    public void addNewResponse(int index, ArrayList newResponse){
        if(allResponses.size() <= index){
            allResponses.add(newResponse);
        } else {
            int cnt =0;

            while(cnt < newResponse.size()){
                allResponses.get(index).add(newResponse.get(cnt));
                cnt +=1;
            }
        }
    }
}

