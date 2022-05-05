package dragapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class is the controllor for deleting hosts and contestants from seasons and
 * episodes
 *
 * @version 2.01 5.5.2022
 * @author Nanuli M.
 */
public class DragDatabase {

    /**
     *ObservableList for holding contestants
     */
    public static ObservableList<Kilpailija> kilpailijat = FXCollections.observableArrayList();

    /**
     *ObservableList for holding contestants seasons
     */
    public static ObservableList<KilpailijanKausi> kaudenKilp = FXCollections.observableArrayList();

    /**
     *ObservableList for holding contestants episodes
     */
    public static ObservableList<KilpailijaJaksot> kilpJaksot = FXCollections.observableArrayList();

    /**
     *ObservableList for holding contestants
     */
    public static ObservableList<Tuomari> tuomarit = FXCollections.observableArrayList();

    /**
     *
     */
    public static ObservableList<TuomarinKausi> kaudenTuom = FXCollections.observableArrayList();

    /**
     *
     */
    public static ObservableList<Kausi> kaudet = FXCollections.observableArrayList();

    /**
     *
     */
    public static ObservableList<Jakso> jaksot = FXCollections.observableArrayList();

    /**
     *
     * @param connString
     * @return
     * @throws SQLException
     */
    public Connection openConnection(String connString) throws SQLException {
        Connection con = DriverManager.getConnection(connString);
        Statement stmt = con.createStatement();
        stmt.execute("USE dragrace;");
//        stmt.execute("USE 2108418DragRaceDatabase;");
        System.out.println("\t>>Yhteys Ok");
        System.out.println("\t>>Database loaded");
        return con;
    }

