
import java.net.URL;

public class Light
{
	private int _light_id;
	private boolean _is_on = false;
	private URL _url;

	Light(int light_id, URL url)
	{
		_light_id = light_id;
		_url = url;
	}


	public boolean is_on()
	{

		_is_on = true;
		return _is_on;
	}
}