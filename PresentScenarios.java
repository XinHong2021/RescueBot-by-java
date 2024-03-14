import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.PrintWriter;
import java.io.FileNotFoundException;


/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */

public class PresentScenarios {

    private ArrayList<ArrayList<String>> dataframe;
    private Map<String, Map<String, Integer>> zipCounts = new HashMap<>();
    private Map<String, Integer> ageGroupCounts = new HashMap<>();
    private Map<String, Integer> genderCounts = new HashMap<>();
    private Map<String, Integer> bodyTypeCounts = new HashMap<>();
    private Map<String, Integer> professionCounts = new HashMap<>();
    private Map<String, Integer> pregnantCounts = new HashMap<>();
    private Map<String, Integer> animalCounts = new HashMap<>();
    private Map<String, Integer> speciesCounts = new HashMap<>();
    private Map<String, Integer> isPetsCounts = new HashMap<>();
    private Map<String, Integer>  age = new HashMap<>();
    private Map<String, Integer> legalOrTres = new HashMap<>();
    private String quit = "FALSE";



    public PresentScenarios(ArrayList<ArrayList<String>> dataframe) {
        this.dataframe = dataframe;
    }


    /**
     * Read the all scenarios if the user doesn't choose quit the program.
     * @param readFilePath       users' arguments after -s, a scenario file path
     * @param writeFilePath      users' arguments after -l, a log file path or a default file path "rescuebot.log".
     * @param totalCount         Total number of scenarios in the scenario file.
     * @param consent            (yes/no) Whether the user consent to have your decisions saved to a file.
     */
    public void readScenarios(String readFilePath, String writeFilePath,int totalCount,String consent) {
        int i = 0;
        int indexScenario = 0;
        int run = 0;
        ArrayList<Integer> tempArray = new ArrayList<>();
        tempArray.add(indexScenario);
        tempArray.add(run);
        ShowStatistic showStatistic = new ShowStatistic();

        while (i < dataframe.size() && getQuit() == "FALSE") {

            tempArray = showAScenario(dataframe, showStatistic,tempArray,writeFilePath,totalCount,readFilePath,consent);

        }

    }


