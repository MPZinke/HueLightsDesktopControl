

// URL
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// STRING
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


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
}
