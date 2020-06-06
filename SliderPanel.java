

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

// EXCEPTIONS
import java.net.MalformedURLException;
import java.io.IOException;


public class SliderPanel extends JPanel
{
	private JPanel _button_panel;
	private JPanel _slider_panel;

	private Room _room;

	private JButton _main_button;
	private JSlider _main_slider;

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
		_button_panel.add(new_button(_room.name()));

		_light_buttons = new JButton[_room.light_count()];
		for(int x = 0; x < _light_buttons.length; x++)
		{
			_light_buttons[x] = new_button(_room.light(x).name());

			final int final_x = x;
			_light_buttons[x].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
			      	try
			      	{
			      		Light light = _room.light(final_x);
			      		if(light.is_on()) 
			      		{
			      			_lights_sliders[final_x].setValue(0);
			      		}
			      		else
			      		{
			      			_lights_sliders[final_x].setValue(128);
			      		}
			      	}
			      	catch(MalformedURLException exception)
			      	{
						System.out.println("URL");  //TODO: warning
			      	}
				}
			});
			_button_panel.add(_light_buttons[x]);
		}
	}


	private void add_sliders()
	{
		_slider_panel.add(new_slider(128));

		_lights_sliders = new JSlider[_room.light_count()];
		for(int x = 0; x < _room.light_count(); x++)
		{
			_lights_sliders[x] = this.new_slider(_room.light(x).previous_brightness());

			final int final_x = x;
			_lights_sliders[x].addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent event)
			      {
			      	try
			      	{
			      		int brightness = _lights_sliders[final_x].getValue();
			      		Light light = _room.light(final_x);
			      		if(brightness == 0) _room.light(final_x).off();
			      		else if(light.is_on()) light.set_brightness(brightness);
			      		else light.on(brightness);
			      	}
			      	catch(MalformedURLException exception)
			      	{
						System.out.println("URL");  //TODO: warning
			      	}
			      	catch(IOException exception)
			      	{
			      		System.out.println("IO");  //TODO: warning
			      	}
				}
			});
			_slider_panel.add(_lights_sliders[x]);
		}
	}


	private JButton new_button(String text)
	{
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(150, 50));
		button.setBackground(new Color(20, 18, 18));
		button.setForeground(new Color(40, 60, 200));

		return button;
	}


	private JSlider new_slider(int current_brightness)
	{
		JSlider slider = new JSlider(0, 255, current_brightness);
		slider.setPreferredSize(new Dimension(150, 50));
		slider.setBackground(new Color(20, 18, 18));
		slider.setForeground(Color.darkGray);

		return slider;
	}


	public void update_sliders()
	{

	}
}