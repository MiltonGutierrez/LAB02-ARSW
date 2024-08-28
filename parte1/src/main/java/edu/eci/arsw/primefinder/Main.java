package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		LinkedList<PrimeFinderThread> threads = new LinkedList<>();
		LinkedList<Integer> primes = new LinkedList<>();
		PrimeFinderThread pft0=new PrimeFinderThread(1, 10000000);
		PrimeFinderThread pft1=new PrimeFinderThread(10000001, 20000000);
		PrimeFinderThread pft2=new PrimeFinderThread(20000001, 30000000);

		threads.add(pft0);
		threads.add(pft1);
		threads.add(pft2);

		for(PrimeFinderThread pt: threads){
			pt.start();
		}

		boolean running = true;
		int primesFound = 0;
		while(running){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for(PrimeFinderThread pt: threads) {
				pt.stopRunning();
				primesFound += pt.getPrimes().size();
			}

			System.out.println("Primes found: " + primesFound);

			primesFound = 0;

			Scanner readinput = new Scanner(System.in);
			System.out.println("Presione ENTER para continuar");
			readinput.nextLine();

			running = false;

			for(PrimeFinderThread pt: threads){
				if(pt.isAlive()){
					running = true;
					pt.startRunning();
				}
			}		
		}

		for(PrimeFinderThread pt: threads){
			primesFound += pt.getPrimes().size();
		}
		System.out.println("Total quantity of primes found: " + primesFound);


	}
}

 