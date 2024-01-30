import abstractedInputOutput.In;
import abstractedInputOutput.Out;
import java.io.File;


public class MainDriver {

    static Survey loadedSurvey = null;
    static Test loadedTest = null;
    static Response loadedAnswer = null;
    static Response loadedResponse = null;

    public static void main(String[] args) {
        while(true) {
            System.out.println("Choose an option\n" +
                    "1) Survey\n" +
                    "2) Test\n3)Quit");
            int mainInput = In.getInstance().readInt();
            if(mainInput==1){
                SurveyMainMenu();
            } else if(mainInput==2){
                TestMainMenu();
            } else if(mainInput==3){
                break;
            } else {
                System.out.println("Please enter a valid integer input!");
            }
        }
    }

    public static void SurveyMainMenu(){
        while(true) {

            System.out.println("1) Create a new Survey\n" +
                    "2) Display an existing Survey\n" +
                    "3) Load an existing Survey\n" +
                    "4) Save the current Survey\n" +
                    "5) Take the current Survey\n" +
                    "6) Modifying the current Survey\n" +
                    "7) Tabulate\n8)Quit");

            Out.getInstance().say("Enter a number from 1-7: ");
            int consoleInt = In.getInstance().readInt();

            if (consoleInt == 1) {
                Out.getInstance().say("Enter a name for your survey: ");
                String surveyName = In.getInstance().readStr();
                Survey survey = new Survey();
                survey.setName(surveyName);
                createSurveyMenu(survey);
            } else if (consoleInt == 2) {
                display("Survey");
            } else if (consoleInt == 3) {
                loadMenu("Survey");
            } else if (consoleInt == 4) {
                saveMenu("Survey");
            } else if (consoleInt == 5) {
                takeMenu("Survey");
            } else if (consoleInt == 6) {
                modifyMenu("Survey");
            } else if (consoleInt == 7) {
                tabulateMenu("Survey");
            } else if (consoleInt == 8) {
                loadedSurvey = null;
                break; 
            }else {
                System.out.println("You must enter a valid integer input!");
            }
        }
    }

    public static void TestMainMenu(){
        while(true) {

            System.out.println("1) Create a new Test\n" +
                    "2) Display an existing Test without correct answers\n" +
                    "3) Display an existing Test with correct answers\n" +
                    "4) Load an existing Test\n" +
                    "5) Save the current Test\n" +
                    "6) Take the current Test\n" +
                    "7) Modifying the current Test\n" +
                    "8) Tabulate\n9) Grade a Test\n10)Return to previous menu" );

            Out.getInstance().say("Enter a number from 1-7: ");
            int consoleInt = In.getInstance().readInt();

            if (consoleInt == 1) {
                Out.getInstance().say("Enter a name for your test: ");
                String testName = In.getInstance().readStr();
                Test test = new Test();
                test.setName(testName);
                createTestMenu(test);
            } else if (consoleInt == 2) {
                display("Test");
            } else if(consoleInt == 3){
                displayWithResponse();
            }
            else if (consoleInt == 4) {
                loadMenu("Test");
            } else if (consoleInt == 5) {
                saveMenu("Test");
            } else if (consoleInt == 6) {
                takeMenu("Test");
            } else if (consoleInt == 7) {
                modifyMenu("Test");
            } else if (consoleInt == 8) {
                tabulateMenu("Test");
            } else if(consoleInt == 9){
                gradeTest();
            } else if(consoleInt == 10){   //DONE
                loadedTest = null;
                break;
            }
            else{
                System.out.println("You must enter a valid integer input!");
            }
        }
    }


