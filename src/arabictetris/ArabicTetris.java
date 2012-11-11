package arabictetris;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;


public class ArabicTetris extends PApplet {

	/* = = = = = = = = = = = = = = = = = =

	   AraTetric - A game to learn the Arabic Alphabet
	   
	   Next Steps:
	    
	    1. Show the Latin equivalent .... show Arabic word too? Do I have the correct Latin equivalent
	    
	    2. ADD buttons
	    
	    3. Add Difficulty Level
	    
	    4. When you win or lose, show the word that was jsut done using the square typeface...
	  
	  = = = = = = = = = = = = = = = = = = */
	   

	private static final long serialVersionUID = 1L;

	String[][] alphabetArray;
	
	String[][] dictionaryArray;

	PImage[][] imageArray;

	String playWord; 

	ArrayList<letter> letters;
	
	ArrayList<button> buttons;
	
	int DictionaryrowCount;
	
	int randomDictionaryEntry;
	
	int buttonListCount; 

	/* = Visual Stuf == */

	float margin = 20; 

	int blockHeight = 9; 
	
	PImage titleImage;
	
	PImage titleBackgroundImage;
	
	/* ================ */

	/* = Game Stuff == */

	float currentBorder; // We'll set this up in a bit... 

	int currentLetter = 0; 

	int gameState = 0;
	
	boolean gamePaused = false; 
	
	int baseline; 
	
	float score; 

	/* ================ */

	public void setup() {
	  
	  size(960, 640);
	  
	  frameRate(30);
	 
	  /* ================ */
	  
	  baseline = height/2 + 100;
	  
	  score = 0; 

	  /* ================ */
	  
	  //Load TItle Images
	  
	  titleImage = loadImage("title.png");
	  
	  titleBackgroundImage = loadImage("title-bg.png");
	  	  
	  /* ================ */
	  
	  Table alphabetTable;
	  
	  // Make a data table from a file that contains 
	  // the coordinates of each state.
	  alphabetTable = new Table("letters.tsv",this);
	  
	  // The row count will be used a lot, store it locally.
	  int rowCount = alphabetTable.getRowCount();
	  
	  //Populate the Arrays
	  
	  alphabetArray = new String[rowCount][11];
	  
	  imageArray    = new PImage[rowCount][4];
	  
	  //Load All Letters into an Array
	  // Loop through the rows of the locations file and draw the points  
	  for (int iii = 1; iii < rowCount; iii++) { //We leave out the titile
		  
		 int row = iii - 1;  //Used for Naming purposes, but not for calling up the table
		  
		//Get the ID
		alphabetArray[row][0] = alphabetTable.getString(iii, 0);  
		  
	    //Get the Latin Letter
	    alphabetArray[row][1] = alphabetTable.getString(iii, 1);  
	    
	    //Can we get the Arabic Letter
	    alphabetArray[row][2] = alphabetTable.getString(iii, 2);  
	    
		    for(int ii = 0; ii < 4; ii++){
		    
		    //Start (3,4,0)
		    	
		    //Middle (5,6,1)
		    	
		    //End (7,8,2)
		    	
		    //Alone (9,10,3)
		    	
		    	int rowIndex = (ii * 2) + 3; 	
		
			    //Get the Baseline
			    alphabetArray[row][rowIndex] = alphabetTable.getString(iii, rowIndex);  
			    
			    //Get the name of the Letter
			    alphabetArray[row][rowIndex + 1] = alphabetTable.getString(iii, rowIndex + 1) + ".png";  
			    
			    //println(row + " - " + ii + " / " + alphabetArray[row][rowIndex + 1]);
			    
			    //Get the Image of the Letter
			    imageArray[parseInt(alphabetArray[row][0])][ii] = loadImage(alphabetArray[row][rowIndex + 1]);
			    
		  }
	    
		  //println("--------");  
		    
	  }
	  
	  //println(alphabetArray);
	  
	  
	  // Get the dictionary and then Pick a word, after that populate the Letters...
	  
	  //Get the Dictionary
	  
	  Table dicionaryTable;
	  
	  // Make a data table from a file that contains 
	  // the coordinates of each state.
	  dicionaryTable = new Table("dictionary.tsv",this);
	  
	  // The row count will be used a lot, store it locally.
	  DictionaryrowCount = dicionaryTable.getRowCount();
	  
	  //Populate the Arrays
	  
	  dictionaryArray = new String[DictionaryrowCount][4];
	  
	  println("Number of Words in Dictionary: " + DictionaryrowCount);
	  
	  //Load All words into an Array
	  for (int row1 = 1; row1 < DictionaryrowCount; row1++) { //We leave out the titile
	
			  
			  //English Word
			  dictionaryArray[row1][0] = dicionaryTable.getString(row1, 0);
			  
			  //Arabic Word
			  dictionaryArray[row1][1] = dicionaryTable.getString(row1, 1);
			  
			   //Letters
			  dictionaryArray[row1][2] = dicionaryTable.getString(row1, 2);
			  
			   //Arabish
			  dictionaryArray[row1][3] = dicionaryTable.getString(row1, 3);
			  
			  //println("Dict: " + row1 + " / " + dictionaryArray[row1][0] + " / " + dictionaryArray[row1][1] + " / "+ dictionaryArray[row1][2] + " / "+ dictionaryArray[row1][3] + " / ");
		 
	  }
	 
	  //Pick a Word from the Dictionary
	  //Create these letters as the Letter Object
	  
	  letters = new ArrayList<letter>();
	  
	  //Load all buttons
	  
	  Table buttonList;
	  
	  buttonList = new Table("buttonlist.tsv",this);
	  
	  buttonListCount = dicionaryTable.getRowCount();
	  
	//Load All words into an Array
	  for (int row2= 1; row2 < buttonListCount; row2++) { //We leave out the titile
	
			  
			  //English Word
			   = buttonList.getString(row1, 0);
			  
			  //Arabic Word
			   = buttonList.getString(row1, 1);
			  
			   //Letters
			  = buttonList.getString(row1, 2);
			  
			   //Arabish
			   = buttonList.getString(row1, 3);
			  
		 
	  }
	 
	} //End Setup

