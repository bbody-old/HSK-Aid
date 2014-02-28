package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import data.Card;
import data.Cards;
import data.CardsSaveFile;
import data.StaticFunctions;


public class MainWindow {

	private JFrame frame;
	private JLabel label_1;
	private JLabel lblPinyin;
	private JTextPane txtpnEnglish;
	
	Cards cards;
	private String filename;

	/**
	 * Create the application.
	 * @param cards 
	 */
	public MainWindow(Cards cards, String filename) {
		this.filename = filename;
		frame = new JFrame();
		this.cards = cards;
		initialize();
		frame.setVisible(true);
	}
	
	public void close(){
		// TODO: Save
		frame.dispose();
		CardsSaveFile csf = new CardsSaveFile();
		try {
			csf.saveProgress(filename, cards);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Setup frame
		frame.setBounds(StaticFunctions.bounds);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu(Messages.getString("MainWindow.MENU_ITEM_FILE")); //$NON-NLS-1$
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem(Messages.getString("MainWindow.MENU_ITEM_OPEN")); //$NON-NLS-1$
		mntmOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				//String filename = getFile();
				//cards = new Cards(StaticFunctions.getCardsFromCSV(filename));
				String filename = StaticFunctions.getFile(frame);
				
				// Check if filename has any problems
				switch (StaticFunctions.filenameCheck(filename)){
					case OK:
						frame.setVisible(false);
						ExcelLoader el = new ExcelLoader(filename);
						Preferences prefs = Preferences.userRoot().node("lastfile");
						prefs.put("LASTFILENAME", filename);
						el.setVisible(true);
						break;
					case NULL:
						JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NOTHING_SELECTED"));
						break;
					case NOTHING:
						JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NOTHING_SELECTED"));
						break;
					case EXTENSION:
						JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_EXTENSION"));
						break;
					case NOT_EXIST:
						JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NON_EXIST"));
						break;
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmAbout = new JMenuItem(Messages.getString("MainWindow.MENU_ITEM_ABOUT")); //$NON-NLS-1$
		mnFile.add(mntmAbout);
		
		JMenuItem mntmExit = new JMenuItem(Messages.getString("MainWindow.MENU_ITEM_EXIT")); //$NON-NLS-1$
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO clean up save files
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		label = new JLabel(Messages.getString("MainWindow.LABEL_HANZI")); //$NON-NLS-1$
		label.setFont(new Font(Messages.getString("MainWindow.FONT_TYPE_OPEN_SANS"), Font.PLAIN, 48)); //$NON-NLS-1$
		panel.add(label);
		
		JPanel panel_4 = new JPanel();
		frame.getContentPane().add(panel_4, BorderLayout.CENTER);
		
		label_1 = new JLabel((String) null);
		panel_4.add(label_1);
		label_1.setVerticalAlignment(SwingConstants.BOTTOM);
		label_1.setFont(new Font(Messages.getString("MainWindow.FONT_TYPE"), Font.PLAIN, 28)); //$NON-NLS-1$
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		panel_1.add(splitPane);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		lblPinyin = new JLabel(Messages.getString("MainWindow.LABEL_PINYIN")); //$NON-NLS-1$
		lblPinyin.setFont(new Font(Messages.getString("MainWindow.FONT_TYPE_LUCIDA_GRANDE"), Font.PLAIN, 22)); //$NON-NLS-1$
		lblPinyin.setHorizontalAlignment(SwingConstants.CENTER);
		splitPane.setLeftComponent(lblPinyin);
		
		txtpnEnglish = new JTextPane();
		txtpnEnglish.setEditable(false);
		txtpnEnglish.setFont(new Font(Messages.getString("MainWindow.FONT_TYPE_LUCIDA_GRANDE"), Font.PLAIN, 16)); //$NON-NLS-1$
		txtpnEnglish.setText(Messages.getString("MainWindow.LABEL_ENGLISH")); //$NON-NLS-1$
		splitPane.setRightComponent(txtpnEnglish);
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.isControlDown()){
					if (textField_1.getText().equals(currentCard.getSimp())){
						incrementCard(true);
					} else {
						textField_1.setText(Messages.getString("MainWindow.EMPTY_STRING")); //$NON-NLS-1$
					}
					
				} else if (arg0.isAltDown()){
					incrementCard(false);
				}
			}
		});
		textField_1.setColumns(10);
		panel_3.add(textField_1);
		
		System.out.println(cards.getSize());
		incrementCard(true);
	}
	JLabel label;
	Card currentCard;
	private JTextField textField_1;
	private void incrementCard(boolean pass){
		currentCard = cards.getNext(pass);
		if (currentCard == null){
			JOptionPane.showMessageDialog(frame,
					"Ran out of words, closing.", //$NON-NLS-1$
				    "Finished", //$NON-NLS-1$
				    JOptionPane.ERROR_MESSAGE);
			frame.dispose();
			if (Main.close()){
				System.exit(0);
			}
		} else {
			label.setText(currentCard.getSimp());
			lblPinyin.setText(currentCard.getPinyin());
			txtpnEnglish.setText(currentCard.getEnglish());
			
			textField_1.setText(Messages.getString("MainWindow.EMPTY_STRING")); //$NON-NLS-1$
		}
	}
}