    public static void createSurveyMenu(Survey toCreate){
        System.out.println("1) Add a new T/F question\n" +
                "2) Add a new multiple-choice question\n" +
                "3) Add a new short answer question\n" +
                "4) Add a new essay question\n" +
                "5) Add a new date question\n" +
                "6) Add a new matching question\n" +
                "7) Return to previous menu");

        Out.getInstance().say("Choose a number from 1-7: ");
        int consoleInt = In.getInstance().readInt();

        if (consoleInt == 1) {
            toCreate.createQuestions("true/false");
        } else if (consoleInt == 2) {
            toCreate.createQuestions("multiple choice");
        } else if (consoleInt == 3) {
            toCreate.createQuestions("Short answer");
        } else if (consoleInt == 4) {
            toCreate.createQuestions("essay");
        } else if (consoleInt == 5) {
            toCreate.createQuestions("date");
        } else if (consoleInt == 6) {
            toCreate.createQuestions("matching");
        } else if (consoleInt == 7) {
            Survey.serialize(toCreate);
            return;
        } else {
            System.out.println("Please enter a valid integer input!");
        }
        createSurveyMenu(toCreate);
    }

    public static void createTestMenu(Test toCreate){
        System.out.println("1) Add a new T/F question\n" +
                "2) Add a new multiple-choice question\n" +
                "3) Add a new short answer question\n" +
                "4) Add a new essay question\n" +
                "5) Add a new date question\n" +
                "6) Add a new matching question\n" +
                "7) Return to previous menu");

        Out.getInstance().say("Choose a number from 1-7: ");
        int consoleInt = In.getInstance().readInt();

        if (consoleInt == 1) {
            toCreate.createQuestions("true/false");
        } else if (consoleInt == 2) {
            toCreate.createQuestions("multiple choice");
        } else if (consoleInt == 3) {
            toCreate.createQuestions("Short answer");
        } else if (consoleInt == 4) {
            toCreate.createQuestions("essay");
        } else if (consoleInt == 5) {
            toCreate.createQuestions("date");
        } else if (consoleInt == 6) {
            toCreate.createQuestions("matching");
        } else if (consoleInt == 7) {
            toCreate.addCorrectResponses();
            Test.serialize(toCreate);
            return;
        } else {
            System.out.println("Enter a valid input!");
        }
        createTestMenu(toCreate);
    }

    public static void display(String type){
        if(type.equalsIgnoreCase("Survey")){
            if(loadedSurvey == null){
                System.out.println("You must have a survey loaded in order to display it.\n");
                return;
            }
            loadedSurvey.display();
        } else if(type.equalsIgnoreCase("Test")){
            if(loadedTest == null){
                System.out.println("You must have a test loaded in order to display it.\n");
                return;
            }
            loadedTest.display();
        }
    }

    public static void displayWithResponse(){
        if(loadedTest == null || loadedAnswer == null){
            System.out.println("You need to load a test and correct answer to display!");
        } else {
            loadedTest.displayWithAnswer(loadedAnswer);
        }
    }

    public static void saveMenu(String type){
        if(type.equalsIgnoreCase("test")){
            if(loadedTest == null || loadedAnswer == null){
                System.out.println("You must have a test and correct answer loaded in order to save it.\n");
                return;
            }
            loadedTest.display();
            loadedTest.saveResponses(loadedAnswer);      //save the correct answer
            Test.serialize(loadedTest);                  //save the test

        } else if(type.equalsIgnoreCase("survey")){
            if(loadedSurvey == null){
                System.out.println("You must have a survey loaded in order to save it.\n");
                return;
            }
            loadedSurvey.display();
            Survey.serialize(loadedSurvey);           //save the survey
        }
    }

