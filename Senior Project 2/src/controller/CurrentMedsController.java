package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.AnnaMain;
import application.DBConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.CurMedModel;

public class CurrentMedsController {
	
	//code for testing
	String testCode = "1";
	Connection conn = AnnaMain.con;

    @FXML
    private TableView<CurMedModel> medicationTable;
    @FXML
    private TableColumn<CurMedModel, String> mName;
    @FXML
    private TableColumn<CurMedModel, String> mDosage;
    @FXML
    private TableColumn<CurMedModel, String> mDate;
    @FXML
    private TableColumn<CurMedModel, String> mDoc;
    ObservableList<CurMedModel> patientMeds = FXCollections.observableArrayList();
    
    
    public void initialize(){
    	grabMeds();
    }
    
    
    
    void grabMeds() {
    	
    	try {
		    	String medQ = "SELECT * FROM currentMeds WHERE patientCode = ?";
		    	PreparedStatement curMedPS = conn.prepareStatement(medQ);
		    	curMedPS.setString(1, testCode);
		    	ResultSet rs = curMedPS.executeQuery();
		    	
		    	
		    	CurMedModel tempMed;
		    	String medName;
		    	String medDate;
		    	String medDetails;
		    	String doc;
		    	String medDose;
		    	String purpose;
		    	
		    	
		    while(rs.next())
		    	{
		    		medName = rs.getString("medName");
		    		System.out.println(medName);
		    		medDate = rs.getString("prescribDate");
		    		medDetails = rs.getString("medDescript");
		    		doc = rs.getString("prescribDoc");
		    		medDose = rs.getString("medDosage");
		    		purpose = rs.getString("purpPresrcipt");
		    		
		    		tempMed = new CurMedModel(medName, medDate, medDetails, doc, medDose, purpose);
		    		patientMeds.add(tempMed);	
		    		System.out.println("grabbing med... " + tempMed);
		    	}
	    	}
    	catch (SQLException e) {
    		DBConfig.displayException(e);	
    		System.out.println("failed grab");
    	}
    	
    	
    	mName.setCellValueFactory(cellData -> cellData.getValue().getMedName());
    	mDosage.setCellValueFactory(cellData -> cellData.getValue().getMedDosage());
    	mDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
    	mDoc.setCellValueFactory(cellData -> cellData.getValue().getDoc());
    	medicationTable.setItems(patientMeds);

    	
    }
    
    

}
