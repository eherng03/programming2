
public class Mesa {
	
	private boolean[] hayTenedor = { true, true, true, true, true };
	public int filosofosEnMesa = 0;

	public synchronized void ObtenTenedor(int numFilosofo, int i) {	//al ser synchronized nadie puede obtener un tenedor a la vez que otro
		while (!hayTenedor[i]) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		//postcondicion el tenedr i esta libre

		System.out.println("Filosofo " + numFilosofo + " toma tenedor " + i);
		hayTenedor[i] = false;

	}

	public synchronized void DevolverTenedor(int numFilosofo, int i) {
		System.out
				.println("Filosofo " + numFilosofo + " devuelve tenedor " + i);
		hayTenedor[i] = true;
		notifyAll();

	}

	public synchronized void pedirmesa(int numFilosofo, int i) {
		while (filosofosEnMesa == 2) {
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		}
		System.out.println("Filosofo " + numFilosofo + " esta en la mesa.");
		filosofosEnMesa++;
	}

	public synchronized void DejarMesa(int numFilosofo, int i) {
		filosofosEnMesa--;

		if (filosofosEnMesa == 1) {
			notifyAll();

		}
	}

}

