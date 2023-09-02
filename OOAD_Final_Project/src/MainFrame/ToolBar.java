package MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Mode.*;
/**
 1. 創建 toolBar 的 button
 2. 將 button 都加入 ActionListener
 **/
public class ToolBar extends JToolBar{
	private static final int TOOL_NUM = 6;
	private static final Color BUTTON_SELECTED_COLOR = Color.GRAY;
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	private static final Color BUTTON_DEFAULT_COLOR = Color.WHITE;

	private JButton holdBtn = null;
	private Canvas canvas;

	public ToolBar() {
		this.canvas = Canvas.getInstance();
		initializeToolBar();
		initializeButtons();
	}

	private void initializeToolBar() {
		setLayout(new GridLayout(TOOL_NUM, 1, 2, 2));
		this.setBackground(BACKGROUND_COLOR);
	}

	private void initializeButtons() {
		createAndAddButton("select", "icon/select.png", new selectMode());
		createAndAddButton("associate", "icon/associate.png", new createLineMode("associate"));
		createAndAddButton("general", "icon/general.png", new createLineMode("general"));
		createAndAddButton("composite", "icon/composite.png", new createLineMode("composite"));
		createAndAddButton("class", "icon/class.png", new createShapeMode("class"));
		createAndAddButton("usecase", "icon/usecase.png", new createShapeMode("usecase"));
	}

	private void createAndAddButton(String toolName, String iconPath, Mode toolMode) {
		ToolBtn button = new ToolBtn(toolName, new ImageIcon(iconPath), toolMode);
		add(button);
	}

	private class ToolBtn extends JButton{
		private Mode toolMode;

		public ToolBtn(String toolName, ImageIcon icon, Mode toolMode) {
			this.toolMode = toolMode;
			initializeButton(toolName, icon);
		}

		private void initializeButton(String toolName, ImageIcon icon) {
			setToolTipText(toolName);
			setIcon(icon);
			setFocusable(false);
			setBackground(BUTTON_DEFAULT_COLOR);
			setBorderPainted(false);
			setRolloverEnabled(true);
			addActionListener(new ToolListener());
		}

		class ToolListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(holdBtn != null)
					holdBtn.setBackground(BUTTON_DEFAULT_COLOR);
				holdBtn = (JButton) e.getSource();
				holdBtn.setBackground(BUTTON_SELECTED_COLOR);
				canvas.currentMode = toolMode;
				canvas.setCurrentMode();
				canvas.reset();
				canvas.repaint();
			}
		}
	}
}
