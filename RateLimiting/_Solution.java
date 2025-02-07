package RateLimiting;

//Common solution to call for any of the five Rate Limiting Techniques
public class _Solution {
    public static void main(String[] args){
        //TokenBucket limiter = new TokenBucket(10, 1);
        //LeakyBucket limiter = new LeakyBucket(10, 1);
        //FixedWindowCounter limiter = new FixedWindowCounter(10, 10);
        SliderWindowLog limiter = new SliderWindowLog(10, 10);
        try{
            for(int i=1; i<=15; i++){
                System.out.println(i+" "+limiter.allowRequest());  //request id and check to allow or not
                Thread.sleep(100); //wait for 0.1 sec
            }
            Thread.sleep(10000); //wait of 10 secs 
            System.out.println(limiter.allowRequest());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
