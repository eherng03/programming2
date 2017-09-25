
public class Filosofo implements Runnable {
	
	private int numFilosofo;
	private  Mesa mesa;
	public Filosofo (int n, Mesa mesa){
		numFilosofo = n;
		this.mesa = mesa;
		System.out.println("Filosofo "+ numFilosofo +" creado.");
	}
	
	public void run(){
		try{
		while(true){
			pensar();
			comer();
		}
		}catch (InterruptedException e){
			System.out.print("Filosofo ha muerto");
		}
	}
	private void comer() throws InterruptedException{
		System.out.println("Filosofo " + numFilosofo +" intetado comer.");
		//Modulo 5 espara que cuando se pase de 5 vuelva al 1 y nunca pase de 5
		mesa.ObtenTenedor(numFilosofo , (numFilosofo -1) % 5); 	//tenedor de la izquierda
		mesa.ObtenTenedor(numFilosofo , numFilosofo % 5);		//tenedor de la derecha
		System.out.println("Filosofo " + numFilosofo +" comiendo.");
		Thread.sleep(2000);
		mesa.DevolverTenedor(numFilosofo , numFilosofo %5);
		mesa.DevolverTenedor(numFilosofo , (numFilosofo -1) %5);
		
	}
	private void pensar() throws InterruptedException{
		System.out.println("Filosofo " + numFilosofo + " pensando.");
		Thread.sleep(Math.round(1000 * Math.random()));
	}
}



