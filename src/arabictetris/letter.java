package arabictetris;

import processing.core.PApplet;

public class letter {

	  float x;
	  float y;  
	  float rotation; 
	  float wth; 
	  float hgt; 
	  int letterId;
	  int order;
	  int position;
	  boolean settled; 
	  int letterType; 
	  PApplet parent; // The parent PApplet that we will render ourselves onto
	  
	  int xBlocks; 
	  int yBlocks;
	  
	  float currentBorder;
	  float margin;
	  
	  letter(int TletterId, int TletterType, int Tposition, PApplet p) {
		  
		parent = p; 
		
		letterType = TletterType; //Start, Middle, End, Alone (0-3)
		  
	    letterId = TletterId;

	    //Height and Width
	    
	    wth = ((ArabicTetris) parent).imageArray[letterId][letterType].width; 

	    hgt = ((ArabicTetris) parent).imageArray[letterId][letterType].height;
	    
	    xBlocks = (int) ((wth -1) / 9);
	    		
	    yBlocks = (int) ((hgt -1) / 9);
	    
	    //More...
	    
	    position = Tposition; 

	    rotation = 90 * ((int) parent.random(1, 4)); 

	    //The X is automatically set, depending on the order
	    
	    margin = ((ArabicTetris) parent).margin;

	    x = (parent.width - margin) - (position * 100) - 300;

	    //Y is generated Randomly

	    y = ((ArabicTetris) parent).baseline - hgt + ((int) parent.random(-4,4)) * ((ArabicTetris) parent).blockHeight;
	  }

	  void update() {

	    float xDif = 0;

	    float yDif = 0;
	    
	    float letterPosition = x;
	    
	    if (rotation == 0){
	    	
	    	letterPosition = wth + x;
	    	
	    }

	    else if (rotation == 90) {

	      xDif += wth;
	      
	      yDif += hgt - wth;
	      
	      letterPosition = wth + x;
	      
	    }

	    else if (rotation == 270) {

	      xDif += wth - hgt;
	      
	      yDif += hgt;
	      
	      letterPosition = hgt + x + xDif;
	      
	    }

	    else if (rotation == 180) {

	      yDif += hgt;

	      xDif += wth;
	      
	      letterPosition = x + wth;
	    }
	    
	    parent.pushMatrix();
	    
	    parent.translate( letterPosition, (float) (((ArabicTetris) parent).baseline - ( hgt * 0.15 )));
	    
		    parent.fill(255);
		    
		    parent.textSize(12);
		    
		    parent.text(((ArabicTetris) parent).alphabetArray[letterId][1 ],0,-100);
	    
	    parent.popMatrix();

	    parent.pushMatrix();

	    parent.translate(x + xDif, y + yDif);
	
		    parent.rotate(PApplet.radians(rotation)); 
	
		    //parent.image(((ArabicTetris) parent).imageArray[letterId][letterType], 0, 0, wth, hgt);   
		    
		    //PApplet.println("Calling Image: " + letterId + " / " + letterType);
		    
		    parent.image(((ArabicTetris) parent).imageArray[letterId][letterType], 0, 0); 
	    
	    parent.popMatrix();
	    
	    /* For Seeing the Position of the Line

	    stroke(0);

	    line(x + wth, 0, x + wth, height); 
	    
	    */

	    checkAndIncrease(false); 
	    
	  }

	  void rotateLetterRight() {

	    rotation += 90;

	    if (rotation == 360) {

	      rotation = 0;
	    }

	   // PApplet.println("New Rotation: " + rotation);
	  }

	  void rotateLetterLeft() {

	    rotation -= 90;

	    if (rotation == -90) {

	      rotation = 270;
	    }

	    //PApplet.println("New Rotation: " + rotation);
	  }

	  void advanceLetter() {

	    checkAndIncrease(true);
	    
	  }

	  void moveLetter(boolean direction) {
	    
	    if(!direction){ //UP
	    
	      y += ((ArabicTetris) parent).blockHeight;
	    
	    }
	    
	    else {
	      
	      y -= ((ArabicTetris) parent).blockHeight;
	      
	    }
	    
	  }
	  
	  void showImage(float maxW, float maxH){
		  
		  float originalImageWidth  = (float) ((ArabicTetris) parent).imageArray[letterId][letterType].width;
		  
		  float originalImageHeight = (float) ((ArabicTetris) parent).imageArray[letterId][letterType].height;
		  
		  //MiddlePoint = 75,75
		  
		  float imgH;
		  
		  float imgW;
		  
		  float xPos; 
		  
		  float yPos; 
		  
		  if(originalImageWidth > originalImageHeight){
			  
			  imgW = maxW;
			  
			  imgH = (originalImageHeight/originalImageWidth) * imgW; 
			  
		  }
		  
		  else {
			  
			  imgH = maxH;
			  
			  imgW = (originalImageWidth/originalImageHeight) * imgH; 
			  
		  }
		  
		  xPos = 75 - imgW/2; 
		  
		  yPos = 75 - imgH/2;
		  
		  parent.image(((ArabicTetris) parent).imageArray[letterId][letterType], xPos, yPos, imgW, imgH); 
		  
		  //PApplet.println(xPos + " - " + yPos + " / " + imgW + " - " + imgH);
		  
	  }
	  
	  void checkAndIncrease(boolean advance){
		  
		currentBorder = ((ArabicTetris) parent).currentBorder;
	   
	    if (!settled) {
	      
	      if(advance){
	         
	      x = currentBorder - wth;
	      
	      }
	      
	      else {
	       
	      x++;  
	        
	      }

	      if ((x + wth) >= currentBorder) {
	    	  
	    	//Calculate the appropriate Y position, taking baseline into consideration
	    	  
	    	float targetYposition = (((ArabicTetris) parent).baseline - hgt);
	    	
	    	String baselineString = ((ArabicTetris) parent).alphabetArray[letterId][(letterType * 2) + 3];
	    	
	    	int baseline = Integer.parseInt(baselineString);
	    	
	    	//PApplet.println("LetterType: " + letterType + " / LetterId: " + letterId + " / " + baseline);
	    	
	    	if(baseline != 0){
	    		
	    		targetYposition += ((baseline) * ((ArabicTetris) parent).blockHeight);
	    		
	    	}
	    	  
	    	//Now that the new correct height (with baseline) is established, go on to the next step

	        if (rotation == 0 && y == targetYposition 
	        		
	        		|| letterId == 0 && y == targetYposition && (rotation == 0 || rotation == 180)
	        		
	        		|| letterId == 25 && y == targetYposition && letterType == 3
	        		
	        		) {

	          //The Letter has been positioned correctly!

	          settled = true;
	          
	          ((ArabicTetris) parent).score += parent.frameRate; 

	          ((ArabicTetris) parent).currentBorder -= wth;

	          ((ArabicTetris) parent).currentLetter++; 

	          //Lever is over, wuju!

	          if (((ArabicTetris) parent).currentLetter >= ((ArabicTetris) parent).letters.size()) {

	            // You Win!

	        	  ((ArabicTetris) parent).gameState = 3;
	        	  
	        	  ((ArabicTetris) parent).endGame(1);
	            
	          }
	          
	        }

	        else {

	          // Game over baby, you screwed up!
	        	
	        	//PApplet.println("GameOver: " + y + " / "+ targetYposition);

	        	((ArabicTetris) parent).gameState = 4;
	        	
	        	((ArabicTetris) parent).endGame(2);
	          
	        }
	        
	      }
	      
	    }

	    else {
	    
	    } 
	    
	  }
	}

