
package dragapp;

/**
 * Class handles TuomarinKausi-entities from the database
 * @version 2.01 5.5.2022
 * @author Nanuli M. 
 */
public class TuomarinKausi {
    
    /**
     * String for identifying Tuomari
     */
    public String tuomariID;

    /**
     * String for identifying Kausi
     */
    public String kausiID;

    /**
     * Method returns String tuomariID
     * @return tuomariID
     */
    public String getTuomariID() {
        return tuomariID;
    }

    /**
     * Method changes String tuomariID
     * @param tuomariID
     */
    public void setTuomariID(String tuomariID) {
        this.tuomariID = tuomariID;
    }

    /**
     * method returns String kausiID
     * @return kausiID
     */
    public String getKausiID() {
        return kausiID;
    }

    /**
     * Method changes String kausiID
     * @param kausiID
     */
    public void setKausiID(String kausiID) {
        this.kausiID = kausiID;
    }
    
}
