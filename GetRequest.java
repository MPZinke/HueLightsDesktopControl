

// REQUEST
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// STRING
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class GetRequest extends Request
{
	GetRequest(URL url)
	{
		super(url);
	}


	GetRequest(String url) throws MalformedURLException
	{
		super(url);
	}


	public String send() throws IOException
	{
		// if(_url.toString().equals("http://10.0.0.2/api/X8NSTfNvBDHy9EBcEJv-4f1HtSoB3eGr0AaAU9vh/groups"))
		// 	return "{\"1\":{\"name\":\"Bedroom\", \"type\":\"Room\", \"lights\":[\"1\",\"2\",\"3\",\"8\",\"9\"]}, \"2\":{\"name\":\"Office\", \"type\":\"Room\", \"lights\":[\"4\",\"5\",\"6\",\"7\"]}, \"3\":{\"name\":\"Bathroom\", \"type\":\"Room\", \"lights\":[\"10\",\"11\",\"12\",]},\"4\":{\"name\":\"Kitchen\", \"type\":\"Room\", \"lights\":[\"13\"]}}";
		// else if (_url.toString().contains("/state"))
		// 	return "{\"on\":\"true\",\"bri\":255}";
		// else return "{\"name\":\"Light\",\"state\":{\"on\":\"true\",\"bri\":255}}";
		_connection = (HttpURLConnection) _url.openConnection();
		_connection.setRequestMethod("GET");
		_response_code = _connection.getResponseCode();

		_reader = new BufferedReader(new InputStreamReader(_connection.getInputStream()));
		_builder = new StringBuilder();
		while((_line = _reader.readLine()) != null) _builder.append(_line);

		_reader.close();
		_previous_result = _builder.toString();
		if(_previous_result.substring(0,1).equals("[") 
		&& _previous_result.substring(_previous_result.length()-1).equals("]"))
			_previous_result = _previous_result.substring(1, _previous_result.length()-1);

		return _previous_result;
	}
}