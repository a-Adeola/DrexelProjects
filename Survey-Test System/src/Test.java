import abstractedInputOutput.In;
import abstractedInputOutput.Out;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Test extends Survey{
    private static final long serialVersionUID = 1L;

    private static String saveDirPath = "Tests" + File.separator;
    protected ArrayList<Object> correctResponses = new ArrayList<>();
    protected Response correctAnswer = new Response(new ArrayList());


    Test(){}

    public void createQuestions(String q_type) {
         //creates & adds the questions to the test, then ask for the correct answer to the questions
        Out.getInstance().say("Enter the prompt for your " + q_type + " question:");
        String question_prompt = In.getInstance().readStr();

        if (q_type.equalsIgnoreCase("short answer")) {
            ShortAnswer short_answer = new ShortAnswer(question_prompt);
            short_answer.getUserInputNum();
            addQuestion(short_answer);
            askCorrectAnswer(short_answer);
        } else if (q_type.equalsIgnoreCase("essay")) {
            Essay essay = new Essay(question_prompt);
            essay.getUserInputNum();
            addQuestion(essay);
            askCorrectAnswer(essay);
        } else if (q_type.equalsIgnoreCase("date")) {
            Date date = new Date(question_prompt);
            date.getUserInputNum();
            addQuestion(date);
            askCorrectAnswer(date);
        } else if (q_type.equalsIgnoreCase("matching")) {
            Matching match = new Matching(question_prompt);
            match.addOptions();
            addQuestion(match);
            askCorrectAnswer(match);
        } else if (q_type.equalsIgnoreCase("multiple choice")) {
            MultipleChoice multiple = new MultipleChoice(question_prompt);
            multiple.addUserChoices();
            addQuestion(multiple);
            askCorrectAnswer(multiple);
        } else if (q_type.equalsIgnoreCase("true/false")) {
            TrueFalse trueFalse = new TrueFalse(question_prompt);
            trueFalse.addChoice("True");
            trueFalse.addChoice("False");
            addQuestion(trueFalse);
            askCorrectAnswer(trueFalse);
        }
    }

    //ask user for correct answer to created question
    public void askCorrectAnswer(Question toAsk){
        if(toAsk.type().equalsIgnoreCase("essay")){  //essay has no correct answer so user is not asked
            ArrayList<Object> noRightAnswer = new ArrayList<>();
            noRightAnswer.add(" ");
            correctResponses.add(noRightAnswer);
        }
        else {
            Out.getInstance().say("Enter the correct answer(s) to the question");
            ArrayList<Object> rightResponse = toAsk.take();
            correctResponses.add(rightResponse);
        }
    }

    public void addCorrectResponses(){      //take all given correct answers to the questions and save as a Response object
        correctAnswer = new Response(correctResponses);
        responseName = name +"-CorrectAnswer";
        saveResponses(correctAnswer);
    }

    public static void serialize(Test test){   //save Test to Test directory

        createDirectory(Test.saveDirPath);
        String savePath = Test.saveDirPath + test.getName();
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream(savePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(test);
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(2);
        }
        finally{
            try{
                if(fos != null)
                    fos.close();
                if(oos != null)
                    oos.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public static Test deserialize(String path){
        File test = new File(path);

        if(!test.exists() || !test.isFile())
            throw new IllegalArgumentException(path + " is invalid");
        Test deserializedTest = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            deserializedTest = (Test) ois.readObject();
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

        return deserializedTest;    //returns the deserialized or loaded test
    }

    public void displayWithAnswer(Response loadedAnswer){
        //display all questions and the correct answer for each question
        for(int i = 0; i < questions.size(); i++){
            System.out.print((i+1 + ") "));
            questions.get(i).display();
            System.out.println();
            if(!questions.get(i).type().equalsIgnoreCase("essay")){
            System.out.println("Correct Answer:");
            }
            System.out.println(loadedAnswer.getResponseAt(i));
        }
    }

    public void grade(Response loadedAnswer, Response loadedResponse) {
        double essays = 0;
        double compared = 0;

        for (int i = 0; i < questions.size(); i++) {
            if(questions.get(i).type().equalsIgnoreCase("essay")){
                essays+=1;       //increments number of essay questions
            } else {
                    if(Response.compare(loadedAnswer.getResponseAt(i), loadedResponse.getResponseAt(i))){
                        compared+=1;    // increments number of comparable questions (questions that are not essays)
                    }
                }
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.00");            //formats doubles to 2 decimal places
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        loadedResponse.setGrade(Double.parseDouble(decimalFormat.format((compared/questions.size()) * 100)));

        double canCalc = (((questions.size()-essays)*100)/ questions.size());
        canCalc = Double.parseDouble(decimalFormat.format(canCalc));            //number of points that can be calculated(does not include points from essays)


        Out.getInstance().say("You received "+ loadedResponse.getGrade() + " on the test. The test was worth 100 points.");
        if(essays>0){
            System.out.println("Only " + canCalc + " of those points could be auto graded because there was " + essays+ " essay question(s).\n");
        }
    }

              //modify for test including the correct answer
    public void modifyAdapter(Response loadedAnswer) {
        display();
        Out.getInstance().say("What question do you wish to modify?\n");
        int toModify = In.getInstance().readInt();
        toModify -=1;

        if(toModify > questions.size()){            //validate question to be modified
            System.out.println("Enter a valid question integer to modify!");
        } else {
            questions.get(toModify).modify();
        }
                                                              //modify correct answer to question if applicable
        if(!(questions.get(toModify).type().equalsIgnoreCase("essay"))){
            Out.getInstance().say("Do you wish to modify the correct answer to the question?\n");
            String userChoice = null;

            while(userChoice == null){
                Out.getInstance().say("(enter yes/no:)");
                userChoice = In.getInstance().readStr();
            }

            if(userChoice.equalsIgnoreCase("yes")){      //correct answer modification
                ArrayList<Object> newAns = questions.get(toModify).take();
                loadedAnswer.modify(toModify, newAns);
            }
        }
    }

    public String type(){
        return "test";
    }
}
