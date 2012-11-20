package arabictetris;

import processing.core.PApplet;

public class button{
	
	//Button Properties
	
	float x; 
	
	float y; 
	
	String text; 
	
	int destinationState;
	
	int parentState; 
	
	int language; 
	
	 PApplet parent; // The parent PApplet that we will render ourselves onto
	
	float widthOfText; 
	
	/* 
	 * 
	 * 0 - StartScreen
	 * 
	 * 1 - HomeScreen (Once Language is Picked)
	 * 
	 * 2 - Actual Game (Hence, Pause buttons)
	 * 
	 * 3 - You win
	 * 
	 * 4 - You lose
	 * 
	 * 7 - Submit High Score
	 * 
	 * 5 - Records
	 * 
	 * 6 - Exit
	 * 
	 * 
	 */
	
	button(float tx, float ty, String ttext, int tDestinationState, int tlanguage, PApplet p){
		
		parent = p; 
		
		widthOfText = ((ArabicTetris) parent).widthOfTextBox;
	
		if(tx == 0.0 || Float.isNaN(tx)){
			
			x = parent.width/2 - (widthOfText/2); 
			
		}
		
		else{
			
			x = parent.width * tx;
			
		}
		
		y = ty * parent.height; 
		
		destinationState = tDestinationState;

		text = ttext;
		
		language = tlanguage;
			
	}

	public void show(boolean current){
	
	
		if(current && !((ArabicTetris) parent).writingMode){
			
			((ArabicTetris) parent).fill(0,0,255);
			
		}
		
		else {
			
			((ArabicTetris) parent).fill(255);
			
		}
		
		((ArabicTetris) parent).textSize(14);
		
		((ArabicTetris) parent).text(text,x,y,widthOfText,25);
		
	}
	
	public void execute(){
		
		//If there is a destination state....
		
		//Else, there should be a function
		
		/*
		 * List of Functions: 
		 * 
		 * 0 - Exit Program exit()
		 */
	
	}

}
