import java.util.ArrayList;
import java.util.Arrays;



public class Main {

	
	public static void main(String[] args) {
		
		java.util.Scanner teclado = new java.util.Scanner(System.in);
		
		int dimension =  teclado.nextInt();
		
		
		String[] palabras = new String[dimension];
		for(int i = 0; i < dimension; i++){			//ya tengo un array con las palabras
			palabras[i] = teclado.next();
		}
		//fallo, limpiarlo en cada palabra
		ArrayList<String> Soluciones = new ArrayList<String>();		//en donde voy a almacenar las soluciones sin ordenar
		
		for(int j = 0; j < dimension; j++){				//recorre el array de palabras
			
			Soluciones.clear();		//nuevo despues de la entrega
			int num = palabras[j].length();                  //numero de letras
			String[] letras = new String[num];
			
			for(int w = 0; w < num; w++){					//crea un array de letras
				letras[w] = palabras[j].substring(w, w+1);
			}
			
			int[] letrasAscii = toAscii(letras);			//paso a mi ascii el array
			Arrays.sort(letrasAscii);							//lo ordeno
			letras = toWord(letrasAscii);					//vuelvo a almacenar en la misma variable el mismo array pero ya ordenado
			
			String Stringletras = "";
			for(int i = 0; i < letras.length; i++){			//convierto el array en una string
				Stringletras = Stringletras + letras[i];
			}
			combinaciones(Stringletras, "", Soluciones, 0);
			
			for(int p = 0; p < Soluciones.size(); p++){		//Imprimo las soluciones
				System.out.println(Soluciones.get(p));
			}
		}
		teclado.close();
	}

	
	private static void combinaciones(String letra, String act,  ArrayList<String> Soluciones ,int contador) {
		//cuando el contador es igual al numero de todas las posibles combinaciones sale
		//fallo: no imprimia las palabras de una sola letra
		if(letra.length() == 1){
			Soluciones.add(letra);
			return;
		}
		if(contador == factorial(letra.length())){			
    		return;
    	}else{
	        if(letra.length() == 2){
	        	contador=contador+2;						//cuando a la cadena que le pasas solo le quedan dos letras
	        	//miro a ver silas palabras ya estan en soluciones
	        	if(estaRepetida(act+letra.charAt(0)+letra.charAt(1), Soluciones) == false){			
	        		Soluciones.add(act+letra.charAt(0)+letra.charAt(1));			//si no está repetida la añado a soluciones
	        	}
	        	if(estaRepetida(act+letra.charAt(1)+letra.charAt(0), Soluciones)== false){
	        		Soluciones.add(act+letra.charAt(1)+letra.charAt(0));
	        	}
	        }else{
	        	for(int i = 0; i < letra.length(); i++){
	        		String cadenaAux = quitarLetra(i,letra);
	        		//ordeno lo que queda de cadena para que salgan las palabras ordenadas
	        		int num = cadenaAux.length();                  //numero de letras
	    			String[] Arrayaux = new String[num];
	    			
	    			for(int w = 0; w < num; w++){					//crea un array de letras
	    				Arrayaux[w] = cadenaAux.substring(w, w+1);
	    			}
	    			
	    			int[] cadenaAscii = toAscii(Arrayaux);			//paso a mi ascii el array
	    			Arrays.sort(cadenaAscii);						//lo ordeno
	    			Arrayaux = toWord(cadenaAscii);					//vuelvo a almacenar en la misma variable el mismo array pero ya ordenado
	    			
	    			String Stringletras = "";
	    			for(int d = 0; d < cadenaAux.length(); d++){			//convierto el array en una string
	    				Stringletras = Stringletras + Arrayaux[d];
	    			}
	        		combinaciones(Stringletras, act+letra.charAt(i), Soluciones, contador );
	        	}
	        }
	    }
    }
	
	
	 private static boolean estaRepetida(String string, ArrayList<String> soluciones) {
		for(int i = 0; i < soluciones.size(); i++){
			if(soluciones.get(i).equalsIgnoreCase(string) == true){
				return true;
			}
		}
		return false;
	}

