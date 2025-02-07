package RateLimiting;

import java.time.*;

public class FixedWindowCounter {
    private final long windowSizeInSeconds;     //Size of eah window in seconds
    private final long maxRequestsPerWindow;    //Maximum number of requests that can be made in a window
    private long requestsCounter;               // Count of number of request in the current window
    private Instant windowStartTime;            //Gives the current window start time

    public FixedWindowCounter(long windowSizeInSeconds, long maxRequestsPerWindow){
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.requestsCounter = 0;
        this.windowStartTime = Instant.now();
    }
    public synchronized boolean allowRequest(){
        Instant currentTime = Instant.now();
        long elapsedTime = Duration.between(this.windowStartTime, currentTime).toSeconds();
        if(elapsedTime>=this.windowSizeInSeconds){
            this.windowStartTime = Instant.now();
            this.requestsCounter = 0;
        } 
        if(this.requestsCounter<maxRequestsPerWindow){
            this.requestsCounter++;
            return true;        //allows the request
        }
        return false;
       }
}
