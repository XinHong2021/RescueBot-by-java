
import java.io.*;
import java.util.*;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */


public class RescueBot {

    public static final Scanner KEYBOARD = new Scanner(System.in);
    public static ArrayList<ArrayList<String>> dataframe;

    /**
     * Decides whether to save the passengers or the pedestrians
     * @param Scenario scenario: the ethical dilemma
     * @return Decision: which group to save
     */

    /*
    public static Location decide(Scenario scenario) {
        // a very simple decision engine
        // TODO: take into account at least 5 characteristics

        // 50/50
        if(Math.random() > 0.5) {
            return scenario.getLocation(1);
        } else {
            return scenario.getLocation(2);
        }
    }
     */


    /**
     * Program entry
     */

    public static void main(String[] args) {

        String readFilePath = null;
        String writeFilePath = null;
        String help = "RescueBot - COMP90041 - Final Project\n" +
                "\n" +
                "Usage: java RescueBot [arguments]\n" +
                "\n" +
                "Arguments:\n" +
                "-s or --scenarios\tOptional: path to scenario file\n" +
                "-h or --help\t\tOptional: Print Help (this message) and exit\n" +
                "-l or --log\t\tOptional: path to data log file";

        // handle with arguments
        if (args.length == 0){
            readWelcome();
            simulateOrRead(readFilePath,writeFilePath);
        } else {
            int i = 0;
            while (i < args.length){

                if ((args[i].equals("-s") || args.equals("--scenarios"))  && i < args.length-1 && (!(args[i+1].equals("-l")) || args[i+1].equals("--log"))){
                    readFilePath = args[i+1];
                    i += 2;
                }
                else if ((args[i].equals("-s")||args[i].equals("--scenarios")) && (i == args.length-1 || args[i+1].equals("-l") || args[i+1].equals("--log"))){
                    System.out.println(help);
                    break;
                }
                else if (args[i].equals("-l") || args[i].equals("--log") && i < args.length-1 && (!(args[i+1].equals("-s")) || args[i+1].equals("--scenarios"))){
                    writeFilePath = args[i+1];
                    i += 2;
                }else if (args[i].equals("-l") || args[i].equals("--log") && (i == args.length-1 || args[i+1].equals("-s") || args[i+1].equals("-scenarios"))){
                    System.out.println(help);
                    break;
                } else {
                    System.out.println(help);
                    break;
                }
            }
            if (i == args.length) {
                if (readFilePath != null){
                    try {

                        BufferedReader inputStream = new BufferedReader(new FileReader(readFilePath));
                        readWelcome();
                        simulateOrRead(readFilePath, writeFilePath);

                    } catch (FileNotFoundException e) {
                        System.out.println("java.io.FileNotFoundException: could not find scenarios file.");
                        System.out.println(help);
                    }
                }
            }
        }
    }

    /**
     * The method is used fill scenario file path and log file path and allocates to simulation and reading scenario file respectively.
     * If user doesn't give a scenario file path, program leads users to simulate scenarios.
     * If user doesn't give a log path, program will set a default path 'rescuebot.log' for further usages.
     * After ensuring user give a scenario file path, the program will check whether the scenario file is valid.
     * @param readFilepath         users' arguments after -s, a scenario file path
     * @param writeFilePath        users' arguments after -l, a log file path
     */
    public static void simulateOrRead(String readFilepath, String writeFilePath){
        if (readFilepath == null){
            if (writeFilePath == null){
                writeFilePath = "rescuebot.log";
            }
            int totalCount = 0;
            ArrayList<ArrayList<String>> dataframe = null;
            mainMenu(totalCount,dataframe, readFilepath,writeFilePath);
        } else if (writeFilePath == null){
            writeFilePath = "rescuebot.log";
            tryValidScenarios(readFilepath,writeFilePath);
        } else {
            tryValidScenarios(readFilepath,writeFilePath);
        }

    }


