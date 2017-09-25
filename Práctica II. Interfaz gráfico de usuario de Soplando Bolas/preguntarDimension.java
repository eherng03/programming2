import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class preguntarDimension extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNmeroDeFilas;
	private JTextField txtNmeroDeColumnas;
	private JTextField txtNmeroDeBolas;
	public static EditionWindow edicionQueLLama;
	private final JSpinner spinnerBolas;
	private final JSpinner spinnerColumnas;
	private final JSpinner spinnerFilas;

	

	/**
	 * Create the dialog.
	 */
	public preguntarDimension(EditionWindow x) {
		setUndecorated(true);
		setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
		this.setAlwaysOnTop(true);
		preguntarDimension.edicionQueLLama = x;
		setTitle("Selecciona la dimensión");
		setBounds(100, 100, 239, 222);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		contentPanel.setBackground(new Color(255, 239, 213));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtNmeroDeFilas = new JTextField();
		txtNmeroDeFilas.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
		txtNmeroDeFilas.setEditable(false);
		txtNmeroDeFilas.setText("Número de filas");
		txtNmeroDeFilas.setBounds(32, 79, 141, 20);
		contentPanel.add(txtNmeroDeFilas);
		txtNmeroDeFilas.setColumns(10);
		
		spinnerFilas = new JSpinner();
		spinnerFilas.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		spinnerFilas.setBounds(172, 79, 34, 20);
		contentPanel.add(spinnerFilas);
		spinnerFilas.setEnabled(true);
		
		spinnerColumnas = new JSpinner();
		spinnerColumnas.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		spinnerColumnas.setBounds(172, 110, 34, 20);
		contentPanel.add(spinnerColumnas);
		spinnerColumnas.setEnabled(true);
		
		spinnerBolas = new JSpinner();
		spinnerBolas.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		spinnerBolas.setBounds(172, 140, 34, 20);
		contentPanel.add(spinnerBolas);
		spinnerBolas.setEnabled(true);
		
		txtNmeroDeColumnas = new JTextField();
		txtNmeroDeColumnas.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
		txtNmeroDeColumnas.setEditable(false);
		txtNmeroDeColumnas.setText("Número de columnas");
		txtNmeroDeColumnas.setBounds(32, 110, 141, 20);
		contentPanel.add(txtNmeroDeColumnas);
		txtNmeroDeColumnas.setColumns(10);
		
		txtNmeroDeBolas = new JTextField();
		txtNmeroDeBolas.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
		txtNmeroDeBolas.setEditable(false);
		txtNmeroDeBolas.setText("Número de bolas");
		txtNmeroDeBolas.setBounds(32, 140, 141, 20);
		contentPanel.add(txtNmeroDeBolas);
		txtNmeroDeBolas.setColumns(10);
		
		JTextPane txtpnPorFavorIntroduce = new JTextPane();
		txtpnPorFavorIntroduce.setBackground(new Color(255, 239, 213));
		txtpnPorFavorIntroduce.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
		txtpnPorFavorIntroduce.setEditable(false);
		txtpnPorFavorIntroduce.setText("Por favor, introduce las dimensiones del nuevo tablero");
		txtpnPorFavorIntroduce.setBounds(32, 32, 174, 36);
		contentPanel.add(txtpnPorFavorIntroduce);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
			buttonPane.setBackground(new Color(255, 239, 213));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if((int) spinnerFilas.getValue() < 2 || (int) spinnerColumnas.getValue() < 2 || (int) spinnerBolas.getValue() < 2){
							JOptionPane.showMessageDialog(getParent(), "Debes introducir valores mayores que 1\n");
						}else{
							
							ok();
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}



	protected void ok() {
		edicionQueLLama.principalWindow.panelTablero = new Tablero((int) spinnerFilas.getValue(), (int) spinnerColumnas.getValue());
		edicionQueLLama.principalWindow.panelTablero.nombre = "SoplarBolas" + edicionQueLLama.contadorEdiciones + ".txt";
		
		edicionQueLLama.principalWindow.panelTablero.numeroBolas = (int) spinnerBolas.getValue();
		edicionQueLLama.principalWindow.panelTablero.isEditarBolas = false;
		edicionQueLLama.principalWindow.panelTablero.isEditarObjetivos = true;	//por defecto
		edicionQueLLama.tableroEdicion = edicionQueLLama.principalWindow.panelTablero;
		edicionQueLLama.getContentPane().add(edicionQueLLama.principalWindow.panelTablero, BorderLayout.CENTER);
		edicionQueLLama.revalidate();
		
	}
}