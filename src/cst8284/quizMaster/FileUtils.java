package cst8284.quizMaster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;



public class FileUtils {
	
	 private static String QuizFileAbsolutePathAndName="";
	
   public static File getFileHandle(Stage primaryStage)  {
	   
	   
	
	 FileChooser fc = new FileChooser();
	  fc.setTitle("Open Quiz File");
	  fc.getExtensionFilters().addAll(
	      new ExtensionFilter("Quiz Files", "*.quiz"),
	      new ExtensionFilter("All Files", "*.*"));
	  File quizFile = fc.showOpenDialog(primaryStage);
	  setFileName(quizFile);
	  return(quizFile);
   }
      
  
	      
	   
 
   /*public static QA[] getQAArray(String absPath, int numObjects) {
	      QA[] qaAr = new QA[numObjects];
		  try  {
	         FileInputStream fis = new FileInputStream(absPath);
	         ObjectInputStream ois = new ObjectInputStream(fis);
			 for (int i = 0; i < numObjects; i++)
	            qaAr[i] = (QA)(ois.readObject());
			 ois.close();
		  } catch (FileNotFoundException e) {
	           e.printStackTrace();
	       } catch (IOException e) {
	           e.printStackTrace();
	       } catch (ClassNotFoundException e) {
	           e.printStackTrace();
		    } catch (NullPointerException e) {
		        e.printStackTrace();
		    }
			return qaAr;
	   }*/
   
   
 
   
   
   public static ArrayList<QA> getQAArrayList(String absPath) {
       ObjectInputStream o;
       ArrayList<QA> QAList = new ArrayList<>();
       try {
           o = new ObjectInputStream(new FileInputStream(absPath));
           while(o.available() != -1)
           {
        	   QAList.add((QA)o.readObject());
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
	    } catch (NullPointerException e) {
	        e.printStackTrace();
	    }
       return QAList; }
   
   
   
   
    
   private static void setFileName(File f) {
	   QuizFileAbsolutePathAndName = (FileExists(f))? f.getAbsolutePath():"";
   }
   
   public static String getFileName(){
	   return QuizFileAbsolutePathAndName;
   }
   
   public static String getFileName(File f){
	   return f.getAbsolutePath();
   }
   
   private static Boolean FileExists(File f){
	   return (f!=null && f.exists() && f.isFile() && f.canRead());
   }











      
}
