package com.starpeppy.replaycore.utilities;

public class SystemTime {


	// false = credits, true = debits
	public SystemTime() {

	}

	// gets system time.
	public double calculateDifference(double sysTime, double currentSysTime){
		// Calculate the amount of time that has passed
		// since the time stored.
		// Compare the current time with the time
		// put in to the calculator.
		
		double timeDifference = currentSysTime - sysTime;
		
		
		
		
		return timeDifference;
	}
	//usage: double checkCooldown = SystemTime().calculateDifference(storedOldSysTime);
	// returns difference. if you wanted like 200 seconds to
	// pass then you would compare to 200 seconds.
}