    public static void loadMenu(String type){
        String dirName;
          //use type to determine directory name where file types can be found
        if(type.equalsIgnoreCase("survey")){
            dirName = "Surveys";
        } else if(type.equalsIgnoreCase("test")){
            dirName = "Tests";
        } else {
            dirName = "Responses";
        }

        File directory = new File(dirName);
        File[] files = directory.listFiles();

        if(files == null || files.length == 0){
            System.out.println("You need to create a " + type + " to load!");
            return;
        }

        Out.getInstance().say("Please select a file to load:\n");
        for(int i =0; i < files.length; i++){
            System.out.println((i+1)+ ")" + files[i].getName() + "\n");
        }

        int toLoad = In.getInstance().readInt();

        while(toLoad > files.length || toLoad < 1){
            System.out.println("Enter a valid integer option");
            toLoad = In.getInstance().readInt();
        }

        //check that the correct answer or response file chosen is for the right test
        if(type.equalsIgnoreCase("correct answer") || type.equalsIgnoreCase("user response")) {
            while(toLoad > files.length || toLoad < 1 || !(files[(toLoad-1)].getName().startsWith(loadedTest.getName()))){
                System.out.println("Your selection is not a valid " + type +" for the chosen Test\nEnter a valid integer:");
                toLoad = In.getInstance().readInt();
            }
        }

        if(type.equalsIgnoreCase("Survey")){      //deserialize survey to loadedSurvey
            if(files[toLoad-1].isFile()){
                loadedSurvey = Survey.deserialize(files[(toLoad-1)].getPath());
            }
            else{
                Out.getInstance().say("Your selection is not a file. Try Again");
            }
        } else if(type.equalsIgnoreCase("test")){            // if type is a test, deserialize to loadedTest
            if(files[toLoad-1].isFile()){
                loadedTest = Test.deserialize(files[(toLoad-1)].getPath());
                System.out.println("Please load the correct answer for the corresponding test.");
                loadMenu("correct answer");
            }
            else{
                Out.getInstance().say("Your selection is not a file. Try Again");
            }
        } else if(type.equalsIgnoreCase("correct answer")){       //if type is a correct answer, deserialize to loadedAnswer
            if(files[toLoad-1].isFile()){
                loadedAnswer = Response.deserialize(files[(toLoad-1)].getPath());
            }
            else{
                Out.getInstance().say("Your selection is not a file. Try Again");
            }
        }else {                                                            //if type is a user response, deserialize to loadedResponse
            if(files[toLoad-1].isFile()){
                loadedResponse = Response.deserialize(files[(toLoad-1)].getPath());
            }
            else{
                Out.getInstance().say("Your selection is not a file. Try Again");
            }
        }
    }


    public static void modifyMenu(String type){
        if(type.equalsIgnoreCase("survey")){
            if(loadedSurvey == null){
                System.out.println("You must have a survey loaded in order to modify it.\n");
            } else {
                loadedSurvey.modify();
            }
        } else if(type.equalsIgnoreCase("test")){
            if(loadedTest == null){
                System.out.println("You must have a test loaded in order to modify it.\n");
            } else {
                loadedTest.modifyAdapter(loadedAnswer);
            }
        }
    }

    public static void takeMenu(String type){
        if(type.equalsIgnoreCase("test")){
            if(loadedTest == null){
                System.out.println("You must have a test loaded in order to take it.\n");
                return;
            }
            loadedTest.take();

        } else if(type.equalsIgnoreCase("survey")){
            if(loadedSurvey == null){
                System.out.println("You must have a survey loaded in order to take it.\n");
                return;
            }
            loadedSurvey.take();
        }
    }

    private static void tabulateMenu(String type) {
        if(type.equalsIgnoreCase("test")){
            if(loadedTest == null){
                System.out.println("You must have a test loaded in order to tabulate it.\n");
                return;
            }
            loadedTest.tabulate();
        } else if(type.equalsIgnoreCase("survey")){
            if(loadedSurvey == null){
                System.out.println("You must have a survey loaded in order to tabulate it.\n");
                return;
            }
            loadedSurvey.tabulate();
        }
    }

    public static void gradeTest(){
        if(loadedTest != null){
            System.out.println("Please load a response to be graded.");
            loadMenu("user response");}             //takes user to load menu for the response

        if(loadedTest == null || loadedAnswer == null || loadedResponse == null) {
            System.out.println("You must have a test, a response and a correct answer loaded in order to grade a response!\n");
            return;
        }

        loadedTest.grade(loadedAnswer,loadedResponse);
    }
}
//complete testing of everything including survey






