
import javax.swing.*;



public class Display
{
	JFrame _frame;

	// rooms
	String[] _room_names = {"Bedroom", "Office", "Bathroom", "Kitchen"};
	JButton[] _room_buttons = new JButton[4];

	Display()
	{
		_frame = new JFrame("Lights");
		_frame.pack();

		for(int x = 0; x < 4; x++)
		{
			_room_buttons[x] = new JButton(_room_names[x]);
			_room_buttons[x].setBounds(100*x, 100, 100, 40);
			_frame.add(_room_buttons[x]);
		}

		_frame.setSize(700, 500);
		_frame.setLayout(null);
		_frame.setVisible(true);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}