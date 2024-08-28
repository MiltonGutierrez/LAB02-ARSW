package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

public class PrimeFinderThread extends Thread{

	
	int a,b;
	boolean running = true;
	private List<Integer> primes=new LinkedList<Integer>();
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public void run(){
		for(int i = a; a < b; a++){
			if(running){
				if(isPrime(i)){
					primes.add(i);
				}

			}
			else{
				synchronized(primes){
					while(!running){
						try {
							primes.wait();
						} catch (Exception e) {
							e.printStackTrace();
						}		
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

	public void setRunningState(boolean running){
		this.running = running;
		if(running == true){
			synchronized(primes){
				primes.notify();
			}
		}
	}
}




