package MainFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 1. 創建上排 menuBar 的選單
 2. 創建改名字時的輸入框
 3. 創建 rename、group、ungroup 3個選單的監聽(都會導到 canvas 去處理)
 **/

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	private Canvas canvas;

	public MenuBar() {
		// 實作 Singleton
		canvas = Canvas.getInstance();

		JMenu menu;

		// 加入 File menubar (沒功能)
		add(createMenu("File"));

		// 加入 Edit menubar
		menu = createMenu("Edit");
		add(menu);

		//** 將細功能項加入 Edit menubar 底下 **//
		menu.add(createMenuItem("Change object name", new ChangeNameListener()));
		menu.add(createMenuItem("Group", new GroupObjectListener()));
		menu.add(createMenuItem("Ungroup", new UngroupObjectListener()));
	}

	// Create a new menu with a given name
	private JMenu createMenu(String name) {
		return new JMenu(name);
	}

	// Create a new menu item with a given name and ActionListener
	private JMenuItem createMenuItem(String name, ActionListener listener) {
		JMenuItem mi = new JMenuItem(name);
		mi.addActionListener(listener);
		return mi;
	}

	// change Name method
	private void reName() {
		// 新增一個 JFrame 視窗
		JFrame inputTextFrame = new JFrame("Change Object Name");
		inputTextFrame.setSize(400, 100);
		inputTextFrame.getContentPane().setLayout(new GridLayout(0, 1));
		
		JPanel panel = null;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JTextField Text =  new JTextField("Object Name");
		panel.add(Text);
		inputTextFrame.getContentPane().add(panel);
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton confirm = new JButton("OK");
		panel.add(confirm);
		
		JButton cancel = new JButton("Cancel");
		panel.add(cancel);

		inputTextFrame.getContentPane().add(panel);
		
		inputTextFrame.setLocationRelativeTo(null);
		inputTextFrame.setVisible(true);
		
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.reName(Text.getText());
				inputTextFrame.dispose();
				
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputTextFrame.dispose();
			}
		});
	}

	// ungroup 監聽會導到 canvas 去實作 removeGroup function
	class UngroupObjectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			canvas.removeGroup();
		}
	}

	// group 監聽會導到 canvas 去實作 GroupShape function
	class GroupObjectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			canvas.groupShape();
		}
	}

	// change Name 監聽會在這個 menubar 實作完成
	class ChangeNameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			reName();
		}
	}
}