    /**
     *
     * @param c
     * @return
     */
    public ObservableList<Kilpailija> getKilpailija(Connection c) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT KilpailijaID, Etunimi, Sukunimi, Drag_nimi\n"
                    + " FROM Kilpailija\n"
                    + " ORDER BY KilpailijaID; "
            );
            while (rs.next()) {
                Kilpailija kilp = new Kilpailija();
                kilp.setKilpailijaID(rs.getString("KilpailijaID"));
                kilp.setEtunimi(rs.getString("Etunimi"));
                kilp.setSukunimi(rs.getString("Sukunimi"));
                kilp.setDrag_Nimi(rs.getString("Drag_nimi"));
                kilpailijat.add(kilp);
            }
            System.out.println("\t>>Contestants uploaded.");
        } catch (SQLException e) {
            System.out.println("\t>>Error loading kilpailijat");
            System.out.println("\t>>" + e);
        }
        return kilpailijat;

    }

    /**
     *
     * @param c
     * @return
     */
    public ObservableList<KilpailijanKausi> getKilpailijanKaudet(Connection c) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT kilpailijaID, kausiID\n"
                    + " FROM kauden_kilpailija; "
            );
            while (rs.next()) {
                KilpailijanKausi kilpKausi = new KilpailijanKausi();
                kilpKausi.setKilpailijaID(rs.getString("kilpailijaID"));
                kilpKausi.setKausiID(rs.getString("kausiID"));
                kaudenKilp.add(kilpKausi);
            }
            System.out.println("\t>>Season contestants uploaded");
        } catch (SQLException e) {
            System.out.println("\t>>Error loading kauden kilpailijat");
            System.out.println("\t>>" + e);
        }
        return kaudenKilp;
    }

    /**
     *
     * @param c
     * @return
     */
    public ObservableList<KilpailijaJaksot> getKilpailijanJaksot(Connection c) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT kilpailijaID, jaksoID\n"
                    + " FROM jakson_kilpailijat; "
            );
            while (rs.next()) {
                KilpailijaJaksot kilpJakso = new KilpailijaJaksot();
                kilpJakso.setKilpailijaID(rs.getString("kilpailijaID"));
                kilpJakso.setJaksoID(rs.getString("jaksoID"));
                kilpJaksot.add(kilpJakso);
            }
            System.out.println("\t>>Contestant episodes loaded");
        } catch (SQLException e) {
            System.out.println("\t>>Error loading contestant episodes");
            System.out.println("\t>>" + e);
        }
        return kilpJaksot;
    }

    /**
     *
     * @param c
     * @return
     */
    public ObservableList<Tuomari> getTuomari(Connection c) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT TuomariID, Etunimi, Sukunimi\n"
                    + " FROM Tuomari\n"
                    + " ORDER BY TuomariID;"
            );
            while (rs.next()) {
                Tuomari tuom = new Tuomari();
                tuom.setTuomariID(rs.getString("TuomariID"));
                tuom.setEtunimi(rs.getString("Etunimi"));
                tuom.setSukunimi(rs.getString("Sukunimi"));
                tuomarit.add(tuom);
            }
            System.out.println("\t>>Hosts uploaded.");
        } catch (SQLException e) {
            System.out.println("\t>>Error loading tuomarit");
            System.out.println("\t>>" + e);
        }
        return tuomarit;
    }

    /**
     *
     * @param c
     * @return
     */
    public ObservableList<Kausi> getKausi(Connection c) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT KausiID, KausiNro, KausiNimi\n"
                    + "FROM Kausi\n"
                    + "ORDER BY KausiID;"
            );
            while (rs.next()) {
                Kausi kausi = new Kausi();
                kausi.setKausiID(rs.getString("KausiID"));
                kausi.setKausiNro(rs.getInt("KausiNro"));
                kausi.setKausiNimi(rs.getString("KausiNimi"));
                kaudet.add(kausi);
            }
            System.out.println("\t>>Seasons uploaded.");
        } catch (SQLException e) {
            System.out.println("\t>>Error loading kaudet");
            System.out.println("\t>>" + e);
        }
        return kaudet;
    }

    /**
     *
     * @param c
     * @return
     */
    public ObservableList<Jakso> getJakso(Connection c) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT JaksoID, IlmestymisPvm, JaksonNimi, KausiID\n"
                    + " FROM Jakso\n"
                    + " ORDER BY IlmestymisPvm;;"
            );
            while (rs.next()) {
                Jakso jakso = new Jakso();
                jakso.setJaksoID(rs.getString("JaksoID"));
                jakso.setIlmPvm(rs.getString("IlmestymisPvm"));
                jakso.setJaksonNimi(rs.getString("JaksonNimi"));
                jakso.setKausiID(rs.getString("KausiID"));
                jaksot.add(jakso);
            }
            System.out.println("\t>>Episodes uploaded.");
        } catch (SQLException e) {
            System.out.println("\t>>Error loading episodes");
            System.out.println(e);
        }
        for (int i = 0; i < jaksot.size(); i++) {
        }
        return jaksot;
    }

    /**
     *
     * @param c
     * @return
     */
    public ObservableList<TuomarinKausi> getTuomarinKaudet(Connection c) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT tuomariID, kausiID\n"
                    + "FROM kauden_tuomari; "
            );
            while (rs.next()) {
                TuomarinKausi tuomKausi = new TuomarinKausi();
                tuomKausi.setTuomariID(rs.getString("tuomariID"));
                tuomKausi.setKausiID(rs.getString("kausiID"));
                kaudenTuom.add(tuomKausi);
            }
            System.out.println("\t>>Season hosts uploaded");
        } catch (SQLException e) {
            System.out.println("\t>>Error loading kauden tuomarit");
            System.out.println("\t>>" + e);
        }
        return kaudenTuom;
    }

    /**
     *
     * @param c
     * @return
     */
    public ObservableList<String> getEpisodes(Connection c) {
        ObservableList<String> kaudenJakso = FXCollections.observableArrayList();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT Jakso.Ilmestymispvm, Jakso.JaksonNimi, Kausi.KausiID\n"
                    + " FROM Jakso, Kausi\n"
                    + " WHERE Jakso.KausiID = Kausi.KausiID\n"
                    + " ORDER BY Kausi.KausiID ASC, Jakso.Ilmestymispvm ASC;"
            );
            while (rs.next()) {
                String ilmPvm = " ";
                String jaksoNimi = " ";
                String kausID = " ";
                String both = " ";
                ilmPvm = (rs.getString("Jakso.Ilmestymispvm"));
                jaksoNimi = (rs.getString("Jakso.JaksonNimi"));
                kausID = (rs.getString("Kausi.KausiID"));
                both = ilmPvm + "\n" + jaksoNimi + " " + kausID;
                kaudenJakso.add(both);
            }
            System.out.println("\t>>Season episodes uploaded");
        } catch (SQLException e) {
            System.out.println("\t>>Error loading season episodes");
            System.out.println("\t>>" + e);
        }

        return kaudenJakso;
    }

    /**
     *
     * @param c
     * @param kilp
     */
    public void newKilpailija(Connection c, Kilpailija kilp) {
        String kilpailijaID = kilp.getKilpailijaID();
        String sukunimi = kilp.getSukunimi();
        String etunimi = kilp.getEtunimi();
        String drag_name = kilp.getDrag_Nimi();
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "INSERT INTO Kilpailija (KilpailijaID, Sukunimi, Etunimi, Drag_nimi)\n"
                    + "	VALUES \n"
                    + "('" + kilpailijaID + "', '" + sukunimi + "', '" + etunimi + "','" + drag_name + "');"
            );
            System.out.println("\t >>Contestant added");

        } catch (SQLException e) {
            System.out.println("\t>>Error creating new contestant");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param kilpID
     * @param kausiID
     */
    public static void newKilpKausi(Connection c, String kilpID, String kausiID) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "INSERT INTO kauden_kilpailija(KilpailijaID, KausiID)\n"
                    + " VALUES  \n"
                    + "('" + kilpID + "', '" + kausiID + "');"
            );
            System.out.println("\t >>Contestant season added");

        } catch (SQLException e) {
            System.out.println("\t>>Error creating new contestant season");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param kilpID
     * @param jaksoID
     */
    public static void newKilpJakso(Connection c, String kilpID, String jaksoID) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "INSERT INTO jakson_kilpailijat(KilpailijaID, JaksoID)\n"
                    + " VALUES  \n"
                    + "('" + kilpID + "', '" + jaksoID + "');"
            );
            System.out.println("\t >>Contestant added to an episode");

        } catch (SQLException e) {
            System.out.println("\t>>Error adding contestant to an episode");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param kilpID
     * @param etunimi
     * @param sukunimi
     * @param dragNimi
     */
    public static void editKilpailija(Connection c, String kilpID, String etunimi, String sukunimi, String dragNimi) {
        try {
            PreparedStatement ps = c.prepareStatement(
                    "UPDATE kilpailija\n"
                    + " SET sukunimi = \"" + sukunimi + "\", etunimi = \"" + etunimi + "\", drag_nimi = \"" + dragNimi + "\"\n"
                    + " WHERE kilpailijaID = \"" + kilpID + "\";"
            );
            ps.executeUpdate();
            System.out.println("\t>>Contestant edited");
        } catch (SQLException e) {
            System.out.println("\t>>Error editing contestant");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param kilpailijaID
     * @param kausiID
     */
    public static void editKilpKausi(Connection c, String kilpailijaID, String kausiID) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "UPDATE kauden_kilpailija\n"
                    + " SET KausiID = '" + kausiID + "'"
                    + " WHERE KilpailijaID = '" + kilpailijaID + "';"
            );
            System.out.println("\t>>Contestant season edited");
        } catch (SQLException e) {
            System.out.println("\t>>Error editing contestant season");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param conn
     * @param kilpailijaID
     * @param jaksoID
     */
    public static void editKilpailijaJakso(Connection conn, String kilpailijaID, String jaksoID) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery(
                    "UPDATE jakson_kilpailijat\n"
                    + " SET jaksoID = '" + jaksoID + "'"
                    + " WHERE KilpailijaID = '" + kilpailijaID + "';"
            );
            System.out.println("\t>>Contestant episode edited");
        } catch (SQLException e) {
            System.out.println("\t>>Error editing contestant episode");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param kilpID
     */
    public static void delCont(Connection c, String kilpID) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    " DELETE FROM Kilpailija\n"
                    + " WHERE KilpailijaID = '" + kilpID + "';"
            );
            System.out.println("\t>>Contestant deleted");
        } catch (SQLException e) {
            System.out.println("\t>>Error deleting contestant");
            System.out.println("\t " + e);
        }
    }
    
    /**
     *
     * @param conn
     * @param kilpID
     */
    public static void delContFromAllSeasons(Connection conn, String kilpID){
         try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery(
                    " DELETE FROM kauden_kilpailija\n"
                    + " WHERE KilpailijaID = '" + kilpID + "';"
            );
            System.out.println("\t>>Contestant deleted from all seasons");
        } catch (SQLException e) {
            System.out.println("\t>>Error deleting contestant from all seasons");
            System.out.println("\t " + e);
        }
    }

    /**
     *
     * @param conn
     * @param kilpID
     * @param kausiID
     */
    public static void delContFromSeason(Connection conn, String kilpID, String kausiID) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery(
                    " DELETE FROM kauden_kilpailija\n"
                    + " WHERE KilpailijaID = '" + kilpID + "' AND kausiID = '" + kausiID + "';"
            );
            System.out.println("\t>>Contestant deleted from season");
        } catch (SQLException e) {
            System.out.println("\t>>Error deleting contestant from season");
            System.out.println("\t " + e);
        }
    }
    
    /**
     *
     * @param conn
     * @param kilpID
     */
    public static void delContFromAllEpisodes(Connection conn, String kilpID){
            try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery(
                    " DELETE FROM jakson_kilpailijat\n"
                    + " WHERE KilpailijaID = '" + kilpID + "';"
            );
            System.out.println("\t>>Contestant deleted from all episodes");

        } catch (SQLException e) {
            System.out.println("\t>>Error deleting contestant from all episodes");
            System.out.println("\t " + e);
        }
    
    }

    /**
     *
     * @param conn
     * @param kilpID
     * @param jaksoID
     */
    public static void delContFromEpisode(Connection conn, String kilpID, String jaksoID) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery(
                    " DELETE FROM jakson_kilpailijat\n"
                    + " WHERE KilpailijaID = '" + kilpID + "' AND jaksoID = '" + jaksoID + "';"
            );
            System.out.println("\t>>Contestant deleted from season");

        } catch (SQLException e) {
            System.out.println("\t>>Error deleting contestant from season");
            System.out.println("\t " + e);
        }
    }

    /**
     *
     * @param c
     * @param tuomID
     * @param etunimi
     * @param sukunimi
     */
    public static void editHost(Connection c, String tuomID, String etunimi, String sukunimi) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "UPDATE Tuomari \n"
                    + "	SET TuomariID = '" + tuomID + "', Sukunimi = '" + sukunimi + "', Etunimi = '" + etunimi + "'"
                    + "\n WHERE TuomariID = '" + tuomID + "';"
            );
            System.out.println("\t>>Host edited");
        } catch (SQLException e) {
            System.out.println("\t>>Error editing host");
            System.out.println("\t>>" + e);
        }

    }

    /**
     *
     * @param conn
     * @param tuomID
     * @param kausiID
     */
    public static void editHostSeason(Connection conn, String tuomID, String kausiID) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery(
                    "UPDATE kauden_tuomari \n"
                    + "	SET KausiID = '" + kausiID + "'"
                    + "\n WHERE TuomariID = '" + tuomID + "';"
            );
            System.out.println("\t>>Host season edited");
        } catch (SQLException e) {
            System.out.println("\t>>Error editing host season");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param tuomID
     * @param etunimi
     * @param sukunimi
     */
    public static void newHost(Connection c, String tuomID, String etunimi, String sukunimi) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "INSERT INTO Tuomari (TuomariID, Sukunimi, Etunimi)\n"
                    + "	VALUES \n"
                    + "('" + tuomID + "', '" + sukunimi + "', '" + etunimi + "');"
            );
            System.out.println("\t>>Host added");

        } catch (SQLException e) {
            System.out.println("\t>>Error creating new host");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param tuomID
     * @param kausiID
     */
    public static void newHostSeason(Connection c, String tuomID, String kausiID) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "INSERT INTO kauden_tuomari (TuomariID, KausiID)\n"
                    + "	VALUES \n"
                    + "('" + tuomID + "', '" + kausiID + "');"
            );
            System.out.println("\t>>Host added to a season");

        } catch (SQLException e) {
            System.out.println("\t>>Error adding host to a season");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param tuomID
     */
    public static void delHost(Connection c, String tuomID) {

        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "DELETE FROM Tuomari\n"
                    + " WHERE TuomariID = '" + tuomID + "';"
            );
            System.out.println("\t>>Host deleted");

        } catch (SQLException e) {
            System.out.println("\t>>Error deleting host");
            System.out.println("\t>>" + e);
        }
    }
    
    /**
     *
     * @param conn
     * @param tuomID
     * @param kausiID
     */
    public static void delHostFromSeason(Connection conn, String tuomID, String kausiID){
            try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery(
                    "DELETE FROM kauden_tuomari\n"
                    + " WHERE TuomariID = '" + tuomID + "' AND kausiID = '" + kausiID + "';"
            );
            System.out.println("\t>>Host deleted from season");

        } catch (SQLException e) {
            System.out.println("\t>>Error deleting host from season");
            System.out.println("\t>>" + e);
        }
    
    }

    /**
     *
     * @param c
     * @param kausiID
     * @param kausiNro
     * @param kausiNimi
     */
    public static void newSeason(Connection c, String kausiID, int kausiNro, String kausiNimi) {

        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "INSERT INTO Kausi (KausiID, Kausinro, KausiNimi)\n"
                    + "	VALUES \n"
                    + "('" + kausiID + "', '" + kausiNro + "', '" + kausiNimi + "');"
            );
            System.out.println("\t>>Season added");

        } catch (SQLException e) {
            System.out.println("\t>>Error creating new season");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param jaksoID
     * @param jaksonNimi
     * @param ilmestymisPvm
     * @param kausiID
     */
    public static void newEpisode(Connection c, String jaksoID, String jaksonNimi, LocalDate ilmestymisPvm, String kausiID) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "INSERT INTO Jakso (JaksoID, JaksonNimi, Ilmestymispvm, KausiID)\n"
                    + "	VALUES \n"
                    + "('" + jaksoID + "', '" + jaksonNimi + "', '" + ilmestymisPvm + "', '" + kausiID + "');"
            );
            System.out.println("\t>>Episode added");

        } catch (SQLException e) {
            System.out.println("\t>>Error creating new episode");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param kausiID
     * @param kausiNro
     * @param kausiNimi
     */
    public static void editSeason(Connection c, String kausiID, int kausiNro, String kausiNimi) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "UPDATE Kausi\n"
                    + "	SET kausiNro = '" + kausiNro + "', kausiNimi ='" + kausiNimi + "'\n"
                    + "WHERE kausiID = '" + kausiID + "';"
            );
            System.out.println("\t>>Season edited");
        } catch (SQLException e) {
            System.out.println("\t>>Error editing season");
            System.out.println("\t>>" + e);
        }
    }

    /**
     *
     * @param c
     * @param jaksoID
     * @param ilmestymisPvm
     * @param jaksonNimi
     * @param kausiID
     */
    public static void editEpisode(Connection c, String jaksoID, LocalDate ilmestymisPvm, String jaksonNimi, String kausiID) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeQuery(
                    "UPDATE Jakso\n"
                    + "	SET ilmestymisPvm = '" + ilmestymisPvm + "', jaksonNimi ='" + jaksonNimi + "', kausiID ='" + kausiID + "'\n"
                    + "WHERE jaksoID = '" + jaksoID + "';"
            );
            System.out.println("\t>>Season edited");
        } catch (SQLException e) {
            System.out.println("\t>>Error editing season");
            System.out.println("\t>>" + e);
        }
    }
}//end class
