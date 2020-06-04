
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.lang.System;  //TESTING

public class Light
{
	private String _id;

	private int _brightness;
	private boolean _is_on;
	private boolean _is_reachable;
	private String _name;
	private URL _url;

	Light(String light_id, String url)
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
	}


	public boolean is_on()
	{
		try
		{
			GetRequest request = new GetRequest(_url);
			System.out.println(request.send());
		}
		catch(IOException exception)
		{
			System.out.println("Failed");
		}

		_is_on = true;
		return _is_on;
	}


	// getters
	public String name()
	{
		return _name;
	}


	public String light_id()
	{
		return _id;
	}
}