    /**
     * Welcome words appears after user's arguments are valid. The program is officially begin.
     */

    public static void readWelcome() {
        try {
            BufferedReader welcome = new BufferedReader(new FileReader("welcome.ascii"));
            String line;
            while ((line = welcome.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Something wrong with the welcome file");
        }
    }


    /**
     * Test whether the scenario file is valid. After handling all the invalid data rows, it will get the total number
     * of scenarios,a two-dimensional dataframe of the scenario file. Additionally, it will print the number of scenarios imported.
     * @param readFilePath      users' arguments after -s, a scenario file path
     * @param writeFilePath     users' arguments after -l, a log file path or a default file path "rescuebot.log".
     */
    public static void tryValidScenarios(String readFilePath, String writeFilePath) {
        try {

            int totalCount;
            totalCount = scenarioCount(readFilePath);


            dataframe = scenarioValid(readFilePath, writeFilePath);

            if (totalCount > 0) {
                System.out.println(totalCount + " scenarios imported.");
            }
            mainMenu(totalCount,dataframe,readFilePath,writeFilePath);
        } catch (IOException e) {
            System.out.println("An error occurred when trying to read the scenario file");
        }
    }


    /**
     * ScenarioCount method will return the number of scenarios by reading scenario file line by line.
     * If the line begins with "scenario" count 1.
     * @param readFilePath     users' arguments after -s, a scenario file path
     * @return                 Total number of scenarios in the file.
     * @throws IOException     print out warning if there is an error when reading the scenario file. But normally, it
     * will not be triggered because the validation of file path has been tested before in the main method.
     */
    public static int scenarioCount(String readFilePath) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(readFilePath));
        int totalCount = 0;

        String line;
        while ((line = inputStream.readLine()) != null) {

            if (line.substring(0,8).equals("scenario")){
                totalCount += 1;
            }

        }

        return totalCount;
    }


