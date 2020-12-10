import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class stockTradeRandomizer {
	
	stockTradeRandomizer str = new stockTradeRandomizer();
	
	public static void main (String[] args) {
		Random random = new Random();
		//new random stock price
		
		LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.parse("09:30"));
		//Start time at 9:30 (NYSE opens at 9:30)
		
		//create new random stock file
		try {
		      File stockFile = new File("stockprices.txt");
		      if (stockFile.createNewFile()) {
		        System.out.println("File created: " + stockFile.getName());
		      } else {
		        System.out.println("stockprices.txt already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred creating stockprices.txt");
		      e.printStackTrace();
		    }
		//create a handle to write to the file
		try {
		      FileWriter stockFileHandle = new FileWriter("stockprices.txt");
		     
		      for (int x=0; x<390; x++) {
		  		//writes 390 minutes of lines (corresponds to NYSE closing at 5p)
		  			
		  		
		  		int price = random.nextInt(1000);
			    stockFileHandle.write(price + " "+ String.format("%02d",time.plusMinutes(x).getHour()) + ":" + String.format ("%02d",time.plusMinutes(x).getMinute())+"\n");
			    
			    //#debug	
		  		//System.out.print(price + " ");
		  		//System.out.println(String.format("%02d",time.plusMinutes(x).getHour()) + ":" + String.format ("%02d",time.plusMinutes(x).getMinute()));
		  		
		  		}//end for loop
		      
		      stockFileHandle.close();
		      System.out.println("Successfully wrote to the stockprices.text file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred writing to stockprices.txt");
		      e.printStackTrace();
		    }
		
		
		
		
		
		
		
	}

}