	public static String quitarLetra(int i, String cadena) {
		 if(i == 0){			//Para quitar la primera letra me quedo con la substring desde la segunda hasta el final
	    	return cadena.substring(i+1, cadena.length());
		 }else{					//para quitar la ultima me quedo con todas las anteriores
			if(i == cadena.length()){
				 return cadena.substring(0, cadena.length()-1);
			}else{				//para quitar alguna del medio me quedo con las anteriores, y con las siguientes
				return cadena.substring(0, i) + cadena.substring(i+1, cadena.length());
			}
		 }
	}
	
	
	public static int[] toAscii(String[] palabra){
		
		int numLetras = palabra.length;
		int[] letras = new int[numLetras];			//cada palabra tiene su array de letras en formato ascii
	
		for(int k = 0; k < numLetras; k++){	//separa el array en otro array de chars
			switch(palabra[k]){
			case "1":
				letras[k] = 1;
				break;
			case "2":
				letras[k] = 2;
				break;
			case "3":
				letras[k] = 3;
				break;
			case "4":
				letras[k] = 4;
				break;
			case "5":
				letras[k] = 5;
				break;
			case "6":
				letras[k] = 6;
				break;
			case "7":
				letras[k] = 7;
				break;
			case "8":
				letras[k] = 8;
				break;
			case "9":
				letras[k] = 9;
				break;
			case "A":
				letras[k] = 65;
				break;
			case "a":
				letras[k] = 66;
				break;
			case "B":
				letras[k] = 67;
				break;
			case "b":
				letras[k] = 68;
				break;
			case "C":
				letras[k] = 69;
				break;
			case "c":
				letras[k] = 70;
				break;
			case "D":
				letras[k] = 71;
				break;
			case "d":
				letras[k] = 72;
				break;
			case "E":
				letras[k] = 73;
				break;
			case "e":
				letras[k] = 74;
				break;
			case "F":
				letras[k] = 75;
				break;
			case "f":
				letras[k] = 76;
				break;
			case "G":
				letras[k] = 77;
				break;
			case "g":
				letras[k] = 78;
				break;
			case "H":
				letras[k] = 79;
				break;
			case "h":
				letras[k] = 80;
				break;
			case "I":
				letras[k] = 81;
				break;
			case "i":
				letras[k] = 82;
				break;
			case "J":
				letras[k] = 83;
				break;
			case "j":
				letras[k] = 84;
				break;
			case "K":
				letras[k] = 85;
				break;
			case "k":
				letras[k] = 86;
				break;
			case "L":
				letras[k] = 87;
				break;
			case "l":
				letras[k] = 88;
				break;
			case "M":
				letras[k] = 89;
				break;
			case "m":
				letras[k] = 90;
				break;
			case "N":
				letras[k] = 91;
				break;
			case "n":
				letras[k] = 92;
				break;
			case "Ñ":
				letras[k] = 93;
				break;
			case "ñ":
				letras[k] = 94;
				break;
			case "O":
				letras[k] = 95;
				break;
			case "o":
				letras[k] = 96;
				break;
			case "P":
				letras[k] = 97;
				break;
			case "p":
				letras[k] = 98;
				break;
			case "Q":
				letras[k] = 99;
				break;
			case "q":
				letras[k] = 100;
				break;
			case "R":
				letras[k] = 101;
				break;
			case "r":
				letras[k] = 102;
				break;
			case "S":
				letras[k] = 103;
				break;
			case "s":
				letras[k] = 104;
				break;
			case "T":
				letras[k] = 105;
				break;
			case "t":
				letras[k] = 106;
				break;
			case "U":
				letras[k] = 107;
				break;
			case "u":
				letras[k] = 108;
				break;
			case "V":
				letras[k] = 109;
				break;
			case "v":
				letras[k] = 110;
				break;
			case "W":
				letras[k] = 111;
				break;
			case "w":
				letras[k] = 112;
				break;
			case "X":
				letras[k] = 113;
				break;
			case "x":
				letras[k] = 114;
				break;
			case "Y":
				letras[k] = 115;
				break;
			case "y":
				letras[k] = 116;
				break;
			case "Z":
				letras[k] = 117;
				break;
			case "z":
				letras[k] = 118;
				break;
			}
		}
		return letras;
	}
	
