import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */

public class ShowStatistic {

    private Map<String, Map<String, Integer>> testCount = new HashMap<>();
    private Map<String, Map<String, Integer>> scenarioCount = new HashMap<>();
    private Map<String, Map<String, Integer>> scenarioTemp = new HashMap<>();
    private Map<String, Map<String, Integer>> accumulativeCount = new HashMap<>();
    private Map<String, Map<String, Integer>> saveAccumulativeCount = new HashMap<>();


    /**
     * Accumulate and store the number of every characteristic in a multi-hash map.
     * Each type of characteristic is stored in a hash map. All of them combines a multi-hash map.
     * @param targetMap         An accumulated characteristic information.
     * @param scenarioCount     The characteristic information that are going to add in.
     * @return                  The updated version of accumulated characteristic information.
     */
    public Map<String, Map<String, Integer>> accumulate(Map<String, Map<String, Integer>> targetMap,Map<String, Map<String, Integer>> scenarioCount) {

        // Accumulate the values from the source map to the target map
        for (Map.Entry<String, Map<String, Integer>> sourceEntry : scenarioCount.entrySet()) {
            String outerKey = sourceEntry.getKey();
            Map<String, Integer> innerMap = sourceEntry.getValue();

            if (!targetMap.containsKey(outerKey)) {
                targetMap.put(outerKey, new HashMap<>());
            }

            Map<String, Integer> targetInnerMap = targetMap.get(outerKey);
            for (Map.Entry<String, Integer> innerEntry : innerMap.entrySet()) {
                String innerKey = innerEntry.getKey();
                int value = innerEntry.getValue();

                targetInnerMap.put(innerKey, targetInnerMap.getOrDefault(innerKey, 0) + value);
            }
        }
        return targetMap;
    }


    /**
     * Show the ratio of statistic via using the accumulated saved statistic divided by accumulated total statistic.
     * @return      A string contains all the ratio of characteristic in descending order and the average age of saved lives.
     */
    public String showSaveRate(){

        List<CalculateRatio> ratios = new ArrayList<>();

        for (String outerKey: getAccumulativeCount().keySet()){

            Map<String, Integer> type = getAccumulativeCount().get(outerKey);

            if (type!= null && (!outerKey.equals("age"))){
                for (String innerKey: type.keySet()){
                    Integer saveValue = 0;
                    if (getSaveAccumulativeCount().get(outerKey).containsKey(innerKey)){
                        saveValue = getSaveAccumulativeCount().get(outerKey).get(innerKey);
                    }
                    Integer value = getAccumulativeCount().get(outerKey).get(innerKey);
                    if (innerKey != null){
                        double doubleValue = value;
                        double doubleSaveValue = saveValue;
                        double ratio = doubleSaveValue/doubleValue;
                        ratios.add(new CalculateRatio(ratio,innerKey));
                    }

                }
            }
        }


        Collections.sort(ratios, Comparator.comparingDouble(CalculateRatio::getValue).reversed()
                .thenComparing(CalculateRatio::getName));
        String information = "";
        for (CalculateRatio ratio : ratios){
            if (ratio.getName() != null){
                double value = Math.ceil(ratio.getValue()*100.00)/100.00;
                information = information + ratio.getName() + ": " + df.format(value)  + "\n";
            }

        }

        information = information + "--\naverage age: " + calculateAverageAge();

        return information;
    }


    /**
     * Calculate the average age.
     * @return     the average age with the correct format.
     */
    public String calculateAverageAge(){
        String averageAge = null;
        double sum = 0;
        double count = 0;
        scenarioTemp = getSaveAccumulativeCount();
        if (scenarioTemp.size() !=0){
            for (String age : scenarioTemp.get("age").keySet()){
                if (!age.equals(null)){
                    sum += Double.parseDouble(age) * scenarioTemp.get("age").get(age);
                    count += scenarioTemp.get("age").get(age);
                }
            }
        }
        double number = Math.ceil((sum/count)*100.00)/100.00;
        String average = df.format(number);
        return average;

    }


    /**
     * A tool of convert a double into two digits
     */
    DecimalFormat df = new DecimalFormat("0.00");


    /**
     * A getter of accumulated statistic information
     * @return    accumulated statistic information
     */
    public Map<String, Map<String, Integer>> getAccumulativeCount() {
        return accumulativeCount;
    }


    /**
     * A setter of accumulated statistic information
     * @param accumulativeCount   accumulated statistic information
     */
    public void setAccumulativeCount(Map<String, Map<String, Integer>> accumulativeCount) {
        this.accumulativeCount = accumulativeCount;
    }

    /**
     * A getter of saved lives in accumulated statistic information
     * @return    A getter of accumulated statistic information
     */
    public Map<String, Map<String, Integer>> getSaveAccumulativeCount() {
        return saveAccumulativeCount;
    }


    /**
     *  A setter of saved lives in accumulated statistic information
     * @param saveAccumulativeCount    saved lives in accumulated statistic information
     */
    public void setSaveAccumulativeCount(Map<String, Map<String, Integer>> saveAccumulativeCount) {
        this.saveAccumulativeCount = saveAccumulativeCount;
    }
}