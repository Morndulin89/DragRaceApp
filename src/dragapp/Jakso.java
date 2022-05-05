package dragapp;

/**
 * Class handles Jakso-entities from the database
 *
 * @version 1.01 UPDATE DATE
 * @author Nanuli M.
 */
public class Jakso {

    /**
     * String jaksoID is the unique key for identifying the Jakso
     */
    public String jaksoID;

    /**
     *String ilmPvm is the date the episode aired 
     */
    public String ilmPvm;

    /**
     *String jaksonNimi is the name of the episode
     */
    public String jaksonNimi;

    /**
     *String kausiID is the key to which season the episode belongs to 
     */
    public String kausiID;


    /**
     *Method gets String jaksoID
     * @return jaksoID
     */
    public String getJaksoID() {
        return jaksoID;
    }

    /**
     *Method changes the String jaksoID
     * @param jaksoID
     */
    public void setJaksoID(String jaksoID) {
        this.jaksoID = jaksoID;
    }

    /**
     *Method gets String ilmPvm 
     * @return ilmPvm
     */
    public String getIlmPvm() {
        return ilmPvm;
    }

    /**
     *Method sets String ilmPvm
     * @param ilmPvm
     */
    public void setIlmPvm(String ilmPvm) {
        this.ilmPvm = ilmPvm;
    }

    /**
     *Method gets String jaksonNimi
     * @return jaksonNimi
     */
    public String getJaksonNimi() {
        return jaksonNimi;
    }

    /**
     *Method changes String jaksonNimi
     * @param jaksonNimi
     */
    public void setJaksonNimi(String jaksonNimi) {
        this.jaksonNimi = jaksonNimi;
    }

    /**
     *Method gets String KausiID
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


    /**
     *Method gets jakso to String
     * @return jaksonNimi
     */
    public String toString() {
        return this.getJaksonNimi();
    }
}
