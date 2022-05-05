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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Class is the controllor for editing Season and Episodes and their contestants
 * and hosts
 *
 * @version 2.01 5.5.2022
 * @author Nanuli M.
 */
public class EditSeasonFXMLController implements Initializable {

    @FXML
    private AnchorPane scene2;
    @FXML
    private ComboBox<Kausi> cboEditSeason;
    @FXML
    private TextField txtSeasonNumber;
    @FXML
    private TextField txtEditSeasonName;
    @FXML
    private Button btnEditSeason;
    @FXML
    private ChoiceBox<Jakso> editEpisode;
    @FXML
    private ComboBox<Kausi> cboEditEpisodeSeason;
    @FXML
    private TextField txtEditEpisodeName;
    @FXML
    private DatePicker datePickEditEpisodeDate;
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
    String connString = "jdbc:mariadb://localhost:3306?user=harjoitus&password=harjoitus";
//    String connString = "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1";
    private DragDatabase ddb = new DragDatabase();
    private ObservableList<Kausi> newKaudet;
    private ObservableList<Jakso> newJaksot;
    private ObservableList<Kilpailija> newKilpailijat;
    private ObservableList<Tuomari> newTuomarit;
    private ObservableList<KilpailijanKausi> newKausiKilp;

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
            cboEditSeason.getItems().add(kausi);
            cboEditEpisodeSeason.getItems().add(kausi);
            cboContSeasonSeason.getItems().add(kausi);
            cboHostSeasonSeason.getItems().add(kausi);
        }
        for (Jakso jakso : newJaksot) {
            editEpisode.getItems().add(jakso);
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
     * Method edits season
     */
    private void editSeason() {
        String kausiID = cboEditSeason.getSelectionModel().getSelectedItem().getKausiID();
        int kausiNro = Integer.parseInt(txtSeasonNumber.getText());
        String kausiNimi = txtEditSeasonName.getText();
        ddb.editSeason(conn, kausiID, kausiNro, kausiNimi);
        newKaudet.clear();
        cboEditSeason.getItems().clear();
        cboEditEpisodeSeason.getItems().clear();
        cboContSeasonSeason.getItems().clear();
        cboHostSeasonSeason.getItems().clear();

    }

    /**
     * Method finalizes edits to a season
     */
    @FXML
    private void onActionEditSeason(ActionEvent event) {
        editSeason();
        newKaudet = ddb.getKausi(conn);
        cboEditSeason.setValue(null);
        txtSeasonNumber.setText("");
        txtEditSeasonName.setText("");

        for (Kausi kausi : newKaudet) {
            cboEditSeason.getItems().add(kausi);
            cboEditEpisodeSeason.getItems().add(kausi);
            cboContSeasonSeason.getItems().add(kausi);
            cboHostSeasonSeason.getItems().add(kausi);
        }
    }

    /**
     * Method edits to an episode
     */
    private void editEpisode() {

        String jaksoID = editEpisode.getSelectionModel().getSelectedItem().getJaksoID();
        String kausiID = cboEditEpisodeSeason.getSelectionModel().getSelectedItem().getKausiID();
        String jaksonNimi = txtEditEpisodeName.getText();
        LocalDate ilmestymispvm = datePickEditEpisodeDate.getValue();
        ddb.editEpisode(conn, jaksoID, ilmestymispvm, jaksonNimi, kausiID);
        newJaksot.clear();
        editEpisode.getItems().clear();
        cboEditEpisodeSeason.getItems().clear();
    }

    /**
     * Method finalizes edits to an episode
     */
    @FXML
    private void onActionEditEpisode(ActionEvent event) {
        editEpisode();
        newJaksot = ddb.getJakso(conn);
        for (Jakso jakso : newJaksot) {
            editEpisode.getItems().add(jakso);
        }
        editEpisode.setValue(null);
        cboEditEpisodeSeason.setValue(null);
        txtEditEpisodeName.setText(null);
        datePickEditEpisodeDate.setValue(null);
    }

    /**
     * Method edits contestant's seasons
     */
    private void editContSeason() {
        String kilpailijaID = cboSeasonCont.getSelectionModel().getSelectedItem().getKilpailijaID();
        String kausiID = cboContSeasonSeason.getSelectionModel().getSelectedItem().getKausiID();
        ddb.editKilpKausi(conn, kilpailijaID, kausiID);
        newKausiKilp.clear();

    }

    /**
     * Method finalizes edits to contestant's seasons
     */
    @FXML
    private void onActionEditContToSeason(ActionEvent event) {
        editContSeason();
        newKausiKilp = ddb.getKilpailijanKaudet(conn);
        cboSeasonCont.setValue(null);
        cboContSeasonSeason.setValue(null);
    }

    /**
     * Method creates EpisodeComboBox based on the seasons the contestant
     * participates
     */
    private void createEpisodeCombobox() {
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
     * Method edits contestant's episodes
     */

    private void editContEpisode() {
        String kilpailijaID = cboEpisodesCont.getSelectionModel().getSelectedItem().getKilpailijaID();
        String jaksoID = cboContEpisodeEpisode.getSelectionModel().getSelectedItem().getJaksoID();
        ddb.editKilpailijaJakso(conn, kilpailijaID, jaksoID);
    }
    /**
     * Method finalizes edits to contestant's episodes
     */
    @FXML
    private void onActionEditContToEpisode(ActionEvent event) {
        editContEpisode();
        cboEpisodesCont.setValue(null);
        cboContEpisodeEpisode.setValue(null);
    }
        /**
     * Method edits to host's seasons
     */

    private void editHostSeason() {
        String tuomID = cboSeasonHost.getSelectionModel().getSelectedItem().getTuomariID();
        String kausiID = cboHostSeasonSeason.getSelectionModel().getSelectedItem().getKausiID();
        ddb.editHostSeason(conn, tuomID, kausiID);

    }
    /**
     * Method finalizes edits to host's seasons
     */
    @FXML
    private void onActionEditHostToSeason(ActionEvent event) {
        editHostSeason();
        cboSeasonHost.setValue(null);
        cboHostSeasonSeason.setValue(null);

    }

    /**
     * Method closes the current scene, clears the cache and returns to the
     * parent-scene
     */
    @FXML
    private void onActionSendInfoToMainWindow(ActionEvent event) {

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
