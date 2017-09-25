import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EditionWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ventanaPrincipal principalWindow;
	public Tablero tableroEdicion;
	public int nBolas;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	//private ArrayList <Tablero> movimientos = new ArrayList<Tablero>();		//arrayList en el que se van guardando los movimoentos que se realizan para poder volver atras


	public int contadorEdiciones;

	

	/**
	 * Create the frame.
	 */
	public EditionWindow(ventanaPrincipal window) {
		this.tableroEdicion = window.panelTablero;
		this.principalWindow = window;
		
		setTitle("Editor de tableros.");
		contadorEdiciones = 0;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 446, 459);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(10, 10, 450, 20);
		getContentPane().add(panelBotones, BorderLayout.NORTH);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Seleccionar posiciones de las bolas");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableroEdicion != null)	{
					tableroEdicion.isEditarBolas = true;
					tableroEdicion.isEditarObjetivos = false;
				}
			}
		});
		
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Seleccionar posiciones objetivo");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableroEdicion != null)	{
					tableroEdicion.isEditarObjetivos = true;
					tableroEdicion.isEditarBolas = false;
				}
			};;                                    
		});
		
		rdbtnNewRadioButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
		rdbtnNewRadioButton.setSelected(true);
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
		buttonGroup.add(rdbtnNewRadioButton_1);
		GroupLayout gl_panelBotones = new GroupLayout(panelBotones);
		gl_panelBotones.setHorizontalGroup(
			gl_panelBotones.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBotones.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelBotones.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_1)
						.addComponent(rdbtnNewRadioButton))
					.addContainerGap(213, Short.MAX_VALUE))
		);
		gl_panelBotones.setVerticalGroup(
			gl_panelBotones.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelBotones.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(rdbtnNewRadioButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnNewRadioButton_1)
					.addContainerGap())
		);
		panelBotones.setLayout(gl_panelBotones);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		menuBar.add(mnArchivo);
		
		JMenuItem mntmNuevo = new JMenuItem("Nuevo");
		mntmNuevo.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mntmNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditionWindow panelEdicion = new EditionWindow(principalWindow);
				panelEdicion.setVisible(true);
			}
		});
		
		mnArchivo.add(mntmNuevo);
		
		
		final JMenuItem mntmSalvar = new JMenuItem("Salvar");
		mntmSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//solo lo guarda si tiene todas las posiciones y bolas
				if(tableroEdicion.contadorBolas == tableroEdicion.numeroBolas && tableroEdicion.contadorObjetivos == tableroEdicion.numeroBolas){
					tableroEdicion.guardar();
				}else{
					JOptionPane.showMessageDialog(mntmSalvar, "No puedes guardar un tablero hasta \n que esten todas las posiciones objetivo \n y bolas colocadas");
				}
			}
		});
		mntmSalvar.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mnArchivo.add(mntmSalvar);
		
		JMenuItem mntmSalvarComo = new JMenuItem("Salvar como...");
		mntmSalvarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tableroEdicion.contadorBolas == tableroEdicion.numeroBolas && tableroEdicion.contadorObjetivos == tableroEdicion.numeroBolas){
					tableroEdicion.guardarComo();
				}else{
					JOptionPane.showMessageDialog(mntmSalvar, "No puedes guardar un tablero hasta \n que esten todas las posiciones objetivo \n y bolas colocadas");
				}
			}
		});
		mntmSalvarComo.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mnArchivo.add(mntmSalvarComo);
		
		final JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//primero pregunta si desea cargar uno sin guardar
				int k = JOptionPane.showConfirmDialog(mntmSalir, "¿Está seguro que desea salir sin terminar la edicion?\n");
				if(k == JOptionPane.YES_OPTION){
						dispose();
				}else if(k == JOptionPane.NO_OPTION){
					//seguir resolviendo
					//tableroEdicion.guardarComo();
				}
			}
		});
		mnArchivo.add(mntmSalir);
		
		JMenu mnEditar = new JMenu("Editar");
		mnEditar.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		menuBar.add(mnEditar);
		
		JMenuItem mntmDeshacer = new JMenuItem("Deshacer");
		mntmDeshacer.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mntmDeshacer.setEnabled(false);
		mnEditar.add(mntmDeshacer);
		
		JMenuItem mntmRehacer = new JMenuItem("Rehacer");
		mntmRehacer.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mntmRehacer.setEnabled(false);
		mnEditar.add(mntmRehacer);
		
		
		JMenu mnResolver = new JMenu("Resolver");
		mnResolver.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		menuBar.add(mnResolver);
		
		JMenuItem mntmResolver = new JMenuItem("Resolver");
		mntmResolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tableroEdicion.contadorBolas == tableroEdicion.numeroBolas && tableroEdicion.contadorObjetivos == tableroEdicion.numeroBolas){
					tableroEdicion.isEditarBolas = false;
					tableroEdicion.isEditarObjetivos = false;
					tableroEdicion.isResolver = true;
					tableroEdicion.setObjetivos();
					tableroEdicion.pintarTablero();
					principalWindow.frame.getContentPane().add(tableroEdicion); 
					principalWindow.frame.revalidate();
					dispose();
				}else{
					
				}
			}
		});
		mntmResolver.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mnResolver.add(mntmResolver);
		
		JMenuItem mntmResolverAutomticamente = new JMenuItem("Resolver automáticamente");
		mntmResolverAutomticamente.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		mnResolver.add(mntmResolverAutomticamente);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		mnAyuda.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAbrirAyuda = new JMenuItem("Abrir ayuda");
		mntmAbrirAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpWindow ayudaEditar = new helpWindow("edicion");
				ayudaEditar.setVisible(true);
			}
		});
		mnAyuda.add(mntmAbrirAyuda);
		
		//si no estas editando un tablero que mete
		if(principalWindow.panelTablero == null){
			preguntarDimension selectDimension = new preguntarDimension(this);
			selectDimension.setVisible(true);
			revalidate();
		}else{
			//tableroEdicion = principalWindow.panelTablero;
			principalWindow.panelTablero.nombre = "SoplarBolas" + contadorEdiciones + ".txt";
			principalWindow.panelTablero.isEditarBolas = false;
			principalWindow.panelTablero.isEditarObjetivos = true;	//por defecto
			//para que al principio solo pueda quitar bolas u objetivos
			principalWindow.panelTablero.contadorBolas = principalWindow.panelTablero.numeroBolas;
			principalWindow.panelTablero.contadorObjetivos = principalWindow.panelTablero.numeroBolas;
			getContentPane().add(principalWindow.panelTablero, BorderLayout.CENTER);
			
			revalidate();
		}
		
		
	}




		
	}


