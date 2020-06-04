
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
	public static ArrayList<Room> create_rooms(String base_url)
	{
		ArrayList<Room> rooms = new ArrayList<Room>();
		try
		{
			GetRequest request = new GetRequest(base_url + "/groups");
			String result = request.send();

			JSONObject json = new JSONObject(result);
			for(int x = 1; true; x++)
			{
				String id = Integer.toString(x);
				JSONObject room_json = json.getJSONObject(id);

				if(!room_json.getString("type").equals("Room")) continue;

				String name = room_json.getString("name");
				JSONArray json_array = room_json.getJSONArray("lights");
				String[] string_array = new String[json_array.length()];
				for(int y = 0; y < json_array.length(); y++) string_array[y] = json_array.getString(y);

				rooms.add(new Room(id, name, string_array, base_url));
			}
		}
		catch(MalformedURLException exception)
		{
			// bad URL
		}
		catch(IOException exception)
		{
			// unable to connect
		}
		catch(JSONException exception) { /* done adding to rooms */}

		return rooms;
	}


	public static void main(String[] args)
	{
		Properties PROPERTIES = new Properties();
		String url_string = PROPERTIES.HueURLString + PROPERTIES.HueAPIKey;

		ArrayList<Room> rooms = create_rooms(url_string);
		System.out.println(rooms.size());  //TESTING

		for(int x = 0; x < rooms.size(); x++) rooms.get(x).individually_get_lights_that_are_on();


		// javax.swing.SwingUtilities.invokeLater(new Runnable()
		// {
            	Display display = new Display();
		// });
	}
}