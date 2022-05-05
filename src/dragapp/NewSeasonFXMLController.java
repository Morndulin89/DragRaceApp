package dragapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Class is the controllor for adding new Season, new Episodes and hosts and
 * contestants to these
 *
 * @version 2.01 5.5.2022
 * @author Nanuli M.
 */
public class NewSeasonFXMLController implements Initializable {

    @FXML
    private TextField txtNewSeasonID;
    @FXML
    private TextField newSeasonNumber;
    @FXML
    private TextField txtNewSeasonName;
    @FXML
    private Button btnAddNewSeason;
    @FXML
    private TextField txtNewEpisodeID;
    @FXML
    private TextField txtNewEpisodeName;
    @FXML
    private DatePicker datePickNewEpisodeDate;
    @FXML
    private ComboBox<Kausi> cboNewEpisodeSeason;
    @FXML
    private ComboBox<Kilpailija> cboSeasonCont;
    @FXML
    private ComboBox<Kausi> cboContSeasonSeason;
    @FXML
    private Button btnAddContToSeason;
    @FXML
    private ComboBox<Kilpailija> cboEpisodesCont;
    @FXML
    private ComboBox<Jakso> cboContEpisodeEpisode;
    @FXML
    private Button btnAddContToEpisode;
    @FXML
    private ComboBox<Tuomari> cboSeasonHost;
    @FXML
    private ComboBox<Kausi> cboHostSeasonSeason;
    @FXML
    private Button btnAddHostToSeason;

