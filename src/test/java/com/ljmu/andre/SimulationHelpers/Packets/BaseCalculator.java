package com.ljmu.andre.SimulationHelpers.Packets;

public class BaseCalculator {
	  
       int[] preload = new int[] { 1, 2, 3, 4, 10, 20, -1, 5 };
       
         private int i;
         
      
	private OPERATION setOperation = OPERATION.ADDITION;
    
  public enum OPERATION {
  		ADDITION,
  		MULTIPLY;
	  
  	}

  public BaseCalculator() {
	  
  }
	public BaseCalculator(int...preload) {
		
		this.preload = preload;
	}
   
		public void setFirst(int preload) {
		 this.preload[i] = preload;
		
	}
	public int getFirst() {
	
		return this.preload[i];
	}

	public void setSecond(int preload) {
		this.preload[i+1] = preload;
	}

		public int getSecond() {
	
		return this.preload[i+1] ;
	}

	public void setOperation(OPERATION addition) {
		
		if (addition==OPERATION.ADDITION)
			
			this.setOperation = OPERATION.ADDITION;
		else
			this.setOperation = OPERATION.MULTIPLY;
		
	}
	
	public OPERATION getOperation() {
	
		return this.setOperation;
			}
	
	
	
	
	public int solution(int... preload) {
			
		if( this.setOperation == OPERATION.ADDITION)
			return (getFirst() + getSecond()) ;
			else
				return (getFirst() * getSecond()) ;
		      
		
		
	}
	
	
}
