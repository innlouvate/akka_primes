package application;

public class PrimeWorker {

    public static void main(String[] args) {
        int start = 1;
        int end = 100;

        for(int i = start; i <= end; i++)
            if(isPrime(i))
                System.out.println(i);
    }

    private static boolean isPrime(int n) {
        if(n == 1)
            return false;
        else if(n == 2)
            return true;
        if(n % 2 == 0)
            return false;

        for(int i=3; i*i<=n; i+=2)
            if(n % i == 0)
                return false;
        return true;
    }

}
