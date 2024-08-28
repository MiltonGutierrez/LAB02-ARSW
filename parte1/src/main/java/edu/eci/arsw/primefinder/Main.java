package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Boolean running = true;
		LinkedList<Integer> primes = new LinkedList<>();

		PrimeFinderThread pft0=new PrimeFinderThread(0, 10000000);
		PrimeFinderThread pft1=new PrimeFinderThread(10000000, 20000000);
		PrimeFinderThread pft2=new PrimeFinderThread(20000000, 30000000);

		PrimeFinderThread[] threads = {pft0, pft1, pft2};

		int primesFound = 0;


		for(PrimeFinderThread pt: threads){
			pt.start();
		}

		while(true){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for(PrimeFinderThread pt: threads){
				pt.setRunningState(false);
				primesFound += pt.getPrimes().size();
			}

			System.out.println("Primos encontrados: " + primesFound);

			Scanner input = new Scanner(System.in);
			System.out.println("Presione ENTER para continar:");
			input.nextLine();

			running = false;

			for(PrimeFinderThread pt: threads){
				if(pt.isAlive()){
					running = true;
				}
			}

			
			
		}
		
	}
}

 