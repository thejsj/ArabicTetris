package arabictetris;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.prefs.*;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;


public class ArabicTetris extends PApplet {

	/* = = = = = = = = = = = = = = = = = =

	   AraTetric - A game to learn the Arabic Alphabet
	   
	   Next Steps:
	    
	    1. When you win or lose, show the word that was jsut done using the square typeface...
	    
	    2. Change typeface to Helvetica
	    
	    3. Finish When you get a high score! ... submit name, submit date, re write text file
	    
	    4. 
	  
	  = = = = = = = = = = = = = = = = = = */
	   

	private static final long serialVersionUID = 1L;
	
	// declare my variable at the top of my Java class
	private Preferences prefs;

	/* = Preferences == */
	
	String[][] alphabetArray;
	
	String[][] dictionaryArray; 

	PImage[][] imageArray;
	
	String[][] buttonArray;
	
	String[][] highScores;

	String playWord; 

	ArrayList<letter> letters;
	
	ArrayList<button> buttons;
	
	int DictionaryrowCount;
	
	int randomDictionaryEntry;
	
	int buttonListCount; 
	
	int highScoresCount;

	/* = Visual Stuf == */

	float margin = 20; 

	int blockHeight = 9; 
	
	PImage titleImage;
	
	PImage titleBackgroundImage;
	
	PImage[] instructionImages = new PImage[2];
	
	float widthOfTextBox; 
	
	PrintWriter output, output2;
	
	PFont helvetica;
	
	PFont univers;
	
	public int letterColor = color(46,54,52);
	
	public int backgroundColor = color(63,71,69);
	
	public int hightlightColor = color(255,130,116);
	
	public int whiteColor = color(255,255,255);
	
	public int mutedHighlightColor = color(125,89,83);
	
	/* ================ */

	/* = Game Stuff == */

	float currentBorder; // We'll set this up in a bit... 

	int currentLetter = 0; 
	
	int currentButton = 0; 

	int gameState = 0;
	
	int gameState2Mode = 0;

	int chosenLanguage = 0; //English as Default
	
	boolean gamePaused = false; 
	
	int baseline; 
	
	float score; 
	
	String highScoreName;
	
	boolean writingMode;


	/* ================ */

