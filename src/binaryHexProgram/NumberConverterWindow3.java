package binaryHexProgram;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

/** 
 * This program is used to convert between hexadecimal, binary and decimal numbers
 * After typing in a number it will update due to the keyboard update.
 * @author s164166@student.dtu.dk
*/
public class NumberConverterWindow3 implements KeyListener{
	//our components for the window.
	private JTextField bina, deci, hexa;

	public NumberConverterWindow3()
	{
		//basic stuff for our container
		JFrame container = new JFrame("Number Conversion 101");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//our textfields,
		bina	= new JTextField(200);
		hexa	= new JTextField(200);
		deci	= new JTextField(200);
		
		bina.setAlignmentX(Component.CENTER_ALIGNMENT);
		hexa.setAlignmentX(Component.CENTER_ALIGNMENT);
		deci.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		bina.addKeyListener(this);
		hexa.addKeyListener(this);
		deci.addKeyListener(this);
		
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
		 new NumberConverterWindow3(); 
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
	/**
	 * Takes an event, checks whether our source is bina, hexa or deci and then updates our code
	 * @param event, the events we've given .addKeyListener(this)
	 */
	@Override
	public void keyReleased(KeyEvent event) {
		try
		{
			if (event.getSource() == bina && bina.getText().matches("[01]+"))
			{
				deci.setText(convertBinatoDeci(bina.getText()));
				hexa.setText(convertToHexa(deci.getText()));	
			} 
			else if (event.getSource() == hexa && hexa.getText().matches("-?[0-9a-fA-F]+"))
			{
				deci.setText(convertToDeci(hexa.getText()));
				bina.setText(convertToBina(deci.getText()));
			}
			else if (event.getSource() == deci && deci.getText().matches("-?\\d+(\\.\\d+)?"))
			{
				hexa.setText(convertToHexa(deci.getText()));
				bina.setText(convertToBina(deci.getText()));
			}
		}
		catch(NumberFormatException ex)
		{
			
		}
	}
	//unused but since we interface they need to be here
	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}
	//unused but since we interface they need to be here
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
}
