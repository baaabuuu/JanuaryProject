package binaryHexProgram;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/** 
 * This program is used to convert between hexadecimal, binary and decimal numbers
 * After typing in a number and the user uses a document listener.
 * This turnt out to be very inefficent, as it whenever it checked the input it would update the other values.
 * @author s164166@student.dtu.dk
*/
public class NumberConverterWindow1 implements ActionListener, FocusListener
{
	//our components for the window.
	private JTextField bina, deci, hexa;
	private String currentFocus, currVal;
	public NumberConverterWindow1()
	{
		//basic stuff for our container
		JFrame container = new JFrame("Number Conversion 101");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//our textfields,
		bina	= new JTextField(50);
		hexa	= new JTextField(50);
		deci	= new JTextField(50);
		
		bina.setAlignmentX(Component.CENTER_ALIGNMENT);
		hexa.setAlignmentX(Component.CENTER_ALIGNMENT);
		deci.setAlignmentX(Component.CENTER_ALIGNMENT);

		bina.addActionListener(this);
		hexa.addActionListener(this);
		deci.addActionListener(this);
		
		bina.getDocument().addDocumentListener(new DocuListen());
		hexa.getDocument().addDocumentListener(new DocuListen());
		deci.getDocument().addDocumentListener(new DocuListen());

		bina.addFocusListener(this);
		hexa.addFocusListener(this);
		deci.addFocusListener(this);
		
		panel.add(new Label("Binary:"));
		panel.add(bina);
		panel.add(new Label("Hexadecimal:"));
		panel.add(hexa);
		panel.add(new Label("Decimal:"));
		panel.add(deci);
		
		container.add(panel);
		
		//displays the graphics
		container.pack();
		container.setVisible(true);
	}
	
	public static void main(String args[])
	{
		 new NumberConverterWindow1(); 
	}
	/**
	 * our action listener taking the values and putting them in
	 * it requires the user hitting enter
	 */
	public void actionPerformed(ActionEvent activeField)
	{
		if (activeField.getSource() == bina && bina.getText().matches("[01]+"))
		{
			deci.setText(convertBinatoDeci(bina.getText()));
			hexa.setText(convertToHexa(deci.getText()));	
		} 
		else if (activeField.getSource() == hexa && hexa.getText().matches("-?[0-9a-fA-F]+"))
		{
			deci.setText(convertToDeci(hexa.getText()));
			bina.setText(convertToBina(deci.getText()));
		}
		else if (activeField.getSource() == deci && deci.getText().matches("-?\\d+(\\.\\d+)?"))
		{
			hexa.setText(convertToHexa(deci.getText()));
			bina.setText(convertToBina(deci.getText()));
		}
	}
	/**
	 * Takes a decimal number and converts it to a hexadecimal number
	 * 
	 * @param a string in the form of a decimal number
	 * @return a string value in the form of a hexadecimal number
	 */
	public String convertToHexa(String val)
	{
		return Integer.toHexString(Integer.parseInt(val));
	}
	/**
	 * converts a hexadecimal number to a decimal number
	 * 
	 * @param val is a string, but it really is a hexadecimal number
	 * @return it returns a string value
	 */
	public String convertToDeci(String val)
	{
		return Integer.toString(Integer.parseInt(val,16));
	}
	
	/**
	 * Takes a decimal number and converts it to a binary number
	 * 
	 * @param a string in the form of a decimal number
	 * @return a string value in the form of a binary number
	 */
	public String convertToBina(String val)
	{
		return Integer.toBinaryString((Integer.parseInt(val)));
	}
	/**
	 * Takes a binary number and converts it to a decimal number
	 * 
	 * @param a string in the form of a binary number
	 * @return a string value in the form of a decimal number
	 */
	public String convertBinatoDeci(String val)
	{
		return Integer.toString(Integer.parseInt(val,2));
	}
	
	
	class DocuListen implements DocumentListener { 
        public void insertUpdate(DocumentEvent e) {
            updateLog(e);
        }
        public void removeUpdate(DocumentEvent e) {
            updateLog(e);
        }
        public void changedUpdate(DocumentEvent e) {
        	updateLog(e);
        }
 
        public void updateLog(DocumentEvent e)
        {
        	Runnable doHighlight = new Runnable() {
	            @Override
	            public void run()
	            {
	              	if (currentFocus.equals("bina") && bina.getText().matches("[01]+"))
	            	{
	              		currVal = bina.getText();
	            		deci.setText(convertBinatoDeci(bina.getText()));
	            		hexa.setText(convertToHexa(deci.getText()));	
	            	} 
	            	else if (currentFocus.equals("hexa") && hexa.getText().matches("-?[0-9a-fA-F]+"))
	            	{
	            		currVal = hexa.getText();
	            		deci.setText(convertToDeci(hexa.getText()));
	            		bina.setText(convertToBina(deci.getText()));
	            	}
	            	else if (currentFocus.equals("deci") && deci.getText().matches("-?\\d+(\\.\\d+)?"))
	            	{
	            		currVal = deci.getText();
	            		hexa.setText(convertToHexa(deci.getText()));
	            		bina.setText(convertToBina(deci.getText()));
	            	}
            }
            };       
            SwingUtilities.invokeLater(doHighlight);
            
        }
    }


	public void focusGained(FocusEvent event) {
		if (event.getSource() == bina)
		{
			currentFocus = "bina";
			currVal = bina.getText();
		} else if (event.getSource() == hexa)
		{
			currentFocus = "hexa";
			currVal = hexa.getText();
		} else if (event.getSource() == deci)
		{
			currVal = deci.getText();
			currentFocus = "deci";
		}		
	}

	@Override
	//we do not use this function but since we implement it, it has to be here
	public void focusLost(FocusEvent arg0) {}
	
}
