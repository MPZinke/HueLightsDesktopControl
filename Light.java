
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.lang.System;  //TESTING

public class Light
{
	private String _light_id;

	private int _brightness;
	private boolean _is_on;
	private boolean _is_reachable;
	private String _name;
	private URL _url;

	Light(String light_id, String url)
	{
		_light_id = light_id;
		try
		{
			_url = new URL(url);
		}
		catch(MalformedURLException exception)
		{}
	}


	public boolean is_on()
	{
		try
		{
			HttpURLConnection connection = (HttpURLConnection)_url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			in.close();
			System.out.println(inputLine);
			System.out.println("Check");
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
		return _light_id;
	}
}