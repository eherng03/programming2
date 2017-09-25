import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Tablero extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Bola[][] matriz;
	public ArrayList <Bola> bolasQuitadas;
	public int[][] objetivos;
	public String objetivosCadena;
	public String bolasCadena;
	public int numeroBolas;
	public ArrayList<Integer> soplidos;
	public Casilla[][] mCasillas;
	public String nombre;
	public File fichero;
	public int contadorBolas;
	public int filas;
	public int columnas;
	public int contadorObjetivos;
	public int bolasCreadas;
	public int indiceDeshacer;
	public ArrayList<ArrayList <Integer>> SOLUCIONES;
	
	public ArrayList<Tablero> tablerosMovidos;
	
	public ArrayList<Tablero> movimientos;


	public boolean isEditarBolas;
	public boolean isEditarObjetivos;
	public boolean isResolver;

	//Métodos constructores del tablero
	
	
	
	public Tablero(int filas, int columnas){
		this.objetivosCadena = "";
		this.bolasCadena = "";
		this.filas = filas;
		this.columnas = columnas;
		this.contadorObjetivos = 0;
		this.contadorBolas = 0;
		this.bolasCreadas = 0;
		//crea la matriz de bolas
		this.matriz = new Bola[filas][columnas];
		this.soplidos = new ArrayList<Integer>();
		//bolasMetidas = new Bola[numeroBolas];
		bolasQuitadas = new ArrayList <Bola>();
		this.indiceDeshacer = 0;
		this.SOLUCIONES = new ArrayList<ArrayList <Integer>>();
		this.tablerosMovidos = new ArrayList<Tablero>();
		this.movimientos = new ArrayList<Tablero>();
		//this.añadirTablero();
		
		GridLayout gbl_panel = new GridLayout(filas, columnas);
		this.setLayout(gbl_panel);
		
		//dibuja el tablero
		mCasillas = new Casilla[filas][columnas];      
	    for(int fila=0;fila<mCasillas.length;fila++) {          
	        for(int columna=0;columna<mCasillas[0].length;columna++) {             
	            final Casilla temp = new Casilla();
	            temp.fila = fila;
	            temp.esObjetivo = false;
	            temp.columna = columna;
	            temp.texto = "[" + (fila+1) + "],[" + (columna+1) + "]";
	            temp.setText(temp.texto);                                       
	            temp.setBackground(new Color(255, 248, 220));
	            temp.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
	            temp.setBorder(new LineBorder(new Color(0, 0, 0)));
	            temp.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent arg0) {
	    				if(isEditarObjetivos){
	    					if(temp.esObjetivo){
		    					//si ya era un objetivo lo quita
	    						quitarObjetivo(temp);
	    					}else if(contadorObjetivos >= numeroBolas){
		    					JOptionPane.showMessageDialog(temp, "Lo siento, no puedes poner mas posiciones objetivo");
		    				}else if(!temp.esObjetivo && contadorObjetivos < numeroBolas){
		    					ponerObjetivo(temp);
		    				}
	    				}else if(isEditarBolas){
	    					if(matriz[temp.fila][temp.columna] != null){			//si hay bola la quita
	    						quitarBola(temp);
	    					}else if(contadorBolas >= numeroBolas){
	    						JOptionPane.showMessageDialog(temp, "Lo siento, no puedes poner más bolas.");
	    					}else if(matriz[temp.fila][temp.columna] == null && contadorBolas < numeroBolas){ //si no hay bola la pone...
	    						ponerBola(temp);
	    					}
	    				}else if(isResolver){
	    					if(indiceDeshacer != tablerosMovidos.size()){
	    						while(indiceDeshacer < tablerosMovidos.size()){
	    							tablerosMovidos.remove(indiceDeshacer);
	    						}
	    					}
	    					añadirTablero();
	    					soplarLineas(matriz[temp.fila][temp.columna].ID);
	    					pintarTablero();
	    					revalidate();
	    					SOLUCIONES = resolver(movimientos);
	    					if(SOLUCIONES.isEmpty()){
	    						//JOptionPane.showMessageDialog(temp, "Has llegado a una posición en la que no se puede solucionar el juego, \n carga un nuevo tablero o créalo");
	    					}else if(esSolucion()){
	    						JOptionPane.showMessageDialog(temp, "¡ENHORABUENA! Has resuelto el juego. \n Si quieres seguir jugando pulsa Archivo -> Nuevo \n o carga un nuevo tablero desde un archivo");
	    						isResolver = false;
	    					}
	    				}
	    			}
	    		});
	            mCasillas[fila][columna] = temp;                                     
	            this.add(temp);
	            temp.setEnabled(true);
	            temp.setVisible(true);
	        }
        }
	    
	}
	
	protected void añadirTablero() {
		this.tablerosMovidos.add(new Tablero(this));
		this.indiceDeshacer++;
	}

	public Tablero(final Tablero tabActual) {
		this.objetivosCadena = tabActual.objetivosCadena;
		this.bolasCadena = tabActual.bolasCadena;
		this.filas = tabActual.filas;
		this.columnas = tabActual.columnas;
		this.contadorObjetivos = tabActual.contadorObjetivos;
		this.contadorBolas = tabActual.contadorBolas;
		this.bolasCreadas = tabActual.bolasCreadas;
		this.nombre = tabActual.nombre;
		this.objetivosCadena = tabActual.objetivosCadena;
		this.bolasCadena = tabActual.bolasCadena;
		this.indiceDeshacer = tabActual.indiceDeshacer;
		this.SOLUCIONES = new ArrayList<ArrayList <Integer>>(tabActual.SOLUCIONES);
		this.tablerosMovidos = new ArrayList<Tablero>(tabActual.tablerosMovidos);
		this.movimientos = new ArrayList<Tablero>(tabActual.movimientos);
		//crea la matriz de bolas
		this.matriz = new Bola[tabActual.filas][tabActual.columnas];
		for(int i = 0; i < tabActual.matriz.length; i++){
			for(int j = 0; j < tabActual.matriz[0].length; j++){
				if(tabActual.matriz[i][j] != null){	//si hay bola...
					this.addBola(new Bola(tabActual.matriz[i][j]));
				}
			}
		}
		this.isEditarBolas = tabActual.isEditarBolas;
		this.isEditarObjetivos = tabActual.isEditarObjetivos;
		this.isResolver = tabActual.isResolver;
		this.setObjetivos(tabActual.objetivos);
		this.soplidos = new ArrayList<Integer>(tabActual.soplidos);
		//bolasMetidas = new Bola[numeroBolas];
		bolasQuitadas = new ArrayList <Bola>(tabActual.bolasQuitadas);
		bolasCreadas = tabActual.bolasCreadas;
		GridLayout gbl_panel = new GridLayout(filas, columnas);
		this.setLayout(gbl_panel);
		
		//dibuja el tablero
		mCasillas = new Casilla[filas][columnas];      
	    for(int fila=0;fila<mCasillas.length;fila++) {          
	        for(int columna=0;columna<mCasillas[0].length;columna++) {             
	            final Casilla temp = new Casilla();
	            temp.fila = fila;
	            temp.esObjetivo = tabActual.mCasillas[fila][columna].esObjetivo;
	            temp.columna = columna;
	            temp.texto = "[" + (fila+1) + "],[" + (columna+1) + "]";
	            temp.setText(temp.texto);                                       
	            temp.setBackground(new Color(255, 248, 220));
	            temp.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
	            temp.setBorder(new LineBorder(new Color(0, 0, 0)));
	            temp.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent arg0) {
	    				if(isEditarObjetivos){
	    					if(temp.esObjetivo){
		    					//si ya era un objetivo lo quita
	    						quitarObjetivo(temp);
	    					}else if(contadorObjetivos >= numeroBolas){
		    					JOptionPane.showMessageDialog(temp, "Lo siento, no puedes poner mas posiciones objetivo");
		    				}else if(!temp.esObjetivo && contadorObjetivos < numeroBolas){
		    					ponerObjetivo(temp);
		    				}
	    				}else if(isEditarBolas){
	    					if(matriz[temp.fila][temp.columna] != null){			//si hay bola la quita
	    						quitarBola(temp);
	    					}else if(contadorBolas >= numeroBolas){
	    						JOptionPane.showMessageDialog(temp, "Lo siento, no puedes poner más bolas.");
	    					}else if(matriz[temp.fila][temp.columna] == null && contadorBolas < numeroBolas){ //si no hay bola la pone...
	    						ponerBola(temp);
	    					}
	    				}else if(isResolver){
	    					if(indiceDeshacer != tablerosMovidos.size()){
	    						while(indiceDeshacer < tablerosMovidos.size()){
	    							tablerosMovidos.remove(indiceDeshacer);
	    						}
	    					}
	    					if(indiceDeshacer == 0){
	    						añadirTablero();
	    					}
	    				
	    					soplarLineas(matriz[temp.fila][temp.columna].ID);
	    					añadirTablero();
	    					pintarTablero();
	    					revalidate();
	    					SOLUCIONES = resolver(movimientos);
	    					if(SOLUCIONES.isEmpty()){
	    						JOptionPane.showMessageDialog(temp, "Has llegado a una posición en la que no se puede solucionar el juego, \n carga un nuevo tablero o créalo");
	    					}else if(esSolucion()){
	    						JOptionPane.showMessageDialog(temp, "¡ENHORABUENA! Has resuelto el juego. \n Si quieres seguir jugando pulsa Archivo -> Nuevo \n o carga un nuevo tablero desde un archivo");
	    						isResolver = false;
	    					}
	    				}
	    			}
	    		});
	            mCasillas[fila][columna] = temp;                                     
	            this.add(temp);
	            temp.setEnabled(true);
	            temp.setVisible(true);
	        }
        }
	}

	protected void quitarBola(Casilla temp) {
		bolasQuitadas.add(matriz[temp.fila][temp.columna]);
		matriz[temp.fila][temp.columna] = null;
		this.contadorBolas--;
		pintarTablero();
	}

	protected void ponerBola(Casilla temp) {
		if(bolasCreadas < numeroBolas){
			bolasCreadas++;
			Bola bolaAux = new Bola(bolasCreadas, temp.fila, temp.columna);
			//addBola(bolaAux);
			matriz[temp.fila][temp.columna] = bolaAux;
			this.contadorBolas++;
			pintarTablero();
		}else if(!bolasQuitadas.isEmpty()){
			Bola bolaAux = new Bola(bolasQuitadas.get(0).ID, temp.fila, temp.columna);
			bolasQuitadas.remove(0);
			addBola(bolaAux);
			this.contadorBolas++;
			pintarTablero();
		}
		
	}

	protected void ponerObjetivo(Casilla temp) {
		temp.esObjetivo = true;
		this.contadorObjetivos++;
		pintarTablero();
	}

	protected void quitarObjetivo(Casilla temp) {
		temp.esObjetivo = false;
		this.contadorObjetivos--;
		pintarTablero();
	}

	public boolean esObjetivo(int fila, int columna){
		for(int i = 0; i < objetivos.length; i++){
			if(fila == objetivos[i][0]){
				if(columna == objetivos[i][1]){
					return true;
				}
			}
		}
		return false;
	}
	
	public void pintarTablero(){
		 for(int fila=0;fila<mCasillas.length;fila++) {          
		        for(int columna=0;columna<mCasillas[0].length;columna++) {  
		        	if(matriz[fila][columna] != null){	//es decir, si hay bola
		        		mCasillas[fila][columna].setBorder(new LineBorder(new Color(0, 0, 0), 10, true));
		        		mCasillas[fila][columna].setText(((Integer)matriz[fila][columna].ID).toString());
		        	}
		        	if(mCasillas[fila][columna].esObjetivo){
		        		mCasillas[fila][columna].setBackground(new Color(0, 250, 154));	
	        		}
		        	if(matriz[fila][columna] == null){		//si no hay bola lo velve a pintar del color de antes
		        		mCasillas[fila][columna].setBorder(new LineBorder(new Color(0, 0, 0)));
		        		mCasillas[fila][columna].setText(mCasillas[fila][columna].texto);
		        	}
		        	if(!mCasillas[fila][columna].esObjetivo){
		        		mCasillas[fila][columna].setBackground(new Color(255, 248, 220));
	        		}
		        }
		 }
	}

	public void copiarMatriz(Tablero tablero) {
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
	
	public void setObjetivos(){
		objetivos = new int[numeroBolas][2];
		int obj = 0;
			for(int fila = 0; fila < matriz.length; fila++){
				for(int columna = 0; columna < matriz[0].length; columna++){
					if(mCasillas[fila][columna].esObjetivo){
						objetivos[obj][0] = fila;
						objetivos[obj][1] = columna;
						obj++;
						if(obj == numeroBolas){
							return;
						}
					}
				}
			}
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
			if(this.matriz[objetivos[i][0]][objetivos[i][1]] == null){
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
	
	
	private ArrayList<ArrayList <Integer>> resolver(ArrayList<Tablero> movimientos) {
		ArrayList<ArrayList <Integer>> soluciones = new ArrayList<ArrayList <Integer>>();
		if(!movimientos.isEmpty()){
			movimientos.clear();
		}
		movimientos.add(this);
		
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
						if(!soluciones.contains(tabAux.soplidos)){
							movimientos.add(tabAux);		//añado el tablero generado al arraylist de tableros movidos
							if(tabAux.esSolucion()){		//si es solucion también añado los soplidos realizados en ese tablero al arraylist correspondiente
								soluciones.add(tabAux.soplidos);
							}
							//System.out.println(tabAux.soplidos.toString());
						}
					}
				}
			}
		}
	
		return soluciones;
	}

	/**
	 * Método que activa los botones para que sean bolas
	 */


	public void guardarComo() {
		try {
			if(objetivos == null){
				setObjetivos();
			}
			
			this.bolasCadena = pasarBolasACadena();
			this.objetivosCadena = pasarObjetivosACadena();
			// Crear un objeto File se encarga de crear o abrir acceso a un
			// archivo que se especifica en su constructor
			
			JFileChooser jfc = new JFileChooser();
			String ruta = "";
			if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				ruta = jfc.getSelectedFile().getAbsolutePath();
				//Aqui ya tiens la ruta,,,ahora puedes crear un fichero n esa ruta y escribir lo k kieras...
			} 
			
			this.nombre = ruta + ".txt";
			
			if(new File(this.nombre).exists()){
				int x = JOptionPane.showConfirmDialog(this,"El fichero nombrado existe,¿deseas reemplazarlo?");
				if(x == JOptionPane.YES_OPTION){
					new File(this.nombre).delete();
					this.fichero = new File(this.nombre);
					
					// Crear objeto FileWriter que sera el que nos ayude a escribir
					// sobre archivo
					FileWriter escribir = new FileWriter(this.fichero, true);

					// Escribimos en el archivo con el metodo write
					escribir.write(this.parametrosString()); //< aqui metes lo que quieres escribir o string normales>);

					// Cerramos la conexion
					escribir.close();
				}else if(x == JOptionPane.NO_OPTION){
					this.guardarComo();
				}
			}
			
		}
		
		// Si existe un problema al escribir cae aqui
		catch (Exception e) {
			JOptionPane.showMessageDialog( getParent(), "Error al guardar", "Error", JOptionPane.WARNING_MESSAGE);
			//System.out.println("Error al guardar");
		}		
	}

	public String parametrosString() {
		String bonito = null;
		bonito = this.filas + " " + this.columnas + "\n";
		bonito = bonito + this.numeroBolas + "\n";
		bonito = bonito + this.objetivosCadena + "\n";
		bonito = bonito + this.bolasCadena + "\n";
		return bonito;
	}

	public void guardar() {
		try {
			if(objetivos == null){
				setObjetivos();
			}
			this.bolasCadena = pasarBolasACadena();
			this.objetivosCadena = pasarObjetivosACadena();
			// Crear un objeto File se encarga de crear o abrir acceso a un
			// archivo que se especifica en su constructor
			//primero borra lo que haya
			if(new File(this.nombre).exists()){
				new File(this.nombre).delete() ;
			}
			
			
			this.fichero = new File(this.nombre);
			
			// Crear objeto FileWriter que sera el que nos ayude a escribir
			// sobre archivo
			FileWriter escribir = new FileWriter(this.fichero, true);

			// Escribimos en el archivo con el metodo write
			escribir.write(this.parametrosString()); //< aqui metes lo que quieres escribir o string normales>);

			// Cerramos la conexion
			escribir.close();
		}
		// Si existe un problema al escribir cae aqui
		catch (Exception e) {
			JOptionPane.showMessageDialog( getParent(), "Error al guardar", "Error", JOptionPane.WARNING_MESSAGE);
			//System.out.println("Error al guardar");
		}
	}
	
	private String pasarObjetivosACadena() {
		String sol = "";
		for(int i = 0; i < objetivos.length; i++){
			int objFila = objetivos[i][0]+1;
			int objCol = objetivos[i][1]+1;
			sol = sol  + objFila + " " + objCol + " ";
		}
		return sol;
	}
	
	public String pasarBolasACadena(){
		String sol = "";
		for(int bola = 1; bola <= numeroBolas; bola++){
			int[] posiciones = buscarBola(bola);
			int bolaFila = posiciones[0]+1;
			int bolaColumna = posiciones[1]+1;
			sol = sol  + bolaFila + " " + bolaColumna + " ";
		}
		return sol;
	}
}