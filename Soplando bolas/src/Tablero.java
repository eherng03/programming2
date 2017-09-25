import java.util.ArrayList;
import java.util.Arrays;

public class Tablero {
	
	public Bola[][] matriz;
	public int[][] objetivos;
	public int numeroBolas;
	public ArrayList<Integer> soplidos;

	//Métodos constructores del tablero
	
	public Tablero(int filas, int columnas){
		this.matriz = new Bola[filas][columnas];
		this.soplidos = new ArrayList<Integer>();
	}

	public Tablero(Tablero tablero) {
		this.matriz = new Bola[tablero.matriz.length][tablero.matriz[0].length];
		for(int i = 0; i < tablero.matriz.length; i++){
			for(int j = 0; j < tablero.matriz[0].length; j++){
				if(tablero.matriz[i][j] != null){	//si hay bola...
					this.addBola(new Bola(tablero.matriz[i][j]));
				}
			}
		}
		this.setObjetivos(tablero.objetivos);
		this.setNumeroDeBolas(tablero.numeroBolas);
		this.soplidos = new ArrayList<Integer>();
		for(int i = 0; i < tablero.soplidos.size(); i++){
			this.soplidos.add(tablero.soplidos.get(i));
		}
	}


	/**
	 * Método que añade la bola al tablero
	 * @param bola
	 */
	public void addBola(Bola bola) {
		this.matriz[bola.fila][bola.columna] = bola;	
	}

	/**
	 * Método que añade las posiciones objetivo al tablero
	 * @param objetivos2
	 */
	public void setObjetivos(int[][] objetivos2) {
		this.objetivos = objetivos2;
	}
	
	/**
	 * Método que añade el numero de bolas al tablero.
	 * @param id
	 */
	public void setNumeroDeBolas(int id) {
		this.numeroBolas = id;
	}

	//----------------------------------------------------------------------------
	

	/**
	 * Método que al soplar en una bola, sopla todas las lineas hacia todos los sentidos, comenzando
	 * en la mas cercana al limite del tablero
	 * @param bola
	 */
	public void soplarLineas(int id){
		int [] coord = new int[2];
		coord = this.buscarBola(id);
		Bola bola = this.matriz[coord[0]][coord[1]];
		this.soplidos.add(id);
		//mover en vertical
			//hacia abajo
				//bucle que va desde el limite de la matriz hasta la posicion de la bola, moviendo todas las bolas
				for(int i = this.matriz.length-1 ; i > bola.fila; i--){	//en el metodo que sopla una bola mira a ver si se sale del tablero
					this.soplarBolaDet(this.matriz[i][bola.columna], 1, 0);
				}
			//hacia arriba
				for(int i = 0 ; i < bola.fila; i++){	//bucle que mueve las bolas desde la fila 0 hasta la bola
					this.soplarBolaDet(this.matriz[i][bola.columna], -1, 0);
				}
		//mover en horizontal
			//hacia la derecha
				for(int i = this.matriz[0].length-1; i > bola.columna; i--){
					this.soplarBolaDet(this.matriz[bola.fila][i], 0, 1);
				}
			//hacia la izquierda
				for(int i = 0; i < bola.columna; i++){
					this.soplarBolaDet(this.matriz[bola.fila][i], 0, -1);
				}
	}
	
	/**
	 * Método que mueve una bola concreta a una posicion concreta, primero mira que la bola que se ha pasado no este vacía,
	 * luego comprueba que no se vaya a salir de los límites, y por último que la posicion a la que se la quiere mover no se 
	 * salga de los límites
	 * @param bola es la bola concreta que se tiene que mover
	 * @param filas que se tiene que mover
	 * @param columnas que se tiene que mover
	 **/
	public void soplarBolaDet(Bola bola, int filas, int columnas){
		//si hay bola...
		if(bola != null){
			int filaFin = bola.fila + filas;
			int columnaFin = bola.columna + columnas;
			//si al soplar no se sale de los limites...
			if(filaFin >= 0 && filaFin < matriz.length && columnaFin >= 0 && columnaFin < matriz[0].length){
				//si a la casilla a la que la quiero mover no hay ninguna bola
				if(this.matriz[filaFin][columnaFin] == null){
					//vacio la posicion antigua
					this.matriz[bola.fila][bola.columna] = null;
					//muevo la bola
					bola.fila = filaFin;
					bola.columna = columnaFin;
					// la vuelvo a añadir al tablero
					this.addBola(bola);
				}
			}
		}
	}

