import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */


public class Simulation {

    private int numScenarios = 3;
    private static final int MINAGE = 0;
    private static final int MAXAGE = 80;
    private static final ArrayList<String> GENDER = new ArrayList<>(Arrays.asList("female", "male"));
    private static final ArrayList<String>  BODYTYPE = new ArrayList<>(Arrays.asList("overweight","athletic","average"));
    private static final ArrayList<String>  PROFESSION =  new ArrayList<>(Arrays.asList("chef","student","unemployed",
            "professor","doctor","lawyer","criminal","ceo","homeless"));
    private static final ArrayList<String> PREGNANT =  new ArrayList<>(Arrays.asList("true","flase"));
    private static final ArrayList<String>  SPECIES = new ArrayList<>(Arrays.asList("cat","dog","koala","wallaby","rabbit",
            "parrot","cockatoo","ferret","snake"));
    private static final ArrayList<String> ISPET = new ArrayList<>(Arrays.asList("true","false"));
    private static final int MINILOCATION = 2;
    private static final ArrayList<String>  LATITUDE = new ArrayList<>(Arrays.asList("W","E"));
    private static final ArrayList<String>  LONGTITUDE = new ArrayList<>(Arrays.asList("N","S"));
    private static final ArrayList<String> LEGALORTRES = new ArrayList<>(Arrays.asList("trespassing","legal"));
    private static final ArrayList<String> LIVES = new ArrayList<>(Arrays.asList("human","animal"));
    Random random = new Random();
    private static final ArrayList<String> SCENARIOS = new ArrayList<>(Arrays.asList("bushfire","flood","cyclone","earthquake"));


    /**
     * Generate scenarios.
     * @return  Three scenarios in dataformat.
     */
    public static ArrayList<ArrayList<String>> generateThreeScenarios(){
        ArrayList<ArrayList<String>> locations = new ArrayList<>();
        for (int i = 0; i <3;i++){
            String nameOfScenario = "scenario:" + generateRandomScenarioName();
            ArrayList<String> scenario = new ArrayList<>();
            scenario.add(nameOfScenario);
            for (int j = 0; j < 7; j++){
                scenario.add("");
            }
            locations.add(scenario);
            locations.addAll(generateLocations());
        }
        return locations;
    }

    /**
     * generate a random number with lower bound and upper bound.
     * @param min  lower bound.
     * @param max  upper bound.
     * @return    a random number
     */
    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * generate a group of locations including human and animals
     * @return   a location including human and animals in dataformat
     */
    public static ArrayList<ArrayList<String>> generateLocations() {
        ArrayList<ArrayList<String>> location = new ArrayList<>();
        String info = generateRandomLocation() + generateRandomLegalOrTres();
        ArrayList<String> position = new ArrayList<>(Arrays.asList(info));
        for (int j = 0; j < 7; j ++){
            position.add("");
        }
        location.add(position);
        int numLives = generateRandomNumber(1,5);
        while (numLives < 2){
            numLives = generateRandomNumber(1,5);
        }
        for (int i = 0; i < numLives; i++) {
            location.add(generateLife());
        }
        return location;
    }


    /**
     * Generate a random location name and position
     * @return  a location including longtitude and latitude,
     */
    public static String generateRandomLocation() {
        double minLatitude = -90.0;
        double maxLatitude = 90.0;
        double latitude = generateRandomNumber((int) minLatitude, (int) maxLatitude - 1) + Math.random();

        double minLongitude = -180.0;
        double maxLongitude = 180.0;
        double longitude = generateRandomNumber((int) minLongitude, (int) maxLongitude - 1) + Math.random();
        String location = "location: " + String.format("%.4f", latitude) + " " + generateRandomLongtitude() + " " + String.format("%.4f", longitude) + " " + generateRandomLatitude();
        return location;
    }

    /**
     * Generate a person or animal
     * @return  a person or animal with characteristic.
     */
    public static ArrayList<String> generateLife(){
        ArrayList<String> life = new ArrayList<>();
        life.add(generateRandomLives());
        life.add(generateRandomGender());
        life.add(Integer.toString(generateRandomNumber(MINAGE,MAXAGE)));
        life.add(generateRandomBodyType());

        // if adult, add profession.
        if (life.get(0).equals("human") && Integer.parseInt(life.get(2)) >= 17 && Integer.parseInt(life.get(2)) <= 68){
            life.add(generateRandomProfession());
            // if adult is woman, add pregenancy,
            if (life.get(1).equals("woman")){
                life.add(generateRandomPregnancyStatus());
                // add species as empty
                life.add("");
                // add species as empty
                life.add("");
            } else {
                // if adult is man, add pregnacy as empty
                life.add("");
                life.add("");
                life.add("");
            }
        } else if (life.get(0).equals("animal")) {
            life.add("");
            life.add("");
            life.add(generateRandomSpecies());
            life.add(generateRandomIsPetStatus());
        } else {
            // If not adult and not animal, add profession, pregnant, species, ispet all null.
            life.add("");
            life.add("");
            life.add("");
            life.add("");
        }
        return life;
    }


    /**
     * generate random number of lives.
     * @return  the number of lives.
     */
    public static String generateRandomLives() {
        Random random = new Random();
        return LIVES.get(random.nextInt(LIVES.size()));
    }


    /**
     * generate a gender
     * @return a gender
     */
    public static String generateRandomGender() {
        Random random = new Random();
        int index = random.nextInt(GENDER.size());
        return GENDER.get(index);
    }

    /**
     * generate a profession type
     * @return a profession
     */
    public static String generateRandomProfession() {
        Random random = new Random();
        int index = random.nextInt(PROFESSION.size());
        return PROFESSION.get(index);
    }

    /**
     * generate a body type
     * @return  a body type
     */
    public static String generateRandomBodyType() {
        Random random = new Random();
        int index = random.nextInt(BODYTYPE.size());
        return BODYTYPE.get(index);
    }

    /**
     * generate a pregnant type
     * @return pregnant true or false
     */
    public static String generateRandomPregnancyStatus(){
        Random random = new Random();
        int index = random.nextInt(PREGNANT.size());
        return PREGNANT.get(index);
    }

    /**
     * generate a specie
     * @return  a specie
     */
    public static String generateRandomSpecies() {
        Random random = new Random();
        int index = random.nextInt(SPECIES.size());
        return SPECIES.get(index);
    }

    /**
     * generate a pet
     * @return  a pet
     */
    public static String generateRandomIsPetStatus(){
        Random random = new Random();
        int index = random.nextInt(ISPET.size());
        return ISPET.get(index);
    }

    /**
     * generate legal or tresspasing
     * @return  legal or trespassing
     */
    public static String generateRandomLegalOrTres(){
        Random random = new Random();
        int index = random.nextInt(LEGALORTRES.size());
        return LEGALORTRES.get(index);
    }

    /**
     * generate a longtitude.
     * @return   a random longtitude
     */
    public static String generateRandomLongtitude(){
        Random random = new Random();
        int index = random.nextInt(LONGTITUDE.size());
        return LONGTITUDE.get(index);
    }

    /**
     * generate a latitude
     * @return  a random latitude
     */
    public static String generateRandomLatitude(){
        Random random = new Random();
        int index = random.nextInt(LATITUDE.size());
        return LATITUDE.get(index);
    }

    /**
     * generate a random scenario name
     * @return  a scenario name
     */
    public static String generateRandomScenarioName(){
        Random random = new Random();
        int index = random.nextInt(SCENARIOS.size());
        return SCENARIOS.get(index);
    }


}