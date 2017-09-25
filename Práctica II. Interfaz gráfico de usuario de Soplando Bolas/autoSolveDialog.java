import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class autoSolveDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtSeleccionaLaSolucion;
	public Tablero tablero;
	public int seleccionada;
	public ventanaPrincipal principalWindow;
	public String[] soluciones;

	/**
	 * Create the dialog.
	 */
	public autoSolveDialog(Tablero tablerox, ventanaPrincipal ventanaP) {
		this.tablero = tablerox;
		this.principalWindow = ventanaP;
		setAlwaysOnTop(true);
		setUndecorated(true);
		setBounds(100, 100, 462, 132);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 250, 205));
		contentPanel.setBorder(new MatteBorder(2, 2, 0, 2, (Color) new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtSeleccionaLaSolucion = new JTextField();
		txtSeleccionaLaSolucion.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		txtSeleccionaLaSolucion.setEditable(false);
		txtSeleccionaLaSolucion.setBackground(new Color(255, 250, 205));
		txtSeleccionaLaSolucion.setText("Selecciona la solucion que deseas resolver:");
		txtSeleccionaLaSolucion.setBounds(62, 28, 324, 20);
		contentPanel.add(txtSeleccionaLaSolucion);
		txtSeleccionaLaSolucion.setColumns(10);
		

		
		soluciones = new String[tablero.SOLUCIONES.size()];
		for(int i = 0; i < soluciones.length; i++){
			soluciones[i] = tablero.SOLUCIONES.get(i).toString();
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox comboBox = new JComboBox(soluciones);
		comboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		comboBox.setBounds(62, 59, 324, 20);
		seleccionada = comboBox.getSelectedIndex();
		
		
		contentPanel.add(comboBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new MatteBorder(0, 2, 2, 2, (Color) new Color(0, 0, 0)));
			buttonPane.setBackground(new Color(255, 250, 205));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(!tablero.SOLUCIONES.isEmpty()){
							principalWindow.resolveAuto(tablero.SOLUCIONES.get(seleccionada));
						}
					}
				});
				okButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