	/**
	 * Metodo que busca una bola en un tablero pasándole el número identificador de la bola.
	 * @param ID
	 */
	public int[] buscarBola(int ID){
		int [] coordenadas = new int[2];
		boolean encontrado = false;
		for(int i = 0; i < this.matriz.length; i++){
			if(encontrado == true){
				break;
			}else{
				for(int j = 0; j < this.matriz[0].length; j++){
					if(encontrado == true){
						break;
					}else{
						if(this.matriz[i][j] != null && this.matriz[i][j].ID == ID){
							coordenadas[0] = i;
							coordenadas[1] = j;
							encontrado = true;
						}
					}
				}
			}
		}
		return coordenadas;
	}
	
	/**
	 * Método que comprueba que un tablero sea solucion, es decir, que las bolas estén en las posiciones objetivo.
	 * Si alguna de las posiciones objetivo está vacía, ya no  es solución.
	 * @return true or false 
	 */
	public boolean esSolucion(){
		for(int i = 0; i < objetivos.length; i++){
			if(this.matriz[objetivos[i][0]-1][objetivos[i][1]-1] == null){
				return false;
			}
		}
		return true;
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
		if (!Arrays.deepEquals(matriz, other.matriz))
			return false;
		if (numeroBolas != other.numeroBolas)
			return false;
		if (!Arrays.deepEquals(objetivos, other.objetivos))
			return false;
		return true;
	}
	
	/**
	 * Método que mira a ver si el los soplidos anteriores han sido los mismos, lo que implica un bucle
	 * @param movimientos
	 * @return
	 */
	public boolean esBucle(ArrayList<Tablero> movimientos){
		if(this.igualMatriz(movimientos.get(0))){
			return true;
		}else if(movimientos.size() > 1){
			if(this.soplidos.size() > 1){
				//empieza en el 1 porque en la posicion 0 del array esta el tablero inicial y no tiene soplidos
				for(int i = 1; i < movimientos.size(); i++){
					String thisCadena = "";
					String compararCadena = "";
					Tablero comparar = movimientos.get(i);
					if(this.igualMatriz(comparar) ){
						if(this.soplidos.get(0) == comparar.soplidos.get(0)){
						//solo si el primer bucle vale como solucion
						//if(this.soplidos.get(this.soplidos.size()-1) == comparar.soplidos.get(this.soplidos.size()-1) ){
							thisCadena = this.soplidosCadena();
							compararCadena = comparar.soplidosCadena();
							if(thisCadena.contains(compararCadena)){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo que pasa los soplidos a una cadena todos juntos, para comprobar si es un bucle
	 * @return solucion
	 */
	private String soplidosCadena(){
		Integer[] soplidosArray = new Integer[this.soplidos.size()];
		this.soplidos.toArray(soplidosArray);
		StringBuffer solucion = new StringBuffer("");
		solucion.setLength(0);
		for(int i = 0; i < soplidosArray.length; i++){
			String meter = Integer.toString((int)soplidosArray[i]);
			solucion.append(meter);
		}
		return solucion.toString();
	}

	/**
	 * Método que devuelve true o false si la matriz de dos tableros es igual
	 * @param comparar
	 * @return
	 */
	private boolean igualMatriz(Tablero comparar) {
		boolean sol = true;
		for(int i = 0; i < this.matriz.length;i++){
			for(int j = 0; j < this.matriz[0].length;j++){
				if(this.matriz[i][j] != null && comparar.matriz[i][j] == null){
					sol = false;
					return sol;
				}else if(this.matriz[i][j] == null && comparar.matriz[i][j] != null){
					sol = false;
					return sol;
				}else if(this.matriz[i][j] != null && comparar.matriz[i][j] != null){
					//si las bolas no son iguales
					if(!this.matriz[i][j].equals(comparar.matriz[i][j])){
						sol = false;
						return sol;
					}
				}
			}
		}
		return sol;
	}
}