package cst8284.quizMaster;


import java.io.EOFException;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;



import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuizMain extends Application {
	/**  @Copyright Dave Houtman 2016.  For use in Fall 2016 - CST8284 classes only */
	
	
	
	private ArrayList<QA> QAList;  
	/** Array of QA objects, read from file*/
	
	private final int MAX_QA_SIZE = 5;        
	/** Number of QA objects in array, assumed known*/
	
	private File f;
	/**This is to Handle QA file*/
	
	public Object getSplashPane;
	private static int currentQuestionNumber;  /** Current question number, zero-based */
	
	@Override
	public void start(Stage primaryStage) throws EOFException {	
	
	   // Display Splash Screen
	   
	   
	try {
		 primaryStage.setTitle("Quiz Master");
		 Scene scene;
		scene = new Scene(getSplashPane("Welcome to QuizMaster", primaryStage), 1024, 512);
		 primaryStage.setScene(scene);
	     primaryStage.show();	
	} catch (EOFException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    finally{
	    	System.out.print("hmmm");
	    }
	    }
	/**
	 * This method is used to load primaryStage(Splash Screen) of pixel 1024 and 512  
	 */
	
	
	
	
   private void displayQA(int q, ArrayList<QA> qAList2, Stage pStage){
	   Scene scene;                           /** This will hold current scene object*/
	   if (q < MAX_QA_SIZE){
		   /**
		    *  hold current scene object
		    */
		   QA thisQA = qAList2.get(q);
		   currentQuestionNumber = q;		  
		   /** This will load current question to static variable*/
		   
		   BorderPane bp = new BorderPane();  
		   /** This is use as BorderPane to organize GUI objects */
		   
		   BorderPane border = new BorderPane(); 
		   HBox s = new HBox();
			s.setStyle("-fx-font-size: 15pt; -fx-background-color: lightyellow;-fx-border-color: blue; -fx-border-width: 1px; -fx-padding: 10; -fx-spacing: 8;");
			s.setPadding(new Insets(120,120,120,120));
			s.setSpacing(15);
			s.setAlignment(Pos.CENTER_LEFT);
			
			s.getChildren().add(getSortMethod());
	
		   String qNum = new String((currentQuestionNumber + 1) + ". ");  
		   Text txtQuestion = new Text(qNum + thisQA.getQuestion() + "\n");
		   
		   /**
		    *  This will Instantiate AnswerPane object and load radio buttons/answers into VBox
		    */
		   
		   
		   AnswerPane ap = new AnswerPane(thisQA.getAnswers());
   		   VBox vbAnswerPane = ap.getAnswerPane();
		   
   		   
   		   /** This will configure buttons*/
		   Button btnCheckAnswer = new Button("Check Answer");
		   Button btnNextQuestion = new Button("Next Button");
		   configureButtons(btnCheckAnswer, btnNextQuestion, bp, border, ap, thisQA);
		   
		   /** When 'Next' button clicked, re-enter this code with next question indexed in first parameter*/
		   btnNextQuestion.setOnAction((ActionEvent e) -> displayQA(++currentQuestionNumber, qAList2, pStage));
		   
		   /** This will Instantiate and configure sub-Panes before loadings into BorderPane*/
		   
		   HBox hbCheckPane = new HBox(btnCheckAnswer);
		   VBox vbNextPane = new VBox(btnNextQuestion);
		   configurePanes(border ,bp, hbCheckPane, vbNextPane);
		   VBox vbQAPane = new VBox();
		   vbQAPane.getChildren().addAll(txtQuestion, vbAnswerPane, hbCheckPane);

		   /** 
		    * This will Load BorderPane with Center and Right Panes
		    * */
		  bp.setTop(s);
		   bp.setCenter(vbQAPane);
		   bp.setRight(vbNextPane);
		   scene = new Scene(bp, 1024, 512);                   
		  
		   /** 
		    * Its will  Display question in the scene
		    */
	   }
	   else 
	  	   scene = new Scene(getQAResults(QAList), 1024, 512 );
	   /** It will show show  result in new scene*/
	   pStage.setScene(scene);                                
   }
   
   private void configureButtons(Button btnCheck, Button btnNext,BorderPane border, BorderPane bp, AnswerPane ap, QA thisQuestion){
	   /** It will give the properties to the buttons*/
	   btnCheck.setDisable(false);                 
	   /** It will enable 'Check' and disable 'Next'*/
	   btnNext.setDisable(true);
	   
	   btnCheck.setAlignment(Pos.BOTTOM_RIGHT);
	   btnNext.setAlignment(Pos.BOTTOM_RIGHT);
	   
	   btnCheck.setOnAction((ActionEvent e) -> {   /** Check clicked; test for valid answer*/
	      int selectedAnswer = (ap.getButtonSelected());  /** Which button was selected?*/
		  if (selectedAnswer <= 0){                /** If invalid selection, show alert...*/
		     Alert alert = new Alert(AlertType.ERROR);
		     alert.setTitle("Missing Selection");
		     alert.setContentText("Please select an answer from the choices available");
		     alert.showAndWait();
		  }
		  else {                                   /**otherwise display result and explanation*/
			 thisQuestion.setResult(selectedAnswer == thisQuestion.getCorrectAnswerNumber());  /** set result: correct or not?*/
			 String rightWrong = thisQuestion.isCorrect()?"Right! ":"Wrong. ";
			 Text txtExplanation = new Text(rightWrong + thisQuestion.getExplanation());
			 
			 BorderPane.setMargin(txtExplanation, new Insets(0,0,100,100));  /** Explanation text formatting*/
			 bp.setBottom(txtExplanation);
			 
		     btnCheck.setDisable(true);            /** When 'Check' is Disable 'Next' is Enabled*/
		     btnNext.setDisable(false);
		  }
	   });
   }
   private void showMissingFileNameAlert(){
	  	  Alert alert = new Alert(AlertType.CONFIRMATION);
		  alert.setTitle("Missing File Name");
		  alert.setContentText("Press OK to exit program or Continue to re-enter file name");
		  Optional<ButtonType> response = alert.showAndWait();
		  if (response.isPresent() && response.get() == ButtonType.OK) {
	        
	         System.exit(0);
	      }    
	   }
   
   public void configurePanes(BorderPane border,BorderPane bp, HBox hbCheck, VBox vbNext){
	  // Handles spacing requirements for BorderPane
      VBox vbSpace = new VBox(); vbSpace.setPrefHeight(100); bp.setTop(vbSpace);
 	  HBox hbSpace = new HBox(); hbSpace.setPrefWidth(100); bp.setLeft(hbSpace);
 	  VBox vbBottom = new VBox(); vbSpace.setPrefHeight(150); bp.setBottom(vbBottom);
	  BorderPane.setMargin(vbBottom, new Insets(0,0,115,100));
	  vbNext.setAlignment(Pos.BOTTOM_RIGHT);
	  hbCheck.setAlignment(Pos.CENTER_RIGHT);
   }
	
   // Modified from: Prasad Saya, https://examples.javacodegeeks.com/desktop-java/
   // javafx/dialog-javafx/javafx-dialog-example/  Downloaded Nov. 20, 2016
   
 

	Pane getSplashPane(String str,Stage primaryStage) throws EOFException {
		
       Reflection reflection = new Reflection();
		   reflection.setFraction(1.0);  
		     Text t = new Text(str);                                                       /** This is used to add new text*/
		       t.setStyle("-fx-font: 40px Forte; -fx-stroke: BLACK; -fx-stroke-width: 1;");
		         t.setX(10.0);
		           t.setY(50.0);
		              t.setCache(true);
		                 t.setText("Welcome to QuizMaster");
		                    t.setFill(Color.GOLD);
		                       t.setFont(Font.font(null, FontWeight.BOLD, 40));
		                          t.setEffect(reflection);
		 /** 
		  * This Effect is used to add reflection with the text
		  */
		 
		

		BorderPane splashPane = new BorderPane();
		splashPane.setCenter(t);
	   
		   
		   FadeTransition fT = new FadeTransition(Duration.millis(3000), t);  
		   /**
		    * This is used to fade text while Animation
		    */
		              fT.setFromValue(1.0f);
		              fT.setToValue(0.3f);
		              fT.setCycleCount(2);
		              fT.setAutoReverse(true);
		       
		   TranslateTransition tt = new TranslateTransition(Duration.millis(2000), t);
		   /**
		    * This is used to move text in Y direction while Animation
		    * */
		              tt.setByY(20f);
		              tt.setCycleCount((int) 4f);
		              tt.setAutoReverse(true);
		        
		   ScaleTransition sT = new ScaleTransition(Duration.millis(2000), t);
		   /**
		    * This is used to zoom an text while animation
		    * */
		              sT.setToX(2f);
		              sT.setToY(2f);
		              sT.setCycleCount(2);
		              sT.setAutoReverse(true);
		        
		   ParallelTransition parallelTransition = new ParallelTransition(); /**This is used to combine all the three effects*/
		   parallelTransition.getChildren().addAll(
		                fT,
		                tt,
		                sT );
		       
		   parallelTransition.setCycleCount(Timeline.INDEFINITE);
		   parallelTransition.play();
		   
		   MenuBar menuBar = new MenuBar();
	         Menu fileMenu = new Menu("File");
			 MenuItem open= new MenuItem("Open");
		       fileMenu.getItems().add(open);
		            open.setOnAction(e->{
		            		while(true){
		            
		      	      f = FileUtils.getFileHandle(primaryStage);  
		    	      if (FileUtils.getFileName()=="") showMissingFileNameAlert();
		    	      else break;       }
		            	
		           
					QAList = FileUtils.getQAArrayList(FileUtils.getFileName(f));
					
					BorderPane s = new BorderPane();
					s.getChildren().add(getSortMethod());
		    	   displayQA(0, QAList, primaryStage);
		    	   });
		            
		            
			   MenuItem exit= new MenuItem("Exit");
			   fileMenu.getItems().add(exit);
		
			   exit.setOnAction(e -> System.exit(0));
			   
			                
			   splashPane.setTop(menuBar);
			   menuBar.getMenus().add(fileMenu);
		
		return splashPane;
		   
		  
           
			
	}
	
	public VBox getSortMethod() {
		
		ToggleGroup T = new ToggleGroup();
		


		RadioButton Button1 = new RadioButton("Sort by difficulty");
		Button1.setToggleGroup(T);
		Button1.setSelected(true);

		RadioButton Button2 = new RadioButton("Sort by author");
	    Button2.setToggleGroup(T);
		 
		RadioButton Button3 = new RadioButton("Sort by Points");
	    Button3.setToggleGroup(T);
		return null;
		
		
		 
		 
		   }
		
		 // sortPane.setPadding(new Insets(10, 20, 10, 20)); Button
		 // btnBottom = new Button("Sort");
		 // sortPane.setBottom(btnBottom);
		 

		//sortPane.setPadding(new Insets(10, 20, 10, 20));
		

		
	
	
	
	     
	       
	       
	   
	
	private BorderPane getQAResults(ArrayList<QA> QAlist){
		StringBuilder strResults = new StringBuilder();
		for (int question=0; question < QAlist.size(); question++){ /** loop through each QA object*/
			String str = "Q" + (question + 1) + (QAlist.get(question).isCorrect()?".  Correct":".  Wrong") + "\n";
			strResults.append(str);                       /**For each QA, indicate Question number and result*/
		}
		Text txtResults = new Text(strResults.toString());  /** Output results to BorderPane*/
		BorderPane.setAlignment(txtResults, Pos.CENTER);
		BorderPane QAResults = new BorderPane();
		QAResults.setCenter(txtResults);
		return QAResults;                                   /**and return pane for display*/
	}
	
	
	public static void main(String[] args){
		Application.launch(args);
	}
	class Sortbyauthor implements Comparator<QA> {
	    

		@Override
		public int compare(QA Ques1, QA Ques2) {
			return MAX_QA_SIZE;
		
		   
		}
	}





class Sortbydifficulty implements Comparator<QA> {
 
   
	@Override
	public int compare(QA o1, QA o2) {
		// TODO Auto-generated method stub
		
		return MAX_QA_SIZE;
	}
}

//compare
class Sortbypoints implements Comparator<QA> {
   

	@Override
	public int compare(QA o1, QA o2) {
		return MAX_QA_SIZE;
		
	}
}

	

public void s(Stage stage) {
  //  Parent scene = new Scene(scene);
    stage.setTitle("Table View Sample");
    stage.setWidth(300);
    stage.setHeight(500);

   
    
 

    final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));

   

  //  stage.setScene(scene);
    stage.show();
}}