	public void draw(){
	  
	  if(gameState == 2){
		  
		  if(gamePaused != true){
		  
			  background(0); 
			  
			  setEnviorment(); 
		 
			  //Draw Letters
			  
			  for(int i = 0; i < letters.size(); i++){
			  
			    letter oneDot = (letter) letters.get(i);
			    
			    if(i == currentLetter){
			      
			    	println("LetterId: " + oneDot.letterId);  
			    	
			    	println("Rotation: " + oneDot.rotation);
			    	
			    	 oneDot.showImage(40,40);
			      
			    }
			    
			    oneDot.update();
			  
			  }
		  
		  }
	  
	  }
	  
	  else if(gameState != 2){
	    
	    if(gameState == 3){
	      
	    	// You Win
	     
	    	
	      
	    }
	    
	    if(gameState == 4){
	      
			// You Lose
			 
			
	      
	    }
	    
	    if(gameState == 1){
	    	
	    	homeScreen(); //home screen is where the 2nd window, where the language has already been selected
	    	
	    }
	    
	    if(gameState == 0){
	    	
	    	startScreen(); //to selecte language
	    	
	    }
	    
	  }  
	  
	  showButtons();
	  
	}

	public void keyPressed(){
		
		if(gameState == 2){
	  
			//Here the Letters Are Controlled
			  
			if(keyCode == 39){ //Right Arrow
			  
				letter oneDot = (letter) letters.get(currentLetter);
				        
				oneDot.rotateLetterRight();
			  
			}
			  
			else if(keyCode == 37){ //Left Arrow
			  
				letter oneDot = (letter) letters.get(currentLetter);
				        
				oneDot.rotateLetterLeft();
			  
			}
			  
			else if(keyCode == 32){ //Left Arrow
			  
				letter oneDot = (letter) letters.get(currentLetter);
				        
				oneDot.advanceLetter();
			  
			}
			  
			else if(keyCode == 38){ //UP Arrow
			  
				letter oneDot = (letter) letters.get(currentLetter);
				        
				oneDot.moveLetter(true);
			  
			}
			  
			else if(keyCode == 40){ //Down Arrow
			  
				letter oneDot = (letter) letters.get(currentLetter);
				        
				oneDot.moveLetter(false);
			  
			}
			
			else if(keyCode == 80 ||  key == 'p'){ // P letter
				  
				gamePaused = !gamePaused;
			  
			}
			  
			else {
			    
				println("KeyCode: " + keyCode);
			    
			}
		
		}
		
		else {
			
			if(keyCode == 32){ //Space Bar
				
				StartNewGame();
				
			}
			    
			println("KeyCode: " + keyCode);
	
		}
	  
	}
	
	public void mousePressed(){
		
		if(gameState == 0){
			
			gameState++;
			
		}
		
		else if(gameState == 1){
			
			gameState++;
		
			StartNewGame();
		
		}
		
		else {
			
			StartNewGame();
			
		}
		
	}

