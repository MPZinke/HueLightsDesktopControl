

// URL
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// STRING
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// JSON
import org.json.*;


class Request
{
	protected URL _url;
	protected HttpURLConnection _connection;
	protected int _response_code;

	protected String _line;
	protected String _previous_result;
	protected StringBuilder _builder;
	protected BufferedReader _reader;


	protected Request(URL url)
	{
		_url = url;
	}


	protected Request(String url) throws MalformedURLException
	{
		_url = new URL(url);
	}


	public boolean is_reachable() throws IOException
	{
		_connection = (HttpURLConnection) _url.openConnection();
		_response_code = _connection.getResponseCode();

		return _response_code == 200;
	}


	public void validate() throws MalformedURLException
	{
		JSONObject json = new JSONObject(_previous_result);
		if(json.has("error"))
		{
			String error_message = "Request::validate: "+json.getJSONObject("error").getString("description");
			throw new MalformedURLException(error_message);
		}
	}
}
