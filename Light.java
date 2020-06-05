
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// JSON
import org.json.*;

import java.lang.System;  //TESTING

public class Light
{
	private String _id;
	private String _name;
	private URL _url;

	private int _brightness;
	private boolean _is_on;
	private boolean _is_reachable;

	Light(String light_id, String url) throws MalformedURLException
	{
		_id = light_id;
		try
		{
			_url = new URL(url);
		}
		catch(MalformedURLException exception)
		{
			System.out.println("Failed");
		}

		JSONObject json = data();
		if(json != null)
		{
			_name = json.getString("name");
			_brightness = json.getJSONObject("state").getInt("bri");
			_is_on = json.getJSONObject("state").getBoolean("on");
			_is_reachable = json.getJSONObject("state").getBoolean("reachable");
		}
	}


	Light(String light_id, URL url) throws MalformedURLException
	{
		_id = light_id;
		_url = url;

		JSONObject json = data();
		if(json != null)
		{
			_brightness = json.getJSONObject("state").getInt("bri");
			_is_on = json.getJSONObject("state").getBoolean("on");
			_is_reachable = json.getJSONObject("state").getBoolean("reachable");
			_name = json.getString("name");
		}
	}


	Light(String light_id, String name, URL url, int brightness, boolean is_on, boolean is_reachable)
	{
		_id = light_id;
		_name = name;
		_url = url;
		_brightness = brightness;
		_is_on = is_on;
		_is_reachable = is_reachable;
	}


	public Light copy()
	{
		return new Light(_id, _name, _url, _brightness, _is_on, _is_reachable);
	}


	// ————————————————————— GETTERS —————————————————————

	public int brightness() throws MalformedURLException
	{
		JSONObject json = data();
		if(json != null)
		{
			_brightness = json.getJSONObject("state").getInt("bri");
		}

		return _brightness;
	}


	public JSONObject data() throws MalformedURLException
	{
		try
		{
			GetRequest request = new GetRequest(_url);
			String result = request.send();

			JSONObject json = new JSONObject(result);
			if(json.has("error"))
			{
				String error_message = "Light::data: "+json.getJSONObject("error").getString("description");
				throw new MalformedURLException(error_message);
			}
			return json;
		}
		catch(IOException exception)
		{
			return null;
		}
	}


	public String id()
	{
		return _id;
	}


	public boolean is_on() throws MalformedURLException
	{
		JSONObject json = data();
		if(json != null)
		{
			_is_on = json.getJSONObject("state").getBoolean("on");
		}

		return _is_on;
	}


	public boolean is_reachable() throws MalformedURLException
	{
		JSONObject json = data();
		if(json != null)
		{
			_is_reachable = json.getJSONObject("state").getBoolean("reachable");
		}

		return _is_reachable;
	}


	public String name()
	{
		return _name;
	}


	public int previous_brightness()
	{
		return _brightness;
	}


	public boolean previous_is_on()
	{
		return _is_on;
	}

	public boolean previous_is_reachable()
	{
		return _is_reachable;
	}


	public URL url()
	{
		return _url;
	}


	// ————————————————————— SETTERS —————————————————————

	public void set_value(int value)
	{
		System.out.println(value);  //TESTING
	}
}