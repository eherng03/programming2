//package ccia.labarberia;

import java.util.Scanner;
import java.util.logging.*;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;


public class Main {

	public static int tiempoSimulacion;
	
	public static Logger logger = Logger.getLogger("ccia.labarberia");
	static {
		logger.setLevel(Level.OFF)
		//logger.setLevel(Level.WARNING)
		;}
	
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		
		int nBarberos = sc.nextInt();
		int nClientes = sc.nextInt();
		tiempoSimulacion = sc.nextInt();
		
		Cliente.distribucionNormal = new NormalDistribution(sc.nextInt(),sc.nextInt());

		Barbero.distribucionExponencial = new ExponentialDistribution(sc.nextInt());
		
		Barberia b = Barberia.getBarberia(); // La Barberia sigue el patrón Singleton
		
		Cliente.barberia = b;
		Barbero.barberia = b;
		b.setNumeroSillas(sc.nextInt());
		sc.close();
		
		Barbero[] barberos = new Barbero[nBarberos];
		for (int i = 1; i <= nBarberos; i++){
			barberos[i-1] = new Barbero(i);
			barberos[i-1].start();
		}
		b.setBarberos(barberos);
		
		Thread[] clientes = new Thread[nClientes];
		for (int j = 1; j <= nClientes; j++){
			clientes[j-1] = new Thread(new Cliente(j));
			clientes[j-1].start();
		}
		
		Thread.sleep(tiempoSimulacion*1000);
		
		for (int j=0; j<nClientes; j++){
			clientes[j].interrupt();
		}
	
		for (int i=0; i<nBarberos; i++){
			barberos[i].interrupt();
		}
		
		for (int j=0; j<nClientes; j++){
			clientes[j].join();
		}
	
		for (int i=0; i<nBarberos; i++){
			barberos[i].join();
		}
		
		
	}
}
