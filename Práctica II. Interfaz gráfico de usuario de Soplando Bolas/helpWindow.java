import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class helpWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField txtVentanaDeAyuda;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private final String ayudaProfesorEditar = "El funcionaiento de esta ventana queda explicado en la ayuda del usuario.\n"
			+ "A parte, desde esta ventana no se podrá deshacer ni rehacer \n"
			+ ", para quitar un objetivo, o bola vale con pulsar sobre el \n "
			+ "cuando ya está puesto.";
	private final String ayudaProfesorPrincipal = "El funcionamiento del juego queda explicado en la ayuda del usuaio";
	private final String ayudaPrincipalUsuario = "Bienvenido a la ayuda del juego 'Soplar bolas'. \n"
			+ "El juego consiste en pulsar las bolas(soplarlas) hasta \n"
			+ "que todas lleguen a las posiciones finales.\n"
			+ "Se puede iniciar un nuevo juego en el que se introduce \n"
			+ "el numero de filas, columnas y bolas, o cargar un juego \n"
			+ "nuevo desde un archivo .txt con la dimension del tablero \n"
			+ "(filas columnas), en la siguiente linea el numero de \n"
			+ "posiciones objetivo, luego las coordenadas de las posiciones  \n"
			+ "objetivo, y finalmente las posiciones de las bolas. \n"
			+ "Tambien podrás editar estas posiciones haciendo clic \n"
			+ "en 'Editar' -> 'Editar'.\n"
			+ "El juego tambien puede ser resuelto automáticamente.";
	private final String ayudaEditarUsuario = "La ventana editar permite al usuario vambiar las posiciones objetivo, y las bolas de sitio"
			+ "de una manera rápida y sencilla; la edición consiste simplemente en seleccionar 'Seleccionar posiciones objetivo' o "
			+ "'Seleccionar posiciones de las bolas' y hacer clic en la posicion del tablero donde desee que esté el objeto previamente seleccionado."
			+ "Para quitar las bolas, o los objetivos de la posicion asignada, solo hay que volver a hacer clic encima."
			+ "Cuando la edicion haya terminado, habrá que pulsar 'Resolver', para pasar el tablero editado a la ventana principal, y ahi poder"
			+ "intentar resolver el juego.\n"
			+ "Un tablero únicamente podrá cargarse o guardarse desde la ventana edición, cuando todas las posiciones objetivo, y "
			+ "todas las bolas esten colocadas";



	/**
	 * El identificador es una cadena que sirve para saber si es la ayuda de la ventana principal o de la de edición
	 * Create the dialog.
	 */
	public helpWindow(final String identificador) {
		setAlwaysOnTop(true);
		setBackground(new Color(255, 248, 220));
		setUndecorated(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 250, 205));
		contentPanel.setBorder(new MatteBorder(2, 2, 0, 2, (Color) new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtVentanaDeAyuda = new JTextField();
		txtVentanaDeAyuda.setBounds(2, 2, 446, 24);
		txtVentanaDeAyuda.setEditable(false);
		txtVentanaDeAyuda.setBackground(new Color(255, 250, 205));
		txtVentanaDeAyuda.setHorizontalAlignment(SwingConstants.CENTER);
		txtVentanaDeAyuda.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		txtVentanaDeAyuda.setText("Ventana de ayuda");
		contentPanel.add(txtVentanaDeAyuda);
		txtVentanaDeAyuda.setColumns(10);
		
		final JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
		textArea.setBackground(new Color(255, 250, 205));
		textArea.setBounds(10, 58, 430, 207);
		contentPanel.add(textArea);
		
		
		
		//Botón de usuario
		JRadioButton rdbtnUsuario = new JRadioButton("Usuario");
		rdbtnUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(identificador == "principal"){
					textArea.setText(ayudaPrincipalUsuario);
				}else if(identificador == "edicion"){
					textArea.setText(ayudaEditarUsuario);
				}
			}
		});
		rdbtnUsuario.setSelected(true);
		rdbtnUsuario.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		rdbtnUsuario.setBackground(new Color(255, 250, 205));
		buttonGroup.add(rdbtnUsuario);
		rdbtnUsuario.setBounds(127, 28, 109, 23);
		contentPanel.add(rdbtnUsuario);
		
		
		//Botón de profesor
		JRadioButton rdbtnProfesor = new JRadioButton("Profesor");
		rdbtnProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(identificador == "principal"){
					textArea.setText(ayudaProfesorPrincipal);
				}else if(identificador == "edicion"){
					textArea.setText(ayudaProfesorEditar);
				}
			}
		});
		rdbtnProfesor.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		rdbtnProfesor.setBackground(new Color(255, 250, 205));
		rdbtnProfesor.setSelected(true);
		buttonGroup.add(rdbtnProfesor);
		rdbtnProfesor.setBounds(259, 28, 109, 23);
		contentPanel.add(rdbtnProfesor);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new MatteBorder(0, 2, 2, 2, (Color) new Color(0, 0, 0)));
			buttonPane.setBackground(new Color(255, 250, 205));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}