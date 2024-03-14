import java.util.Map;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */

public class NonAdult extends Human implements AgeGroup{

    private String groupAge;
    private int baby = 0;
    private int child = 0;
    private int senior = 0;

    /**
     * A constructor with age, gender, bodytype, and trespassing.
     * @param age           the age of the human/animal.
     * @param gender        the gender of the human/animal.
     * @param bodyType      the bodytype of the human/animal.
     * @param trespassing   the trespassing of the human/animal.
     */
    public NonAdult(int age, String gender, String bodyType, String trespassing) {
        super(age, gender, bodyType,trespassing);
    }


    /**
     * A getter of the age group of the adult.
     * @param age    the age of the adult.
     * @return       the age group of the adult.
     */
    @Override
    public String ageGroup(int age) {
        if (getAge() >= 0 && getAge() <= 4){
            groupAge = "baby";
        } else if (getAge() >= 5 && getAge() <= 16){
            groupAge = "child";
        } else if (getAge() >= 17 && getAge() <= 68){
            groupAge = "adult";
        } else if (getAge() > 68){
            groupAge = "senior";
        } else {
            groupAge = "unknown";
        }
        return groupAge;
    }

    /**
     * A description of non-adult.
     * @return    the description in string type.
     */
    @Override
    public String describe() {
        return "- " + getBodyType() + " " + ageGroup(getAge()) + " " + getGender();
    }

    /**
     * Accumulate the characteristic of the non-adult to the current location.
     * @param zipCounts     A statistic information for a location.
     * @return               An updated version of statistic information for the location.
     */
    @Override
    public Map<String, Map<String, Integer>> counts(Map<String, Map<String, Integer>> zipCounts) {

        zipCounts.get("genderCounts").put(getGender(), zipCounts.get("genderCounts").getOrDefault(getGender(),0)+1);
        zipCounts.get("ageGroupCounts").put(ageGroup(getAge()), zipCounts.get("ageGroupCounts").getOrDefault(ageGroup(getAge()),0)+1);
        zipCounts.get("bodyTypeCounts").put(getBodyType(),zipCounts.get("bodyTypeCounts").getOrDefault(getBodyType(),0)+1);
        zipCounts.get("age").put(getAge() + "",zipCounts.get("age").getOrDefault(getAge() + "",0)+1);
        zipCounts.get("animalCounts").put("human", zipCounts.get("animalCounts").getOrDefault("human",0)+1);
        zipCounts.get("legalOrTres").put(getTrespassing(), zipCounts.get("legalOrTres").getOrDefault(getTrespassing(),0)+1);

        return zipCounts;
    }
}