    /**
     * ScenarioValid method is used to handle invalid data rows. Three types of invalid formats can occur, which are
     * incorrect number of columns, incorrect data format if characters appear in age column, invalid characteristic if
     * the value is not in default options.
     * @param readFilePath          users' arguments after -s, a scenario file path
     * @param writeFilePath         users' arguments after -l, a log file path or a default file path "rescuebot.log".
     * @return                      a dataframe copied from the scenario file.
     * @throws IOException          Other unknown types errors.
     */
    public static ArrayList<ArrayList<String>> scenarioValid(String readFilePath, String writeFilePath) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(readFilePath));
        String line;
        int row = 0; // index of the line
        ArrayList<ArrayList<String>> dataframe = new ArrayList<ArrayList<String>>();

        while ((line = inputStream.readLine()) != null) {

            row += 1;

            if (row == 1) {
                continue;
            }

            String[] lineString = line.split(",",-1);
            ArrayList<String> list = new ArrayList<>(Arrays.asList(lineString));
            String listA = list.get(0).replace("\"", "");
            list.set(0, listA);
            String listB = list.get(list.size() - 1).replace("\"", "");
            list.set(list.size() - 1, listB);
            dataframe.add(list);

            try {
                // Invalid data format
                if (list.size() != 8) {
                    System.out.println("WARNING: invalid data format in scenarios file in line " + row);
                }


                if (list.get(0).equals("human") || list.get(0).equals("animal")) {

                    // Invalid number format
                    int age = Integer.parseInt(list.get(2));
                    invalidCharacterType(list,row);

                }
            } catch (NumberFormatException e) {
                System.out.println("WARNING: invalid number format in scenarios file in line " + row);
            }
        }

        return dataframe;
    }

    /**
     * The program asks users to enter a command from main menu.
     * The default options are judge scenarios, run simulation, show audit.
     * @param totalCount        Total number of scenarios in the scenario file.
     * @param dataframe         A copy of scenarios file.
     * @param readFilePath      users' arguments after -s, a scenario file path
     * @param writeFilePath     users' arguments after -l, a log file path or a default file path "rescuebot.log".
     */

    public static void mainMenu( int totalCount,ArrayList<ArrayList<String>> dataframe, String readFilePath, String writeFilePath) {
        PresentScenarios presentScenarios = new PresentScenarios(dataframe);

        String scenarios = "Please enter one of the following commands to continue:\n" +
                "- judge scenarios: [judge] or [j]\n" +
                "- run simulations with the in-built decision algorithm: [run] or [r]\n" +
                "- show audit from history: [audit] or [a]\n" +
                "- quit the program: [quit] or [q]";
        System.out.println(scenarios);
        System.out.print("> ");
        String option = KEYBOARD.next();

        while (!(option.equals("judge")||option.equals("j")||option.equals("run")||option.equals("r")||
                option.equals("audit")||option.equals("a")||option.equals("quit")||option.equals("q"))){
            System.out.println("Invalid command! Please enter one of the following commands to continue:\n"
                    + scenarios);
            option = KEYBOARD.next();
        }


        if (option.equals("judge")||option.equals("j")){
            String consent = whetherSaveToLog();
            presentScenarios.readScenarios(readFilePath,writeFilePath,totalCount,consent);
        }else if (option.equals("run")||option.equals("r")){

        }else if (option.equals("audit") || option.equals("a")){
        } else if (option.equals("quit") || option.equals("q")) {
        }
    }


    /**
     * If user choose to judge scenarios, a consent request of save the following decisions in to given log file path is asked.
     * @return      The user's choice: yes / no
     */
    public static String whetherSaveToLog(){
        System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
        System.out.print("> ");
        String consent = RescueBot.KEYBOARD.next();
        while (!(consent.toUpperCase().equals("YES") || consent.toUpperCase().equals("NO"))){
            System.out.println("Invalid response! Do you consent to have your decisions saved to a file? (yes/no)");
            System.out.print("> ");
            consent = RescueBot.KEYBOARD.next();
        }
        return consent;
    }


    /**
     * Handling all possible invalid characters in rows. If a characteristic of human or animals is not in default choice,
     * a warning message will print out.
     * @param list      A line of the scenario file.
     * @param row       The row number of the line.
     */
    public static void invalidCharacterType(ArrayList<String> list, int row){

        // Invalid field values

        if (Integer.parseInt(list.get(2)) < 0) {
            System.out.println("WARNING: invalid characteristic in scenarios file in line " + row);
        }

        if (!(list.get(1).equals("female") || list.get(1).equals("male") || list.get(1).toUpperCase().equals("UNKNOWN"))) {
            System.out.println("WARNING: invalid characteristic in scenarios file in line " + row);
        }

        if (!(list.get(3).equals("overweight") || list.get(3).equals("athletic") || list.get(3).equals("average") || list.get(3).toUpperCase().equals("UNSPECIFIED"))) {
            System.out.println("WARNING: invalid characteristic in scenarios file in line " + row);
        }

        if (!(list.get(4).equals("none") || list.get(4).equals("professor") || list.get(4).equals("unemployed") || list.get(4).equals("doctor") || list.get(4).toUpperCase().equals("")
                || list.get(4).equals("criminal") || list.get(4).equals("ceo") || list.get(4).equals("homeless") || list.get(4).equals("student"))) {
            System.out.println("WARNING: invalid characteristic in scenarios file in line " + row);
        }


        if (!(list.get(5).toUpperCase().equals("TRUE") || (list.get(5).toUpperCase().equals("FALSE")) ||  (list.get(5).toUpperCase().equals("")))){
            System.out.println("WARNING: invalid characteristic in scenarios file in line " + row);
        }

        if (!((list.get(7).toUpperCase().equals("FALSE") || list.get(7).toUpperCase().equals("TRUE"))||list.get(7).toUpperCase().equals(""))) {
            System.out.println("WARNING: invalid characteristic in scenarios file in line " + row);
        }

    }


}