    /**
     * Present all the locations in the scenario including characteristic information.
     * After presenting all locations, ask user to choose one location to save.
     * Create a multiple hash table to store statistic (percentage of what have been saved).
     * If the users haved judge 3 scenarios, present the statistic.
     * @param dataframe         a copy of scenarios file.
     * @param tempArray          an array list including the row of next scenario beginning and number of user's judge.
     * @param showStatistic     call the showStatistic object.
     * @param writeFilePath     users' arguments after -l, a log file path or a default file path "rescuebot.log".
     * @param totalCount        total number of scenarios in the scenario file.
     * @param readFilePath      users' arguments after -s, a scenario file path
     * @param consent           (yes/no) Whether the user consent to have your decisions saved to a file.
     * @return                  index of the next scenario title in dataframe.
     */
    public ArrayList<Integer> showAScenario(ArrayList<ArrayList<String>> dataframe, ShowStatistic showStatistic, ArrayList<Integer> tempArray, String writeFilePath, int totalCount, String readFilePath, String consent){

        zipCounts = new HashMap<>();
        ageGroupCounts = new HashMap<>();
        genderCounts = new HashMap<>();
        bodyTypeCounts = new HashMap<>();
        professionCounts = new HashMap<>();
        pregnantCounts = new HashMap<>();
        animalCounts = new HashMap<>();
        speciesCounts = new HashMap<>();
        isPetsCounts = new HashMap<>();
        age = new HashMap<>();
        legalOrTres = new HashMap<>();


        zipCounts.put("bodyTypeCounts",bodyTypeCounts);
        zipCounts.put("ageGroupCounts",ageGroupCounts);
        zipCounts.put("professionCounts",professionCounts);
        zipCounts.put("genderCounts",genderCounts);
        zipCounts.put("pregnantCounts",pregnantCounts);
        zipCounts.put("animalCounts",animalCounts);
        zipCounts.put("speciesCounts",speciesCounts);
        zipCounts.put("petsCounts",isPetsCounts);
        zipCounts.put("age",age);
        zipCounts.put("legalOrTres",legalOrTres);

        ArrayList<Integer> rowOfLocation = new ArrayList<Integer>();
        int i = tempArray.get(0);
        int run = tempArray.get(1) + 1;
        System.out.println("======================================");
        String[] scenarioInfo = dataframe.get(i).get(0).split(":");
        System.out.println("# Scenario: " + scenarioInfo[1]);
        System.out.println("======================================");
        int locationCounts = 0;
        int indexScenario = i +1;

        /**
         * tempArray stores the row of next location and accumulated location count in the scenario.
         */
        tempArray.set(0,indexScenario);

        ArrayList<Integer> tempLocation = new ArrayList<>();
        tempLocation = showALocation(indexScenario,rowOfLocation,locationCounts,dataframe,zipCounts);
        indexScenario = tempLocation.get(0);
        locationCounts = tempLocation.get(1);

        rowOfLocation.add(indexScenario);

        /**
         * After looping a location, accumulate the statistic information using multiple hash map.
         */
        Map<String, Map<String, Integer>> accumulateCount = showStatistic.getAccumulativeCount();

        accumulateCount = showStatistic.accumulate(accumulateCount,zipCounts);

        showStatistic.setAccumulativeCount(accumulateCount);


        System.out.println("To which location should RescueBot be deployed?");
        System.out.print("> ");
        String option = RescueBot.KEYBOARD.next();

        boolean validInPut = false;

        /**
         * If the user doesn't give an integer input, keep asking until get an integer which is smaller than number of locations.
         */
        while (!validInPut){

            try {
                int choice = Integer.parseInt(option);
                validInPut = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid response! To which location should RescueBot be deployed?");
                option = RescueBot.KEYBOARD.next();
            }
        }

        while (Integer.parseInt(option) > locationCounts){
            System.out.println("Invalid response! To which location should RescueBot be deployed?");
            option = RescueBot.KEYBOARD.next();
        }

        int choice = Integer.parseInt(option);

        /**
         * Store the statistic according to user's choice of location. The statistic will be accumulated separately with all locations' statistics.
         */
        Map<String, Map<String, Integer>> tempCount = deployALocation(dataframe,rowOfLocation,choice);

        Map<String, Map<String, Integer>> saveAccumulate = showStatistic.getSaveAccumulativeCount();

        tempCount = showStatistic.accumulate(saveAccumulate,tempCount);

        showStatistic.setSaveAccumulativeCount(tempCount);



        /**
         * If user has been judged 3 scenarios, print out the results.
         */
        if (run == 3 || run == 6 || run == 9 || run == 12 || run == totalCount){
            tempArray.set(1,run);
            tempArray.set(0,indexScenario);

            tempArray = saveAndRepeat(tempArray,showStatistic,writeFilePath,readFilePath,totalCount,consent);
        }else{
            tempArray.set(1,run);
            tempArray.set(0,indexScenario);
        }


        return tempArray;

    }


    /**
     * Present a location's information.
     * @param indexScenario        The row of next location begins.
     * @param rowOfLocation        Store the row of location in the array.
     * @param locationCounts       Number of locations which have been looped in the scenario.
     * @param dataframe            A copy of scenarios file.
     * @param zipCounts            A multiple hash map storing accumulated information from locations in the scenario.
     * @return                     An array list containing the row of next scenario line and number of locations currently
     */
    public ArrayList<Integer> showALocation(int indexScenario,ArrayList<Integer> rowOfLocation, int locationCounts,ArrayList<ArrayList<String>> dataframe,Map<String, Map<String, Integer>> zipCounts){

        while (indexScenario < dataframe.size()-1 && dataframe.get(indexScenario).get(0).substring(0,8).equals("location") ) {


            String[] locationInfo = dataframe.get(indexScenario).get(0).split("[:,;]");

            String position = "Location: " + locationInfo[1] + ", " + locationInfo[2];
            String trespassing = locationInfo[3];

            Location location = new Location(position, trespassing);

            int index = indexScenario + 1;

            rowOfLocation.add(indexScenario);

            index = loopALocation(dataframe, index, location,trespassing);


            // after loop
            locationCounts += 1;


            String locationDescription = location.displayDescription();
            indexScenario = index;
            System.out.print("[" + locationCounts + "] " + locationDescription);


            zipCounts = location.locationCount(zipCounts);

        }

        ArrayList<Integer> tempLocation = new ArrayList<>();
        tempLocation.add(indexScenario);
        tempLocation.add(locationCounts);
        return tempLocation;
    }


