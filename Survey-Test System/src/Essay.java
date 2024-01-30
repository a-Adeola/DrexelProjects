import abstractedInputOutput.In;

import java.util.ArrayList;

public class Essay extends ShortAnswer{
    private static final long serialVersionUID = 1L;

    Essay(String prompt){
        super(prompt);
    }

    //continuously asks for input until the allowed number of valid inputs is reached
    @Override
    public ArrayList take() {

        ArrayList<Object> userInput = new ArrayList<>();

        for (int i=0; i < numInputs; i++) {
            String input = null;

            while (input == null || input.length() == 0) {   //ask until input is valid
                System.out.println("enter your response!");
                input = In.getInstance().readStr();
            }
            userInput.add(input);

        }
        return userInput;        //returns a list of input given by the user
    }

    public void tabulate(){
                     //loop to print all given responses
        for (int i = 0; i < allResponses.size(); i++) {
            int count = 0;
            while(count < allResponses.get(i).size()){
                System.out.println(allResponses.get(i).get(count));
                count+=1;
            }
        }
    }

    public String type(){
        return "essay";
    }
}
