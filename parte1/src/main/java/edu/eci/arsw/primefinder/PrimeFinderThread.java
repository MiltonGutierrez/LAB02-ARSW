package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a,b;
	
	private List<Integer> primes;
	
	public PrimeFinderThread(int a, int b, LinkedList<Integer> primes) {
		super();
		this.a = a;
		this.b = b;
		this.primes = primes;
	}

	public void run(){
		while(true){
			try {
				wating();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	
	private void wating() throws InterruptedException{
		synchronized (primes){
			long t= System.currentTimeMillis();
			long end = t+5000;
			while(System.currentTimeMillis() < end ){
				if (isPrime(a)){
					primes.add(a);
					//System.out.println(a);
					System.out.println(System.currentTimeMillis());
				}
				a++;
			}	
		}
	}
}
