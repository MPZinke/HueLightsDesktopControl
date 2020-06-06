
// GUI
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// REQUESTS
import java.net.MalformedURLException;

// TYPES
import java.util.ArrayList;

import java.lang.System;  //TESTING

public class Display
{
	private JFrame _frame;
	private JPanel _panel;  // holds button & slider panel

	private JPanel _slider_panel;
	private SliderPanel[] _slider_panels;  // nested in _slider_panel

	private JPanel _button_panel;
	private JPanel _button_sub_panel;
	private JButton[] _buttons;
	private ArrayList<Room> _rooms;

	// rooms
	private String[] _room_names = {"Bedroom", "Office", "Bathroom", "Kitchen"};
	private JButton[] _room_buttons = new JButton[4];

	Display(ArrayList<Room> rooms)
	{
		_rooms = rooms;

		_frame = new JFrame("Lights");
		_panel = new JPanel();
		_slider_panel = new JPanel();
		_button_panel = new JPanel(new GridLayout(_rooms.size(), 1));

		_panel.setLayout(new GridBagLayout());
		_panel.setBackground(Color.darkGray);
		_panel.setPreferredSize(new Dimension(700, 300));

		// add panels
		GridBagConstraints grid_constraints = new GridBagConstraints();
		grid_constraints.fill = GridBagConstraints.HORIZONTAL;
		set_grid_constraints(grid_constraints, 0, 0);
		_panel.add(_slider_panel);
		set_grid_constraints(grid_constraints, 1, 0);
		_panel.add(_button_panel);

		_slider_panel.setPreferredSize(new Dimension(550, 300));
		_button_panel.setPreferredSize(new Dimension(150, 300));
		_slider_panel.setBackground(Color.red);
		_button_panel.setBackground(Color.darkGray);

		this.add_SliderPanels();
		this.add_Buttons();

		this.setup_frame();
	}


	private void add_Buttons()
	{
		_buttons = new JButton[_rooms.size()];
		for(int x = 0; x < _rooms.size(); x++)
		{
			final int final_x = x;
			_buttons[x] = this.new_button(_rooms.get(x).name());
			_buttons[x].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						show_panel(final_x);
					}
					catch(MalformedURLException exception)
					{
						//TODO: alert of error
					}
				}
			});
			_button_panel.add(_buttons[x], BorderLayout.EAST);
		}
	}


	private void add_SliderPanels()
	{
		_slider_panel.setLayout(new BoxLayout(_slider_panel, BoxLayout.Y_AXIS));
		_slider_panels = new SliderPanel[_rooms.size()];
		for(int x = 0; x < _rooms.size(); x++)
		{
			_slider_panels[x] = new SliderPanel(_rooms.get(x));
			_slider_panel.add(_slider_panels[x], BorderLayout.WEST);
		}
	}


	private JButton new_button(String text)
	{
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(150, 50));
		button.setBackground(new Color(38, 38, 38));
		button.setForeground(new Color(40, 60, 200));
		return button;
	}


	private void show_panel(int exception) throws MalformedURLException
	{
		for(int x = 0; x < _slider_panels.length; x++)
		{
			if(x != exception) _slider_panels[x].setVisible(false);
			else
			{
				_rooms.get(x).update();
				_slider_panels[x].setVisible(true);
			}
		}
	}


	private void set_grid_constraints(GridBagConstraints grid_constraints, int x, int y)
	{
		grid_constraints.gridx = x;
		grid_constraints.gridy = y;
	}


	private void setup_frame()
	{
		_frame.add(_panel);

		_frame.pack();
		_frame.setPreferredSize(new Dimension(700, 300));
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
		try
		{
			this.show_panel(0);
		}
		catch(MalformedURLException exception)
		{
			//TODO: display error
		}
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}