package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Boolean running = true;
		LinkedList<Integer> primes = new LinkedList<>();

		PrimeFinderThread pft0=new PrimeFinderThread(0, 10000000);
		PrimeFinderThread pft1=new PrimeFinderThread(10000001, 20000000);
		PrimeFinderThread pft2=new PrimeFinderThread(20000001, 30000000);

		PrimeFinderThread[] threads = {pft0, pft1, pft2};
		while(true){
			for(PrimeFinderThread pt: threads){
				if(!pt.isAlive()){
					pt.start();
				}
				else{
					pt.resumeThread();
				}
			}

			Scanner readinput = new Scanner(System.in);
			System.out.println("Presione ENTER para continuar");
			String input = readinput.nextLine();

			while(!input.equals("")){
				System.out.println("Presione ENTER para continuar");
				input = readinput.nextLine();
			}
		}

	}
}

 