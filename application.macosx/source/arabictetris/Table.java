package arabictetris;
import processing.core.PApplet;
public class Table {
	  int rowCount;
	  String[][] data;
	  PApplet parent;
	  
	  Table(String filename, PApplet p) {
		  
		parent = p; 
		
	    String[] rows = parent.loadStrings(filename);
	    data = new String[rows.length][];
	    
	    for (int i = 0; i < rows.length; i++) {
	      if (parent.trim(rows[i]).length() == 0) {
	        continue; // skip empty rows
	      }
	      if (rows[i].startsWith("#")) {
	        continue;  // skip comment lines
	      }
	      
	      // split the row on the tabs
	      String[] pieces = parent.split(rows[i], parent.TAB);
	      // copy to the table array
	      data[rowCount] = pieces;
	      rowCount++;
	      
	      // this could be done in one fell swoop via:
	      //data[rowCount++] = split(rows[i], TAB);
	    }
	    // resize the 'data' array as necessary
	    data = (String[][]) parent.subset(data, 0, rowCount);
	  }
	  
	  
	  int getRowCount() {
	    return rowCount;
	  }
	  
	  
	  // find a row by its name, returns -1 if no row found
	  int getRowIndex(String name) {
	    for (int i = 0; i < rowCount; i++) {
	      if (data[i][0].equals(name)) {
	        return i;
	      }
	    }
	    parent.println("No row named '" + name + "' was found");
	    return -1;
	  }
	  
	  
	  String getRowName(int row) {
	    return getString(row, 0);
	  }


	  String getString(int rowIndex, int column) {
	    return data[rowIndex][column];
	  }

	  
	  String getString(String rowName, int column) {
	    return getString(getRowIndex(rowName), column);
	  }

	  
	  int getInt(String rowName, int column) {
	    return parent.parseInt(getString(rowName, column));
	  }

	  
	  int getInt(int rowIndex, int column) {
	    return parent.parseInt(getString(rowIndex, column));
	  }

	  
	  float getFloat(String rowName, int column) {
	    return parent.parseFloat(getString(rowName, column));
	  }

	  
	  float getFloat(int rowIndex, int column) {
	    return parent.parseFloat(getString(rowIndex, column));
	  }
	  
	  
	  void setRowName(int row, String what) {
	    data[row][0] = what;
	  }


	  void setString(int rowIndex, int column, String what) {
	    data[rowIndex][column] = what;
	  }

	  
	  void setString(String rowName, int column, String what) {
	    int rowIndex = getRowIndex(rowName);
	    data[rowIndex][column] = what;
	  }

	  
	  void setInt(int rowIndex, int column, int what) {
	    data[rowIndex][column] = parent.str(what);
	  }

	  
	  void setInt(String rowName, int column, int what) {
	    int rowIndex = getRowIndex(rowName);
	    data[rowIndex][column] = parent.str(what);
	  }

	  
	  void setFloat(int rowIndex, int column, float what) {
	    data[rowIndex][column] = parent.str(what);
	  }


	  void setFloat(String rowName, int column, float what) {
	    int rowIndex = getRowIndex(rowName);
	    data[rowIndex][column] = parent.str(what);
	  }  
	}