package RateLimiting;

public class _Solution {
    public static void main(String[] args){
        //TokenBucket limiter = new TokenBucket(10, 1);
        LeakyBucket limiter = new LeakyBucket(10, 1);
        try{
            for(int i=1; i<=15; i++){
                System.out.println(i+" "+limiter.allowRequest());
                Thread.sleep(100); //wait for 0.1 sec
                 /*prints true for first 11(10+1): 10 for capacity and refill after a fixed rate*/
            }
            Thread.sleep(5000); //wait of 5 secs fills the bucket with 5 more tokens
            System.out.println(limiter.allowRequest());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
