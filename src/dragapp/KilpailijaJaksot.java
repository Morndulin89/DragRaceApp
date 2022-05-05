
package dragapp;

/**
 * Class handles KilpailijanJakso-entities from the database
 * @version 2.01 5.5.2022
 * @author Nanuli M. 
 */
public class KilpailijaJaksot {
    
    /**
     *String for identifying Kilpailija
     */
    public String kilpailijaID;

    /**
     *String for identifying Jakso
     */
    public String jaksoID;

    /**
     *Method gets String kilpailijaID
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
     *Method gets String jaksoID
     * @return jaksoID
     */
    public String getJaksoID() {
        return jaksoID;
    }

    /**
     *Method changes String jaksoID
     * @param jaksoID
     */
    public void setJaksoID(String jaksoID) {
        this.jaksoID = jaksoID;
    }
    
    
}
