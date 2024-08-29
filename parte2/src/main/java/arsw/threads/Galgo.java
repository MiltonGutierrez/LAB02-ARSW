package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private boolean stop;

	//Constructor
	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
		this.stop = false;
	}


	public void corra() throws InterruptedException {
		while (paso < carril.size()) {
			Thread.sleep(100);
			//metodo sea ejecutado por un solo hilo a la vez (detener temporalmente)
			synchronized (this) {
				while (stop) {
					// si stop es verdadero (espere)
					this.wait();
				}
			}
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);

			synchronized (this) {
				if (paso == carril.size()) {
					//carril.finish(); //se quita para mejorar la eficiencia del programa al reducir el tiempo en que se mantiene el bloqueo
					int ubicacion = regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada(ubicacion + 1);
					System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
					if (ubicacion == 1) {
						regl.setGanador(this.getName());
					}
				}
			}
		}
	}

	//Implementación metodo que 'despierta' a los hilos bloqueados y reanuden su trabajo.
	public synchronized void stop(boolean stop) {
		this.stop = stop;
		if(!stop) {
			this.notifyAll();
		}
	}

	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
