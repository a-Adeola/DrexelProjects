import abstractedInputOutput.In;

import java.util.ArrayList;

public class TrueFalse extends MultipleChoice{
    private static final long serialVersionUID = 1L;


    TrueFalse(String prompt){
        super(prompt);
    }

    @Override
    public void display(){
        System.out.print(prompt+"\nT/F\n");
    }

    //continuously asks for input until the allowed number of valid inputs is reached
    @Override
    public ArrayList take() {
        ArrayList<Object> userInput = new ArrayList<>();
        String input = null;

        while(input == null || input.length() != 1 || !((input.equalsIgnoreCase("T")) || (input.equalsIgnoreCase("F")))) {
            System.out.println("enter T or F!");
            input = In.getInstance().readStr();
        }
        userInput.add(input);

        return userInput;  //returns a list of input given by the user
    }

    public void getUserInputNum() { //input number is always 1
    }
}
