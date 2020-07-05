
import java.awt.Color;


public class Properties
{
	public static String HueAPIKey = "";  // EDIT: use your Hue API key
	public static String HueURLString = "http://<IP_ADDRESS>/api/";  // EDIT: use your Hue Bridge IP address
	public static int TrayRoomNumber = 1;  // the room number for room that appears in system tray options
	public static String TrayRoomName = "Office";  // the room name for room that appears in system tray options

	public static Color Background = new Color(20, 18, 18);
	public static Color BackgroundLight = new Color(40, 38, 38);
	public static Color Warning = new Color(80, 0, 0);
	public static Color TextColor = new Color(30, 144, 255);

	public static int Width = 10;
}