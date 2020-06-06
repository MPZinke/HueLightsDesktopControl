
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

	private GetRequest _get_request;
	private PutRequest _put_request;
	private URL _url;

	private int _brightness;
	private boolean _is_on;
	private boolean _is_reachable;

	Light(String light_id, String url) throws MalformedURLException
	{
		_id = light_id;

		_url = new URL(url);
		_get_request = new GetRequest(_url.toString());
		_put_request = new PutRequest(_url.toString()+"/state");

		JSONObject json = data();
		if(json != null)
		{
			_is_on = json.getJSONObject("state").getBoolean("on");
			if(!_is_on) _brightness = 0;
			else _brightness = json.getJSONObject("state").getInt("bri");

			_is_reachable = json.getJSONObject("state").getBoolean("reachable");
			_name = json.getString("name");
		}
	}


	Light(String light_id, URL url) throws MalformedURLException
	{
		_id = light_id;

		_url = url;
		_get_request = new GetRequest(_url.toString());
		_put_request = new PutRequest(_url.toString()+"/state");

		JSONObject json = data();
		if(json != null)
		{
			_is_on = json.getJSONObject("state").getBoolean("on");
			if(!_is_on) _brightness = 0;
			else _brightness = json.getJSONObject("state").getInt("bri");

			_is_reachable = json.getJSONObject("state").getBoolean("reachable");
			_name = json.getString("name");
		}
	}


	Light(String light_id, String name, URL url, int brightness, boolean is_on, boolean is_reachable, 
	GetRequest get_request, PutRequest put_request)
	{
		_id = light_id;
		_name = name;
		_url = url;
		_brightness = brightness;
		_is_on = is_on;
		_is_reachable = is_reachable;
		_get_request = get_request;
		_put_request = put_request;
	}


	public Light copy()
	{
		return new Light(_id, _name, _url, _brightness, _is_on, _is_reachable, _get_request, _put_request);
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
			String result = _get_request.send();

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

	public void set_brightness(int brightness) throws MalformedURLException, IOException
	{
		JSONObject json = new JSONObject();
		json.put("bri", brightness);

		_put_request.send(json);
		_brightness = brightness;
	}


	public void off() throws MalformedURLException, IOException
	{
		JSONObject json = new JSONObject();
		json.put("on", false);

		_put_request.send(json);
		_is_on = false;
	}


	public void on() throws MalformedURLException, IOException
	{
		JSONObject json = new JSONObject();
		json.put("on", true);

		_put_request.send(json);
		_is_on = true;
	}


	public void on(int brightness) throws MalformedURLException, IOException
	{
		JSONObject json = new JSONObject();
		json.put("on", true);
		json.put("bri", brightness);

		_put_request.send(json);
		_is_on = true;
		_brightness = brightness;
	}
}