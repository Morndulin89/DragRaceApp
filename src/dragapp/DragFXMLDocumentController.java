package dragapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Class is the main controllor for the application
 *
 * @version 2.01 5.5.2022
 * @author Nanuli M.
 */
public class DragFXMLDocumentController implements Initializable {

    // * * * * * * * * * * * * * * * * * * * *
    // Contestant components: //
    // * * * * * * * * * * * * * * * * * * * *
    @FXML
    private TableView<Kilpailija> tblViewContestants;
    @FXML
    private TableColumn<Kilpailija, String> tblViewColumnContFname;
    @FXML
    private TableColumn<Kilpailija, String> tblViewColumnContLname;
    @FXML
    private TableColumn<Kilpailija, String> tblViewColumnContDname;
    @FXML
    private Button btnContNew;
    @FXML
    private Button btnContNewSave;
    @FXML
    private Label lblWins;
    @FXML
    private Label lblSeasons;
    @FXML
    private Button btnContEdit;
    @FXML
    private Button btnContDelete;
    @FXML
    private ImageView imgViewCont;
    @FXML
    private TextField txtContFName;
    @FXML
    private TextField txtContSName;
    @FXML
    private TextField txtContDName;

    @FXML
    private TextArea txtContSeasons;
    @FXML
    private TextArea txtContWins;

    // * * * * * * * * * * * * * * * * * * * *
    // Host components:                     //
    // * * * * * * * * * * * * * * * * * * * *
    @FXML
    private Button btnHostSaveNew;
    @FXML
    private Button btnHostNewSave;
    @FXML
    private Button btnHostEdit;
    @FXML
    private Button btnHostDelete;
    @FXML
    private Label lblHostSeasons;
    @FXML
    private TableView<Tuomari> tblViewHosts;
    @FXML
    private TableColumn<Tuomari, String> tblViewHostFName;
    @FXML
    private TableColumn<Tuomari, String> tblViewHostLName;
    @FXML
    private TextField txtHostFName;
    @FXML
    private TextField txtHostSName;
    @FXML
    private TextArea txtHostSeasons;
    // * * * * * * * * * * * * * * * * * * * *
    // Season components:                    //
    // * * * * * * * * * * * * * * * * * * * *
    @FXML
    private AnchorPane scene1;
    @FXML
    private ComboBox<Kausi> cboSeasonPicker;
    @FXML
    private TableView<Jakso> tblViewSeasonEpisodes;
    @FXML
    private TableColumn<Jakso, String> tblColumnSeasonEpName;
    @FXML
    private TableColumn<Jakso, Date> tblColumnSeasonEpDate;
    @FXML
    private TableView<Kilpailija> tblViewSeasonCont;
    @FXML
    private TableColumn<Kausi, String> tblColumnSeasonContDName;
    @FXML
    private TableView<Tuomari> tblViewSeasonHost;
    @FXML
    private TableColumn<Tuomari, String> tblColumnSeasonHostFName;
    @FXML
    private TableColumn<Tuomari, String> tblColumnSeasonHostLName;
    @FXML
    private Button btnEditSeason;
    @FXML
    private Button btnSeasonDelete;
    @FXML
    private Button btnSeasonNew;

    // * * * * * * * * * * * * * * * * * * * *
    // Other important ingredients:         //
    // * * * * * * * * * * * * * * * * * * * *
    String connString = "jdbc:mariadb://localhost:3306?user=harjoitus&password=harjoitus";
//    String connString = "jdbc:mariadb://maria.westeurope.cloudapp.azure.com:3306?user=opiskelija&password=opiskelija1";

    Connection conn;

    Jakso jakso;

    private DragDatabase ddb = new DragDatabase();
    private CreateDDB cddb = new CreateDDB();
    public ObservableList<Kilpailija> kilpailijat;
    public ObservableList<KilpailijanKausi> kilpKaudet;
    public ObservableList<KilpailijaJaksot> kilpJaksot;
    public ObservableList<Tuomari> tuomarit;
    public ObservableList<Kausi> kaudet;
    public ObservableList<TuomarinKausi> tuomKaudet;
    public ObservableList<String> kausiEp;
    public ObservableList<Jakso> jaksot;

