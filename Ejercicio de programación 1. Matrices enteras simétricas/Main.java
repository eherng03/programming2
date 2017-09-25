import java.util.Scanner;

public class Main {
	/*
	 * entradas erróneas cuando hay numeros negativos, palabras, 
	 * menos numeros de los necesarios...
	 */
	public static int inicioRaiz = 1;
	public static String simetrica = "Es simétrica la raíz cuadrada entera de la matriz de entrada.\n";
	public static String noSimetrica = "No es simétrica la raíz cuadrada entera de la matriz de entrada.\n";
	public static String error = "Entrada errónea.\n";
	public static int contador = 0;
	
	public static void main(String[] args) {
		
		Scanner teclado = null;
		try {
			teclado = new java.util.Scanner(System.in);
		} catch (Exception e1) {
			System.out.print(error);
			System.exit(0);
		}
		
		//BLOQUE QUE COMPRUEBA QUE LA DIMENSION ES UN ENTERO
		int dim = 0;
		try {
			dim = teclado.nextInt();
		} catch (Exception e) {
			System.out.print(error);
			System.exit(0);
		}
		
		if(dim < 0){					//si la dimension es negativa imprime el mensaje de error y sale de la ejecucion
			System.out.print(error);
			System.exit(0);
		}
		
		
		
		int [][] matrizIni = new int[dim][dim];		//creo la matriz vacia en la que voy a meter los numeros
		
		matrizIni = llenarMatRecu(matrizIni, 0, 0, dim, teclado);			//llamo al método recursivo para rrellenarla
		
		int [][] matrizRaiz = new int[dim][dim];
		
		matrizRaiz = raizMatRecu(matrizIni, 0, 0, dim);
		
		if(matrizRaiz[0][0] == -1){					//si hay algun numero negativo, y el metodo recursivo ha metido un -1 en (0,0)
			System.out.print(error);				//imprime solo la cadena del error, si no sigue
			System.exit(0);
		}else{
			boolean sim = simetrica(matrizRaiz, 0, 0, dim);			
			
			if(sim == true){
				System.out.print(simetrica);
			}else{
				System.out.print(noSimetrica);
			}
		}
		teclado.close();
	}
	
	public static boolean simetrica(int matriz[][], int fila, int columna, int dim){
		if(matriz[fila][columna] != matriz[columna][fila]){
			return false;
		}else if(fila == dim - 1 && columna == dim - 1){
			return true;
		}
		if(columna == dim - 1){ 		//si es el ultimo elemento de la fila, paso a la siguiente
			columna = 0;
			fila = fila + 1;
		}else{							//si no, la fila es la misma, pero la columna aumenta
			columna = columna + 1;
		}
		
		return simetrica(matriz, fila, columna, dim);
	}
	
	public static int[][] raizMatRecu(int matriz[][], int fila, int columna, int dim) {
		if(matriz[fila][columna] < 0){			//si el elemento es negativo, meto un menos uno y salgo del metodo
			matriz[0][0] = -1;
			return matriz;
		}
		
		matriz[fila][columna] = (int)Math.sqrt(matriz[fila][columna]);
			
		if(fila == dim - 1 && columna == dim - 1){		
			return matriz;				// si la fila y la columna coinciden con la matriz sale del metodo
		}else{
				
		
		
			if(columna == dim - 1){ 		//si es el ultimo elemento de la fila, paso a la siguiente
				columna = 0;
				fila = fila + 1;
			}else{							//si no, la fila es la misma, pero la columna aumenta
				columna = columna +1;
			}
			
			return raizMatRecu(matriz, fila, columna, dim);
		}
	}

	public static int[][] llenarMatRecu(int matriz[][], int fila, int columna, int dim,  Scanner teclado){
		
		try {								//si algun elemento no es entero para la ejecucion
			matriz[fila][columna] = teclado.nextInt();
		} catch (Exception e) {
			System.out.print(error);
			System.exit(0);
		}
		
		if(fila == dim - 1 && columna == dim - 1){		
			return matriz;				// si la fila y la columna coinciden con la dimension de la matriz sale del metodo
		}else{
			if(columna == dim - 1){ 		//si es el ultimo elemento de la fila, paso a la siguiente
				columna = 0;
				fila = fila + 1;
			}else{
				columna = columna+1;
			}
		return llenarMatRecu(matriz, fila, columna, dim, teclado);	
		}
	}
}


