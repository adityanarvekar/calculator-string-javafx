/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorjavafx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**
 *
 * @author aditya
 */
public class FXMLDocumentController implements Initializable {
    
   String inputString="";
   
   boolean isEqualsPress=false;
   DecimalFormat df = new DecimalFormat("#.##");
    @FXML
    private TextField calcSc;
   
    @FXML
    private Button btnEquals;
    
    @FXML
    private Button btnAdd;
    
    @FXML
    private Button btnSubtract;
    
    @FXML
    private Button btnMultiply;
    
    @FXML
    private Button btnDivide;
    
    @FXML
    private Button btnMod;
    
    @FXML
    private Button btnPlusMinus;
    
    @FXML
    private Button btnSqt;
    
    @FXML
     private void clearCalc(ActionEvent event) {
           calcSc.clear();
           inputString="";
     }
   
     @FXML
     private void numberClick(ActionEvent event) {
         btnEquals.setDisable(false);
         if(isEqualsPress){
           calcSc.clear();
           inputString="";
           inputString=inputString+((Button)event.getSource()).getText();
           calcSc.setText(inputString);
           isEqualsPress=false;
         }
         else{
          inputString=inputString+((Button)event.getSource()).getText();
          calcSc.setText(inputString);
         }
         operatorFunc(false);
     }
     
     @FXML
     private void operatorClick(ActionEvent event) {
           btnEquals.setDisable(false);
           isEqualsPress=false;
         if(((Button)event.getSource()).getText().equals("x")){
             inputString=inputString+" * ";
         }
         else{
             inputString=inputString+" "+((Button)event.getSource()).getText()+" ";
         }
          
          
          calcSc.setText(inputString);
          operatorFunc(true);
     }
     
     @FXML 
     private void sqtFunc(ActionEvent event) {
        btnEquals.setDisable(false);
        
        if(!btnAdd.isDisabled()){
            if(inputString.contains(" ")){
                String [] exparray = inputString.split(" ");
                if(Double.parseDouble(exparray[exparray.length-1])<0){
                    
                }
                else{
                    btnSqt.setDisable(false);
                    exparray[exparray.length-1]=df.format(String.valueOf(Math.sqrt(Double.parseDouble(exparray[exparray.length-1]))));
                        inputString=Arrays.toString(exparray);
                        inputString=inputString.replace(",","");
                        inputString=inputString.replace("[","");
                        inputString=inputString.replace("]","");
                        calcSc.setText(inputString);
                }
                        
            }
            else{
                if(Double.parseDouble(inputString)<0){
                    
                }
                else{
                    btnSqt.setDisable(false);
                    inputString=String.valueOf(Math.sqrt(Double.parseDouble(inputString)));
                    calcSc.setText(inputString);
                }
                    
            }
        }
        
     }
     
     
      @FXML
     private void plusMinusClick(ActionEvent event) {
        btnEquals.setDisable(false);
        
        if(!btnAdd.isDisabled()){
            if(inputString.contains(" ")){
                String [] exparray = inputString.split(" ");
                    if(!exparray[exparray.length-1].contains("-")){
                        exparray[exparray.length-1]="-"+exparray[exparray.length-1];
                        inputString=Arrays.toString(exparray);
                        inputString=inputString.replace(",","");
                        inputString=inputString.replace("[","");
                        inputString=inputString.replace("]","");
                        calcSc.setText(inputString);
                        
                    }
            else{
                 exparray[exparray.length-1]=exparray[exparray.length-1].replace("-", "");
                 inputString=Arrays.toString(exparray);
                 inputString=inputString.replace(",","");
                 inputString=inputString.replace("[","");
                 inputString=inputString.replace("]","");
                 calcSc.setText(inputString);
            }
            }
            else{
                if(inputString.contains("-")){
                    inputString=inputString.replace("-","");
                    calcSc.setText(inputString);
                }
                else{
                    inputString="-"+inputString;
                    calcSc.setText(inputString);
                }
            }
        }
        
     }
     @FXML
     private void equalsClick(ActionEvent event) throws IOException{
         if(inputString.endsWith(" ")){
         
           
         }
         
         
         else{
            boolean calculationValid=true;
             
            String [] exparray = inputString.split(" ");
            double result=Double.parseDouble(exparray[0]);
          
            for(int i=1;i<exparray.length-1;i+=2){
            switch (exparray[i]) { 
                case "+": 
                result = result + Double.parseDouble(exparray[i+1]);
                break; 
                case "-": 
                result = result - Double.parseDouble(exparray[i+1]);
                break; 
                case "*": 
                result = result * Double.parseDouble(exparray[i+1]);
                break; 
                case "%":
                    result = result % Double.parseDouble(exparray[i+1]);
                    break;
                case "/": 
                    
                    if(Double.parseDouble(exparray[i+1])==0){
                        calculationValid=false;
                        result=0;
                    }
                    else{
                       result = result / Double.parseDouble(exparray[i+1]); 
                    }
                break; 
                
                default: 
                break; 
        } 
   
        }
            if(calculationValid){
                printResToFile(result);
            }
            else{
                printErrToFile();
            }
            
           
        }

     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     btnSqt.setText("\u221A");
        
    }
    
    
     private void operatorFunc(boolean val) {
       
         
    btnAdd.setDisable(val);
    btnSqt.setDisable(val);
    btnSubtract.setDisable(val);
    btnMultiply.setDisable(val);
    btnDivide.setDisable(val);
    btnMod.setDisable(val);
    btnPlusMinus.setDisable(val);     
        
    }
     
     private void printResToFile(double result) throws IOException{
         
          String finalAnswer=df.format(result);
            if(finalAnswer.contains(".00")){
               finalAnswer = finalAnswer.replace(".00","");
            }
            calcSc.setText(finalAnswer);
            btnEquals.setDisable(true);
            isEqualsPress=true;
             try (BufferedWriter outs = new BufferedWriter(new FileWriter("calculator.txt",true))) {
                 outs.newLine();
                 outs.write(inputString+" = "+finalAnswer);
                 outs.close();
             }
             
            inputString=finalAnswer;
         
     }
     
     private void printErrToFile() throws IOException{
         
          
            calcSc.setText("Cannot Divide by Zero");
            btnEquals.setDisable(true);
            isEqualsPress=true;
             try (BufferedWriter outs = new BufferedWriter(new FileWriter("calculator.txt",true))) {
                 outs.newLine();
                 outs.write(inputString+"= Cannot Divide by Zero");
                 outs.close();
             }
            
            inputString="";
            operatorFunc(true);
         
     }
     
    
}
