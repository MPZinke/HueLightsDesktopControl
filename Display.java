
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

import java.lang.System;  //TESTING

public class Display
{
	private JFrame _frame;
	private JPanel _panel;  // holds button & slider panel

	private JPanel _slider_panel;
	private SliderPanel[] _room_panels;  // nested in _slider_panel

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
		_panel = new JPanel(new GridLayout(1, 2));
		_frame.add(_panel);

		add_SliderPanels();
		add_Buttons();

		_frame.pack();
		_frame.setSize(700, 50*rooms.size()+25);
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
		this.show_panel(0);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	private void add_Buttons()
	{
		_button_panel = new JPanel(new GridLayout(_rooms.size(), 1));
		_panel.add(_button_panel);

		_buttons = new JButton[_rooms.size()];
		for(int x = 0; x < _rooms.size(); x++)
		{
			final int final_x = x;
			_buttons[x] = new JButton(_rooms.get(x).name());
			_buttons[x].setPreferredSize(new Dimension(150, 50));
			_buttons[x].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					show_panel(final_x);
				}
			});
			_button_panel.add(_buttons[x], BorderLayout.EAST);
		}
	}


	private void add_SliderPanels()
	{
		_slider_panel = new JPanel();
		_slider_panel.setLayout(new BoxLayout(_slider_panel, BoxLayout.Y_AXIS));
		_panel.add(_slider_panel);

		_room_panels = new SliderPanel[_rooms.size()];
		for(int x = 0; x < _rooms.size(); x++)
		{
			_room_panels[x] = new SliderPanel(_rooms.get(x));
			_slider_panel.add(_room_panels[x], BorderLayout.WEST);
		}
	}


	private void show_panel(int exception)
	{
		for(int x = 0; x < _room_panels.length; x++)
		{
			if(x == exception) _room_panels[x].setVisible(true);
			else _room_panels[x].setVisible(false);
		}
	}
}