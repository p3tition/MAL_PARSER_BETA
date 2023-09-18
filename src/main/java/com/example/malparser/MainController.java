package com.example.malparser;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainController {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button btMin;
    @FXML
    private Button btMax;
    @FXML
    private Button btClose;
    @FXML
    private Pane topPane;
    @FXML
    private Label titleLabel;
    @FXML
    private AnchorPane windowButtons;
    @FXML
    private VBox paneAnime;
    @FXML
    private VBox paneManga;
    @FXML
    private VBox paneCharacters;
    @FXML
    private VBox paneGenres;
    @FXML
    private VBox panePeople;
    @FXML
    private VBox paneProducers;
    @FXML
    private VBox paneSettings;
    @FXML
    private VBox paneAbout;

    @FXML
    public void initialize() {
        hideAllPanesExcept(paneAnime);
    }
    @FXML
    protected void handleCloseAction(ActionEvent event) {
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleMinAction(ActionEvent event) {
        Stage stage = (Stage) btMin.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    protected  void handleClickAction(MouseEvent event){
        Stage stage = (Stage) topPane.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }
    @FXML
    protected void handleMovementAction(MouseEvent event){
        Stage stage = (Stage) topPane.getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    protected void handleMaximizeAction(ActionEvent event) {
        Stage stage = (Stage) btMax.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
            double X_width = stage.getWidth();
            titleLabel.setLayoutX((X_width-135)/2);
            windowButtons.setLayoutX(X_width-115);
            topPane.setLayoutX(X_width);
            btMax.setText("□");
        } else {
            stage.setMaximized(true);
            double X_width = stage.getWidth();
            titleLabel.setLayoutX((X_width-135)/2);
            windowButtons.setLayoutX(X_width-115);
            topPane.setLayoutX(X_width);
            btMax.setText("❐");
        }
        System.out.println("Width" + topPane.getLayoutX());
    }

//    #showPane1
private void hideAllPanesExcept(VBox paneToShow) {
    paneAnime.setVisible(false);
    paneManga.setVisible(false);
    paneCharacters.setVisible(false);
    paneGenres.setVisible(false);
    panePeople.setVisible(false);
    paneProducers.setVisible(false);
    paneSettings.setVisible(false);
    paneAbout.setVisible(false);
    paneToShow.setVisible(true);
}
    public void showPaneAnime() {
        hideAllPanesExcept(paneAnime);
    }

    public void showPaneManga() {
        hideAllPanesExcept(paneManga);
    }

    public void showPaneCharacters() {
        hideAllPanesExcept(paneCharacters);
    }
    public void showPaneGenres() {
        hideAllPanesExcept(paneGenres);
    }
    public void showPanePeople() {
        hideAllPanesExcept(panePeople);
    }
    public void showPaneProducers() {
        hideAllPanesExcept(paneProducers);
    }
    public void showPaneSettings() {
        hideAllPanesExcept(paneSettings);
    }

    public void showPaneAbout() {
        hideAllPanesExcept(paneAbout);
    }
}