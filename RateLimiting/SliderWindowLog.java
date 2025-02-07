package RateLimiting;

import java.util.*;
import java.time.*;

public class SliderWindowLog {
    private final long windowSizeInSeconds;     //size of the window in seconds
    private final long maxRequestsPerWindow;    // maximum number of request allowed per window
    private Queue<Long> requestTimeLog;          //stores the timestamp of each request;

    public SliderWindowLog(long windowSizeInSeconds, long maxRequestsPerWindow){
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.requestTimeLog = new LinkedList<>();
    }

    public synchronized boolean allowRequest(){
        long currentTime = Instant.now().getEpochSecond();
        long windowPeriod = currentTime-this.windowSizeInSeconds;

        while(!this.requestTimeLog.isEmpty() && this.requestTimeLog.peek()<windowPeriod){
            this.requestTimeLog.poll();
        }

        if(this.requestTimeLog.size()<this.maxRequestsPerWindow){
            this.requestTimeLog.add(currentTime);
            return true;                    //allow request
        }
        return false;
    } 
}
