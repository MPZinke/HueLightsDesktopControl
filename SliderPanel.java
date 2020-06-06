

// GUI
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.*;

// TYPES
import java.util.ArrayList;
import java.util.function.Function;

// EXCEPTIONS
import java.net.MalformedURLException;
import java.io.IOException;


public class SliderPanel extends JPanel
{
	private JPanel _button_panel;
	private JPanel _slider_panel;

	private Room _room;

	private JButton _room_button;
	private JSlider _room_slider;

	private JButton[] _light_buttons;
	private JSlider[] _lights_sliders;

	SliderPanel(Room room)
	{
		super();

		_room = room;

		this.setLayout(new GridBagLayout());
		_button_panel = new JPanel(new GridLayout(_room.light_count()+1, 1));
		_slider_panel = new JPanel(new GridLayout(_room.light_count()+1, 1));

		this.setPreferredSize(new Dimension(550, 300));
		this.setBackground(Color.darkGray);

		GridBagConstraints grid_constraints = new GridBagConstraints();
		grid_constraints.fill = GridBagConstraints.HORIZONTAL;
		grid_constraints.gridx = 0;
		grid_constraints.gridy = 0;
		this.add(_button_panel);

		grid_constraints.gridx = 1;
		grid_constraints.gridy = 0;
		this.add(_slider_panel);


		_button_panel.setPreferredSize(new Dimension(100, 300));
		_slider_panel.setPreferredSize(new Dimension(450, 300));
		_button_panel.setBackground(Color.darkGray);
		_slider_panel.setBackground(Color.darkGray);


		this.add_buttons();
		this.add_sliders();
	}


	private void add_buttons()
	{
		// _button_panel.add(new_button(_room.name()));
		Function<Integer, String> room_function = value -> switch_room_light(value);
		_button_panel.add(new_button(0, room_function, _room.name()));

		_light_buttons = new JButton[_room.light_count()];
		for(int x = 0; x < _light_buttons.length; x++)
		{
			final int final_x = x;
			Function<Integer, String> individual_function = value -> switch_individual_light(value);
			_light_buttons[x] = new_button(final_x, individual_function, _room.light(x).name());
			_button_panel.add(_light_buttons[x]);
		}
	}


	private void add_sliders()
	{
		Function<Integer, String> room_function = value -> set_room_light(value);
		_room_slider = this.new_slider(128, 0, room_function);
		_slider_panel.add(_room_slider);

		_lights_sliders = new JSlider[_room.light_count()];
		for(int x = 0; x < _room.light_count(); x++)
		{
			final int final_x = x;
			Function<Integer, String> individual_function = value -> set_individual_light(value);
			_lights_sliders[x] = this.new_slider(_room.light(x).previous_brightness(), final_x, individual_function);
			_slider_panel.add(_lights_sliders[x]);
		}
	}


	private JButton new_button(final int final_x, Function<Integer, String> function, String text)
	{
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(150, 50));
		button.setBackground(new Color(20, 18, 18));
		button.setForeground(new Color(40, 60, 200));
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
		      	function.apply(Integer.valueOf(final_x));
			}
		});

		return button;
	}


	private JSlider new_slider( int current_brightness, final int final_x, Function<Integer, String> function)
	{
		JSlider slider = new JSlider(0, 255, current_brightness);
		slider.setPreferredSize(new Dimension(150, 50));
		slider.setBackground(new Color(20, 18, 18));
		slider.setForeground(Color.darkGray);
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent event)
		      {
		      	function.apply(Integer.valueOf(final_x));
			}
		});

		return slider;
	}


	public void update_sliders()
	{

	}


	// ———————————————————— CALLBACKS —————————————————————


	private String set_individual_light(final Integer x)
	{
		try
      	{
      		int brightness = _lights_sliders[x].getValue();
      		Light light = _room.light(x);
      		if(brightness == 0) _room.light(x).off();
      		else if(light.is_on()) light.set_brightness(brightness);
      		else light.on(brightness);
      		return "";
      	}
      	catch(IOException exception)
      	{
      		System.out.println("IO");  //TODO: warning
      		return exception.toString();
      	}
	}


	private String set_room_light(final Integer x)
	{
		try
		{
			int brightness = _room_slider.getValue();
			if(brightness == 0) _room.off();
			else if(_room.any_on()) _room.set_brightness(brightness);
			else _room.on(brightness);
			return "";
		}
		catch(IOException exception)
		{
      		System.out.println(exception.toString());
			return exception.toString();
		}
	}


	private String switch_individual_light(final Integer x)
	{
		try
      	{
      		Light light = _room.light(x);
      		if(light.is_on()) _lights_sliders[x].setValue(0);
      		else _lights_sliders[x].setValue(128);
      		return "";
      	}
      	catch(IOException exception)
      	{
      		System.out.println(exception.toString());
      		return exception.toString();
      	}
	}


	private String switch_room_light(final Integer x)
	{
		try
      	{
      		if(_room.any_on()) _room_slider.setValue(0);
      		else _room_slider.setValue(128);
      		return "";
      	}
      	catch(IOException exception)
      	{
      		System.out.println(exception.toString());
      		return exception.toString();
      	}
	}
}