/**
 * COMP90041, Sem1, 2023: Final Project
 * @author  Xin Hong
 * student id  1351293
 * student email  honxh1@student.unimelb.edu.au
 */


public class InvalidDataFormat extends Exception{
    /**
     * which row is invalid
     */
    private int row;

    /**
     * The invalid row of the data.
     * @param row    The invalid row of data.
     */
    public InvalidDataFormat(int row){
        this.row = row;
    }

    /**
     * Warning messages of the error.
     * @return  Warning messages of the error.
     */
    public String getMessage(){
        return "WARNING: invalid number format in scenarios file in line " + row;
    }

}

