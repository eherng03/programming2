import org.apache.commons.math3.distribution.NormalDistribution;

public class Cliente implements Runnable {

	public static Barberia barberia;
	public static NormalDistribution distribucionNormal;
	public int IDCliente;
	public boolean estaSentado;
	public boolean estaAtendido;
	public int silla;
	public boolean destruido;
	public boolean estaEnLaBarberia;
	
	
	public Cliente(int j) {
		IDCliente = j;
		System.out.println("El cliente " + IDCliente + " se ha creado.");
		this.estaAtendido = false;
		this.destruido = false;
	}
	
	public Cliente(Cliente cliente) {
		this.barberia = cliente.barberia;
		this.distribucionNormal = cliente.distribucionNormal;
		this.IDCliente = cliente.IDCliente;
		this.estaAtendido = cliente.estaAtendido;
		this.estaSentado = cliente.estaSentado;
	}

	public void run(){
		
		while(!destruido){
			try {
				pasear();	//esta un tiempo paseando hasta que entra a la barberia
				llegarBarberia();
			} catch (InterruptedException e) {
				System.out.println("El cliente " + this.IDCliente + " ha sido destruido.");
				destruido = true;
			}
		}
	}


	private void pasear() throws InterruptedException {
		Thread.sleep(Math.abs((long) distribucionNormal.sample()));
	}

	public synchronized void llegarBarberia() throws InterruptedException {
		if (!this.destruido && !this.estaEnLaBarberia) {	//si no esta destruido ni esta en la barberia llega
			System.out.println("El cliente " + this.IDCliente + " llega a la barbería.");
			this.estaEnLaBarberia = true;		//el cliente esta en la barberia
			Barbero barberoLibre = barberia.buscarBarberoDormido();
			if (barberoLibre != null) { //si hay barbero libre lo despierta
				barberoLibre.atenderCliente(this);
			} else {
				boolean hayLibre = false;
				for (int i = 0; i < barberia.NumeroSillas; i++) {
					if (barberia.haySillas[i]) { //si la silla i esta libre...
						hayLibre = true;
						//se sienta
						System.out.println("El cliente " + this.IDCliente + " se sienta en una silla de espera.");
						barberia.haySillas[i] = false;
						barberia.clientesSentadosEnOrden.add(this);
						this.estaSentado = true;
						this.silla = i;
						synchronized (this) {
							this.wait(); //El hilo se sienta a esperar a que un barbero le atienda
						}
						break; //cuando se sienta sale del for
					}
				}
				if (!hayLibre) { //si no hay sillas libres se va
					this.marcharBarberia();
				}
			} 
		}
	}

	public void marcharBarberia() throws InterruptedException{
		System.out.println("El cliente " + this.IDCliente + " se marcha sin ser atendido.");	
		this.estaAtendido = false;
		this.estaEnLaBarberia = false; //se marcha
	}
	
	
	

}