    private ArrayList<String> images = new ArrayList<>();

    // * * * * * * * * * * * * * * * * * * * *
    // INITIALIZE:                           //
    // * * * * * * * * * * * * * * * * * * * *
    /**
     * Initializes the class
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /*Tämä toimii PERIAATTEESSA myös pilviversiossa, mutta sceneä vaihtaessa kaikki nollautuu, eli */
        /*uudet kaudet, jaksot ja niihin yhdistetyt ihmiset ylikirjoitetaan... */

//        try {
//
//            conn = cddb.openConnection(connString);
//            cddb.createDB(conn);
//        } catch (SQLException ex) {
//            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        try {
            conn = ddb.openConnection(connString);
        } catch (SQLException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // * * * * * * * * * * * * * * * * * * * *
        // POPULATING THE OBS-LISTS:            //
        // * * * * * * * * * * * * * * * * * * * *
        kilpKaudet = ddb.getKilpailijanKaudet(conn);
        kilpJaksot = ddb.getKilpailijanJaksot(conn);
        tuomKaudet = ddb.getTuomarinKaudet(conn);
        kaudet = ddb.getKausi(conn);
        kausiEp = ddb.getEpisodes(conn);
        jaksot = ddb.getJakso(conn);

        /*These methods empty the tableviews*/
 /*Important for switching between scenes*/
        tblViewSeasonEpisodes.getItems().clear();
        tblViewSeasonHost.getItems().clear();
        tblViewSeasonCont.getItems().clear();

        /*These methods create the tableviews*/
        createTblViewContestants();
        createTblViewHosts();
        createTblViewsSeason();

        /*Populates the cboSeasonPicker*/
        for (Kausi kausi : kaudet) {
            cboSeasonPicker.getItems().add(kausi);
        }
        /*Creates arraylist of the contestant names*/
 /*Used with the imageview of the contestants*/
        images = imageArray();

        /*Makes the tableview cells editable*/
        editableCells();

        /*Changes the original prompt text for the season tableviews*/
        tblViewSeasonEpisodes.setPlaceholder(new Label("Pick a season!"));
        tblViewSeasonCont.setPlaceholder(new Label("Pick a season!"));
        tblViewSeasonHost.setPlaceholder(new Label("Pick a season!"));

    }

    // * * * * * * * * * * * * * * * * * * * *
    // MENUBAR:                              //
    // * * * * * * * * * * * * * * * * * * * *
    private static void closeConnection(Connection c) throws SQLException {
        if (c != null) {
            c.close();
        }
        System.out.println("\t>> Tietokantayhteys suljettu.");
    }

    /**
     * Method closes the main screen and the connection to the database
     */
    @FXML
    private void onActionMenuItemClose(ActionEvent event) {

        try {
            closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Platform.exit();
        System.exit(0);
    }

    /**
     * Opens an information alert about the application
     */
    @FXML
    private void onActionMenuItemAboutApp(ActionEvent event) {
        Alert about = new Alert(Alert.AlertType.INFORMATION, "(c) The app: Nanu"
                + "\n(c) Everything else: World of Wonders and all the artists", ButtonType.CLOSE);
        about.setTitle("About");
        about.setHeaderText("The Highly Unofficial Fan-App v1.0 "
                + "\n All images and information is copyrighted to their respective owners.");
        about.show();
    }

    /**
     * Opens an information alert about the application
     */
    @FXML
    private void onActionMenuItemAboutRace(ActionEvent event) {
        Alert about = new Alert(Alert.AlertType.INFORMATION, "(c) The app: Nanu"
                + "\n(c) Everything else: World of Wonders and all the artists", ButtonType.CLOSE);
        about.setTitle("About Drag Race");
        about.setHeaderText("RuPaul's Drag Race is an American reality competition television series, that started in 2009. \n"
                + "The show has become an international phenomenon and a beacon for the whole LGBTQ-community. \n"
                + "So far there are 14 seasons and multiple spin-offs. ");
        about.show();
    }

    // * * * * * * * * * * * * * * * * * * * *
    // CONTESTANT TAB:                      //
    // * * * * * * * * * * * * * * * * * * * *
    /**
     * Every time user clicks on the contestant on the tableview, their
     * information is presented on the right side
     */
    @FXML
    private void onActionTblViewContestants(MouseEvent event) {
        tblViewContestants.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (tblViewContestants.getSelectionModel().getSelectedItem() != null) {
                    Kilpailija kilpailija = tblViewContestants.getSelectionModel().getSelectedItem();

                    txtContFName.setText(kilpailija.Etunimi);
                    txtContSName.setText(kilpailija.Sukunimi);
                    txtContDName.setText(kilpailija.Drag_Nimi);

                    ArrayList<String> newKaudet = new ArrayList<>();
                    ArrayList<String> newVoitot = new ArrayList<>();
                    String kausiNimi;
                    String jaksoNimi;

                    for (KilpailijanKausi kilpkausi : kilpKaudet) {
                        for (Kausi kausi : kaudet) {
                            if (kilpailija.getKilpailijaID().equals(kilpkausi.getKilpailijaID()) && kilpkausi.getKausiID().equals(kausi.getKausiID())) {
                                kausiNimi = kausi.toString();
                                newKaudet.add(kausiNimi);
                            }
                        }
                    }
                    String sepList = String.join("\n", newKaudet);
                    txtContSeasons.setText(sepList);

                    for (KilpailijaJaksot kilpJakso : kilpJaksot) {
                        for (Jakso jakso : jaksot) {
                            if (kilpailija.getKilpailijaID().equals(kilpJakso.getKilpailijaID()) && kilpJakso.getJaksoID().equals(jakso.getJaksoID())) {
                                jaksoNimi = jakso.toString();
                                newVoitot.add(jaksoNimi);
                            }
                        }
                    }
                    String sepList2 = String.join("\n", newVoitot);
                    txtContWins.setText(sepList2);

                    try {
                        Image imageObject = new Image("Images/defaultIMG.png");

                        for (int i = 0; i < images.size() - 1; i++) {
                            if (images.get(i).contains(kilpailija.getKilpailijaID())) {
                                String imageName = images.get(i);
                                String url = "Images/" + imageName;
                                imageObject = new Image(url);
                                imgViewCont.setImage(imageObject);
                            } else {
                                imgViewCont.setImage(imageObject);
                            }
                        }//end for

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                }
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    /**
     * Clears the textareas and sets the "add new"-button visible
     */
    @FXML
    private void onActionBtnContNew(ActionEvent event) {

        lblWins.setVisible(false);
        lblSeasons.setVisible(false);
        txtContSeasons.setVisible(false);
        txtContWins.setVisible(false);
        btnContNewSave.setVisible(true);
        txtContFName.setText("");
        txtContSName.setText("");
        txtContDName.setText("");

    }

    /**
     * Finalizes adding the new contestant to the database and adds the
     * contestant to the tableview
     */
    @FXML
    private void onActionBtnContNewSave(ActionEvent event) {
        kilpSave();
        lblWins.setVisible(true);
        txtContSeasons.setVisible(true);
        txtContWins.setVisible(true);
        btnContNewSave.setVisible(false);
        txtContFName.setText("");
        txtContSName.setText("");
        txtContDName.setText("");
        tblViewContestants.getItems().clear();
        createTblViewContestants();
    }

    /**
     * Adds the contestant to the database
     */
    private void kilpSave() {
        Kilpailija kilp = new Kilpailija();
        String etuNimi = String.valueOf(txtContFName.getCharacters());
        String sukuNimi = String.valueOf(txtContSName.getCharacters());
        String dragNimi = String.valueOf(txtContDName.getCharacters());
        String kilpaID = dragNimi;
        kilp.setKilpailijaID(kilpaID);
        kilp.setEtunimi(etuNimi);
        kilp.setSukunimi(sukuNimi);
        kilp.setDrag_Nimi(dragNimi);

        ddb.newKilpailija(conn, kilp);
        kilpailijat.add(kilp);

    }

    /**
     * Finalizes editing the contestant to the database and saves the edited
     * contestant's information to the tableview
     */
    @FXML
    private void onActionBtnContEdit(ActionEvent event) {

        if (!tblViewContestants.getSelectionModel().isEmpty()) {
            String contName = tblViewContestants.getSelectionModel().getSelectedItem().getDrag_Nimi();
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert confirm = new Alert(Alert.AlertType.NONE, "Are you sure you want to edit " + contName + "?", ok, no);
            confirm.setTitle("Editing contestant");
            Optional<ButtonType> choice = confirm.showAndWait();

            if (choice.orElse(no) == ok) {
                editContestant();
                tblViewContestants.getItems().clear();
                createTblViewContestants();
                tblViewContestants.refresh();
                txtContFName.setText("");
                txtContSName.setText("");
                txtContDName.setText("");
                txtContSeasons.setText("");
                txtContWins.setText("");
            }
        }
    }

    /**
     * Adds the contestant to the database
     */
    private void editContestant() {

        tblViewColumnContFname.setOnEditCommit(
                e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setEtunimi(e.getNewValue()));
        tblViewColumnContLname.setOnEditCommit(
                e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setSukunimi(e.getNewValue()));
        tblViewColumnContDname.setOnEditCommit(
                e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setDrag_Nimi(e.getNewValue()));

        Kilpailija kilpailija = tblViewContestants.getSelectionModel().getSelectedItem();
        String kilpID = kilpailija.getKilpailijaID();
        String etunimi = kilpailija.getEtunimi();
        String sukunimi = kilpailija.getSukunimi();
        String dragNimi = kilpailija.getDrag_Nimi();
        ddb.editKilpailija(conn, kilpID, etunimi, sukunimi, dragNimi);
    }

    /**
     * Finalizes deleting the contestant to the database and deletes the
     * contestant's information from the tableview
     */
    @FXML
    private void onActionBtnContDelete(ActionEvent event) {

        if (!tblViewContestants.getSelectionModel().isEmpty()) {
            String contName = tblViewContestants.getSelectionModel().getSelectedItem().getDrag_Nimi();
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert confirm = new Alert(Alert.AlertType.NONE, "Are you sure you want to remove " + contName + "?", ok, no);
            confirm.setTitle("Deleting contestant");
            Optional<ButtonType> choice = confirm.showAndWait();

            if (choice.orElse(no) == ok) {
                deleteContestant();
                kilpKaudet = ddb.getKilpailijanKaudet(conn);
                kilpJaksot = ddb.getKilpailijanJaksot(conn);
                tblViewContestants.getItems().clear();
                createTblViewContestants();
                tblViewContestants.refresh();
                txtContFName.setText("");
                txtContSName.setText("");
                txtContDName.setText("");
                txtContSeasons.setText("");
                txtContWins.setText("");
            }
        }

    }

    /**
     * Deletes the contestant from the database
     */
    private void deleteContestant() {
        Kilpailija kilpailija = tblViewContestants.getSelectionModel().getSelectedItem();
        String kilpailijaID = kilpailija.getKilpailijaID();

        ddb.delCont(conn, kilpailijaID);
        ddb.delContFromAllSeasons(conn, kilpailijaID);
        ddb.delContFromAllEpisodes(conn, kilpailijaID);
        kilpKaudet.clear();
        kilpJaksot.clear();

    }

    // * * * * * * * * * * * * * * * * * * * *
    // HOST TAB:                            //
    // * * * * * * * * * * * * * * * * * * * *
    /**
     * Every time user clicks on the contestant on the tableview, their
     * information is presented on the right side
     */
    @FXML
    private void onActionTblViewHosts(MouseEvent event) {
        tblViewHosts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (tblViewHosts.getSelectionModel().getSelectedItem() != null) {
                    Tuomari tuomari = tblViewHosts.getSelectionModel().getSelectedItem();
                    txtHostFName.setText(tuomari.etunimi);
                    txtHostSName.setText(tuomari.sukunimi);

                    ArrayList<String> newKaudet = new ArrayList<>();
                    String kaus;

                    for (TuomarinKausi tuomKausi : tuomKaudet) {
                        for (Kausi kausi : kaudet) {
                            if (tuomari.getTuomariID().equals(tuomKausi.getTuomariID()) && tuomKausi.getKausiID().equals(kausi.getKausiID())) {
                                kaus = kausi.toString();
                                newKaudet.add(kaus);
                            }
                        }
                    }
                    String sepList = String.join("\n", newKaudet);
                    txtHostSeasons.setText(sepList);
                } else {
                }
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    /**
     * Clears the textareas and sets the "add new"-button visible
     */
    @FXML
    private void onActionBtnHostNew(ActionEvent event) {
        txtHostSeasons.setVisible(false);
        btnHostNewSave.setVisible(true);
        lblHostSeasons.setVisible(false);
        txtHostFName.setText("");
        txtHostSName.setText("");
        txtHostSeasons.setText("");
    }

    /**
     * Finalizes adding the new host to the database and adds the host to the
     * tableview
     */
    @FXML
    private void onActionBtnHostNewSave(ActionEvent event) {
        tuomSave();
        txtHostSeasons.setVisible(true);
        btnHostNewSave.setVisible(false);
        lblHostSeasons.setVisible(true);
        txtHostFName.setText("");
        txtHostSName.setText("");

        tblViewHosts.getItems().clear();
        createTblViewHosts();
    }

    /**
     * Adds new host to the database and adds the host to the tableview
     */
    private void tuomSave() {

        Tuomari tuom = new Tuomari();

        String etunimi = String.valueOf(txtHostFName.getCharacters());
        String sukunimi = String.valueOf(txtHostSName.getCharacters());

        String tuomID = sukunimi + 1;

        ddb.newHost(conn, tuomID, etunimi, sukunimi);
        tuomarit.add(tuom);

    }

    /**
     * Finalizes editing the host to the database and edits the contestant's
     * information in the tableview
     */
    @FXML
    private void onActionBtnHostEdit(ActionEvent event) {
        if (!tblViewHosts.getSelectionModel().isEmpty()) {
            String hostfName = tblViewHosts.getSelectionModel().getSelectedItem().getEtunimi();
            String hostlName = tblViewHosts.getSelectionModel().getSelectedItem().getSukunimi();
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert confirm = new Alert(Alert.AlertType.NONE, "Are you sure you want to edit " + hostfName + " " + hostlName + "?", ok, no);
            confirm.setTitle("Editing host");
            Optional<ButtonType> choice = confirm.showAndWait();

            if (choice.orElse(no) == ok) {
                editHost();
                tblViewHosts.getItems().clear();
                createTblViewHosts();
                tblViewHosts.refresh();
                txtHostFName.setText("");
                txtHostSName.setText("");
                txtHostSeasons.setText("");
            }
        }
    }

    /**
     * Edits host's information in the database and edits the hosts info in the
     * tableview
     */
    private void editHost() {
        tblViewHostFName.setOnEditCommit(
                e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setEtunimi(e.getNewValue()));
        tblViewHostLName.setOnEditCommit(
                e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setSukunimi(e.getNewValue()));

        Tuomari tuom = tblViewHosts.getSelectionModel().getSelectedItem();
        String tuomID = tuom.getTuomariID();
        String etunimi = tuom.getEtunimi();
        String sukunimi = tuom.getSukunimi();

        ddb.editHost(conn, tuomID, etunimi, sukunimi);
    }

    /**
     * Finalizes deleting the host from the database and deletes the host from
     * the tableview
     */
    @FXML
    private void onActionBtnHostDelete(ActionEvent event) {
        if (!tblViewHosts.getSelectionModel().isEmpty()) {
            String hostfName = tblViewHosts.getSelectionModel().getSelectedItem().getEtunimi();
            String hostlName = tblViewHosts.getSelectionModel().getSelectedItem().getSukunimi();
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert confirm = new Alert(Alert.AlertType.NONE, "Are you sure you want to delete " + hostfName + " " + hostlName + "?", ok, no);
            confirm.setTitle("Deleting host");
            Optional<ButtonType> choice = confirm.showAndWait();

            if (choice.orElse(no) == ok) {
                deleteHost();
                tblViewHosts.getItems().clear();
                createTblViewHosts();
                tblViewHosts.refresh();
                txtHostFName.setText("");
                txtHostSName.setText("");
                txtHostSeasons.setText("");
            }
        }
    }

    /**
     * Deletes the host from the database
     */
    private void deleteHost() {
        Tuomari tuom = tblViewHosts.getSelectionModel().getSelectedItem();
        String tuomariID = tuom.getTuomariID();

        ddb.delHost(conn, tuomariID);
    }

    // * * * * * * * * * * * * * * * * * * * *
    // SEASON TAB:                            //
    // * * * * * * * * * * * * * * * * * * * *
    /**
     * Method closes the current scene, clears the cache and opens a new scene
     * for editing the season, episodes and the connections between the
     * participants
     */
    @FXML
    private void onActionBtnSeasonEdit(ActionEvent event) {
        kilpKaudet.clear();
        kilpJaksot.clear();
        tuomKaudet.clear();
        kaudet.clear();
        kausiEp.clear();
        jaksot.clear();

        try {
            closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditSeasonFXML.fxml"));
            AnchorPane scene2 = loader.load();

            scene1.getChildren().removeAll();
            scene1.getChildren().setAll(scene2);

        } catch (IOException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method closes the current scene, clears the cache and opens a new scene
     * for deleting participants from seasons and episodes
     */
    @FXML
    private void onActionBtnSeasonDelete(ActionEvent event) {
        kilpKaudet.clear();
        kilpJaksot.clear();
        tuomKaudet.clear();
        kaudet.clear();
        kausiEp.clear();
        jaksot.clear();

        try {
            closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteSeasonStuff.fxml"));
            AnchorPane scene2 = loader.load();

            scene1.getChildren().removeAll();
            scene1.getChildren().setAll(scene2);

        } catch (IOException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method closes the current scene, clears the cache and opens a new scene
     * for creating new seasons, episodes and the connections between the
     * participants
     */
    @FXML
    private void onActionBtnSeasonNew(ActionEvent event) {
        kilpKaudet.clear();
        kilpJaksot.clear();
        tuomKaudet.clear();
        kaudet.clear();
        kausiEp.clear();
        jaksot.clear();

        try {
            closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewSeasonFXML.fxml"));
            AnchorPane scene2 = loader.load();

            scene1.getChildren().removeAll();
            scene1.getChildren().setAll(scene2);

        } catch (IOException ex) {
            Logger.getLogger(DragFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // * * * * * * * * * * * * * * * * * * * *
    // Other methods:                       //
    // * * * * * * * * * * * * * * * * * * * *
    /**
     * Creates the tableview of the contestants
     */
    private void createTblViewContestants() {

        tblViewColumnContFname.setCellValueFactory(new PropertyValueFactory<>("Etunimi"));
        tblViewColumnContLname.setCellValueFactory(new PropertyValueFactory<>("Sukunimi"));
        tblViewColumnContDname.setCellValueFactory(new PropertyValueFactory<>("Drag_Nimi"));
        kilpailijat = ddb.getKilpailija(conn);
        tblViewContestants.setItems(kilpailijat);
    }

    /**
     * Creates the tableview of the hosts
     */
    private void createTblViewHosts() {
        tblViewHostFName.setCellValueFactory(new PropertyValueFactory<>("etunimi"));
        tblViewHostLName.setCellValueFactory(new PropertyValueFactory<>("sukunimi"));
        tuomarit = ddb.getTuomari(conn);
        tblViewHosts.setItems(tuomarit);
    }

    /**
     * Creates the tableviews based on the chosen season
     */
    private void createTblViewsSeason() {
        ObservableList<Jakso> seasonEpisodes = FXCollections.observableArrayList();
        ObservableList<Kilpailija> seasonContestants = FXCollections.observableArrayList();
        ObservableList<Tuomari> seasonHosts = FXCollections.observableArrayList();

        cboSeasonPicker.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tblViewSeasonEpisodes.getItems().clear();
                tblViewSeasonCont.getItems().clear();
                tblViewSeasonHost.getItems().clear();
                for (Jakso jakso : jaksot) {
                    if (jakso.getKausiID().equals(cboSeasonPicker.getSelectionModel().getSelectedItem().getKausiID())) {
                        seasonEpisodes.add(jakso);
                    }
                }
                for (KilpailijanKausi kilpKausi : kilpKaudet) {
                    for (Kilpailija kilpailija : kilpailijat) {
                        if (kilpKausi.getKausiID().equals(cboSeasonPicker.getSelectionModel().getSelectedItem().getKausiID())
                                && kilpKausi.getKilpailijaID().equals(kilpailija.getKilpailijaID())) {
                            seasonContestants.add(kilpailija);
                        }
                    }
                }
                for (TuomarinKausi tuomKausi : tuomKaudet) {
                    for (Tuomari tuomari : tuomarit) {
                        if (tuomKausi.getKausiID().equals(cboSeasonPicker.getSelectionModel().getSelectedItem().getKausiID())
                                && tuomKausi.getTuomariID().equals(tuomari.getTuomariID())) {
                            seasonHosts.add(tuomari);
                        }
                    }
                }
            }
        });
        tblColumnSeasonEpName.setCellValueFactory(new PropertyValueFactory<>("jaksonNimi"));
        tblColumnSeasonEpDate.setCellValueFactory(new PropertyValueFactory<>("ilmPvm"));
        tblViewSeasonEpisodes.setItems(seasonEpisodes);
        tblColumnSeasonContDName.setCellValueFactory(new PropertyValueFactory<>("Drag_Nimi"));
        tblViewSeasonCont.setItems(seasonContestants);
        tblColumnSeasonHostFName.setCellValueFactory(new PropertyValueFactory<>("etunimi"));
        tblColumnSeasonHostLName.setCellValueFactory(new PropertyValueFactory<>("sukunimi"));
        tblViewSeasonHost.setItems(seasonHosts);
    }

    /**
     * Makes the necessary cells in the tableviews editable
     */
    private void editableCells() {
        tblViewContestants.setEditable(true);
        tblViewColumnContFname.setCellFactory(TextFieldTableCell.forTableColumn());
        tblViewColumnContLname.setCellFactory(TextFieldTableCell.forTableColumn());
        tblViewColumnContDname.setCellFactory(TextFieldTableCell.forTableColumn());

        tblViewHosts.setEditable(true);
        tblViewHostFName.setCellFactory(TextFieldTableCell.forTableColumn());
        tblViewHostLName.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    /**
     * Creates a String arraylist based on the contestants in the ObservableList
     */
    private ArrayList<String> imageArray() {

        ArrayList<String> images = new ArrayList<>();
        String imageName;
        for (Kilpailija kilpailija : kilpailijat) {
            imageName = kilpailija.getKilpailijaID() + ".png";
            images.add(imageName);
        }
        return images;
    }

}//end Class
