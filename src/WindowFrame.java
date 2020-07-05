
// GUI
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.UIManager;
import static javax.swing.JOptionPane.showMessageDialog;

// REQUESTS
import java.net.MalformedURLException;
import java.io.IOException;

// TYPES
import java.util.ArrayList;
import java.util.function.Function;

import java.lang.System;  //TESTING

public class WindowFrame extends JFrame
{
	// system tray
	TrayIcon _tray_icon;
	SystemTray _tray;

	// inner GUI
	private JPanel _panel;  // holds button & slider panel
	// slider
	private JPanel _slider_panel;
	private RoomPanel[] _slider_panels;  // nested in _slider_panel
	// button
	private JPanel _button_panel;
	private JPanel _button_sub_panel;
	private JButton[] _buttons;

	// rooms
	private ArrayList<Room> _rooms;
	private JButton[] _room_buttons = new JButton[4];

	WindowFrame(ArrayList<Room> rooms) throws AWTException, Exception, IOException, MalformedURLException
	{
		super("Lights");
		if(!SystemTray.isSupported()) throw new Exception("Tray is not supported");

		_rooms = rooms;

		_panel = new JPanel();
		_slider_panel = new JPanel();
		_button_panel = new JPanel(new GridLayout(_rooms.size(), 1));

		_panel.setLayout(new GridBagLayout());
		_panel.setBackground(Properties.Background);
		_panel.setPreferredSize(new Dimension(700, 300));

		// add panels
		GridBagConstraints grid_constraints = new GridBagConstraints();
		grid_constraints.fill = GridBagConstraints.HORIZONTAL;
		set_grid_constraints(grid_constraints, 0, 0);
		_panel.add(_slider_panel);
		set_grid_constraints(grid_constraints, 1, 0);
		_panel.add(_button_panel);

		// setup panels
		_slider_panel.setPreferredSize(new Dimension(550, 300));
		_button_panel.setPreferredSize(new Dimension(150, 300));
		_slider_panel.setBackground(Properties.Background);
		_button_panel.setBackground(Properties.Background);

		this.add_slider_panels();
		this.add_buttons();

		setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));

		this.setup();
		this.setup_system_tray();
	}


	private void add_buttons()
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
					catch(Exception exception)
					{
						showMessageDialog(null, exception.toString());
					}
				}
			});
			_button_panel.add(_buttons[x], BorderLayout.EAST);
		}
	}


	private void add_slider_panels()
	{
		_slider_panel.setLayout(new BoxLayout(_slider_panel, BoxLayout.Y_AXIS));
		_slider_panels = new RoomPanel[_rooms.size()];
		for(int x = 0; x < _rooms.size(); x++)
		{
			_slider_panels[x] = new RoomPanel(_rooms.get(x));
			_slider_panel.add(_slider_panels[x], BorderLayout.WEST);
		}
	}


	private JButton new_button(String text)
	{
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(150, 50));
		button.setBackground(Properties.Background);
		button.setForeground(Properties.TextColor);
		return button;
	}


	private MenuItem new_menu_item(Function<Void, Void> function, String text)
	{
		MenuItem new_item = new MenuItem(text);
		new_item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				function.apply((Void)null);
			}
		});
		return new_item;
	}


	// ————————————————————— ACTIONS —————————————————————

	private void add_to_tray(int state)
	{
		if(state == ICONIFIED || state == 7) setVisible(false);
		else if(state == MAXIMIZED_BOTH || state == NORMAL) setVisible(true);
	}


	private void show_panel(int exception) throws MalformedURLException, IOException
	{
		for(int x = 0; x < _slider_panels.length; x++)
		{
			if(x != exception)
			{
				_buttons[x].setBackground(Properties.Background);
				_slider_panels[x].setVisible(false);
			}
			else
			{
				_buttons[x].setBackground(Properties.BackgroundLight);
				_slider_panels[x].update_panel();
				_slider_panels[x].setVisible(true);
			}
		}
	}


	// ———————————————————— CALLBACKS —————————————————————

	private Void exit()
	{
		System.exit(0);
		return (Void)null;
	}


	private Void set_room(int x, int brightness)
	{
		_slider_panels[x].set_all_lights(brightness);
		return (Void)null;
	}


	private Void show_window()
	{
		setVisible(true);
		setExtendedState(JFrame.NORMAL);
		return (Void)null;
	}


	// ————————————————————— SETUP ——————————————————————

	private void set_grid_constraints(GridBagConstraints grid_constraints, int x, int y)
	{
		grid_constraints.gridx = x;
		grid_constraints.gridy = y;
	}


	private void setup() throws MalformedURLException
	{
		this.add(_panel);

		this.pack();
		this.setPreferredSize(new Dimension(700, 300));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
			this.show_panel(0);
		}
		catch(IOException exception)
		{
			//TODO: display error
		}
	}


	private void setup_system_tray() throws AWTException
	{
		_tray = SystemTray.getSystemTray();
		PopupMenu tray_popup = new PopupMenu();

		Function<Void, Void> adjust_fun = value -> show_window();
		MenuItem menu_adjust = new_menu_item(adjust_fun, "Adjust");
		tray_popup.add(menu_adjust);

		Function<Void, Void> on_fun = value -> set_room(Properties.TrayRoomNumber, 128);
		MenuItem menu_on = new_menu_item(on_fun, Properties.TrayRoomName+" On");
		tray_popup.add(menu_on);

		Function<Void, Void> off_fun = value -> set_room(Properties.TrayRoomNumber, 0);
		MenuItem menu_off = new_menu_item(off_fun, Properties.TrayRoomName+" Off");
		tray_popup.add(menu_off);

		Function<Void, Void> exit_fun = value -> exit();
		MenuItem menu_exit = new_menu_item(exit_fun, "Exit");
		tray_popup.add(menu_exit);

		_tray_icon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("icon.png"), "Lights", tray_popup);
		_tray_icon.setImageAutoSize(true);
		addWindowStateListener(new WindowStateListener()
		{
			public void windowStateChanged(WindowEvent e)
			{
				try
				{
					add_to_tray(e.getNewState());
				}
				catch(Exception exception)
				{
					showMessageDialog(null, exception.toString());
				}
			}
		});

		_tray.add(_tray_icon);
	}
}