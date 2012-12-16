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
	
	int otherState;
	
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
	
	button(float tx, float ty, String ttext, int tDestinationState, int tlanguage, int totherState, PApplet p){
		
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
		
		otherState = totherState;
			
	}

	public void show(boolean current){
	
	
		if(current && !((ArabicTetris) parent).writingMode){
			
			((ArabicTetris) parent).fill(((ArabicTetris) parent).hightlightColor);
			
		}
		
		else {
			
			
			((ArabicTetris) parent).fill(((ArabicTetris) parent).whiteColor);
			
		}
		
		if(language == 0){
			
			((ArabicTetris) parent).textFont(((ArabicTetris) parent).univers, 32);
			
		}
		
		else {
			
			//((ArabicTetris) parent).textFont(((ArabicTetris) parent).helvetica,32);
			
		}
		
		((ArabicTetris) parent).textSize(24);
		
		((ArabicTetris) parent).text(text,x,y,widthOfText, 40);
		
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