	public void setup() {
	  
	  size(960, 640);
	  
	  frameRate(15);
	 
	  /* ================ */
	  
	  baseline = height/2 + 100;
	  
	  score = 0; 
	  
	  textAlign(CENTER);
	  
	  widthOfTextBox = width/4;
	  
	  helvetica = loadFont("Helvetica.vlw");
	  
	  univers = loadFont("Univers.vlw");
	  
	  //textFont(univers, 32);
	  
	  /* ================ */
	  
	  // create a Preferences instance (somewhere later in the code)
      
	  /* ================ */
	  
	  //Load Images (Single Images)
	  
	  titleImage = loadImage("images/title.png");
	  
	  titleBackgroundImage = loadImage("images/title-bg.png");
	  
	  //Instructions in English
	  instructionImages[0] = loadImage("images/instructions0.png");
	  
	  //Instructions in Arabic
	  instructionImages[1] = loadImage("images/instructions1.png");
	  	  
	  /* ================ */
	  
	  Table alphabetTable;
	  
	  // Make a data table from a file that contains 
	  // the coordinates of each state.
	  alphabetTable = new Table("tsv/letters.tsv",this);
	  
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
			    
			    //Get the Image of the Letter
			    imageArray[parseInt(alphabetArray[row][0])][ii] = loadImage("letters/" + alphabetArray[row][rowIndex + 1]);
			    
		  }
		    
	  }
	  
	  // Get the dictionary and then Pick a word, after that populate the Letters...
	  //Get the Dictionary
	  Table dicionaryTable;
	  
	  // Make a data table from a file that contains 
	  // the coordinates of each state.
	  dicionaryTable = new Table("tsv/dictionary.tsv",this);
	  
	  // The row count will be used a lot, store it locally.
	  DictionaryrowCount = dicionaryTable.getRowCount();
	  
	  //Populate the Array
	  dictionaryArray = new String[DictionaryrowCount][4];
	  
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
		 
	  }
	 
	  //Pick a Word from the Dictionary
	  //Create these letters as the Letter Object
	  letters = new ArrayList<letter>();
	  
	  //Load all buttons
	  Table buttonList;
	  
	  buttonList = new Table("tsv/buttonlist.tsv",this);
	  
	  buttonListCount = buttonList.getRowCount();
	  
	  buttonArray = new String[buttonListCount][11];
	  
	  buttons = new ArrayList<button>();
	  
	//Load All words into an Array
	  for (int row2 = 1; row2 < (buttonListCount); row2++) { //We leave out the title
			 
		//Text
		buttonArray[row2][0] = buttonList.getString(row2, 0);
		  
		// X
		buttonArray[row2][1] = buttonList.getString(row2, 1);
		  
		// Y
		buttonArray[row2][2] = buttonList.getString(row2, 2);
		  
		// parentState
		buttonArray[row2][3] = buttonList.getString(row2, 3);
		   
		//Destination
		buttonArray[row2][4] = buttonList.getString(row2, 4);
		   
		//Language
		buttonArray[row2][5] = buttonList.getString(row2, 5);
		
		println(buttonList.getString(row2, 5));
		try{
			
			if(buttonList.getString(row2, 6) != null){
				
				buttonArray[row2][6] = buttonList.getString(row2, 6);
				
			}
		
	  	}
		
		catch(Exception e){
			
			println(buttonArray[row2][0] +" / "+buttonArray[row2][5]+" / "+ e);
			
		}
		//buttonArray[row2][6] = buttonList.getString(row2, 6);
			  
	  }
	  
	  //Populate the Buttons Array list with the buttons for the first screen
	  // These will be executed in a function a the end of the draw(), and will changed when the gameState is Changed
	  
	  populateButtonsForState(0);
	  
	  readHighScores();	 
	  
	  	
	} //End Setup

	public void draw(){
	  
	  if(gameState == 2){

		  if(gamePaused != true){
		  
			  background(backgroundColor); 
			  
			  setEnviorment(); 
		 
			  //Draw Letters
			  
			  for(int i = 0; i < letters.size(); i++){
			  
			    letter oneDot = (letter) letters.get(i);
			    
			    if(i == currentLetter){
			    	
			    	 oneDot.showImage(40,40);
			      
			    }
			    
			    oneDot.update();
			  
			  }
		  
		  }
		  
		  else {
			  
			  showGamePausedScreen();
			  
		  }
	  
	  }
	  
	  else if(gameState != 2){
		  
			if(gameState == 0){
			    	
			startScreen(); //to selecte language
				
			}
			
			if(gameState == 1){
				    	
			homeScreen(); //home screen is where the 2nd window, where the language has already been selected
				
			}  
			  
			if(gameState == 3 || gameState == 4 || gameState == 7){
			   
			// You Win or You lose, submit high score
				 
			endGame(gameState);	
				
			}
			
			if(gameState == 5){
				
			showHighScores();
				
			}
			
			if(gameState == 6){
				
			exitGame();
				
			}
	    
	  }
	  
	  if(gameState != 2 || gamePaused == true){
		  
		  showButtons();
		  
	  }
	  
	 
	  
	}

	public void StartNewGame(){
		
		gameState = 2; 
		
		letters.clear();
		
		currentLetter = 0; 
		
		currentBorder =  width - 20;
		
		generateRandomWordFromDictionary();
		
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
			 
			//Create the Object, 
			letters.add(new letter(IdOfLetter, IdOfTypeOfLetter, letters.size(), this));	
		    
		} //End ForEach letter of random Word
		
	}
	
	public void populateButtonsForState(int destinationState){
			
		buttons.clear();
				
		  for (int row3 = 0; row3 < buttonArray.length; row3++){
			  
			  if(buttonArray[row3][3] != null){
			  
				  if(parseInt(buttonArray[row3][3]) == destinationState){
					  
					  //button(float tx, float ty, String tText, int tDestinationState)
					  
					  //Text. X, Y, parentState, Destination,Language,Function
					  
					  float tx = parseFloat(buttonArray[row3][1]);
					  
					  float ty = parseFloat(buttonArray[row3][2]);		
					  
					  String ttext = buttonArray[row3][0];
					 				  
					  int tDestinationState = parseInt(buttonArray[row3][4]);
					  
					  int tLanguage = parseInt(buttonArray[row3][5]);
					  
					  int otherState = 0; 
					  
					  if(buttonArray[row3][6] != null){
						  
							otherState = parseInt(buttonArray[row3][6]);
						  
					  }
					  
					  if(destinationState == 0 || chosenLanguage == tLanguage){
						  
						  buttons.add(new button(tx,ty,ttext,tDestinationState,tLanguage, otherState, this));
						  
					  }
		
				  }
				  
			  }
			  
		  }
		
	}
	
	public void showButtons(){
		
		for(int i = 0; i < buttons.size(); i++){
			  
		    button oneButton = (button) buttons.get(i);

		    if(i == currentButton){
		      
		    	oneButton.show(true);
		    	
		    }
		    
		    else {
		    	
		    	oneButton.show(false);
		    	
		    }
		  
		  }
		
	}
	
	public void checkHighScore(){
		
		//Check if the score is higher than score #10
		
		if(score > parseInt(highScores[9][0])){
			
			// It is, so know we have ask for tphe name and generate a date
			
			highScoreName = "Your Name";
			
			writingMode = true;
			
			gameState = 7;
			
			endGame(7);
			
		}
		
		else {
			
			gameState = 4;
			
			endGame(4);
			
			//Empty Score
			
			score = 0;
			
		}
		
	}

	public void submitHighScore(){
		
		readHighScores();
		
		Preferences prefs = Preferences.userNodeForPackage(ArabicTetris.class);
		
		//to rank the scores, look up a date, ask for a name and then re-write highscores.tsv
		
		String[][] newHighScores = new String[11][3];
		
		for(int i4 = 0; i4 < highScores.length; i4++){
			
			for(int i5 = 0; i5 < highScores[i4].length; i5++){
				
				newHighScores[i4][i5] = highScores[i4][i5];
				
			}
			
		}
		
		newHighScores[10][0] = Integer.toString(parseInt(score));
		
		//Name
		newHighScores[10][1] = highScoreName;
		
		//Date	
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		Date date = new Date();
		
		String theDate = dateFormat.format(date);
		
		newHighScores[10][2] = theDate;
		
		Arrays.sort(newHighScores, new Comparator<String[]>()
			    {
			      public int compare(String[] s1, String[] s2)
			      {
			        return Integer.valueOf(s2[0]).compareTo(Integer.valueOf(s1[0]));
			      }
			    });
		
		//Make 1 string for each of the following: Score, Name, and Date
		
		//Score
		
			String highScoreValues = "";
			
			for(int i6 = 0; i6 < newHighScores.length; i6++){
				
				highScoreValues += newHighScores[i6][0] + ",";
				
			}
			
			println("highScoreValues: " + highScoreValues);
			
			prefs.put("values",highScoreValues);
			
		// Names
			
			String highScoreNames = "";
			
			for(int i6 = 0; i6 < newHighScores.length; i6++){
				
				highScoreNames += newHighScores[i6][1] + ",";
				
			}
			
			println("highScoreNames: " + highScoreNames);
			
			prefs.put("names",highScoreNames);
			
		// Date
			
			String highScoreDates = "";
			
			for(int i6 = 0; i6 < newHighScores.length; i6++){
				
				highScoreDates += newHighScores[i6][2] + ",";
			}
			
			println("highScoreDates: " + highScoreDates);
			
			prefs.put("dates",highScoreDates);
		
		readHighScores();
		
		//Empty Score
		
		score = 0;

		
	}
	
	public void readHighScores(){
		
		//High Score
		  
	//Preferences Code
		
		Preferences prefs = Preferences.userNodeForPackage(ArabicTetris.class);
		  
		  String highScoreValues = prefs.get("values", "0,0,0,0,0,0,0,0,0,0,0");
		  
		  String highScoreNames =  prefs.get("names","Jorge Silva-Jetter,Jorge Silva-Jetter,Jorge Silva-Jetter,Jorge Silva-Jetter,Jorge Silva-Jetter,Jorge Silva-Jetter,Jorge Silva-Jetter,Jorge Silva-Jetter,Jorge Silva-Jetter,Jorge Silva-Jetter,Jorge Silva-Jetter,");
		  
		  String highScoreDates= prefs.get("dates", "12/14/2012,12/14/2012,12/14/2012,12/14/2012,12/14/2012,12/14/2012,12/14/2012,12/14/2012,12/14/2012,12/14/2012,");
		  
		  String[] tempValues = highScoreValues.split(",");
		  
		  String[] tempNames = highScoreNames.split(",");
		  
		  String[] tempDates = highScoreDates.split(",");
		  
		  highScoresCount = tempValues.length;
		  
		  highScores = new String[highScoresCount][3];
		  
		  println("HIGH SCORES:");
		  
		  for (int row3 = 0; row3 < (highScoresCount - 1); row3++){
			  
			  	// Score  
				highScores[row3][0] = tempValues[row3];
					  
				// Person
				highScores[row3][1] = tempNames[row3];
				  
				// Date
				highScores[row3][2] = tempDates[row3];
				
				println(row3 +": "+highScores[row3][0] + " / " + highScores[row3][1] + " / " + highScores[row3][2]);
					  
		  }	  
		
	}
	
	/**************************************

	
        S T A T E S   &   V I S U A L

	
	**************************************/
	
	public void endGame(int what){
		
		/*
		 * 1 - You Wind, you keep going
		 * 
		 * 2 - You lose, no High Score
		 * 
		 * 3 - You lose, but you got a high score...
		 */
		  
		String txt = ""; 
		
		if(what == 3){
			
			fill(mutedHighlightColor);
			
			//stroke(255);
			
			rect(width/2 - widthOfTextBox/2,(float)(height * 0.37), widthOfTextBox,(float)(height * 0.47) );
		
			txt = "YOU WIN";
			
			//Display information about the word
			
			textSize(20);
			
			fill(backgroundColor);
			
			textSize(14);
			
			String finalText = "The word you solved is "+ dictionaryArray[randomDictionaryEntry][3] +" and it means "+dictionaryArray[randomDictionaryEntry][0]+".";
			
			text(finalText, width/2 - 100,300,200,300);
			
			//text(dictionaryArray[randomDictionaryEntry][1], 300,400,360,200);
	
		}
	
		else if(what == 4){
		 
			fill(backgroundColor);
			
		//	stroke(255);
			
			rect(width/2 - widthOfTextBox/2,(float)(height * 0.37), widthOfTextBox,(float)(height * 0.47) );
		
			txt = "That's not quite right.\n Try Again?";
			
			//The letter you got wrong...
			
//			letter oneDot = (letter) letters.get(currentLetter);
//			
//			image(imageArray[oneDot.letterId][oneDot.letterType], 200, 200); 
			
		}
		
		else if(what == 7){
			 
			fill(backgroundColor);
			
			//stroke(255);
			
			rect(width/2 - widthOfTextBox/2 - 10,(float)(height * 0.37), widthOfTextBox + 20,(float)(height * 0.47) );
		
			txt = "NEW HIGH SCORE";
			
			//The letter you got wrong...
			
//			letter oneDot = (letter) letters.get(currentLetter);
//			
//			image(imageArray[oneDot.letterId][oneDot.letterType], 200, 200); 
			
			//Space to Write your name
			
			fill(letterColor);
			
			if(writingMode){
			
			if((frameCount) % 10 > 5){
			
				text(highScoreName, width/2, (float)(height * 0.5));
			
			}
			
			}
			
			else {
				
				text(highScoreName, width/2, (float)(height * 0.5));	
			
			}
			
			stroke(letterColor);
			
			line((float)(width/2 - widthOfTextBox/2),(float)(height * 0.52),(float)(width/2 + widthOfTextBox/2),(float)(height * 0.52));	
			
		}
		 
		fill(letterColor);
	
		textAlign(CENTER); 
	
		textSize(24);
	
		text(txt, width/2, (float)(height * 0.45));	

	}
	
	public void setEnviorment(){
		
		//Set Enviorment
		  
		  stroke(letterColor); //Color of the Baseline
		  
		  line(width - margin, baseline, width - margin, baseline+9); 
		  
		  line(width - margin, baseline - 56, width - margin, baseline - 44); 
		  
		  line(0, baseline, width, baseline); 	
		  
		  //Draw Grey box
		  
		  stroke(letterColor);
		  
		  line(0,150,width,150);
		
		  //Draw Black box for letter
		  
		  fill(letterColor);
		  
		  rect(25,25,100,100);
		  
		  rect(150,25,100,100);
		  
		  //Draw Score
		  
		  textSize(14);
		  
		  println(highScores[9][0]);
		  
		  if( score > Float.parseFloat(highScores[9][0]) ){
			  
			  fill(hightlightColor);
			  
		  }
		  
		  else {
			  
			  fill(letterColor);
			  
		  }
		  
		  fill(whiteColor);
		  
		  textAlign(RIGHT);
		  
		  text("Score:", 210,65);
		  
		  text("Speed:", 210,85);
		  
		  fill(hightlightColor);
		  
		  textAlign(LEFT);
		  
		  text(parseInt(score), 220,65);
		  
		  text(((parseInt(frameRate) - 10) * 2), 220,85);
		  
		  textAlign(CENTER);
	}
	
	public void startScreen(){	
		
		background(backgroundColor); 
		
		//Show the Title Image
		
		image(titleImage,width/2 - titleImage.width/8,150,titleImage.width/4, titleImage.height/4);		
		/*
		float titleBackgroundImageWidth = (float) (titleImage.width/(1.6));
		
		float titleBackgroundImageHeight = (float) (titleImage.height/(1.6));
		
		image(titleBackgroundIm
		age, (width - titleBackgroundImageWidth)/2 + 5, height - 20 - titleBackgroundImageHeight,titleBackgroundImageWidth,titleBackgroundImageHeight);
	
		*/
	
	}
	
	public void homeScreen(){
		
		background(backgroundColor); 
		
		float instructionImageWidth = width - 40;
		
		float InstructionImageHeight = (instructionImageWidth)/instructionImages[chosenLanguage].width * instructionImages[chosenLanguage].height; 
		
		fill(letterColor);
		
		image(instructionImages[chosenLanguage], 20,20, instructionImageWidth, InstructionImageHeight);
		
	}
	
	public void showGamePausedScreen(){
		
		fill(letterColor);
		
	//	stroke(255);
		
	//	strokeWeight(1);
		
		rect(width/2 - widthOfTextBox/2 - 10,(float)(height * 0.37), widthOfTextBox + 20,(float)(height * 0.42) );
		
		fill(mutedHighlightColor);
		
		noStroke();
		
		text("PAUSED", width/2, (float)(height * 0.45));
		
	}
	
	public void showHighScores(){
		
		background(backgroundColor);
		
		fill(letterColor);
		
		float[] xPosistion = {150,200,350};
		
		text("Score",xPosistion[0], 100);

		text("Name",xPosistion[1], 100);

		text("Date",xPosistion[2], 100);
		
		println("HS LEngth:" + highScores.length);
				
		for(int iii = 0; iii < (highScores.length - 1); iii++){

			println("Length of " + iii + ":" + highScores[iii].length);
			
			println("hs: " + highScores[iii][0]);
			
			println("xpos: " + xPosistion[0]);
			
			fill(whiteColor);
			
			textSize(18);
			
			textAlign(RIGHT);
			
			text(highScores[iii][0],xPosistion[0], 150 + iii * 50);
			
			textAlign(LEFT);

			text(highScores[iii][1],xPosistion[1], 150 + iii * 50);
			
			textAlign(LEFT);

			text(highScores[iii][2],xPosistion[2], 150 + iii * 50);
			
		}
		
	}
	
	public void exitGame(){
		
		println("Exiting Game");
		
		exit();
		
	}
	
	
	/********************************
	
	
	    I N T E R A C T I V I T Y
	
	
	*********************************/
	
	public void keyPressed(){
		
		if(writingMode){
			
			if(keyCode == 10){
				
				writingMode = !writingMode;
				
				submitHighScore();
				
			}
			
			else if(keyCode == 8 || keyCode == 127){
				
				if(highScoreName.length() > 0){
				
					highScoreName = highScoreName.substring( 0, highScoreName.length()-1 );
				
				}
				
			}
			
			else if(keyCode != 16 && highScoreName.length() < 21) {
				
				highScoreName = highScoreName + key;
				
			}
			
		}
		
		else if(gameState == 2 && !gamePaused){
	  
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
			
			else if(keyCode == 61 || keyCode == 107){ // 2 + signs
				
				frameRate(frameRate + 5);
				
				if(frameRate > 60){
					
					frameRate(60);
					
				}
				
			}
			
			else if(keyCode == 109 || keyCode == 45){ // 2 - signs
				
				frameRate(frameRate - 5);
				
				if(frameRate < 10){
					
					frameRate(10);
					
				}
				
			}
			
			else if(keyCode == 80 ||  key == 'p'){ // P letter
				  
				gamePaused = !gamePaused;
			  
			}
			  
			else {
			    
			//	println("KeyCode: " + keyCode);
			    
			}
		
		}
		
		else {
			
			// Just this button!
			
			if(gameState == 2){
				
				if(keyCode == 80 ||  key == 'p'){ // P letter
					  
					gamePaused = !gamePaused;
					  
				}
				
			}
			
			if(keyCode == 32 || keyCode == 10){ //Space Bar, Enter
				
				goToDestination();
				
			}
			
			if(keyCode == 120){
				
				StartNewGame();
				
			}
			
			if(keyCode == 38){ //Down Arrow
				
				currentButton--;
				
				if(currentButton < 0){
					
					currentButton = (buttons.size() - 1);
					
				}
				
			}	
			
			if(keyCode == 40){ //Up Arrow
				
				currentButton++;
				
				if(currentButton > (buttons.size() - 1)){
					
					currentButton = 0;
					
				}
				
			}
			    
			println("KeyCode: " + keyCode);
	
		}
	  
	}
	
	public void mousePressed(){
		
		goToDestination();
		
	}
	
	// Functions Shared by Both mousePressed and KeyPressed
	
	public void goToDestination(){		
		
		//Got to Destination
		
		button currentB = (button) buttons.get(currentButton);
		
		if(gameState == 0){
			
			//Since this is the first screen, we will now establish the current Language
			chosenLanguage = currentB.language;
			
		}
		
		gameState = currentB.destinationState;
		
		populateButtonsForState(gameState);
		
		if(gameState == 2){
			
			StartNewGame();
			
			if(currentB.otherState == 1){
				
				gameState2Mode = 1; 
				
				println("GAME STATE: " + gameState2Mode);
				
			}
			
			else if(currentB.otherState == 2){
				
				gameState2Mode = 2; 
				
				println("GAME STATE: " + gameState2Mode);
				
			}
			
			else {
				
				println("GAME STATE NON!!!!");
				
			}
			
			
		}
		
	}	
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { arabictetris.ArabicTetris.class.getName() });
	}
}
