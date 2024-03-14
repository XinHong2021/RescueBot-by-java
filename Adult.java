import java.util.HashMap;
import java.util.Map;
/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */



public class Adult extends Human{
    private String profession;
    private String pregnant;
    private static final String groupAge = "adult";

    /**
     * A constructor with age, gender, bodytype, and trespassing.
     * @param age           the age of the human/animal.
     * @param gender        the gender of the human/animal.
     * @param bodyType      the bodytype of the human/animal.
     * @param trespassing   the trespassing of the human/animal.
     */
    public Adult(int age, String gender, String bodyType, String trespassing) {
        super(age, gender, bodyType,trespassing);
    }


    /**
     * A constructor with age, gender, bodytype, and trespassing.
     * @param age           the age of the adult.
     * @param gender        the gender of the adult.
     * @param bodyType      the bodytype of the adult.
     * @param trespassing   the trespassing of the adult.
     * @param profession    the profession of the adult.
     * @param pregnant      the pregnant status of the women.
     */
    public Adult(int age, String gender, String bodyType, String trespassing, String profession, String pregnant){
        super(age, gender, bodyType,trespassing);
        this.pregnant = pregnant;
        this.profession = profession;
    }


    /**
     * A getter of the age group of the adult.
     * @param age    the age of the adult.
     * @return       the age group of the adult.
     */
    @Override
    public String ageGroup(int age) {
        return groupAge;
    }


    /**
     * A description of adult.
     * @return    the description in string type.
     */
    @Override
    public String describe() {
        if (pregnant.toUpperCase().equals("TRUE")){
            return "- " + getBodyType() + " "+ groupAge + " " + profession + " " + getGender() + " pregnant";
        }
        else {
            return "- " + getBodyType() + " " +groupAge + " " + profession + " " + getGender();
        }
    }


    /**
     * Accumulate the characteristic of the adult to the current location.
     * @param zipCounts     A statistic information for a location.
     * @return               An updated version of statistic information for the location.
     */
    @Override
    public Map<String, Map<String, Integer>> counts(Map<String, Map<String, Integer>> zipCounts) {

        zipCounts.get("genderCounts").put(getGender(), zipCounts.get("genderCounts").getOrDefault(getGender(),0)+1);
        zipCounts.get("ageGroupCounts").put("adult", zipCounts.get("ageGroupCounts").getOrDefault("adult",0)+1);
        zipCounts.get("bodyTypeCounts").put(getBodyType(),zipCounts.get("bodyTypeCounts").getOrDefault(getBodyType(),0)+1);
        zipCounts.get("professionCounts").put(profession,zipCounts.get("professionCounts").getOrDefault(profession,0)+1);
        if (pregnant.toUpperCase().equals("TRUE")){
            zipCounts.get("pregnantCounts").put("pregnant",zipCounts.get("pregnantCounts").getOrDefault("pregnant",0)+1);
        }
        zipCounts.get("animalCounts").put("human", zipCounts.get("animalCounts").getOrDefault("human",0)+1);

        zipCounts.get("age").put(getAge() + "",zipCounts.get("age").getOrDefault(getAge() + "",0)+1);
        zipCounts.get("legalOrTres").put(getTrespassing(), zipCounts.get("legalOrTres").getOrDefault(getTrespassing(),0)+1);

        return zipCounts;
    }


}