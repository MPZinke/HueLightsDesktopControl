

// GUI
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.*;
import static javax.swing.JOptionPane.showMessageDialog;

// TYPES
import java.util.ArrayList;
import java.util.function.Function;

// EXCEPTIONS
import java.net.MalformedURLException;
import java.io.IOException;


public class RoomPanel extends JPanel
{
	private Room _room;

	private JSlider _room_slider;
	private JSlider[] _lights_sliders;

	private boolean _is_user = true;  // used by sliders to determine if changed by program or user

	RoomPanel(Room room)
	{
		super(new GridLayout(room.light_count()+1, 1));

		_room = room;
		this.setPreferredSize(new Dimension(550, 300));
		this.setBackground(Properties.Background);
		this.add_sliders();
		_room_slider.setBackground(Properties.BackgroundLight);
	}


	private void add_sliders()
	{
		Function<Integer, String> room_function = value -> set_room_light(value);
		_room_slider = this.new_slider(_room.brightness(), 0, room_function, _room.name());
		this.add(_room_slider);

		_lights_sliders = new JSlider[_room.light_count()];
		for(int x = 0; x < _room.light_count(); x++)
		{
			final int final_x = x;
			Function<Integer, String> individual_function = value -> set_individual_light(value);
			_lights_sliders[x] = this.new_slider(_room.light(x).previous_brightness(), final_x, individual_function, 
					_room.light(x).name());
			this.add(_lights_sliders[x]);
		}
	}


	private JSlider new_slider(int current_brightness, final int final_x, Function<Integer, String> function, 
	String name)
	{
		JSlider slider = new JSlider(0, 255, current_brightness);
		slider.setPreferredSize(new Dimension(150, 50));
		slider.setBackground(Properties.Background);
		TitledBorder border = new TitledBorder(new LineBorder(Properties.BackgroundLight, 8, true), name);
		border.setTitleColor(Properties.TextColor);
		slider.setBorder(border);
		slider.setForeground(Properties.BackgroundLight);
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent event)
			{
				if(_is_user)
				{
					String error_message = function.apply(Integer.valueOf(final_x));
					if(error_message.length() != 0) showMessageDialog(null, error_message);
				}
			}
		});

		return slider;
	}


	// ———————————————————— CALLBACKS —————————————————————

	private String set_individual_light(final Integer x)
	{
		try
		{
			_room.light(x).set(_lights_sliders[x].getValue());
			match_room_to_lights();
			return "";  // successful
		}
		catch(IOException exception)
		{
			return exception.toString();  // unsuccessful
		}
	}


	private String set_room_light(final Integer x)
	{
		try
		{
			_room.set(_room_slider.getValue());
			match_lights_to_room();
			return "";  // successful
		}
		catch(IOException exception)
		{
			return exception.toString();  // error  // unsuccessful
		}
	}


	// ————————————————————— SETTERS —————————————————————

	private void match_lights_to_room() throws IOException, MalformedURLException
	{
		int brightness = _room_slider.getValue();
		for(int x = 0; x < _lights_sliders.length; x++)
		{
			// determine whether to adjust light (Hue will not turn a light on/off from Room) or it's already set
			_is_user = (_room.light(x).is_on() || brightness == 0) ? false : true;
			_lights_sliders[x].setValue(brightness);
			_is_user = true;
		}
	}


	private void match_room_to_lights()
	{
		_is_user = false;

		int total = 0;
		for(int x = 0; x < _lights_sliders.length; x++) total += _lights_sliders[x].getValue();
		_room_slider.setValue(total / _lights_sliders.length);

		_is_user = true;
	}


	public void set_all_lights(int x)
	{
		_room_slider.setValue(x);
	}


	public void update_panel() throws MalformedURLException, IOException
	{
		_room.update_room();
		_is_user = false;  // just updated info: no need to change light to slider that is changing to light

		Light[] lights = _room.lights();
		for(int x = 0; x < lights.length; x++)
		{
			if(!lights[x].previous_is_on() && _lights_sliders[x].getValue() != 0) _lights_sliders[x].setValue(0);
			else if(lights[x].previous_brightness() != _lights_sliders[x].getValue())
				_lights_sliders[x].setValue(lights[x].previous_brightness());

			if(!lights[x].previous_is_reachable())
			{
				String text = _room.light(x).name();
				TitledBorder border = new TitledBorder(new LineBorder(Properties.Warning, 10, true), text);
				border.setTitleColor(Properties.TextColor);
				_lights_sliders[x].setBorder(border);
			}
			else _lights_sliders[x].setBackground(Properties.Background);
		}

		_is_user = true;
	}
}