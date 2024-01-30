import abstractedInputOutput.In;
import abstractedInputOutput.Out;

import java.io.*;
import java.util.ArrayList;


public class Survey implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String saveDirPath = "Surveys" + File.separator;
    protected String name;
    protected String responseName;

    protected ArrayList<Question> questions = new ArrayList<>();
    protected ArrayList<Object> responses = new ArrayList<>();
    protected Response givenResponse = new Response(new ArrayList());


    Survey(){}

    public void addQuestion(Question q){
        questions.add(q);
    }

    public void setName(String survey_name){
        name = survey_name;
    }

    public String getName(){
        return name;
    }

    public void addQuestionResponse(ArrayList takenResponse){
        responses.add(takenResponse);
    }

    public void display() {
        for(int i = 0; i < questions.size(); i++){
            System.out.print((i+1 + ") "));
            questions.get(i).display();
            System.out.println();
        }
    }


    public void take(){
        Out.getInstance().say("Enter the name of your response: ");
        responseName = name + " - ";
        responseName += In.getInstance().readStr();


        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).display();
            System.out.println();
            ArrayList toAdd = questions.get(i).take();
            questions.get(i).addNewResponse(i,toAdd);
            addQuestionResponse(toAdd);
        }

        givenResponse = new Response(responses);  //create a response with the survey list
        saveResponses(givenResponse);
    }

    public void modify(){
        Out.getInstance().say("What question do you wish to modify?\n");
        int toModify = In.getInstance().readInt();
        toModify -=1;

        if(toModify > questions.size()){
            System.out.println("Enter a valid question integer to modify!");
        } else {
            questions.get(toModify).modify();
        }
    }

    public void tabulate() {
        for(int i = 0; i < questions.size(); i++){
            System.out.print((i+1 + ") "));
            questions.get(i).display();
            questions.get(i).tabulate();
            System.out.println();
        }
    }

    public void createQuestions(String q_type) {
        //create questions and adds the question to the survey
        Out.getInstance().say("Enter the prompt for your "+ q_type + " question:");
        String question_prompt = In.getInstance().readStr();

        if(q_type.equalsIgnoreCase("short answer")){
            ShortAnswer short_answer = new ShortAnswer(question_prompt);
            short_answer.getUserInputNum();
            addQuestion(short_answer);
        }
        else if(q_type.equalsIgnoreCase("essay")){
            Essay essay = new Essay(question_prompt);
            essay.getUserInputNum();
            addQuestion(essay);
        }
        else if(q_type.equalsIgnoreCase("date")){
            Date date = new Date(question_prompt);
            date.getUserInputNum();
            addQuestion(date);
        }
        else if(q_type.equalsIgnoreCase("matching")){
            Matching match = new Matching(question_prompt);
            match.addOptions();
            addQuestion(match);
        }
        else if(q_type.equalsIgnoreCase("multiple choice")){
            MultipleChoice multiple = new MultipleChoice(question_prompt);
            multiple.addUserChoices();
            addQuestion(multiple);
        }
        else if(q_type.equalsIgnoreCase("true/false")){
            TrueFalse trueFalse = new TrueFalse(question_prompt);
            trueFalse.addChoice("True");
            trueFalse.addChoice("False");
            addQuestion(trueFalse);
        }
    }

                       //save survey to a file using the survey name as the file name
    public static void serialize(Survey survey){

        createDirectory(Survey.saveDirPath);
        String savePath = Survey.saveDirPath + survey.getName();
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{                         //saves survey to Surveys directory
            fos = new FileOutputStream(savePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(survey);
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

    //load a survey from the Surveys directory
    public static Survey deserialize(String path){
        File survey = new File(path);

        if(!survey.exists() || !survey.isFile())
            throw new IllegalArgumentException(path + " is invalid");
        Survey deserializedSurvey = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            deserializedSurvey = (Survey) ois.readObject();
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

        return deserializedSurvey;       //returns the previously saved/deserialized survey
    }


    protected static boolean createDirectory(String dirPath) {
        File dir = new File(dirPath);
                   //creates directory if it doesn't already exist
        if(!dir.exists())
            return dir.mkdirs();

        return dir.isDirectory();
    }


    public void saveResponses(Response toSave){
        String dirPath ="Responses" + File.separator;
        createDirectory(dirPath);

        String savePath = dirPath + responseName;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{                           //saves the given response to the Responses directory
            fos = new FileOutputStream(savePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(toSave);
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

    public String type(){
        return "Survey";
    }
}
