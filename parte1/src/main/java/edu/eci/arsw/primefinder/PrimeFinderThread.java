package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

public class PrimeFinderThread extends Thread{

	
	int a,b;
	Boolean running;
	private List<Integer> primes=new LinkedList<Integer>();
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public void run(){
		while(true){
			long actualTime= System.currentTimeMillis();
			long endTime = actualTime+5000;
			synchronized(this){
			while(System.currentTimeMillis() >= endTime){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while(endTime > actualTime){
				if (isPrime(a)){
					primes.add(a);
					System.out.println(a);
				}
				a++;
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
		return primes;
	}

	public void resumeThread(){
		notify();
	}
}




