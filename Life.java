import java.util.HashMap;
import java.util.Map;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */

public abstract class Life implements CountStatistic{

    private int age;
    private String gender;
    private String bodyType;
    private String trespassing;

    /**
     * A constructor with age, gender, bodytype, and trespassing.
     * @param age           the age of the human/animal.
     * @param gender        the gender of the human/animal.
     * @param bodyType      the bodytype of the human/animal.
     * @param trespassing   the trespassing of the human/animal.
     */
    public Life(int age, String gender, String bodyType, String trespassing) {
        super();
        this.age = age;
        this.gender = gender;
        this.bodyType = bodyType;
        this.trespassing = trespassing;
    }

    /**
     * an empty constructor
     */
    public Life() {

    }



    /**
     * A description of adult.
     * @return    the description in string type.
     */
    public abstract String describe();

    /**
     * getter age
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * getter gender
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     * getter body type
     * @return  body type
     */
    public String getBodyType() {
        return bodyType;
    }

    /**
     * getter trespassing
     * @return trespassing
     */
    public String getTrespassing() {
        return trespassing;
    }


    /**
     * Accumulate the characteristic of the life to the current location.
     * @param zipCount     A statistic information for a location.
     * @return               An updated version of statistic information for the location.
     */
    @Override
    public Map<String, Map<String, Integer>>  counts(Map<String, Map<String, Integer>> zipCount) {
        return null;
    }
}
