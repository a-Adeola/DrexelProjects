import abstractedInputOutput.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Date extends Question{
    private static final long serialVersionUID = 1L;

    Date(String prompt){
        super(prompt);

    }

    @Override
    public void display(){
        System.out.print(prompt + "\nA date should be entered in the following format: YYYY-MM-DD" + "\n");
    }

    //continuously asks for input until the allowed number of valid inputs is reached
    @Override
    public ArrayList take() {
        ArrayList<Object> userInput = new ArrayList<>();

        for (int i=0; i < numInputs; i++) {
            String input = null;

            // continuously ask for valid input
            while (input == null || input.length() == 0) {
                System.out.println("enter a valid date in the format YYYY-MM-DD!");
                input = In.getInstance().readDate();
            }
            userInput.add(input);
        }
        return userInput;      //returns a list of input given by the user
    }

    public void tabulate(){
        ArrayList<String> allElements = new ArrayList<>();
        for(int i = 0; i < allResponses.size(); i++) {
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

        for (Map.Entry<String, Integer> entry: occurrence.entrySet()) {
            System.out.println(entry.getKey() + "\n " + entry.getValue() +"\n");
        }
    }
}
