package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

public class PrimeFinderThread extends Thread{

	
	int a,b;
	
	private List<Integer> primes;
	private boolean running = true;
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
		this.primes = new LinkedList<>();
	}

	@Override
	public void run(){
		while (a <= b){
				if(running){
					if (isPrime(a)){
						primes.add(a);
					}				
					a++;
				}
				else{
					synchronized(this){
						try {
							wait();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
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
		synchronized(primes){
			return primes;
		}
		
	}
	
	public void startRunning(){
		running = true;
		synchronized(this){
			notify();
		}
	}

	public void stopRunning(){
		running = false;
	}
		
}




