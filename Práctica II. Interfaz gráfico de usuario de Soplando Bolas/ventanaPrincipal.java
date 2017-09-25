import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;


//cada vez que modifica algo visual hacer revalidate()	

public class ventanaPrincipal{

	public JFrame frame;
	public Tablero panelTablero;
	public JButton[][] mCasillas;
	public int nfilas;
	public int ncolumnas;
	//private final JTextField textField = new JTextField();
	public JLabel lblNewLabel;
	public File archivo;
	public int contadorJuegos;
	public static ventanaPrincipal window;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ventanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ventanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//para que al principio por defecto salga un tablero de 3x3

		//-----------------
		//frame = this;
		frame = new JFrame();
		frame.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		frame.setBounds(100, 100, 422, 422);
		//frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelTablero = null;
		
		lblNewLabel = new JLabel();
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(new Color(255, 248, 220));
		frame.getContentPane().add(lblNewLabel, BorderLayout.SOUTH);
		frame.revalidate();
		//menu
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		menuBar.add(mnArchivo);
		
		final JMenuItem mntmNuevo = new JMenuItem("Nuevo");
		mntmNuevo.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mntmNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panelTablero != null){
					int x = JOptionPane.showConfirmDialog(mntmNuevo, "¿Está seguro que abrir un nuevo tablero sin guardar el anterior?\n");
					if(x == JOptionPane.YES_OPTION){
						crearNuevo();
						panelTablero = null;
						EditionWindow panelEdicion = new EditionWindow(window);
						//panelEdicion.principalWindow =  frame;
						panelEdicion.setVisible(true);
						frame.revalidate();
					}
				}else{
					EditionWindow panelEdicion = new EditionWindow(window);
					panelEdicion.setVisible(true);
					frame.revalidate();
				}
				
			}
		});
		mnArchivo.add(mntmNuevo);
		
		
		
		final JMenuItem mntmCargar = new JMenuItem("Cargar");
		mntmCargar.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mntmCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//primero pregunta si desea cargar uno sin guardar
				if(panelTablero != null){
					int x = JOptionPane.showConfirmDialog(mntmCargar, "¿Está seguro que desea cargar un nuevo tablero sin guardar el anterior?\n");
					if(x == JOptionPane.YES_OPTION){
						frame.getContentPane().remove(panelTablero);
						panelTablero = null;
						cargar();
					}else  if(x == JOptionPane.NO_OPTION){
						panelTablero.guardarComo();
					}
				}else{
					cargar();
				}
			}
		});
		mnArchivo.add(mntmCargar);
		
		JMenuItem mntmSalvar = new JMenuItem("Salvar");
		mntmSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panelTablero != null){
					panelTablero.guardar();
				}
			}
		});
		mntmSalvar.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mnArchivo.add(mntmSalvar);
		
		JMenuItem mntmSalvarComo = new JMenuItem("Salvar como...");
		mntmSalvarComo.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mntmSalvarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panelTablero != null){
					panelTablero.guardarComo();
				}
			}
		});
		mnArchivo.add(mntmSalvarComo);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//primero pregunta si desea cargar uno sin guardar
				int k = JOptionPane.showConfirmDialog(mntmCargar, "¿Está seguro que desea salir sin guardar?\n");
				if(k == JOptionPane.YES_OPTION){
					frame.dispose();
				}else if(k == JOptionPane.NO_OPTION){
					panelTablero.guardarComo();
				}
				
			}
		});
		mnArchivo.add(mntmSalir);
		
		JMenu mnEditar = new JMenu("Editar");
		mnEditar.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		menuBar.add(mnEditar);
		
		final JMenuItem mntmDeshacer = new JMenuItem("Deshacer");
		mntmDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panelTablero != null){
					if(panelTablero.indiceDeshacer == 0){
						mntmDeshacer.setEnabled(false);
					}else if(panelTablero.indiceDeshacer > 0){
						mntmDeshacer.setEnabled(true);
						frame.getContentPane().remove(panelTablero);
						panelTablero.indiceDeshacer--;
						panelTablero = new Tablero(panelTablero.tablerosMovidos.get(panelTablero.indiceDeshacer));
						panelTablero.pintarTablero();
						frame.getContentPane().add(panelTablero);
						frame.revalidate();						
					}
				}
			}
		});
		mntmDeshacer.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mnEditar.add(mntmDeshacer);
		
		final JMenuItem mntmRehacer = new JMenuItem("Rehacer");
		mntmRehacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panelTablero != null){
					if(panelTablero.indiceDeshacer == panelTablero.tablerosMovidos.size()){	//si el indice esta al final del array de tableros movidos no se puede rehacer
						mntmRehacer.setEnabled(false);
					}
					if(panelTablero.indiceDeshacer < panelTablero.tablerosMovidos.size()){
						mntmRehacer.setEnabled(true);
						frame.getContentPane().remove(panelTablero);
						panelTablero.indiceDeshacer++;
						panelTablero = new Tablero(panelTablero.tablerosMovidos.get(panelTablero.indiceDeshacer));
						panelTablero.pintarTablero();
						frame.getContentPane().add(panelTablero);
						frame.revalidate();		
					}
				}
			}
		});
		mntmRehacer.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mnEditar.add(mntmRehacer);
		
		final JMenuItem mntmEditar = new JMenuItem("Editar");
		mntmEditar.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		//si no hay tablero no se puede editar
		if(panelTablero == null){
			mntmEditar.setEnabled(false);
		}else{
		    mntmEditar.setEnabled(true);
    		mntmEditar.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				
    					EditionWindow panelEdicion = new EditionWindow(window);
    					panelEdicion.setVisible(true);
    				
    			}
    		});
    	}
		mnEditar.add(mntmEditar);
		
		
		JMenu mnResolver = new JMenu("Resolver");
		mnResolver.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		menuBar.add(mnResolver);
		
		//esta desactivado en esta ventana
		JMenuItem mntmResolver = new JMenuItem("Resolver");
		mntmResolver.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mntmResolver.setEnabled(false);
		mnResolver.add(mntmResolver);
		
		final JMenuItem mntmResolverAutomticamente = new JMenuItem("Resolver automáticamente");
		mntmResolverAutomticamente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(window.panelTablero == null){
					mntmResolverAutomticamente.setEnabled(false);
				}else{
					mntmResolverAutomticamente.setEnabled(true);
					autoSolveDialog auto = new autoSolveDialog(panelTablero, window);
					auto.setVisible(true);
				}
				
			}
		});
		mntmResolverAutomticamente.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mnResolver.add(mntmResolverAutomticamente);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		mnAyuda.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAbrirAyuda = new JMenuItem("Abrir ayuda");
		mntmAbrirAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpWindow ayudaPrincipal = new helpWindow("principal");
				ayudaPrincipal.setVisible(true);
			}
		});
		mnAyuda.add(mntmAbrirAyuda);
		
		
		
		
		
		


		
		

	}


	protected void crearNuevo() {
		// TODO Auto-generated method stub
		
	}

	protected void deshacer() {
		
	}

	protected void cargar() {
		JFileChooser seleccionarArchivo = new JFileChooser();
		int eleccion = seleccionarArchivo.showOpenDialog(frame);
		if(eleccion == JFileChooser.APPROVE_OPTION){
			FileReader fichero = null;
			try {
				fichero = new FileReader(seleccionarArchivo.getSelectedFile());
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(frame, "Archivo no encontrado.", "Error", JOptionPane.WARNING_MESSAGE);
			}
			BufferedReader bufferReader = new BufferedReader(fichero);
			
			try {
				setParametros(bufferReader);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "Formato incorrecto de entrada en el archivo", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	protected void setParametros(BufferedReader bufferReader) throws IOException {
		String[] dimension = bufferReader.readLine().split(" ");
		if(dimension.length == 2){
			int filasAux = Integer.parseInt(dimension[0]);
			int columnasAux = Integer.parseInt(dimension[1]);
				//System.out.println(filasAux + " " + columnasAux);
			panelTablero = new Tablero(filasAux, columnasAux);
			String nObjetivos = bufferReader.readLine();
				//System.out.println(nObjetivos);
			panelTablero.numeroBolas = Integer.parseInt(nObjetivos);
			panelTablero.objetivosCadena = bufferReader.readLine();
			panelTablero.bolasCadena = bufferReader.readLine();
				//System.out.println(panelTablero.objetivosCadena);
				//System.out.println(panelTablero.bolasCadena);
			String[] objetivos = panelTablero.objetivosCadena.split(" ");
			String[] bolas = panelTablero.bolasCadena.split(" ");
			if(panelTablero.numeroBolas <= filasAux*columnasAux){
				if(objetivos.length == bolas.length){
					if(objetivos.length/2 == panelTablero.numeroBolas){
						int[][] objPos = new int[panelTablero.numeroBolas][2];
						int i = 0;
						for(int fila = 0; fila < panelTablero.numeroBolas*2; fila = fila+2){
								objPos[i][0] = Integer.parseInt(objetivos[fila])-1;
								objPos[i][1] = Integer.parseInt(objetivos[fila+1])-1;
								panelTablero.mCasillas[objPos[i][0]][objPos[i][1]].esObjetivo = true;
								i++;
						}
						panelTablero.setObjetivos(objPos);
						int j = 1; //numero de bola
						for(int bol = 0; bol < panelTablero.numeroBolas*2; bol = bol +2){
								panelTablero.addBola(new Bola(j, Integer.parseInt(bolas[bol])-1, Integer.parseInt(bolas[bol+1])-1));
								j++;
						}
						panelTablero.isEditarBolas = false;
						panelTablero.isEditarObjetivos = false;
						panelTablero.isResolver = true;
						panelTablero.pintarTablero();
						frame.getContentPane().add(panelTablero);
						frame.revalidate();
					}else{
						JOptionPane.showMessageDialog(panelTablero, "Faltan o sobran cordenadas");
					}
				}else{
					JOptionPane.showMessageDialog(panelTablero, "No hay el mismo numero de bolas que de objetivos");
				}
			}else{
				JOptionPane.showMessageDialog(panelTablero, "Ha introducido un numero mayor de objetivos que espacios hay e el tablero.");
			}
		}else{
			JOptionPane.showMessageDialog(panelTablero, "Error de dimension del tablero en la primera linea del fichero");
		}
	}

	public void resolveAuto(final ArrayList<Integer> arrayList) {
		
		int tiempoEjecucion = 500;
		for(int i = 0; i < arrayList.size(); i++){
			final int aux = 1;
			javax.swing.Timer temporizador = new javax.swing.Timer(tiempoEjecucion, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//borro el que hay
					window.frame.getContentPane().remove(panelTablero);
					//soplo las lineas
					window.panelTablero.soplarLineas(arrayList.get(aux));
					//lo pinto
					window.panelTablero.pintarTablero();
					//lo vuelvo a añadir
					window.frame.getContentPane().add(window.panelTablero);
					window.frame.revalidate();
					}
			});
			temporizador.setRepeats(false);
			temporizador.start();
			tiempoEjecucion = tiempoEjecucion + 500;
		}
		
		
	}
}