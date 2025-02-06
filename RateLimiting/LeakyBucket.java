package RateLimiting;
import java.time.*;
import java.util.*;

public class LeakyBucket {
    private final long capacity;        //Total requests a bucket can hold
    private final double leakRate;      //Rate at which requests process
    private Queue<Instant> requests;    //Time stamp of each request
    private Instant lastLeakTime;       //Time at which the bucket has completed precessing a recent request 
    private double partLeakedRequest;   //A fraction of request is leaked or process;

    public LeakyBucket(long capacity, double leakRate){
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.requests = new LinkedList<>();          //Initially the bucket is empty;
        this.lastLeakTime = Instant.now();
        this.partLeakedRequest = 0;
    }

    public void leak(){
        Instant currentTime = Instant.now();
        double timeElapsed = Duration.between(this.lastLeakTime, currentTime).toMillis();
        double requestsLeaked = (timeElapsed*this.leakRate)/1000.0;
        int fullRequests = (int)requestsLeaked;

        this.partLeakedRequest += requestsLeaked - fullRequests;
        if(this.partLeakedRequest>=1){
            fullRequests++;
            this.partLeakedRequest--;
        }
        for(int i=0; i<fullRequests && !this.requests.isEmpty(); i++){
            requests.poll();
        }

        this.lastLeakTime = currentTime;
    }

    public synchronized boolean allowRequest(){
        leak();

        if(this.requests.size()<this.capacity){
            requests.add(Instant.now()); //Add new request;
            return true;
        }

        return false;
    }
}