    /**
     * Present the current statistic to users. User can choose continue after seeing the statistic.
     * The statistic
     * @param tempArray            An array list contains the row of next scenario line and the number of time users judge.
     * @param showStatistic        call the showStatistic object.
     * @param writeFilePath        users' arguments after -l, a log file path or a default file path "rescuebot.log".
     * @param readFilePath         users' arguments after -s, a scenario file path
     * @param totalCount           total number of scenarios in the scenario file.
     * @param consent              (yes/no) Whether the user consent to have your decisions saved to a file.
     * @return                     an arraylist containing the row of next scenario and number of runs currently.
     */
    public ArrayList<Integer> saveAndRepeat(ArrayList<Integer> tempArray, ShowStatistic showStatistic, String writeFilePath,String readFilePath, int totalCount, String consent){
        int run = tempArray.get(1);
        int indexScenario = tempArray.get(0);
        System.out.println( "======================================\n" +
                "# Statistic\n" +
                "======================================\n" + "- % SAVED AFTER " + run + " RUNS");

        String information = showStatistic.showSaveRate();
        System.out.println(information);

        try {
            if (consent.equals("yes")){
                saveToLogFile(writeFilePath,information);
            }
        } catch (FileNotFoundException e){
            System.out.println("ERROR: could not print results. Target directory does not exist.");
        }

        System.out.println("Would you like to continue? (yes/no)");
        System.out.print("> ");

        String option = RescueBot.KEYBOARD.next();
        while (!(option.equals("yes")|| option.equals("no"))){
            System.out.println("Would you like to continue? (yes/no)");
            System.out.print("> ");
            option = RescueBot.KEYBOARD.next();
        }
        if (option.equals("yes") && tempArray.get(1) < totalCount){
            tempArray.set(0,indexScenario);
            tempArray.set(1,run);
            tempArray = showAScenario(dataframe,showStatistic,tempArray,writeFilePath,totalCount,readFilePath,consent);

        } else {
            System.out.println("That's all. Press Enter to return to main menu.");
            System.out.print("> ");
            choiceMenu(showStatistic,totalCount,dataframe,readFilePath,writeFilePath);
        }

        return tempArray;
    }


    /**
     * Save the statistic into log file. It is used when users consent to save the file or user want to audit from history.
     * @param writeFilePath             users' arguments after -l, a log file path or a default file path "rescuebot.log".
     * @param information               Statistic information.
     * @throws FileNotFoundException    If the log path cannot found, then it will throw errors.
     */
    public static void saveToLogFile(String writeFilePath, String information) throws FileNotFoundException{
        PrintWriter writer = new PrintWriter(writeFilePath);
        writer.println(information);
        writer.close();
    }


    /**
     * If users want to get audit history, the file path is examined.
     * @param showStatistic         call the showStatistic object.
     * @param writeFilePath         users' arguments after -l, a log file path or a default file path "rescuebot.log".
     */
    public static void audit(ShowStatistic showStatistic,String writeFilePath){
        if (writeFilePath == null){
            try {
                checkLogFile();
            }catch (IOException e){
                System.out.println("An error occurred in log file");
            }
        }
        try {
            String printWords = "======================================\n" +
                    "# User Audit\n" +
                    "======================================\n" + showStatistic.showSaveRate();
            saveToLogFile(writeFilePath,printWords);
        }catch (IOException e){
            System.out.println("An error occurred in log file");
        }

    }

    /**
     * Check whether the default log file "rescuebot.log" exist.
     * @throws IOException      Print out warning if the file is not exist.
     */
    private static void checkLogFile() throws IOException{
        File logFile = new File("rescuebot.log");
        if (!logFile.exists() || logFile.length() == 0){
            throw new IOException("No history found. Press enter to return to main menu.\n> ");
        }

    }

    /**
     * Add human and animals in the location with their specialized information.
     * @param dataframe          A copy of scenarios file.
     * @param index              The row of the characteristic in dataframe.
     * @param locationNow        An object of the location.
     * @param trespassing        The trespasing type of the location.
     * @return                   The row of next characteristic in dataframe.
     */
    public int loopALocation(ArrayList<ArrayList<String>> dataframe, int index, Location locationNow,String trespassing){

        while ((dataframe.get(index).get(0).equals("human") || dataframe.get(index).get(0).equals("animal")) && index < dataframe.size()-1){

            if (dataframe.get(index).get(0).equals("animal")) {

                locationNow.add(new Animal(Integer.parseInt(dataframe.get(index).get(2)), dataframe.get(index).get(1), dataframe.get(index).get(3),
                        trespassing, dataframe.get(index).get(6), dataframe.get(index).get(7)));

            }
            else if (dataframe.get(index).get(0).equals("human") && Integer.parseInt(dataframe.get(index).get(2)) >= 17 && Integer.parseInt(dataframe.get(index).get(2)) <= 68) {

                locationNow.add(new Adult(Integer.parseInt(dataframe.get(index).get(2)), dataframe.get(index).get(1), dataframe.get(index).get(3),trespassing, dataframe.get(index).get(4), dataframe.get(index).get(5)));

            }
            else if (dataframe.get(index).get(0).equals("human")) {

                locationNow.add(new NonAdult(Integer.parseInt(dataframe.get(index).get(2)), dataframe.get(index).get(1), dataframe.get(index).get(3),trespassing));

            }
            index += 1;
        }
        return index;
    }


