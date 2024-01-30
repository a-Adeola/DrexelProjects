import abstractedInputOutput.In;
import abstractedInputOutput.Out;

import java.util.ArrayList;

public class Matching extends Question{
    private static final long serialVersionUID = 1L;
    private ArrayList<String> left_side = new ArrayList<>();
    private ArrayList<String> right_side = new ArrayList<>();
    Matching(String prompt){
        super(prompt);
    }

    public void addOptions() {
        Out.getInstance().say("How many entry sets do you want:");
        int num = In.getInstance().readInt();
        numInputs = num;

        while(num >0){                                       //request options for left and right side options
            Out.getInstance().say("Enter a left entry:");
            String leftOption = In.getInstance().readStr();
            left_side.add(leftOption);

            Out.getInstance().say("Enter a right entry:");
            String rightOption = In.getInstance().readStr();
            right_side.add(rightOption);

            num -=1;
        }
    }

    @Override
    public void display(){
        System.out.print(prompt + "\n");

        for(int i = 0; i < right_side.size(); i++){
            System.out.print(letters.charAt(i) + ") ");
            System.out.printf("%-20s", left_side.get(i));
            System.out.print((i+1) + ") ");
            System.out.printf("%-20s", right_side.get(i));
            System.out.println();
        }

    }


    @Override
    public void modify(){
        display();
        Out.getInstance().say("Do you wish to modify the prompt?\n");

        String promptModify = In.getInstance().readStr();

        if(promptModify.equalsIgnoreCase("yes")) {
            Out.getInstance().say("Enter your new prompt:");
            String new_prompt = In.getInstance().readStr();

            prompt = new_prompt;
        }
        modifyOptions();        //call to modifyOptions for modification of options

    }//modify tag

    public void modifyOptions(){
        //left side modification
        Out.getInstance().say("Do you wish to modify a left option?\n");

        String modifyLeftOption = In.getInstance().readStr();

        if (modifyLeftOption.equalsIgnoreCase("yes")){
            Out.getInstance().say("Which option do you want to modify? (enter the actual option):");

            String oldOption = In.getInstance().readStr();

            if(left_side.contains(oldOption)){   //check if old option exists, then ask for new option
                Out.getInstance().say("Enter the new option:");
                String newOption = In.getInstance().readStr();

                for (int i = 0; i < left_side.size(); i++) {
                    if(left_side.get(i).equalsIgnoreCase(oldOption)){
                        left_side.set(i,newOption);
                    }
                }
            } else {
                System.out.println("That option does not exist. Try again");
                return;
            }
        }

        //right side modification
        Out.getInstance().say("Do you wish to modify a right option?\n");

        String modifyRightOption = In.getInstance().readStr();

        if (modifyRightOption.equalsIgnoreCase("yes")) {
            Out.getInstance().say("Which option do you want to modify? (enter the actual option):");

            String oldOption = In.getInstance().readStr();

            if (right_side.contains(oldOption)) {             //check if old option exists, then ask for new option
                Out.getInstance().say("Enter the new option:");
                String newOption = In.getInstance().readStr();

                for (int i = 0; i < right_side.size(); i++) {
                    if (right_side.get(i).equalsIgnoreCase(oldOption)) {
                        right_side.set(i, newOption);
                    }
                }
            } else {
                System.out.println("That option does not exist. Try again");
            }
        }
        numInputs = right_side.size();

    }

    @Override
    public ArrayList take() {
        ArrayList<Object> userInput = new ArrayList<>();

        for (int i=0; i < numInputs; i++) {
            String input = null;

            // continuously ask for valid input
            while (input == null || input.length() != 2 || !(letters.contains(input.substring(0,1))) || !(checkInt(input.substring(1))) || !(checkLetter(input.substring(0,1)))) {
                System.out.println("enter a valid letter and number combination!");
                input = In.getInstance().readStr();
            }
            userInput.add(input);
        }
        return userInput;       //returns a list of input given by the user
    }


    //check if a given user input is an integer
    public boolean checkInt(String stringValue){
        try {
            Integer.parseInt((stringValue));
            return (Integer.parseInt(stringValue) <= left_side.size() && Integer.parseInt(stringValue) >= 1);
        }
        catch(NumberFormatException e){
            return false;
        }
    }

    //check if a given user input is a letter corresponding to an option
    public boolean checkLetter(String stringValue){
        return (letters.substring(0,(numInputs)).contains(stringValue));
    }

    public String type(){
        return "matching";
    }

}
