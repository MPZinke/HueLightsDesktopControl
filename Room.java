
import java.net.URL;
import java.net.MalformedURLException;

import java.util.ArrayList;

import java.lang.System;

public class Room
{
	private String _id;
	private String _name;
	private Light[] _lights;
	private URL _lights_URL;
	private boolean _is_on = false;

	Room(String id, String room_name, String[] light_numbers, String base_url)
	{
		_id = id;
		_name = room_name;

		// create URL
		try
		{
			_lights_URL = new URL(base_url+"/groups/"+id);
		}
		catch(MalformedURLException exception)
		{
			System.out.println("Failed");
		}

		// create lights
		_lights = new Light[light_numbers.length];
		for(int x = 0; x < light_numbers.length; x++)
		{
			System.out.println(base_url+"/lights/"+light_numbers[x]);
			_lights[x] = new Light(light_numbers[x], base_url+"/lights/"+light_numbers[x]);
		}
	}


	public ArrayList<String> individually_get_lights_that_are_on()
	{
		ArrayList<String> on_lights = new ArrayList<String>();
		for(int x = 0; x < _lights.length; x++)
		{
			_lights[x].is_on();
			System.out.println("Check");
			// if(_lights[x].is_on()) on_lights.add(_lights[x].light_id());
		}

		return on_lights;
	}
}