package application;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.util.ArrayList;
import application.Copy;
import application.FileData;
import application.Generate;

public class Main extends Application
{
	
	public static ArrayList<String> pins = new ArrayList();
	public static FlowPane spt;
	
	private Label GenLabel;
	
	@Override
	public void start(Stage primaryStage)
	{
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(Main.class.getResource("Main.fxml"));
			Scene scene = new Scene(root,500,280);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.getIcons().add(new Image("rust-pin-logo-100x.png"));
			primaryStage.setTitle("Rust pin code generator");
			Button GenPinBtn = (Button) scene.lookup("#GenPinBtn");
			Button GenCopyBtn = (Button) scene.lookup("#GenCopyBtn");
			Button GenSaveBtn = (Button) scene.lookup("#GenSaveBtn");
			TextField GenTF = (TextField) scene.lookup("#GenTF");
			TextField GenNameTF = (TextField) scene.lookup("#GenNameTF");
			TabPane tabs = (TabPane) scene.lookup("#tabs");
			GenLabel = (Label) scene.lookup("#GenLabel");
			spt = (FlowPane) scene.lookup("#spt");
			
			int screenWidth = (int) Screen.getPrimary().getVisualBounds().getMaxX() - 200;
			
			tabs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				@Override
				public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					int stageWidth = (int) primaryStage.getWidth();    	
				    if(stageWidth < screenWidth){
				       if(!t.getId().equals("SavedTab")){
				    	   primaryStage.setHeight(500);
				       }else{
				    	   primaryStage.setHeight(280);
				       }
				    }
				}
			});
			
			GenPinBtn.setOnAction(new EventHandler <ActionEvent>(){
				@Override 
				public void handle(ActionEvent event) {
					GenTF.setText(Generate.genPin());
				}
			});
			
			GenSaveBtn.setOnAction(new EventHandler <ActionEvent>(){
				@Override 
				public void handle(ActionEvent event) {
					String GenName = GenNameTF.getText();
					String GenText = GenTF.getText();
						
					if(GenText.isEmpty() || GenText == null || GenName.isEmpty() || GenName == null){
						GenLabel.setText("You must generate and name your pin before saving");
					}else{
						try {
							FileData.addToFile(GenName+":"+GenText);
							GenLabel.setText("Pin code Saved!");
							GenNameTF.setText("");
							GenTF.setText("");
						} catch (IOException e) {
							System.out.println(e);
						}
					}
				}
			});
			
			GenCopyBtn.setOnAction(new EventHandler <ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					String GenText = GenTF.getText();
					if(!GenText.isEmpty() && GenText != null){
						Copy.initCopy(GenText);
						GenLabel.setText("Pin copied to clipboard!");
					}
				}
			});
			
			FileData.initFile();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public static void addNoPinsLabel()
	{
		Label anpl = new Label("When you save a pin code it will show up here!");
		anpl.getStyleClass().add("contentLabel");
		anpl.setId("anpl");
		anpl.setAlignment(Pos.CENTER);
		spt.getChildren().add(anpl);
	}
	
	public static void addPinItem(String title,String pass, int index)
	{
		try{
			if(spt.getChildren().get(0).getId().equals("anpl")){
				spt.getChildren().remove(0);
			}
		}catch(IndexOutOfBoundsException ex){
			System.out.println(ex);
		}
		
		FlowPane fp = new FlowPane();
		fp.getStyleClass().add("fp");
		fp.setId("element_"+index);
		Label titleEl = new Label(title);
		titleEl.getStyleClass().add("contentLabel");
		
		TextField pwordEl = new TextField(pass);
		pwordEl.setEditable(false);
		pwordEl.getStyleClass().add("contentPword");
		
		Button contentCopyEl = new Button("Copy");
		contentCopyEl.getStyleClass().add("btnPrimary");
		Label cpyInd = new Label("Copied to clipboard!");
		cpyInd.setVisible(false);
		contentCopyEl.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Copy.initCopy(pass);
				cpyInd.setVisible(true);
			}
		});
		
		Button contentDeleteEl = new Button("Delete");
		contentDeleteEl.getStyleClass().add("btnPrimary");
		contentDeleteEl.setOnAction(new EventHandler <ActionEvent>(){
			@Override 
			public void handle(ActionEvent event) {
				
			
				int len = spt.getChildren().size();
				for(int i = 0;i<len;i++){
					try{
						if(spt.getChildren().get(i).getId().equals("element_"+index)){
							System.out.println(i);
							spt.getChildren().remove(i);
							FileData.removeFromFile(i);
						}
					}catch(Exception ex){
						System.out.println(ex);
					}
				}
			}
		});
		
		HBox btnsContainer = new HBox();
		btnsContainer.getStyleClass().add("contentBtns");
		btnsContainer.getChildren().add(contentDeleteEl);
		btnsContainer.getChildren().add(contentCopyEl);
		btnsContainer.getChildren().add(cpyInd);
		fp.getChildren().add(titleEl);
		fp.getChildren().add(pwordEl);
		fp.getChildren().add(btnsContainer);
		
		spt.getChildren().add(fp);
	}
	
}
