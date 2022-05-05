
package dragapp;

/**
 * Class handles KilpailijanKausi-entities from the database
 * @version 2.01 5.5.2022
 * @author Nanuli M. 
 */
public class KilpailijanKausi {
    
    /**
     *String for identifying Kilpailija
     */
    public String kilpailijaID;

    /**
     *String for identifying Kausi
     */
    public String kausiID;

    /**
     * Method returns String kilpailijaID
     * @return kilpailijaID
     */
    public String getKilpailijaID() {
        return kilpailijaID;
    }

    /**
     *Method changes String kilpailijaID
     * @param kilpailijaID
     */
    public void setKilpailijaID(String kilpailijaID) {
        this.kilpailijaID = kilpailijaID;
    }

    /**
     *Method returns String kausiID
     * @return kausiID
     */
    public String getKausiID() {
        return kausiID;
    }

    /**
     *Method changes String kausiID
     * @param kausiID
     */
    public void setKausiID(String kausiID) {
        this.kausiID = kausiID;
    }
}
