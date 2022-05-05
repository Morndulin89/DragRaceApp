
package dragapp;

/**
 * Class handles Tuomari-entities from the database
 * @version 1.01 UPDATE DATE
 * @author Nanuli M. 
 */
public class Tuomari {
    
    /**
     *String tuomariID is the unique key for identifying the Tuomari
     */
    public String tuomariID;

    /**
     *String etunimi is the first name of the Tuomari
     */
    public String etunimi; 

    /**
     *String sukunimi is the surname of the Tuomari
     */
    public String sukunimi; 

    /**
     *Method returns tuomariID
     * @return tuomariID
     */
    public String getTuomariID() {
        return tuomariID;
    }

    /**
     *Method changes tuomariID
     * @param tuomariID Unique ID 
     */
    public void setTuomariID(String tuomariID) {
        this.tuomariID = tuomariID;
    }

    /**
     *Method returns etunimi
     * @return etunimi
     */
    public String getEtunimi() {
        return etunimi;
    }

    /**
     *Method changes etunimi
     * @param etunimi First name 
     */
    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    /**
     * Method returns sukunimi
     * @return sukunimi
     */
    public String getSukunimi() {
        return sukunimi;
    }

    /**
     * Method changes sukunimi
     * @param sukunimi Surname 
     */
    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }
    
    /**
     *Method gets Tuomari-entitys String etunimi and String sukunimi
     * @return Tuomari-entity in String
     */
    public String toString() {
        return this.etunimi + " " + this.sukunimi;
    }
    
}
