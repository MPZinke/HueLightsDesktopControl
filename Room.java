
import java.net.URL;
import java.net.MalformedURLException;

import java.util.ArrayList;

import java.lang.System;

public class Room
{
	String _room_name;
	Light[] _lights;
	URL _lights_URL;
	boolean _is_on = false;

	Room(String room_name, String[] light_numbers, String url_string)
	{
		_room_name = room_name;

		// create URL
		try
		{
			_lights_URL = new URL(url_string);
		}
		catch(MalformedURLException exception)
		{}

		// create lights
		_lights = new Light[light_numbers.length];
		for(int x = 0; x < light_numbers.length; x++)
		{
			_lights[x] = new Light(light_numbers[x], url_string+"/"+light_numbers[x]);
		}
	}


	public ArrayList<String> individually_get_lights_that_are_on()
	{
		ArrayList<String> on_lights = new ArrayList<String>();
		for(int x = 0; x < _lights.length; x++)
		{
			_lights[x].is_on();
			// System.out.println("Check");
			// if(_lights[x].is_on()) on_lights.add(_lights[x].light_id());
		}

		return on_lights;
	}
}