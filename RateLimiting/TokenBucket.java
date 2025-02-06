package RateLimiting;
import java.time.*;
import java.util.*;

public class TokenBucket {
    private final long capacity;                //Capacity of the buket
    private final double fillRate;              //Rate at which the bucket is filled with token
    private Queue<Instant> tokensInTime;        //Queue to maintain token enter time stamp
    private Instant lastFillTime;               //Last fill time of the bucket
    private double partToken;             //Has the fraction of token
    
    public TokenBucket(long capacity, double fillRate){
        this.capacity = capacity;
        this.fillRate = fillRate;
        this.tokensInTime = new LinkedList<>();
        for(int i=0; i<this.capacity; i++){
            this.tokensInTime.add(Instant.now());
        }
        this.lastFillTime = Instant.now();
        this.partToken = 0;
    }

    public void refill(){
        Instant currentTime = Instant.now();
        double timeElapsed = Duration.between(this.lastFillTime, currentTime).toMillis();
        double tokenstoAdd = (timeElapsed*this.fillRate/1000.0);
        int fullTokens = (int)(tokenstoAdd);

        this.partToken += (tokenstoAdd - fullTokens);
        if(this.partToken>=1){
            fullTokens++;
            this.partToken --;
        }
        //From the missedPartToken use and computation can be ignored if percesion is not required

        for(int i=0; i<fullTokens && this.tokensInTime.size()<this.capacity; i++){
            this.tokensInTime.add(Instant.now());
        }

        this.lastFillTime = currentTime;
    }

    public synchronized boolean allowRequest(){
        refill();

        if(this.tokensInTime.isEmpty()){
            return false;       //Drop request as there are no enough tokens
        }

        this.tokensInTime.poll();

        return true;
    }

}


