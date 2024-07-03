package it.polito.tdp.baseball;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.baseball.model.Model;
import it.polito.tdp.baseball.model.People;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnConnesse;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnDreamTeam;

    @FXML
    private Button btnGradoMassimo;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtYear;

    
    
    @FXML
    void doCalcolaConnesse(ActionEvent event) {
    	
    	txtResult.appendText("Le componenti connesse sono: "+ model.getConnesse());
    	
    }

    
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	model.clearGraph();
    	txtResult.clear();
    	btnConnesse.setDisable(false);
    	btnGradoMassimo.setDisable(false);
    	btnDreamTeam.setDisable(false);
    	String input1 =txtYear.getText();
    	String input2 = txtSalary.getText();
    	int anno = 0;
    	double salario =0;
    	if (input1 == null || input2 == null ) {
    		txtResult.setText("Inserisci un anno e un salario minimo");
    		return;
    	}
    	
    	try {
    		anno = Integer.parseInt(input1);
    		salario = Double.parseDouble(input2);
    		
    	}catch(NumberFormatException e ) {
    		txtResult.setText("Il salrio e l'anno devono essere numeri interi");
    	}
    	
    	model.creaGrafo(anno, salario);
    	txtResult.appendText("Grafo creato: "+ "\n vertici: " + model.loadVertici(salario, anno).size()+ "\n archi:  "+ model.getArchi()+"\n\n");
    	
    }
    
   



	@FXML
    void doDreamTeam(ActionEvent event) {
		String input1 =txtYear.getText();
    
		int anno = 0;
    
    	if (input1 == null) {
    		txtResult.setText("Inserisci un anno");
    		return;
    	}
    	
    	try {
    		anno = Integer.parseInt(input1);
    		
    		
    	}catch(NumberFormatException e ) {
    		txtResult.setText("Il salrio e l'anno devono essere numeri interi");
    	}
    	txtResult.appendText("DreamTeam");
		List<People> team = model.getDreamTeam(anno);
		txtResult.appendText("Il dream team avrà un costo di: " +model.getSalarioTot(team, anno));
    	

    }

    
    @FXML
    void doGradoMassimo(ActionEvent event) {
    	txtResult.appendText("Il vertice con grado massimo è : \n"+model.getGradoMax().getP()+"\n");
    	txtResult.appendText("Grado: " +model.getGradoMax().getGrado()+"\n");
    
    }

    
    @FXML
    void initialize() {
        assert btnConnesse != null : "fx:id=\"btnConnesse\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGradoMassimo != null : "fx:id=\"btnGradoMassimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSalary != null : "fx:id=\"txtSalary\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYear != null : "fx:id=\"txtYear\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
