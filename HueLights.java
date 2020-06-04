

import javax.swing.*;
import java.net.*;
// import java.net.URL;



public class HueLights
{
	Properties PROPERTIES = new Properties();


	public static void main(String[] args)
	{
		URL base = null;
		try
		{
			base = new URL(PROPERTIES.HueBridgeURLString);
		}
		catch(MalformedURLException ex) {}
		Light light = new Light(4, base);


		// javax.swing.SwingUtilities.invokeLater(new Runnable()
		// {
			
            	Display display = new Display();
		// });
	}
}