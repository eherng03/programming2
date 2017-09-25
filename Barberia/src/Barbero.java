import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Barbero extends Thread {
	
	public char IDBarbero; 
	public static ExponentialDistribution distribucionExponencial;
	public static Barberia barberia;
	public boolean dormido;
	public boolean atendiendo;
	public boolean destruido;
	public boolean seguir;

	public Barbero(int i) {
		IDBarbero = crearIDBarbero(i);
		System.out.println("El barbero " + IDBarbero + " se ha creado.");
	}
	
	public void run(){
		seguir = true;
		while(seguir){
			try {
				
				//duerme si no esta dormido ni atendiendo, y las sillas estan vacías
				if(!this.dormido && !this.atendiendo && barberia.clientesSentadosEnOrden.isEmpty()){
					System.out.println("El barbero " + this.IDBarbero + " se pone a dormir.");
					this.dormido = true;
					synchronized(this){
						wait();
					}
				}else if(!barberia.clientesSentadosEnOrden.isEmpty()){		//si hay alguna silla de espera con gente
					//el barbero atiende al siguiente cliente del array
					this.atenderClienteSentado();
				}
			}catch (InterruptedException e) {
				System.out.println("El barbero " + this.IDBarbero + " ha sido destruido.");
				seguir = false;
				this.destruido = true;
				}
			}
		}
	
	

	private synchronized void atenderClienteSentado() throws InterruptedException{
		if(!this.destruido){		//si el barbero ya esta destruido no lo despierta
			Cliente cliente = barberia.clientesSentadosEnOrden.get(0);
			//dejo libre la silla en la que estaba el cliente
			barberia.haySillas[cliente.silla] = true;
			this.atendiendo = true;	//el barbero esta atendiendo
			System.out.println("El barbero " + this.IDBarbero + " atiende al cliente " + cliente.IDCliente + ".");
			cliente.estaAtendido = true;
			barberia.clientesSentadosEnOrden.remove(0);
			this.cortarPelo(cliente);
		}
	}

	private char crearIDBarbero(int i){
		char nuevaID = ' ';
		switch(i){
			case 1:
				nuevaID = 'A';	break;
			case 2:
				nuevaID = 'B';	break;
			case 3:
				nuevaID = 'C';	break;
			case 4:
				nuevaID = 'D';	break;
			case 5:
				nuevaID = 'E';	break;
			case 6:
				nuevaID = 'F';	break;
			case 7:
				nuevaID = 'G';	break;
			case 8:
				nuevaID = 'H';	break;
			case 9:
				nuevaID = 'I';	break;
			case 10:
				nuevaID = 'J';	break;
			case 11:
				nuevaID = 'K';	break;
			case 12:
				nuevaID = 'L';	break;
			case 13:
				nuevaID = 'M';	break;
			case 14:
				nuevaID = 'N';	break;
			case 15:
				nuevaID = 'O';	break;
			case 16:
				nuevaID = 'P';	break;
			case 17:
				nuevaID = 'Q';	break;
			case 18:
				nuevaID = 'R';	break;
			case 19:
				nuevaID = 'S';	break;
			case 20:
				nuevaID = 'T';	break;
			case 21:
				nuevaID = 'U';	break;
			case 22:
				nuevaID = 'V';	break;
			case 23:
				nuevaID = 'W';	break;
			case 24:
				nuevaID = 'X';	break;
			case 25:
				nuevaID = 'Y';	break;
			case 26:
				nuevaID = 'Z';	break;
		}
		return nuevaID;
	}

	
	public synchronized void cortarPelo(Cliente cliente) throws InterruptedException{				//tarda ese tiempo en cortarle el pelo al cliente
		this.despertarBarbero(cliente);
		synchronized (cliente) {
			cliente.notify();
		}
		Thread.sleep(Math.abs((long)distribucionExponencial.sample()));
		System.out.println("El barbero " + this.IDBarbero + " ha cortado el pelo al cliente " + cliente.IDCliente + ".");
		this.atendiendo = false;
		cliente.estaAtendido = false;
		cliente.estaEnLaBarberia = false; 	//El cliente se va y vuelve a pasear, continua el bucle while
		//this.despertarBarbero(cliente);
	}


	
	 public synchronized void despertarBarbero(Cliente cliente) throws InterruptedException {
	 if(!this.destruido && this.dormido){		//si el barbero ya esta destruido no lo despierta
				Barbero barbero = this;
				synchronized(barbero){
					notify();
				}
				barbero.dormido = false;
				//barbero.atenderCliente(cliente);
			
		}
	 }
	 
		
		
	

	public synchronized void atenderCliente(Cliente cliente) throws InterruptedException {
		if(!cliente.estaAtendido){
			this.atendiendo = true;
			System.out.println("El barbero " + this.IDBarbero + " atiende al cliente " + cliente.IDCliente + ".");
			cliente.estaAtendido = true;
			this.cortarPelo(cliente);
		}
	}
}