	public static String[] toWord(int[] letrasAscii){
		int numLetras = letrasAscii.length;
		String[] letras = new String[numLetras];
		for(int k = 0; k < numLetras; k++){	//separa el array en otro array de chars
			switch(letrasAscii[k]){
			case 1:
				letras[k] = "1";
				break;
			case 2:
				letras[k] = "2";
				break;
			case 3:
				letras[k] = "3";
				break;
			case 4:
				letras[k] = "4";
				break;
			case 5:
				letras[k] = "5";
				break;
			case 6:
				letras[k] = "6";
				break;
			case 7:
				letras[k] = "7";
				break;
			case 8:
				letras[k] = "8";
				break;
			case 9:
				letras[k] = "9";
				break;
			case 65:
				letras[k] = "A";
				break;
			case 66:
				letras[k] = "a";
				break;
			case 67:
				letras[k] = "B";
				break;
			case 68:
				letras[k] = "b";
				break;
			case 69:
				letras[k] = "C";
				break;
			case 70:
				letras[k] = "c";
				break;
			case 71:
				letras[k] = "D";
				break;
			case 72:
				letras[k] = "d";
				break;
			case 73:
				letras[k] = "E";
				break;
			case 74:
				letras[k] = "e";
				break;
			case 75:
				letras[k] = "F";
				break;
			case 76:
				letras[k] = "f";
				break;
			case 77:
				letras[k] = "G";
				break;
			case 78:
				letras[k] = "g";
				break;
			case 79:
				letras[k] = "H";
				break;
			case 80:
				letras[k] = "h";
				break;
			case 81:
				letras[k] = "I";
				break;
			case 82:
				letras[k] = "i";
				break;
			case 83:
				letras[k] = "J";
				break;
			case 84:
				letras[k] = "j";
				break;
			case 85:
				letras[k] = "K";
				break;
			case 86:
				letras[k] = "k";
				break;
			case 87:
				letras[k] = "L";
				break;
			case 88:
				letras[k] = "l";
				break;
			case 89:
				letras[k] = "M";
				break;
			case 90:
				letras[k] = "m";
				break;
			case 91:
				letras[k] = "N";
				break;
			case 92:
				letras[k] = "n";
				break;
			case 93:
				letras[k] = "Ñ";
				break;
			case 94:
				letras[k] = "ñ";
				break;
			case 95:
				letras[k] = "O";
				break;
			case 96:
				letras[k] = "o";
				break;
			case 97:
				letras[k] = "P";
				break;
			case 98:
				letras[k] = "p";
				break;
			case 99:
				letras[k] = "Q";
				break;
			case 100:
				letras[k] = "q";
				break;
			case 101:
				letras[k] = "R";
				break;
			case 102:
				letras[k] = "r";
				break;
			case 103:
				letras[k] = "S";
				break;
			case 104:
				letras[k] = "s";
				break;
			case 105:
				letras[k] = "T";
				break;
			case 106:
				letras[k] = "t";
				break;
			case 107:
				letras[k] = "U";
				break;
			case 108:
				letras[k] = "u";
				break;
			case 109:
				letras[k] = "V";
				break;
			case 110:
				letras[k] = "v";
				break;
			case 111:
				letras[k] = "W";
				break;
			case 112:
				letras[k] = "w";
				break;
			case 113:
				letras[k] = "X";
				break;
			case 114:
				letras[k] = "x";
				break;
			case 115:
				letras[k] = "Y";
				break;
			case 116:
				letras[k] = "y";
				break;
			case 117:
				letras[k] = "Z";
				break;
			case 118:
				letras[k] = "z";
				break;
			default: letras[k] = Integer.toString(letrasAscii[k]); 		//caso de que sea un numero lo pasa a char
			}
		}
		return letras;
	}

    public static int factorial(int n){
    	if(n == 0){
    		return 1;
    	}else{
    		return n * factorial(n-1);
    	}
    }
}
