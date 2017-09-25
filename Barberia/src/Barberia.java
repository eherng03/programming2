import java.util.ArrayList;

public class Barberia {
	
	public int NumeroSillas;
	public static Barbero []Barberos;
	public boolean[] haySillas; //true si la silla esta libre, false si esta ocupada
	public ArrayList <Cliente> clientesSentadosEnOrden  = new ArrayList<Cliente>();
	private static Barberia barberia;
	
	public static Barberia getBarberia() {
		if(barberia == null){
			barberia = new Barberia();
		}else{
			return barberia;
		}
		return barberia;
	}

	public void setNumeroSillas(int nextInt) {
		NumeroSillas = nextInt;
		hacerSillasLibres();
	}

	private void hacerSillasLibres() {
		haySillas = new boolean[NumeroSillas];
		for(int i = 0; i < NumeroSillas; i++){
			haySillas[i] = true;
		}
	}

	public void setBarberos(Barbero[] barberos) {
		Barberos = barberos;
	}

		

	public Barbero buscarBarberoDormido() throws InterruptedException {		//si hay barbero dormido hay barbero libre
		for(int i = 0; i < Barberos.length; i++){
			if(Barberos[i].dormido){
				return Barberos[i];	
			}
		}
		return null;
	}
}
