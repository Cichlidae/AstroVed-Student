package ru.premaservices.astroved.student.var;

public class Constants {

	private Integer minSumToEnroll = Integer.MAX_VALUE;
	
	public Constants (Integer minSumToEnroll) {
		this.minSumToEnroll = minSumToEnroll;		
	}

	public Integer getMinSumToEnroll() {
		return minSumToEnroll;
	}
				
}
