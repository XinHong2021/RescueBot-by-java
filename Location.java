import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */

public class Location{
    private ArrayList<Life> lives = new ArrayList<Life>();
    private ArrayList<Life> humans = new ArrayList<Life>();

    private String position;
    private String trespassing;


    /**
     * A constructor of the location position and it is trespassing or legal.
     * @param position        position of the location.
     * @param trespassing     trespassing or legal.
     */
    public Location(String position,String trespassing) {
        this.position = position;
        this.trespassing = trespassing;
    }

    /**
     * A constructor of location including lives.
     * @param life      human/animal including it characteristic in the location
     */
    public void add(Life life) {
        lives.add(life);
    }



    /**
     * A description of adult.
     * @return    the description in string type.
     */
    public String displayDescription() {
        String locationDescription = null;
        if (trespassing.equals("trespassing")){
            locationDescription = position + "\n" + "Trespassing: yes\n" + lives.size() + " Characters: \n";
        } else {
            locationDescription = position + "\n" + "Trespassing: no\n" + lives.size() + " Characters: \n";
        }

        for (Life life : lives) {
            locationDescription = locationDescription + life.describe() + "\n";
        }

        return locationDescription;
    }

    /**
     * Accumulate the characteristic of all the lives to the current location.
     * @param zipCounts     A statistic information for a location.
     * @return               An updated version of statistic information for the location.
     */
    public Map<String, Map<String, Integer>> locationCount(Map<String, Map<String, Integer>> zipCounts){

        for (Life life : lives){
            zipCounts = life.counts(zipCounts);
        }

        return zipCounts;

    }
}