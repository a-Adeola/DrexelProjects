import abstractedInputOutput.In;

import java.util.*;

public class ShortAnswer extends Question{
    private static final long serialVersionUID = 1L;
    protected String errorMessage = "";
    ShortAnswer(String prompt){
        super(prompt);
    }


    //continuously asks for input until the allowed number of valid inputs is reached
    @Override
    public ArrayList<Object> take() {
        ArrayList<Object> userInput = new ArrayList<>();
        for (int i=0; i < numInputs; i++) {
            System.out.println("Enter a response:");
            String input = In.getInstance().readStr();
            while (input == null || input.length() > 15 || input.length() == 0) {
                System.out.println("enter a max of 15 characters");
                input = In.getInstance().readStr();
            }
            userInput.add(input);
        }
        return userInput;            //returns a list of input given by the user
    }

    public String type(){
        return "short answer";
    }


}
