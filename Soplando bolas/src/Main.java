import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	
	public static ArrayList <ArrayList <Integer>> SOLUCIONES = new ArrayList<ArrayList <Integer>>();
	
	public static void main(String[]args){
		java.util.Scanner teclado = new java.util.Scanner(System.in);
		int filas = teclado.nextInt();
		int columnas = teclado.nextInt();
		//creo los tableros
	
		Tablero tablero = new Tablero(filas,columnas);
		//fijo las posiciones objetivo
		int posicionesObj = teclado.nextInt();
		if(posicionesObj > filas*columnas){
		    	System.out.println("Entrada errónea.");
		}else{
		    
		int objetivos[][] = new int[posicionesObj][2];
		for(int i = 0; i < posicionesObj; i++){
			objetivos[i][0] = teclado.nextInt();	//fila
			objetivos[i][1] = teclado.nextInt();	//columna
		}
		tablero.setObjetivos(objetivos);
		int id = 0;
		while(teclado.hasNextInt()){
			id++;	//a cada vuelta lo incremento, y empieza en 1
			tablero.addBola(new Bola(id, teclado.nextInt()-1, teclado.nextInt()-1));
		}
		
		//el programa solo funciona si hay el mismo numero de posiciones objetivo que de bolas, si hay menos bolas que casillas,
		//
		if(posicionesObj != id || id > (filas*columnas) || 1 > (filas*columnas)){
			System.out.println("Entrada errónea.");
		}//quitar este else if si a parte de [] hay que dar mas
		else if(tablero.esSolucion()){
			System.out.println("[]");
		}else{
		
			tablero.setNumeroDeBolas(id);
		
			//ARRAY AL QUE SE VAN AÑADIENDO LAS POSIBLES SOLUCIONES
			ArrayList <Tablero> movimientos = new ArrayList<Tablero>();
			
			movimientos.add(tablero);
			
			resolver(movimientos, SOLUCIONES, 0);	//empieza en el indice 0 del arraylist, que es el tablero inicial
			
			//imprimir soplidos formato [x, x, x, x], si no hay soplidos se imprime []

			if(SOLUCIONES.isEmpty()){
				System.out.println("Rompecabezas sin solución.");
			}else{
				if(tablero.esSolucion()){
					System.out.println("[]");
				}
				String[] cadena = aCadena(SOLUCIONES);//si hay que dar [] y mas se pone aqui el print
				Arrays.sort(cadena);
				for(int k = 0; k < cadena.length; k++){
					//System.out.println(SOLUCIONES.get(k));
					System.out.println(cadena[k]);
				}
				//System.out.println("Número de soluciones: " + cadena.length);
			}
		}
		}
		teclado.close();
	}
	

	private static String[] aCadena(ArrayList<ArrayList<Integer>> sOLUCIONES2) {
		String[] cadena = new String[sOLUCIONES2.size()];
		for(int i = 0; i < sOLUCIONES2.size(); i++){
			cadena[i] = sOLUCIONES2.get(i).toString();
		}
		return cadena;
	}

	private static ArrayList<ArrayList <Integer>> resolver(ArrayList<Tablero> movimientos, ArrayList<ArrayList <Integer>> SOLUCIONES2, int indice) {
		
			//if(indice == movimientos.size()){
				//return SOLUCIONES2;
			//}else{
		for(int k = 0; k < movimientos.size(); k++){
				Tablero tabActual = movimientos.get(k);
				//bucle que mueve todas las bolas, y genera los posibles tableros
				for(int i = 1; i <= tabActual.numeroBolas; i++){
					Tablero tabAux = new Tablero(tabActual);
					tabAux.soplarLineas(i);
					boolean igualAnterior = tabAux.equals(tabActual);
					if(!igualAnterior){
						boolean bucle = tabAux.esBucle(movimientos);
						if(!bucle){
							if(!SOLUCIONES2.contains(tabAux.soplidos)){
								movimientos.add(tabAux);		//añado el tablero generado al arraylist de tableros movidos
								if(tabAux.esSolucion()){		//si es solucion también añado los soplidos realizados en ese tablero al arraylist correspondiente
									SOLUCIONES2.add(tabAux.soplidos);
								}
								//System.out.println(tabAux.soplidos.toString());
							}
							
						}
					}
				}
		}
		
		return SOLUCIONES2;
			//indice = indice +1;
			//return resolver(movimientos, SOLUCIONES2, indice);
	}
}