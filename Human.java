import java.util.Map;


/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */

public abstract class Human extends Life implements AgeGroup, CountStatistic{

    /**
     * A constructor with age, gender, bodytype, and trespassing.
     * @param age           the age of the human/animal.
     * @param gender        the gender of the human/animal.
     * @param bodyType      the bodytype of the human/animal.
     * @param trespassing   the trespassing of the human/animal.
     */
    public Human(int age, String gender, String bodyType,String trespassing) {
        super(age, gender, bodyType,trespassing);
    }


    /**
     * A description of human.
     * @return    the description in string type.
     */
    @Override
    public String describe() {
        return null;
    }

    /**
     * Accumulate the characteristic of the human to the current location.
     * @param zipCounts     A statistic information for a location.
     * @return               An updated version of statistic information for the location.
     */
    @Override
    public Map<String, Map<String, Integer>> counts(Map<String, Map<String, Integer>> zipCounts) {
        return null;
    }

}