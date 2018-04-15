package controller;

import java.net.URL;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class LoginHelpController {
	
	@FXML
    private AnchorPane content_view;
	
	@FXML
	private BorderPane bp;
	
	@FXML
	private AnchorPane ap;
	
	@FXML
	private GridPane grid;

    @FXML
    private MediaView media_view;
    
    @FXML
    private Label lblStatus;
    
    @FXML
    private Label lblDescription;
    
    @FXML
    private Label lblDisclaimer;
    
    @FXML
    private Label lblContact;
    
    @FXML
    private Label lblReturn;
    
    @FXML
    private Button btnMeds;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnPatient;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private JFXSlider timeSlider;
    
    Parent root;
    Stage stage;
    Scene scene;

    boolean end = false;
    
    URL mediaUrl = getClass().getResource("/application/moon_jellies.mp4");
    String mediaStringUrl = mediaUrl.toExternalForm();
    Media media = new Media(mediaStringUrl);
    final MediaPlayer player = new MediaPlayer(media);
    Duration time, curTime;
    
    public void initialize() {
		
    	System.out.println("*******LOGIN HELP*******");
    	media_view.setMediaPlayer(player);
    	timeSlider.setValue(0);
    	lblStatus.setStyle("-fx-background-image: url('/application/play.png');"
				 + "-fx-background-repeat: no-repeat;");
    	
    	lblReturn.setText("This video is a tutorial to help you navigate Forget Me Not:");
    	
    	lblDescription.setText("Forget Me Not is designed to help nonprofessional inhome caregivers keep track of Alzheimer's patient information.\n\n"
    			+ "Forget Me Not seeks to cut down on communication issues between multiple caregivers for one patient.\n\n"
    			+ "Forget Me Not is not limited to tracking just Alzheimer's. This system can be "
    			+ "used to track a patient with any illness, disease, disorder, or disability.");
    	
    	lblDisclaimer.setText("This system was built as a senior project. Therefore, errors and bugs exist in this system. "
    			+ "If you find an issue, feel free to report it to us.\n                                 Please note: This system is not HIPPA compliant.");
    	
    	lblContact.setText("If you need to get in touch with us for any reason. Please email us at:\n          gmail@gmail.com");
    	
    	player.setOnEndOfMedia(new Runnable() {
		    @Override
		    public void run() {
		        player.pause();
	    		lblStatus.setStyle("-fx-background-image: url('/application/replay.png');"
	    				 + "-fx-background-position: center;"
						 + "-fx-background-repeat: no-repeat;");
	    		
	    		end = true;
		        System.out.println("Status: " + player.getStatus());
		    }
    	});
    	
    }
    
    @FXML
	void returnMain(ActionEvent event) {
    	
		 try {
			stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
			scene = new Scene(root);
			stage.setScene(scene);
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
    	
    }
    
    @FXML
    void clickVideo(MouseEvent event) {
    	
    	if(end) {
    		player.stop();
    		player.play();
    		lblStatus.setStyle("-fx-background-image: url('/application/pause.png');"
					 + "-fx-background-repeat: no-repeat;");
    		
    		end = false;
    	} else if(player.getStatus() == Status.PLAYING) {
    		player.pause();
    		lblStatus.setStyle("-fx-background-image: url('/application/play.png');"
					 + "-fx-background-repeat: no-repeat;");
    	} else {
    		player.play();
    		lblStatus.setStyle("-fx-background-image: url('/application/pause.png');"
					 + "-fx-background-repeat: no-repeat;");
    	}
    	
    	System.out.println("Total time: " + player.getTotalDuration().toMinutes());
    	player.currentTimeProperty().addListener(new InvalidationListener() {
    		
    		public void invalidated(Observable ov) {
    			
    			if (!timeSlider.isValueChanging()) {
    				System.out.println("HIT");
    				System.out.println(player.currentTimeProperty().get().toMinutes());
    				timeSlider.setValue(player.currentTimeProperty().get().toMinutes());
    				
    			}
            }
        });
    	
    }
    
    @FXML
    void dragSlider(MouseEvent event) {
    	
    	timeSlider.valueProperty().addListener(new InvalidationListener() {
    	    public void invalidated(Observable ov) {
    	       if (timeSlider.isValueChanging()) {
    	    	   System.out.println("Value: " + timeSlider.getValue());
    	       
    	          player.seek(Duration.minutes(timeSlider.getValue()));
    	       }
    	    }
    	});
    }
    
    @FXML
    void medsVideo(ActionEvent event) {
    	
    }

    @FXML
    void patientVideo(ActionEvent event) {

    }
    

    @FXML
    void userVideo(ActionEvent event) {

    }
    
    @FXML
    void exitVideo(MouseEvent event) {
    	
    	//pauses video
    	Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.seconds(1),
    	        ae -> player.pause()));
    	timeline.play();
    	
    	//sets font size on video pause
    	Timeline tline = new Timeline(new KeyFrame(
    	        Duration.seconds(1),
    	        ae -> lblStatus.setStyle("-fx-background-image: url('/application/play.png');"
						 			   + "-fx-background-repeat: no-repeat;")));
    	tline.play();
  
    }

}
