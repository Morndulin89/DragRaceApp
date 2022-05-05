package dragapp;

/**
 * Class handles Kilpailija-entities from the database
 * @version 2.01 5.5.2022
 * @author Nanuli M.
 */
public class Kilpailija {

    /**
     * String kilpailijaID is the unique key for identifying the Kilpailija
     */
    public String kilpailijaID;

    /**
     * String etunimi is the first name of the Kilpailija
     */
    public String Etunimi;

    /**
     * String sukunimi is the surname of the Kilpailija
     */
    public String Sukunimi;

    /**
     * String drag_nimi is the artist name/drag name of the Kilpailija
     */
    public String Drag_Nimi;

    /**
     * Method returns kilpailijaID
     *
     * @return kilpailijaID
     */
    public String getKilpailijaID() {
        return kilpailijaID;
    }

    /**
     * Method changes kilpailijaID
     *
     * @param kilpailijaID Unique ID
     */
    public void setKilpailijaID(String kilpailijaID) {
        this.kilpailijaID = kilpailijaID;
    }

    /**
     * Method returns etunimi
     *
     * @return etunimi
     */
    public String getEtunimi() {
        return Etunimi;
    }

    /**
     * Method changes etunimi
     *
     * @param etunimi First name
     */
    public void setEtunimi(String etunimi) {
        this.Etunimi = etunimi;
    }

    /**
     * Method returns sukunimi
     *
     * @return sukunimi
     */
    public String getSukunimi() {
        return Sukunimi;
    }

    /**
     * Method changes sukunimi
     *
     * @param sukunimi Surname
     */
    public void setSukunimi(String sukunimi) {
        this.Sukunimi = sukunimi;
    }

    /**
     * Method returns drag_nimi
     *
     * @return drag_nimi
     */
    public String getDrag_Nimi() {
        return Drag_Nimi;
    }

    /**
     * Method changes drag_nimi
     *
     * @param drag_Nimi Artist name for the Kilpailija
     */
    public void setDrag_Nimi(String drag_Nimi) {
        this.Drag_Nimi = drag_Nimi;
    }

    /**
     *Method gets the String drag_nimi
     * @return drag_nimi
     */
    public String toString() {
        return this.getDrag_Nimi();
    }
}
