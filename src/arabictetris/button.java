package arabictetris;

public class button extends ArabicTetris {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Button Properties
	
	float x; 
	
	float y; 
	
	String text; 
	
	int destinationState;
	
	int parentState; 
	
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
	 * 5 - Records
	 * 
	 * 
	 */
	
	button(float tx, float ty, String tText, int tDestinationState){
	
		x = tx; 
		
		y = ty; 
		
		destinationState = tDestinationState;
			
	}

	public void show(){
	
		fill(255);
		
		textSize(14);
		
		text(text,x,y);
		
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
