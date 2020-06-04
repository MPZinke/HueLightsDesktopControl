

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
		_url = url;
	}


	GetRequest(String url) throws MalformedURLException
	{
		_url = new URL(url);
	}


	public String send() throws IOException
	{
		_connection = (HttpURLConnection) _url.openConnection();
		_connection.setRequestMethod("GET");
		_response_code = _connection.getResponseCode();

		_reader = new BufferedReader(new InputStreamReader(_connection.getInputStream()));
		_builder = new StringBuilder();
		while((_line = _reader.readLine()) != null) _builder.append(_line);

		_reader.close();
		_previous_result = _builder.toString();
		return _previous_result;
	}
}