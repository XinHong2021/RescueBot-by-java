import java.util.Map;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */

public interface CountStatistic {

    /**
     * A method of calculating the counts in a value for all types of characteristic.
     * @param zipCount   A multi-hash map storing the counts for all types of characteristic.
     * @return           An updated multi-hash map storing the counts for all types of characteristic.
     */
    public abstract Map<String, Map<String, Integer>> counts(Map<String, Map<String, Integer>> zipCount);

}