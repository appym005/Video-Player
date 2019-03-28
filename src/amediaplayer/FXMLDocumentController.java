/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amediaplayer;


import java.io.File;
import java.net.URL;

import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

/**
 *
 * @author Apoorv
 */
public class FXMLDocumentController implements Initializable {
   
    private MediaPlayer mp;
    
    
    @FXML
    private MediaView mediaView;
    
   @FXML
   private Slider volumeslider;
   @FXML
   private Slider seeker;
    
    
    private String filePath;
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Choose a file", "*.mp4","*.mp3","*.mkv");
        fc.getExtensionFilters().add(filter);
        File file = fc.showOpenDialog(null);
        filePath = file.toURI().toString();
       
        Media media;
        if(filePath != null)
        {
        media = new Media(filePath);
        mp = new MediaPlayer(media);
       mediaView.setMediaPlayer(mp);
        
        DoubleProperty width =mediaView.fitWidthProperty();
        DoubleProperty height =mediaView.fitHeightProperty();
        
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
        mediaView.setPreserveRatio(true);
        
        
        
        volumeslider.setValue(mp.getVolume() * 100);
        volumeslider.valueProperty().addListener(new InvalidationListener(){
            @Override
            public void invalidated(Observable observable) {
               mp.setVolume(volumeslider.getValue()/100);
               
            }
        });
        
        mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
           
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
              seeker.setValue(newValue.toSeconds());
            }
        });
        seeker.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
             mp.seek(Duration.seconds(seeker.getValue()));
            }
        });
        
      
        mp.play();
  
        }    }
    
    @FXML 
    private void pauseVideo(ActionEvent event){
    mp.pause();}
    @FXML
    private void playVideo(ActionEvent event){
    mp.play();
    mp.setRate(1.0);}
    @FXML
    private void stopVideo(ActionEvent event){
    mp.stop();}
    @FXML
    private void fastVideo(ActionEvent event){
    mp.setRate(1.5);}
    @FXML
    private void fasterVideo(ActionEvent event){
    mp.setRate(2.0);}
    @FXML
    private void slowVideo(ActionEvent event){
    mp.setRate(0.5);}
    @FXML
    private void slowerVideo(ActionEvent event){
    mp.setRate(0.25);}
    @FXML
    private void exitVideo(ActionEvent event){
    System.exit(0);}
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
