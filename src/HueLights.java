

// GUI
import javax.swing.*;

// REQUESTS
import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;

// TYPES
import java.util.ArrayList;
import java.util.HashMap;

// STRING
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// JSON
import org.json.*;

import java.lang.System;  //TESTING

public class HueLights
{
	public static ArrayList<Room> create_rooms(String base_url) throws MalformedURLException, IOException
	{
		ArrayList<Room> rooms = new ArrayList<Room>();

		GetRequest request = new GetRequest(base_url + "/groups");
		String result = request.send();

		JSONObject json = new JSONObject(result);
		// shouldn't happen if Properites data is correct
		if(json.has("error"))
		{
			String error_message = "HueLights::create_rooms: "+json.getJSONObject("error").getString("description");
			throw new MalformedURLException(error_message);
		} 

		for(int x = 1; json.has(Integer.toString(x)); x++)
		{
			String id = Integer.toString(x);
			JSONObject room_json = json.getJSONObject(id);

			if(room_json.getString("type").equals("Room")) rooms.add(new Room(id, base_url));
		}

		return rooms;
	}


	public static void main(String[] args)
	{
		System.out.println("Starting Program");  //TESTING
		System.setProperty("apple.awt.UIElement", "true");

		try
		{
			System.out.print("Creating rooms...");  //TESTING
			String url_string = Properties.HueURLString + Properties.HueAPIKey;
			ArrayList<Room> rooms = create_rooms(url_string);

	      	WindowFrame display = new WindowFrame(rooms);
			System.out.println("DONE");  //TESTING
		}
		catch(MalformedURLException exception)
		{
			// prompt user and end program
			System.out.println("MalformedURLException: " + exception.getMessage());
		}
		catch(IOException exception)
		{
			System.out.println("IOException: " + exception.getMessage());
			// prompt user and end program
		}
		catch(Exception exception)
		{
			System.out.println("Exception: " + exception.getMessage());
			// prompt user and end program
		}
	}
}