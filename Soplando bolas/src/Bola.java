public class Bola{
	
	public int ID;
	public int fila;
	public int columna;
	
	public Bola(int identificador, int fila, int columna) {
		this.ID = identificador;
		this.fila = fila;
		this.columna = columna;
	}
	public Bola(Bola bola){
		this.ID = bola.ID;
		this.fila = bola.fila;
		this.columna = bola.columna;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + columna;
		result = prime * result + fila;
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
		Bola other = (Bola) obj;
		if (ID != other.ID)
			return false;
		if (columna != other.columna)
			return false;
		if (fila != other.fila)
			return false;
		return true;
	}
}
