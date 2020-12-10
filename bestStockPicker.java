import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner; 
import java.util.ArrayList;

public class bestStockTrader {
	//assumes stockprices.txt at root (generated from stockTradeRandomizer.java)
	private static int firstPoint;
	private static int secondPoint;
	private static int thirdPoint;
	//need three points to determine maxima/minima
	
	private static LocalTime buyTime;
	private static LocalTime sellTime;

	private static int maxProfit;
	//maximum profit
	private static int sellIndex;
	private static int buyIndex;
	
	



	//List will alternate minima(even) maxima(odd)
	static ArrayList<Integer> criticalPoints = new ArrayList<Integer>();
	//list of critical times which will have the same index as their corresponding critical point
	static ArrayList<String> criticalTimes = new ArrayList<String>();
	//contains an ArrayListof Strings with the criticalTime + 1 minute (shifted in final print)
	
	//Probably a less efficient way by differentiating minima and maxima 
	static ArrayList<Integer> maxima = new ArrayList<Integer>();
	static ArrayList<Integer> minima = new ArrayList<Integer>();
	


	
	bestStockTrader trader = new bestStockTrader();
	
	public static void main(String[] args) {
		
		//reads file stockprices.txt and throws error if issues
		try {
		      File stockpricesFile = new File("stockprices.txt");
		      Scanner stockReader = new Scanner(stockpricesFile);
		      
		      //first two should be compared for first minimum/maximum
		      String firstStockPriceAndTime = stockReader.nextLine();
		      String firstPriceAndTime[]=firstStockPriceAndTime.split(" ");
		      firstPoint=Integer.parseInt(firstPriceAndTime[0]);
		      
		      
		      String secondStockPriceAndTime = stockReader.nextLine();
		      String secondPriceAndTime[]=secondStockPriceAndTime.split(" ");
		      secondPoint=Integer.parseInt(secondPriceAndTime[0]);
		      
		      if (secondPoint > firstPoint)	{
		    	  minima.add(firstPoint);
		    	  criticalPoints.add(firstPoint);
			      criticalTimes.add(secondPriceAndTime[1]);
			      //System.out.println(firstPoint + " at " + firstTime + " is a minima");
			      //System.out.println(firstPoint + " " + secondPoint);

		      }
		     
		      //This point can't be traded so it doesn't matter (also it disrupts the even/odd minima/maxima format of the arraylist)
		      //  if (secondPoint < firstPoint)	{
		      //	  maxima.add(firstPoint);
		      //}
		      
		      //needs better error checking for files at 3 or fewer lines
		     
		      
		      while (stockReader.hasNextLine()) {
		        String stockPriceAndTime = stockReader.nextLine();
		        
		        String priceAndTime[]=stockPriceAndTime.split(" ");
		        //needs better error checking
		        thirdPoint =Integer.parseInt(priceAndTime[0]);
		        	
		        if ( secondPoint <= firstPoint && secondPoint < thirdPoint) {
		        	minima.add(secondPoint);
		        	criticalTimes.add(priceAndTime[1]);
		        	criticalPoints.add(secondPoint);
		        	//System.out.println(secondPoint+ " at "+ firstTime +" is a minima");
		        	//System.out.println(firstPoint + " "+secondPoint + " "+thirdPoint);

		        
		        }
		        else if (secondPoint >= firstPoint && secondPoint > thirdPoint) {
		        	maxima.add(secondPoint);
		        	criticalTimes.add(priceAndTime[1]);
		        	criticalPoints.add(secondPoint);
		        	//System.out.println(secondPoint+ " at "+ firstTime +" is a maxima");
		        	//System.out.println(firstPoint + " "+secondPoint + " "+thirdPoint);
		        

		        }
		        
		        	//prepare variables for next read
		        	firstPoint = secondPoint;
		        	secondPoint = thirdPoint;	        
		        	//System.out.println(Integer.parseInt(priceAndTime[0])+ " "+ priceAndTime[1]);
		      }
		   
		      
		      
		      stockReader.close();
		      //maxima.forEach((n) -> System.out.println(n));
		      //minima.forEach((n) -> System.out.println(n));
		      
		      
		      
		    } catch (FileNotFoundException e) {
		      System.out.println("stockprices.txt was not found at root");
		      e.printStackTrace();
		    }
		
		   //checks for last local maximum
	      if (firstPoint < secondPoint) {
	    	  maxima.add(secondPoint);
	    	  criticalTimes.add("05:01");
	    	  criticalPoints.add(secondPoint);
	    	  //System.out.println(secondPoint+ " at "+ firstTime +" is a maxima");

	      }
			//	System.out.printf("%d,%d",maxima, minima);
	      //System.out.println("Minimum value is: "+Collections.min(criticalPoints));
	      //System.out.println("Maximum value is: "+Collections.max(criticalPoints));
	      
	      //if the size of the critical array is zero, there are no minima/maxima except those at the end/beginning so there is no trade to make money
	      if (criticalPoints.size()==0) return;
	      
	      
	      
	      
	      sellIndex = 1;
	      buyIndex = 0;
	      maxProfit = criticalPoints.get(sellIndex) - criticalPoints.get(buyIndex);
	      //System.out.println("Initial profit is: " + maxProfit + " Buy at: " + criticalPoints.get(buyIndex) + " Sell at: " + criticalPoints.get(sellIndex));
	      
	      
	      
	      int k = 1;
	      for(int i =0;i < criticalPoints.size() && k < criticalPoints.size();i+=2, k+=2) {
	    	  
	    	  for (int j = k;j < criticalPoints.size(); j +=2 ) {
	    		 // System.out.println("trying maxima "+j+" Value: "+ criticalPoints.get(j) + " Minima: " + criticalPoints.get(i));
	    		  if (criticalPoints.get(j)-criticalPoints.get(i)>maxProfit) {
		    		  //System.out.println("greater than maxProfit " + maxProfit + " minimaIndex: "+ i + ", "+criticalPoints.get(i)+" maximaIndex: "+ j + ", "+ criticalPoints.get(j) );
		    		  maxProfit = criticalPoints.get(j)-criticalPoints.get(i);
		    		  sellIndex = j;
		    		  buyIndex = i;
		    		  

	    		  }
	    		  
	    	  }
	    	  
	      }
	      //Shift times back one minute
	      
	      buyTime =LocalTime.parse(criticalTimes.get(buyIndex));
	      sellTime =LocalTime.parse(criticalTimes.get(sellIndex));
	      
	      System.out.println("Maximum profit is : " + maxProfit+ ", Buying when price is: " + criticalPoints.get(buyIndex) + " at: " + 
	      buyTime.minus(1,ChronoUnit.MINUTES) + ",  Selling when price is: " + criticalPoints.get(sellIndex) + " at: " +
	    		  sellTime.minus(1,ChronoUnit.MINUTES));
			
	}

	

}