    Connection conn;
//    String connString = "jdbc:mariadb://localhost:3306?user=harjoitus&password=harjoitus";
    String connString = "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1";
    private DragDatabase ddb = new DragDatabase();
    private ObservableList<Kausi> newKaudet;
    private ObservableList<Jakso> newJaksot;
    private ObservableList<Kilpailija> newKilpailijat;
    private ObservableList<Tuomari> newTuomarit;
    private ObservableList<KilpailijanKausi> newKausiKilp;
    @FXML
    private AnchorPane scene2;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            conn = ddb.openConnection(connString);
        } catch (SQLException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        newKaudet = ddb.getKausi(conn);
        newJaksot = ddb.getJakso(conn);
        newKilpailijat = ddb.getKilpailija(conn);
        newTuomarit = ddb.getTuomari(conn);
        newKausiKilp = ddb.getKilpailijanKaudet(conn);

        for (Kausi kausi : newKaudet) {
            cboNewEpisodeSeason.getItems().add(kausi);
            cboContSeasonSeason.getItems().add(kausi);
            cboHostSeasonSeason.getItems().add(kausi);
        }

        for (Kilpailija kilp : newKilpailijat) {
            cboSeasonCont.getItems().add(kilp);
            cboEpisodesCont.getItems().add(kilp);

        }
        for (Tuomari tuom : newTuomarit) {
            cboSeasonHost.getItems().add(tuom);
        }
        createEpisodeCombobox();
    }

    /**
     * Method closes the connection when the scene closes
     */
    public static void closeConnection(Connection c) throws SQLException {
        if (c != null) {
            c.close();
        }
        System.out.println("\t>> Tietokantayhteys suljettu.");
    }

    /**
     * Method creates a new Season
     */
    public void addNewSeason() {
        Kausi kausi = new Kausi();
        String kausiID = txtNewSeasonID.getText();
        int kausiNro = Integer.parseInt(newSeasonNumber.getText());
        String kausiNimi = txtNewSeasonName.getText();
        ddb.newSeason(conn, kausiID, kausiNro, kausiNimi);
        newKaudet.clear();
        cboNewEpisodeSeason.getItems().clear();
        cboContSeasonSeason.getItems().clear();
        cboHostSeasonSeason.getItems().clear();
    }

    /**
     * Method finalizes creating a new Season
     */
    @FXML
    public void onActionAddNewSeason(ActionEvent event) {
        addNewSeason();
        newKaudet = ddb.getKausi(conn);
        for (Kausi kausi : newKaudet) {
            cboNewEpisodeSeason.getItems().add(kausi);
            cboContSeasonSeason.getItems().add(kausi);
            cboHostSeasonSeason.getItems().add(kausi);
        }
        txtNewSeasonID.setText("");
        newSeasonNumber.setText("");
        txtNewSeasonName.setText("");
    }

    /**
     * Method adds a new episode
     */
    public void addNewEpisode() {
        Jakso jakso = new Jakso();
        String jaksoID = txtNewEpisodeID.getText();
        String jaksoNimi = txtNewEpisodeName.getText();
        LocalDate ilmestymisPvm = datePickNewEpisodeDate.getValue();
        String kausiID = cboNewEpisodeSeason.getSelectionModel().getSelectedItem().getKausiID();
        ddb.newEpisode(conn, jaksoID, jaksoNimi, ilmestymisPvm, kausiID);
        newJaksot.clear();
    }

    /**
     * Method finalizes adding a new episode
     */
    @FXML
    public void onActionAddNewEpisode(ActionEvent event) {
        addNewEpisode();
        newJaksot = ddb.getJakso(conn);
        txtNewEpisodeID.setText("");
        txtNewEpisodeName.setText("");
        datePickNewEpisodeDate.setValue(null);
        cboNewEpisodeSeason.setValue(null);
    }

    /**
     * Method adds a contestant to a season
     */
    public void addContToSeason() {
        String kilpailijaID = cboSeasonCont.getSelectionModel().getSelectedItem().getKilpailijaID();
        String kausiID = cboContSeasonSeason.getSelectionModel().getSelectedItem().getKausiID();
        ddb.newKilpKausi(conn, kilpailijaID, kausiID);
        newKausiKilp = ddb.getKilpailijanKaudet(conn);
    }

    /**
     * Method finalizes adding a contestant to a season
     */
    @FXML
    public void onActionAddContToSeason(ActionEvent event) {
        addContToSeason();
        cboSeasonCont.setValue(null);
        cboContSeasonSeason.setValue(null);
    }

    /**
     * Method adds a contestant to an episode
     */
    public void addContToEpisode() {
        String kilpailijaID = cboEpisodesCont.getSelectionModel().getSelectedItem().getKilpailijaID();
        String jaksoID = cboContEpisodeEpisode.getSelectionModel().getSelectedItem().getJaksoID();
        ddb.newKilpJakso(conn, kilpailijaID, jaksoID);
    }

    /**
     * Method creates EpisodeCombobox based on the selected contestant and the
     * seasons they are on
     */
    public void createEpisodeCombobox() {
        cboEpisodesCont.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Kilpailija kilp = cboEpisodesCont.getSelectionModel().getSelectedItem();
                for (KilpailijanKausi kilpKausi : newKausiKilp) {
                    for (Jakso jakso : newJaksot) {
                        if (kilp.getKilpailijaID().equals(kilpKausi.getKilpailijaID()) && jakso.getKausiID().equals(kilpKausi.getKausiID())) {
                            cboContEpisodeEpisode.getItems().add(jakso);
                        }
                    }
                }
            }
        });
    }

    /**
     * Method finalizes adding a contestant to an episode
     */
    @FXML
    public void onActionAddContToEpisode(ActionEvent event) {
        addContToEpisode();
        cboEpisodesCont.setValue(null);
        cboContEpisodeEpisode.setValue(null);
    }

    /**
     * Method adds a host to a season
     */
    public void addHostToSeason() {
        String tuomID = cboSeasonHost.getSelectionModel().getSelectedItem().getTuomariID();
        String kausiID = cboHostSeasonSeason.getSelectionModel().getSelectedItem().getKausiID();
        ddb.newHostSeason(conn, tuomID, kausiID);

    }

    /**
     * Method finalizes adding a host to a season
     */
    @FXML
    public void onActionAddHostToSeason(ActionEvent event) {
        addHostToSeason();
        cboSeasonHost.setValue(null);
        cboHostSeasonSeason.setValue(null);
    }

    /**
     * Method closes the current scene, clears the cache and returns to the
     * parent-scene
     */
    @FXML
    public void onActionSendInfoToMainWindow(ActionEvent event) {

        newKaudet.clear();
        newJaksot.clear();
        newKilpailijat.clear();
        newTuomarit.clear();
        newKausiKilp.clear();

        try {
            closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            AnchorPane scene1 = FXMLLoader.load(getClass().getResource("DragFXMLDocument.fxml"));
            scene2.getChildren().removeAll();
            scene2.getChildren().setAll(scene1);

        } catch (IOException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
