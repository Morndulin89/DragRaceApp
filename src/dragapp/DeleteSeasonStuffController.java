package dragapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 * Class is the controllor for deleting hosts and contestants from seasons and
 * episodes
 *
 * @version 2.01 5.5.2022
 * @author Nanuli M.
 */
public class DeleteSeasonStuffController implements Initializable {

    @FXML
    private AnchorPane scene2;
    @FXML
    private ComboBox<Kilpailija> cboSeasonCont;
    @FXML
    private ComboBox<Kausi> cboContSeasonSeason;
    @FXML
    private ComboBox<Kilpailija> cboEpisodesCont;
    @FXML
    private ComboBox<Jakso> cboContEpisodeEpisode;
    @FXML
    private ComboBox<Tuomari> cboSeasonHost;
    @FXML
    private ComboBox<Kausi> cboHostSeasonSeason;

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

        /*Populates the ObservableLists from the database*/
        newKaudet = ddb.getKausi(conn);
        newJaksot = ddb.getJakso(conn);
        newKilpailijat = ddb.getKilpailija(conn);
        newTuomarit = ddb.getTuomari(conn);
        newKausiKilp = ddb.getKilpailijanKaudet(conn);

        /*Populates the comboboxes from the observablelists*/
        for (Kausi kausi : newKaudet) {
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
        createSeasonCombobox();
    }

    /**
     * Method creates combobox of contestant's seasons
     */
    private void createSeasonCombobox() {
        cboSeasonCont.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cboContSeasonSeason.getItems().clear();
                Kilpailija kilp = cboSeasonCont.getSelectionModel().getSelectedItem();
                for (KilpailijanKausi kilpKausi : newKausiKilp) {
                    for (Kausi kausi : newKaudet) {
                        if (kilp.getKilpailijaID().equals(kilpKausi.getKilpailijaID()) && kilpKausi.getKausiID().equals(kausi.getKausiID())) {
                            cboContSeasonSeason.getItems().add(kausi);
                        }
                    }
                }
            }
        });
    }

    /**
     * Method creates combobox of contestant's episodes
     */
    private void createEpisodeCombobox() {
        cboEpisodesCont.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cboContEpisodeEpisode.getItems().clear();
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
     * Method gets KilpailijaID and kausiID from the comboboxes and deletes the
     * connection from the database. Method also deletes contestant from all the
     * episodes on that season
     */
    private void delContFromSeason() {
        String kilpID = cboSeasonCont.getSelectionModel().getSelectedItem().getKilpailijaID();
        String kausiID = cboContSeasonSeason.getSelectionModel().getSelectedItem().getKausiID();

        ddb.delContFromSeason(conn, kilpID, kausiID);
        ddb.delContFromAllEpisodes(conn, kilpID);

    }

    /**
     * Method finalizes deleting contestant from the season
     */
    @FXML
    private void onActionDeleteContFromSeason(ActionEvent event) {
        if (!cboSeasonCont.getSelectionModel().isEmpty()) {
            String kilpailijaNimi = cboSeasonCont.getSelectionModel().getSelectedItem().getDrag_Nimi();
            String kausiNimi = cboContSeasonSeason.getSelectionModel().getSelectedItem().getKausiNimi();
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert confirm = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete " + kilpailijaNimi + " from season "
                    + kausiNimi + "? \n\nThe contestant will be removed also from all the episodes on the season.", ok, no);
            confirm.setTitle("Deleting contestant from season");
            Optional<ButtonType> choice = confirm.showAndWait();

            if (choice.orElse(no) == ok) {
                delContFromSeason();
                cboSeasonCont.setValue(null);
                cboContSeasonSeason.setValue(null);
            }
        }
    }

    /**
     * Method gets KilpailijaID and kausiID from the comboboxes and deletes the
     * connection from the database
     */
    private void deleteContFromEpisode() {

        String kilpID = cboEpisodesCont.getSelectionModel().getSelectedItem().getKilpailijaID();
        String jaksoID = cboContEpisodeEpisode.getSelectionModel().getSelectedItem().getJaksoID();
        ddb.delContFromEpisode(conn, kilpID, jaksoID);

    }

    /**
     * Method finalizes deleting contestant from the episode
     */
    @FXML
    private void onActionDeleteContFromEpisode(ActionEvent event) {
        if (!cboEpisodesCont.getSelectionModel().isEmpty()) {
            String kilpailijaNimi = cboEpisodesCont.getSelectionModel().getSelectedItem().getDrag_Nimi();
            String kausiNimi = cboContEpisodeEpisode.getSelectionModel().getSelectedItem().getJaksonNimi();
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert confirm = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete " + kilpailijaNimi + " from episode "
                    + kausiNimi + "?", ok, no);
            confirm.setTitle("Deleting contestant from episode");
            Optional<ButtonType> choice = confirm.showAndWait();

            if (choice.orElse(no) == ok) {
                deleteContFromEpisode();
                cboEpisodesCont.setValue(null);
                cboContEpisodeEpisode.setValue(null);
            }
        }
    }

    /**
     * Method gets TuomariID and kausiID from the comboboxes and deletes the
     * connection from the database
     */
    private void deleteHostFromSeason() {
        String tuomID = cboSeasonHost.getSelectionModel().getSelectedItem().getTuomariID();
        String kausiID = cboHostSeasonSeason.getSelectionModel().getSelectedItem().getKausiID();
        ddb.delHostFromSeason(conn, tuomID, kausiID);
    }

    /**
     * Method finalizes deleting host from the season
     */

    @FXML
    private void onActionDeleteHostFromSeason(ActionEvent event) {
        if (!cboSeasonHost.getSelectionModel().isEmpty()) {
            String tuomENimi = cboSeasonHost.getSelectionModel().getSelectedItem().getEtunimi();
            String tuomSNimi = cboSeasonHost.getSelectionModel().getSelectedItem().getSukunimi();
            String kausiNimi = cboHostSeasonSeason.getSelectionModel().getSelectedItem().getKausiNimi();
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert confirm = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete " + tuomENimi + " " + tuomSNimi + " from season "
                    + kausiNimi + "? \n\nThe contestant will be removed also from all the episodes on the season.", ok, no);
            confirm.setTitle("Deleting contestant from episode");
            Optional<ButtonType> choice = confirm.showAndWait();

            if (choice.orElse(no) == ok) {
                deleteContFromEpisode();
                cboSeasonHost.setValue(null);
                cboHostSeasonSeason.setValue(null);
            }
        }
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
            AnchorPane scene1 = FXMLLoader.load(getClass().getResource("DragFXMLDocument.fxml"));
            scene2.getChildren().removeAll();
            scene2.getChildren().setAll(scene1);

        } catch (IOException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
