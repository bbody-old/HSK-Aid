package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import data.Cards;
import data.CardsSaveFile;
import data.Excel;
import data.StaticFunctions;


public class ExcelLoader extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JComboBox rowComboBox;
	private JComboBox chineseComboBox;
	private JComboBox pinyinComboBox;
	private JComboBox englishComboBox;
	private DefaultTableModel model;
	Excel excel = null;
	public Cards cards;
	/**
	 * Create the dialog.
	 */
	public ExcelLoader(final String filename, final boolean close) {
		setBounds(StaticFunctions.bounds);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblRow = new JLabel("Title row");
			GridBagConstraints gbc_lblRow = new GridBagConstraints();
			gbc_lblRow.insets = new Insets(0, 0, 5, 5);
			gbc_lblRow.gridx = 0;
			gbc_lblRow.gridy = 0;
			contentPanel.add(lblRow, gbc_lblRow);
		}
		{
			JLabel lblChinese = new JLabel("Chinese");
			GridBagConstraints gbc_lblChinese = new GridBagConstraints();
			gbc_lblChinese.insets = new Insets(0, 0, 5, 5);
			gbc_lblChinese.gridx = 2;
			gbc_lblChinese.gridy = 0;
			contentPanel.add(lblChinese, gbc_lblChinese);
		}
		{
			JLabel lblPinyin = new JLabel("Pinyin");
			GridBagConstraints gbc_lblPinyin = new GridBagConstraints();
			gbc_lblPinyin.insets = new Insets(0, 0, 5, 5);
			gbc_lblPinyin.gridx = 4;
			gbc_lblPinyin.gridy = 0;
			contentPanel.add(lblPinyin, gbc_lblPinyin);
		}
		{
			JLabel lblEnglish = new JLabel("English");
			GridBagConstraints gbc_lblEnglish = new GridBagConstraints();
			gbc_lblEnglish.insets = new Insets(0, 0, 5, 0);
			gbc_lblEnglish.gridx = 6;
			gbc_lblEnglish.gridy = 0;
			contentPanel.add(lblEnglish, gbc_lblEnglish);
		}
		{
			rowComboBox = new JComboBox();
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 0;
			gbc_comboBox.gridy = 1;
			contentPanel.add(rowComboBox, gbc_comboBox);
		}
		{
			chineseComboBox = new JComboBox();
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 2;
			gbc_comboBox.gridy = 1;
			contentPanel.add(chineseComboBox, gbc_comboBox);
		}
		{
			pinyinComboBox = new JComboBox();
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 4;
			gbc_comboBox.gridy = 1;
			contentPanel.add(pinyinComboBox, gbc_comboBox);
		}
		{
			{
				englishComboBox = new JComboBox();
				GridBagConstraints gbc_comboBox = new GridBagConstraints();
				gbc_comboBox.insets = new Insets(0, 0, 5, 0);
				gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox.gridx = 6;
				gbc_comboBox.gridy = 1;
				contentPanel.add(englishComboBox, gbc_comboBox);
			}
			
			model = new DefaultTableModel();
			//this.close();
			
			try {
				excel = new Excel(filename);
				//table = new JTable(excel.excel, new Vector() {});
				//new JTable();
				//table
				//table = new JTable(excel.getValues());
				String[] columnNames = new String[excel.columnSize()];
				for (int i = 0; i < excel.columnSize(); i++){
					columnNames[i] = new Integer(i + 1).toString();
					String s = columnNames[i];
					englishComboBox.addItem(s);
					chineseComboBox.addItem(columnNames[i]);
					pinyinComboBox.addItem(columnNames[i]);
				}
				
				for (int i = 0; i < excel.rowSize(); i++){
					rowComboBox.addItem(new Integer(i + 1).toString());
				}
				
				model.addRow(columnNames);
				String [][] data = new String[excel.rowSize()][];
				for (int j = 0; j < excel.rowSize(); j++){
					model.addRow(excel.getValues()[j]);
					data[j] = excel.getValues()[j];
				}
				//table = new JTable(model);
				table = new JTable(data,columnNames);
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			GridBagConstraints gbc_table = new GridBagConstraints();
			gbc_table.gridheight = 2;
			gbc_table.gridwidth = 7;
			gbc_table.fill = GridBagConstraints.BOTH;
			gbc_table.gridx = 0;
			gbc_table.gridy = 2;
			contentPanel.add(new JScrollPane(table), gbc_table);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						int titleRow = rowComboBox.getSelectedIndex();
						int englishColumn = englishComboBox.getSelectedIndex();
						int chineseColumn = chineseComboBox.getSelectedIndex();
						int pinyinColumn = pinyinComboBox.getSelectedIndex();
						
						CardsSaveFile csf = new CardsSaveFile();
						csf.setChinese(chineseColumn);
						csf.setEnglish(englishColumn);
						csf.setPinyin(pinyinColumn);
						csf.setTitle(titleRow);
						try {
							csf.save(filename);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						cards = excel.getCards(titleRow, englishColumn, chineseColumn, pinyinColumn);
						//MainWindow mw = new MainWindow(cards, filename);
						
						dispose();
					}
					
				});
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.setActionCommand("Close");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Close window
						dispose();
						if (close && Main.close()){
							System.exit(0);
						}
					}
					
				});
			}
		}
	}
	
	
	
	
	
	

}
