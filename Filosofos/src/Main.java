
public class Main {

	public static void main(String[] args){
		int n = 5;
		Mesa mesa = new Mesa();
		for(int i = 1; i <= n; i++){
			//Crea 5 hilos, cada uno un filosofo
			new Thread (new Filosofo(i, mesa)).start();
		}
	}
}
