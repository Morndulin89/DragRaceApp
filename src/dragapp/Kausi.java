package dragapp;

/**
 * Class handles Kausi-entities from the database
 * @version 2.01 5.5.2022
 * @author Nanuli M. 
 */
public class Kausi {

    /**
     * String kausiID responds to kausiID from the database
     */
    public String kausiID;

    /**
     *String kausiNro responds to kausiNro from the database
     */
    public int kausiNro;
    
    /**
     * String kausiNimi responds to kausiNimi from the database
     */
    public String kausiNimi; 

    /**
     * Method returns kausiID
     * @return kausiID
     */
    public String getKausiID() {
        return kausiID;
    }

    /**
     *Method changes kausiID
     * @param kausiID Unique ID 
     */
    public void setKausiID(String kausiID) {
        this.kausiID = kausiID;
    }

    /**
     * Method returns kausiNro
     * @return kausiNro
     */
    public int getKausiNro() {
        return kausiNro;
    }

    /**
     * Method changes kausiNro
     * @param kausiNro Season number
     */
    public void setKausiNro(int kausiNro) {
        this.kausiNro = kausiNro;
    }
    
    /**
     * Method returns kausiNimi
     * @return kausiNimi 
     */
    public String getKausiNimi() {
        return kausiNimi;
    }

    /**
     * Method changes kausiNimi
     * @param kausiNimi Name of the season
     */
    public void setKausiNimi(String kausiNimi) {
        this.kausiNimi = kausiNimi;
    }

    /**
     * Method gets String kausiNimi
     * @return kausiNimi
     */
    public String toString() {
        return this.getKausiNimi();
    }
}
