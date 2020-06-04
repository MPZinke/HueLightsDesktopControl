

import javax.swing.*;
import java.net.URL;
import java.lang.System;
import java.util.*;

// import java.net.URL;



public class HueLights
{
	public static Room[] connect_to_hue(String url_string)
	{
		Properties PROPERTIES = new Properties();

		Room[] rooms = new Room[PROPERTIES.HueRooms.size()];
		Object[] key_set = PROPERTIES.HueRooms.keySet().toArray();
		for(int x = 0; x < key_set.length; x++)
			rooms[x] = new Room((String)key_set[x], PROPERTIES.HueRooms.get(key_set[x]), url_string);

		return rooms;
	}


	public static void main(String[] args)
	{
		Properties PROPERTIES = new Properties();
		String url_string = PROPERTIES.HueURLString + PROPERTIES.HueAPIKey + "/lights";

		Room[] rooms = connect_to_hue(url_string);

		for(int x = 0; x < rooms.length; x++) rooms[x].individually_get_lights_that_are_on();


		// javax.swing.SwingUtilities.invokeLater(new Runnable()
		// {
            	Display display = new Display();
		// });
	}
}