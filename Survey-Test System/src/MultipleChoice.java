import abstractedInputOutput.In;
import abstractedInputOutput.Out;

import java.util.ArrayList;

public class MultipleChoice extends Question{
    private static final long serialVersionUID = 1L;

    ArrayList<String> choices = new ArrayList<>();

    MultipleChoice(String prompt){
        super(prompt);
    }

    public void addChoice(String choice){
        choices.add(choice);
    }

    @Override
    public void display(){
        String to_display = prompt + "\n";

        for(int i = 0; i < choices.size(); i++){
            to_display+= letters.charAt(i) + ") ";
            to_display+= choices.get(i) + " ";
        }

        System.out.print(to_display + "\n");
    }

    public void addUserChoices() {
        Out.getInstance().say("Enter the number of choices for your multiple-choice question");
        int choice_num = In.getInstance().readInt();

        while(choice_num < 1){                                                       //ensures input is valid
            Out.getInstance().say("number of choices must be more than 0!");
            choice_num = In.getInstance().readInt();
        }

        int count = 0;

        while (count != choice_num) {
            Out.getInstance().say("Enter choice #" + (count+1));
            String choice = In.getInstance().readStr();
            addChoice(choice);

            count += 1;
        }

        Out.getInstance().say("How many choices can the user enter?");
        numInputs = In.getInstance().readInt();
        while(numInputs >= choice_num || numInputs < 1 ){                                //ensures that given number of allowed choices is valid
            Out.getInstance().say("The number of responses must be less than the number of choices and more than 0!");
            numInputs = In.getInstance().readInt();
        }
    }

    @Override
    public void modify(){
        Out.getInstance().say("Do you wish to modify the prompt?\n");

        String promptModify = In.getInstance().readStr();

        if(promptModify.equalsIgnoreCase("yes")) {    //prompt modification
            Out.getInstance().say("Enter your new prompt:");
            String new_prompt = In.getInstance().readStr();

            prompt = new_prompt;
        }
                                                                 //choices modification
        Out.getInstance().say("Do you wish to modify choices?\n");

        String option = In.getInstance().readStr();

        if (option.equalsIgnoreCase("yes")) {
            StringBuilder display = new StringBuilder();
            Out.getInstance().say("Which choice do you want to modify?: (enter the actual choice string)");
            for(int i = 0; i < choices.size(); i++){
                display.append(letters.charAt(i)).append(") ");
                display.append(choices.get(i)).append(" ");
            }
            System.out.println(display);

            String old_choice = In.getInstance().readStr();

            if(choices.contains(old_choice)){
                Out.getInstance().say("Enter the new choice:");
                String new_choice = In.getInstance().readStr();

                for (int i = 0; i < choices.size(); i++) {
                    if(choices.get(i).equalsIgnoreCase(old_choice)){
                        choices.set(i,new_choice);
                    }
                }
            }
            else {
                System.out.println("That is not a valid choice. Try again.");
            }
        }
    }

    @Override
    public ArrayList take() {
        ArrayList<String> userInput = new ArrayList<>();

        for (int i=0; i < numInputs; i++) {
            String input = null;
             // continuously ask for valid input
            while (input == null || input.length() != 1 || !(letters.contains(input))) {
                System.out.println("enter the letter corresponding to your choice!");
                input = In.getInstance().readStr();
            }
            userInput.add(input);
        }
        return userInput;        //returns a list of input given by the user
    }

    public String type(){
        return "multiple choice";
    }
}