	public void StartNewGame(){
		
		gameState = 2; 
		
		letters.clear();
		
		currentLetter = 0; 
		
		currentBorder =  width - 20;
		
		generateRandomWordFromDictionary();
		
	}
	
	public void endGame(int what){
		  
		String txt = ""; 
	
		if(what == 2){
		 
			fill(255,0,0,50);
			
			rect(0,0,width,height);
		
			txt = "GAME OVER";
			
			letter oneDot = (letter) letters.get(currentLetter);
			
			image(imageArray[oneDot.letterId][oneDot.letterType], 200, 200); 
			
			score = 0; 
		
		}

		else if(what == 1){
		
			fill(0,255,0,1);
			
			rect(0,0,width,height);
		
			txt = "YOU WIN";
	
		}
		 
		fill(255);
	
		textAlign(CENTER); 
	
		textSize(32);
	
		text(txt, width/2, 150);
		
		//Display information about the word
		
		textSize(20);
		
		fill(255);
		
		String finalText = "The word you solved is "+ dictionaryArray[randomDictionaryEntry][3] +" and it means "+dictionaryArray[randomDictionaryEntry][0]+".";
		
		text(finalText, 300,500,360,200);
		
		text(dictionaryArray[randomDictionaryEntry][1], 300,400,360,200);
	

	}
	
	public void generateRandomWordFromDictionary(){
		
		randomDictionaryEntry = (int) random(1,DictionaryrowCount);
		  
		String[] randomWordSplit = split(dictionaryArray[randomDictionaryEntry][2], ' ');
		  
		for(int i = 0; i < randomWordSplit.length; i++){

			//Get the First Letter
			String TypeOfLetter = randomWordSplit[i].substring(0, 1);  
			
			int IdOfTypeOfLetter = 1; 
			
			if(TypeOfLetter.equals("s")){
				
				IdOfTypeOfLetter = 0;  //Start
			
			}
			else if(TypeOfLetter.equals("m")){ 
				
				IdOfTypeOfLetter = 1;  //Middle(default)
			
			}
			else if(TypeOfLetter.equals("e")){
				
				IdOfTypeOfLetter = 2;  //End
			
			}
			else if(TypeOfLetter.equals("a")){
				
				IdOfTypeOfLetter = 3; // Alone  
			
			}
			
			//Get the Row count
			int IdOfLetter = parseInt(randomWordSplit[i].substring(1, randomWordSplit[i].length()));
			  
			//("LETTER: " + IdOfLetter + " - " + IdOfTypeOfLetter + " / " + alphabetArray[IdOfLetter][4 + (IdOfTypeOfLetter * 2)]);
			
			//println();
			
			//Create the Object, 
			letters.add(new letter(IdOfLetter, IdOfTypeOfLetter, letters.size(), this));	
		    
		} //End ForEach letter of random Word
		
	}
	
	public void showButtons(){
		
		
		
	}
	
	//Specific Screen for Specific Game States...
	
	public void setEnviorment(){
		
		//Set Enviorment
		  
		  stroke(0,38,178); //Color of the Baseline
		  
		  line(width - margin, baseline, width - margin, baseline+9); 
		  
		  line(width - margin, baseline - 56, width - margin, baseline - 44); 
		  
		  line(0, baseline, width, baseline); 	
		  
		  //Draw Grey box
		  
		  noStroke(); 
		  
		  fill(51);
		  
		  rect(0,0,width,150);
		
		  //Draw Black box for letter
		  
		  fill(0);
		  
		  rect(25,25,100,100);
		  
		  //Draw Score
		  
		  fill(255);
		  
		  textSize(24);
		  
		  text("Score: " + parseInt(score), 640,75);
	}
	
	public void startScreen(){	
		
		background(0); 
		
		//Show the Title Image
		
		image(titleImage,width/2 - titleImage.width/8,150,titleImage.width/4, titleImage.height/4);		
		
		float titleBackgroundImageWidth = (float) (titleImage.width/(1.6));
		
		float titleBackgroundImageHeight = (float) (titleImage.height/(1.6));
		
		image(titleBackgroundImage, (width - titleBackgroundImageWidth)/2 + 5, height - 20 - titleBackgroundImageHeight,titleBackgroundImageWidth,titleBackgroundImageHeight);
	}
	
	public void homeScreen(){
		
		background(0); 
		
		fill(255);
		
		textAlign(CENTER); 
	
		textSize(width/10);
	
		text("Click to start", width/2, height/2);
		
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { arabictetris.ArabicTetris.class.getName() });
	}
}
