
// REQUESTS
import java.net.URL;
import java.net.MalformedURLException;

// TYPES
import java.util.ArrayList;

// JSON
import org.json.*;

// STRING
import java.io.IOException;

import java.lang.System;  //TESTING

public class Room
{
	private String _id;
	private String _name;
	private Light[] _lights;
	private int _light_count;
	private URL _url;

	private boolean _all_on = false;
	private boolean _any_on = false;

	Room(String id, String base_url) throws MalformedURLException
	{
		_id = id;
		_url = new URL(base_url+"/groups/"+id);

		// pull data
		JSONObject json = data();
		_name = json.getString("name");

		// pull data: lights
		JSONArray json_array = json.getJSONArray("lights");
		String[] light_numbers = new String[json_array.length()];
		for(int x = 0; x < json_array.length(); x++) light_numbers[x] = json_array.getString(x);
		_light_count = light_numbers.length;

		// create lights
		_lights = new Light[light_numbers.length];
		for(int x = 0; x < light_numbers.length; x++)
			_lights[x] = new Light(light_numbers[x], base_url+"/lights/"+light_numbers[x]);
	}


	public ArrayList<Light> individually_get_lights_that_are_on() throws MalformedURLException
	{
		ArrayList<Light> on_lights = new ArrayList<Light>();
		for(int x = 0; x < _lights.length; x++)
			if(_lights[x].is_on()) on_lights.add(_lights[x]);

		return on_lights;
	}


	// ————————————————————— GETTERS —————————————————————

	public JSONObject data() throws MalformedURLException
	{
		try
		{
			GetRequest request = new GetRequest(_url);
			String result = request.send();

			JSONObject json = new JSONObject(result);
			if(json.has("error"))
			{
				String error_message = "Room::data: "+json.getJSONObject("error").getString("description");
				throw new MalformedURLException(error_message);
			}
			return json;
		}
		catch(IOException exception)
		{
			return null;
		}
	}


	public Light light(int index)
	{
		return _lights[index].copy();
	}



	public Light[] lights()
	{
		Light[] copy = new Light[_lights.length];
		for(int x = 0; x < _lights.length; x++) copy[x] = _lights[x].copy();
		return copy;
	}


	public int light_count()
	{
		return _light_count;
	}


	public String name()
	{
		return _name;
	}
}