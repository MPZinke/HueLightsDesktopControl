


import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.ArrayList;


public class SliderPanel extends JPanel
{
	private Room _room;

	private JLabel _main_label;
	private JSlider _main;

	private JLabel[] _slider_labels;
	private final JSlider[] _sliders;

	SliderPanel(Room room)
	{
		super(new GridLayout(room.light_count()+1, 2));
		_room = room;

		_main_label = new JLabel(_room.name());
		_main = new JSlider();
		_main.setPreferredSize(new Dimension(150, 50));
		this.add(_main_label);
		this.add(_main);

		_sliders = new JSlider[room.light_count()];
		_slider_labels = new JLabel[room.light_count()];
		for(int x = 0; x < room.light_count(); x++)
		{
			final int final_x = x;
			_slider_labels[x] = new JLabel(_room.light(final_x).name());
			_sliders[x] = new JSlider(0, 255, _room.light(final_x).previous_brightness());
			_sliders[x].setPreferredSize(new Dimension(150, 50));
			_sliders[x].addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent event)
			      {
			      	_room.light(final_x).set_value(_sliders[final_x].getValue());
				}
			});
			this.add(_slider_labels[x]);
			this.add(_sliders[x]);
		}
	}


	public void update_sliders()
	{

	}



	// public void hide_panel()
	// {
	// 	_main.setVisible(false);
	// 	for(int x = 0; x < _sliders.length; x++) _sliders[x].setVisible(false);
	// }


	// public void show_panel()
	// {
	// 	_main.setVisible(true);
	// 	for(int x = 0; x < _sliders.length; x++) _sliders[x].setVisible(true);
	// }
}