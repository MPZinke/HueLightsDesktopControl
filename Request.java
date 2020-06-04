

// URL
import java.net.URL;
import java.net.HttpURLConnection;

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


	public boolean is_reachable() throws IOException
	{
		_connection = (HttpURLConnection) _url.openConnection();
		_response_code = _connection.getResponseCode();

		return _response_code == 200;
	}
}
