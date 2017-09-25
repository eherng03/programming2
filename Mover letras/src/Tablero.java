import java.util.Arrays;

public class Tablero {

	public char[][] tablero;
	public static int filas;
	public static int columnas;
	
	/**
	 * Constructor que crea un tablero a partir de un array de chars
	 * @param elementos (array de char)
	 */
	public  Tablero(char[] elementos) {			//
		tablero = new char[filas][columnas];
		int index = 0;
		for(int i = 0; i < tablero.length; i++){
			for(int j = 0; j < tablero[0].length; j++){
				tablero[i][j] = elementos[index];
				index++;	//paso al siguiente elemento del array
			}
		}
	}
	
	/**
	 * Constructor que crea un tablero a partir de una matriz
	 * @param elementos (matriz de char)
	 */
	public  Tablero(char[][] elementos) {		
		tablero = new char[filas][columnas];
		for(int i = 0; i < tablero.length; i++){
			for(int j = 0; j < tablero[0].length; j++){
				tablero[i][j] = elementos[i][j];
			}
		}
	}
	
	public static void setDimension(int filasI, int columnasI){
		filas = filasI;
		columnas = columnasI;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(tablero);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tablero other = (Tablero) obj;
		if (!Arrays.deepEquals(tablero, other.tablero))
			return false;
		return true;
	}
}