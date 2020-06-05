

// REQUEST
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// STRING
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class PutRequest extends Request
{
	PutRequest(URL url)
	{
		super(url);
	}


	PutRequest(String url) throws MalformedURLException
	{
		super(url);
	}


	public String send() throws IOException
	{
		_connection = (HttpURLConnection) _url.openConnection();
		_connection.setRequestMethod("PUT");
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