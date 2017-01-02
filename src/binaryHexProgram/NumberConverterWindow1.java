package binaryHexProgram;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/** 
 * This program is used to convert between hexadecimal, binary and decimal numbers
 * After typing in a number and the user hits enter the values in the other fields will change
 * if the value is legal.
 * @author s164166@student.dtu.dk
*/
public class NumberConverterWindow1 implements ActionListener
{
	//our components for the window.
	private JTextField bina, deci, hexa;
	
	public NumberConverterWindow1()
	{
		//our textfields,
		bina	= new JTextField(50);
		hexa	= new JTextField(50);
		deci	= new JTextField(50);
		
		//basic stuff for our container
		JFrame container = new JFrame("Number Conversion 101");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		bina.setAlignmentX(Component.CENTER_ALIGNMENT);
		hexa.setAlignmentX(Component.CENTER_ALIGNMENT);
		deci.setAlignmentX(Component.CENTER_ALIGNMENT);

		bina.addActionListener(this);
		hexa.addActionListener(this);
		deci.addActionListener(this);
		
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
}
