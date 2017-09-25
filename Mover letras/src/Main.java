import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	
	public final static String HAYSOL = "Sí hay solución.";
	public final static String NOHAYSOL = "No hay solución.";
	public final static String ENTRADAERRONEA = "Entrada errónea.";

	/**
	 * Metodo main
	 * @param args
	 */
	public static void main(String[] args) {
		java.util.Scanner teclado = new java.util.Scanner(System.in);
		
		String MENSAJE = "";
		int filas = 0;
		int columnas = 0;
		try {
			filas = teclado.nextInt();
			columnas = teclado.nextInt();
			Tablero.setDimension(filas, columnas); 			//Para que todos los tableros tengan siempre la misma dimension
			int numLetras = filas*columnas;
			
			String palabraFinal = teclado.next();
			String palabraInicial = teclado.next();
			
			char[] elementosFinales = palabraFinal.toCharArray();
			char[] elementosIniciales = palabraInicial.toCharArray();
			
			if(elementosFinales.length != elementosIniciales.length){
				//si tienen distinto numero de elementos la entrada es erronea
				MENSAJE = ENTRADAERRONEA;
			}else if(elementosFinales.length != numLetras){
				//si entra a este if es que ambas tienen la misma cantidad de elementos, pero tienen que ser igual al numero de casillas
				MENSAJE = ENTRADAERRONEA;
			}else if(mismasLetras(elementosFinales, elementosIniciales) == false){
				//si las letras son distintas no hay solucion
				MENSAJE = ENTRADAERRONEA;
			}else if(cuentaEspacio(elementosFinales) != 1){		
				//si en alguna de las cadenas introducidas no hay espacio no hay solucion
				MENSAJE = ENTRADAERRONEA;
			}else{
				
				Tablero tableroFinal = new Tablero(palabraFinal.toCharArray());
				Tablero tableroInicial = new Tablero(palabraInicial.toCharArray());
				
				ArrayList<Tablero> soluciones = new ArrayList<Tablero>();
				soluciones.add(tableroInicial);
				
				boolean solucion = resolverTablero( tableroFinal, soluciones, 0);
				
				if(solucion == true){
					MENSAJE = HAYSOL;
				}else{
					MENSAJE = NOHAYSOL;
				}
			}
			teclado.close();
			
		}catch (java.util.InputMismatchException e) {
			MENSAJE = ENTRADAERRONEA;
		}
			System.out.println(MENSAJE);
		}



	/**
	 * Método que devuelve true o false si tienen o no las mismas letras
	 * @param elementosFinales
	 * @param elementosIniciales
	 * @return true si las dos palabras tienen las mismas letras
	 */
	private static boolean mismasLetras(char[] elementosFinales, char[] elementosIniciales) {
		Arrays.sort(elementosFinales);
		Arrays.sort(elementosIniciales);
		for(int i = 0; i < elementosFinales.length; i++){
			if(elementosFinales[i] != elementosIniciales[i]){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Método que devuelve un array que corresponde a la posicion en la que se encuentra el asterisco
	 * @param tableroInicial
	 * @return [fila, columna]
	 */
	@SuppressWarnings("static-access")
	private static int[] getPosicion(Tablero tableroInicial) {
		int[] posicion = new int[2];		//dimension dos por que tiene dos cordenadas, fila y columna
		char[][] tableroAux = tableroInicial.tablero;
		for(int i = 0; i < tableroInicial.filas; i++){	
			for(int j = 0; j < tableroInicial.columnas; j++){
				if(tableroAux[i][j] == '*'){
					posicion[0] = i;
					posicion[1] = j;
					return posicion;
				}
			}
		}
		return null;
	}

	/**
	 * metodo que cuenta cuantos asteriscos tiene la palabra, el asterisco representa un espacio en el puzzle
	 * @param elementos
	 * @return contador de asteriscos
	 */
	private static int cuentaEspacio(char[] elementos) {	
		int contador = 0;
		for(int i = 0; i < elementos.length; i++){
			if(elementos[i] == '*'){
				contador ++;
			}
		}
		return contador;
	}

	/**
	 * Método que pasandole dos tableros devuelve true si de uno se puede llegar al otro
	 * Precondidion: el tablero inicial esta en el primer elemento del arraylist
	 * @param tableroInicial
	 * @param tableroFinal
	 * @return
	 */
	@SuppressWarnings("static-access")
	private static boolean resolverTablero( Tablero tableroFinal, ArrayList<Tablero> soluciones, int indice) {
		
		int[] posicionEspacio = new int[2];
		for(indice = 0; indice < soluciones.size(); indice++){
			
			Tablero tableroAmover = soluciones.get(indice);
			
			if(tableroAmover.equals(tableroFinal)){		//si ambos tableros son iguales ya tiene solucion
				return true;
			}else{
				posicionEspacio = getPosicion(tableroAmover);
				int[] arriba = {posicionEspacio[0]-1, posicionEspacio[1]};
				int[] derecha = {posicionEspacio[0], posicionEspacio[1]+1};
				int[] abajo = {posicionEspacio[0]+1, posicionEspacio[1]};
				int[] izquierda = {posicionEspacio[0], posicionEspacio[1]-1};
				
				Tablero tableroActual = new Tablero(tableroAmover.tablero);
				//el tablero va cambiandose a media que lo uso, asi que cada vez que lo uso tengo que volver a ponerlo en el valor inicial
				
				//si existe la casilla de arriba...
				if(0 <= posicionEspacio[0]-1 &&  posicionEspacio[0]-1 < tableroActual.filas && 0 <= posicionEspacio[1] && posicionEspacio[1] < tableroActual.columnas){
					tableroActual = new Tablero(tableroAmover.tablero);
					if(mover(tableroActual, arriba, posicionEspacio).equals(tableroFinal)){
						return true;
					}else if(estaRepetido(soluciones, tableroActual) == false){	//si es falso que esta repetido...
						soluciones.add(tableroActual );
					}
				}
				//Si existe la casilla de la derecha...
				if(0 <= posicionEspacio[0] &&  posicionEspacio[0] < tableroActual.filas && 0 <= posicionEspacio[1]+1 && posicionEspacio[1]+1 < tableroActual.columnas){
					tableroActual = new Tablero(tableroAmover.tablero);
					if(mover(tableroActual, derecha, posicionEspacio).equals(tableroFinal)){
						return true;
					}else if(estaRepetido(soluciones, tableroActual) == false){	//si es falso que esta repetido...
						soluciones.add(tableroActual );
					}
				}
				//Si existe la casilla de abajo...
				if(0 <= posicionEspacio[0]+1 &&  posicionEspacio[0]+1 < tableroActual.filas && 0 <= posicionEspacio[1] && posicionEspacio[1] < tableroActual.columnas){
					tableroActual = new Tablero(tableroAmover.tablero);
					if(mover(tableroActual, abajo, posicionEspacio).equals(tableroFinal)){
						return true;
					}else if(estaRepetido(soluciones, tableroActual) == false){	//si es falso que esta repetido...
						soluciones.add(tableroActual );
					}
				}
				//Si existe la casilla de la izquierda...
				if(0 <= posicionEspacio[0] &&  posicionEspacio[0] < tableroActual.filas && 0 <= posicionEspacio[1]-1 && posicionEspacio[1]-1 < tableroActual.columnas){
					tableroActual = new Tablero(tableroAmover.tablero);
					if(mover(tableroActual, izquierda, posicionEspacio).equals(tableroFinal)){
						return true;
					}else if(estaRepetido(soluciones, tableroActual) == false){	//si es falso que esta repetido...
						soluciones.add(tableroActual );
					}
				}
			}
		}//cierra el for
		return false;
	}

	/**
	 * Metodo que devuelve true si el tablero ya esta en el arraylist de soluciones
	 * @param soluciones
	 * @param mover
	 * @return
	 */
	private static boolean estaRepetido(ArrayList<Tablero> soluciones, Tablero mover) {
		for(int i = 0; i < soluciones.size(); i++){
			if(soluciones.get(i).equals(mover) == true){
				return true;
			}
		}
		return false;
	}

	private static Tablero mover(Tablero tableroActual, int[] direccion, int[] espacio) {
		char[][] puzzle = tableroActual.tablero;	//creo un nuevo tablero igual que el actual para que no me modifique el actual
		char auxiliar = puzzle[espacio[0]][espacio[1]];
		puzzle[espacio[0]][espacio[1]] = puzzle[direccion[0]][direccion[1]];	//meto en el espacio lo que tenga la casilla a la que lo muevo
		puzzle[direccion[0]][direccion[1]] = auxiliar;			
		Tablero movido = new Tablero(puzzle);
		return movido;
	}
}