    /**
     * Functionally record location and its information which user judged.
     * @param dataframe        A copy of scenarios file.
     * @param rowOfLocation   The row of the location line in dataframe.
     * @param option          User's option of choosing the location to save.
     * @return                A hash map in these survival lives.
     */
    public Map<String, Map<String, Integer>> deployALocation(ArrayList<ArrayList<String>> dataframe,ArrayList<Integer>  rowOfLocation, int option){
        int start = rowOfLocation.get(option-1) + 1;

        Map<String, Map<String, Integer>> tempCounts = new HashMap<>();
        Map<String, Integer> ageGroupCounts = new HashMap<>();
        Map<String, Integer> genderCounts = new HashMap<>();
        Map<String, Integer> bodyTypeCounts = new HashMap<>();
        Map<String, Integer> professionCounts = new HashMap<>();
        Map<String, Integer> pregnantCounts = new HashMap<>();
        Map<String, Integer> animalCounts = new HashMap<>();
        Map<String, Integer> speciesCounts = new HashMap<>();
        Map<String, Integer> isPetsCounts = new HashMap<>();
        Map<String, Integer> age = new HashMap<>();
        Map<String, Integer> legalOrTres = new HashMap<>();

        tempCounts.put("bodyTypeCounts",bodyTypeCounts);
        tempCounts.put("ageGroupCounts",ageGroupCounts);
        tempCounts.put("professionCounts",professionCounts);
        tempCounts.put("genderCounts",genderCounts);
        tempCounts.put("pregnantCounts",pregnantCounts);
        tempCounts.put("animalCounts",animalCounts);
        tempCounts.put("speciesCounts",speciesCounts);
        tempCounts.put("petsCounts",isPetsCounts);
        tempCounts.put("age",age);
        tempCounts.put("legalOrTres",legalOrTres);

        String[] locationInfo = dataframe.get(rowOfLocation.get(option-1)).get(0).split("[:,;]");

        String position = "Location: " + locationInfo[1] + ", " + locationInfo[2];
        String trespassing = locationInfo[3];

        Location tempLocation = new Location(position,trespassing);

        int end = loopALocation(dataframe,start,tempLocation,trespassing);

        tempLocation.locationCount(tempCounts);

        return tempCounts;

    }


    /**
     * The main menu used when users want to enter other choices.
     * @param showStatistic     call the showStatistic object.
     * @param totalCount        total number of scenarios in the scenario file.
     * @param dataframe         A copy of scenarios file.
     * @param readFilePath      users' arguments after -s, a scenario file path
     * @param writeFilePath     users' arguments after -l, a log file path or a default file path "rescuebot.log".
     */
    public void choiceMenu(ShowStatistic showStatistic, int totalCount,ArrayList<ArrayList<String>> dataframe, String readFilePath, String writeFilePath){

        String scenarios = "Please enter one of the following commands to continue:\n" +
                "- judge scenarios: [judge] or [j]\n" +
                "- run simulations with the in-built decision algorithm: [run] or [r]\n" +
                "- show audit from history: [audit] or [a]\n" +
                "- quit the program: [quit] or [q]";
        System.out.println(scenarios);
        System.out.print("> ");
        String option = RescueBot.KEYBOARD.next();

        while (!(option.equals("judge")||option.equals("j")||option.equals("run")||option.equals("r")||
                option.equals("audit")||option.equals("a")||option.equals("quit")||option.equals("q"))){
            System.out.println("Invalid command! Please enter one of the following commands to continue:\n"
                    + scenarios);
            option = RescueBot.KEYBOARD.next();
        }


        if (option.equals("judge")||option.equals("j")){
            String consent = RescueBot.whetherSaveToLog();
            readScenarios(readFilePath,writeFilePath,totalCount,consent);
        }else if (option.equals("run")||option.equals("r")){
            setQuit("TRUE");
        }else if (option.equals("audit") || option.equals("a")){
            setQuit("TRUE");
            audit(showStatistic,writeFilePath);
        } else if (option.equals("quit") || option.equals("q")) {
            setQuit("TRUE");

        }

    }

    /**
     * getter whether to quit the menu
     * @return  quit
     */
    public String getQuit() {
        return quit;
    }

    /**
     * setter whether to quit the menu
     * @param quit   true or false
     */
    public void setQuit(String quit) {
        this.quit = quit;
    }

}
