
// REQUESTS
import java.net.URL;
import java.net.MalformedURLException;

// TYPES
import java.util.ArrayList;

// JSON
import org.json.*;

// STRING
import java.io.IOException;
import java.util.Comparator;
import java.util.Collections;

import java.lang.System;  //TESTING

public class Room
{
	private String _id;
	private String _name;
	private ArrayList<Light> _lights;
	private int _light_count;
	private URL _url;

	private boolean _all_on = false;
	private boolean _any_on = false;

	Room(String id, String base_url) throws MalformedURLException, IOException
	{
		_id = id;
		_url = new URL(base_url+"/groups/"+id);

		// pull data
		JSONObject json = data();
		_name = json.getString("name");

		// pull data: lights
		JSONArray json_array = json.getJSONArray("lights");
		_light_count = json_array.length();
		String[] light_numbers = new String[_light_count];
		for(int x = 0; x < _light_count; x++) light_numbers[x] = json_array.getString(x);

		// create lights
		_lights = new ArrayList<Light>();
		for(int x = 0; x < _light_count; x++)
			_lights.add(new Light(light_numbers[x], base_url+"/lights/"+light_numbers[x]));

		// sort by name
		Collections.sort(_lights, new Comparator<Light>()
		{
			@Override
			public int compare(Light light1, Light light2)
			{
				return light1.name().compareToIgnoreCase(light2.name());
			}
		});
	}


	// ————————————————————— GETTERS —————————————————————

	// do not catch IOExceptions from GetRequest at this stage, because rooms are required for GUI process
	public JSONObject data() throws MalformedURLException, IOException
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


	public Light light(int index)
	{
		return _lights.get(index).copy();
	}



	public Light[] lights()
	{
		Light[] copy = new Light[_light_count];
		for(int x = 0; x < _light_count; x++) copy[x] = _lights.get(x).copy();
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


	// ————————————————————— SETTERS —————————————————————

	public boolean update() throws MalformedURLException
	{
		try
		{
			JSONObject json = data();
			// json.get

			return false;
		}
		catch(IOException exception)
		{
			return false;
		}
		// catch(MalformedURLException exception)
		// {
		// 	throw exception;
		// }
	}
}