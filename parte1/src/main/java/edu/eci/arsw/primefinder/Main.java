package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		LinkedList<Integer> primes = new LinkedList<>();
		PrimeFinderThread pft0=new PrimeFinderThread(0, 10000000, primes);
		PrimeFinderThread pft1=new PrimeFinderThread(10000001, 20000000, primes);
		PrimeFinderThread pft2=new PrimeFinderThread(20000001, 30000000, primes);
		pft0.start();
		pft1.start();
		pft2.start();

		Scanner readinput = new Scanner(System.in);
		System.out.println("Presione ENTER para continuar");
		readinput.nextLine();

		while(true){
			if(true){
				System.out.println("hola");
				primes.notifyAll();
			}
			else{

			}
		}
	}
	
}
