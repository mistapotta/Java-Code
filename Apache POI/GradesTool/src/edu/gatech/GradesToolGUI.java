package edu.gatech;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;


/**
 *
 */
public class GradesToolGUI extends JFrame implements ActionListener {

	GradesDB database;
	HashSet<Student> students ;
	
	private JComboBox studentSelectionComboBox;
	private JTextArea studentInformationTextArea;
	private JTextField formulaTextField;
	private JButton saveButton;
	
	private JFileChooser fileChooser;
	private JLabel lblNewLabel;
	private JLabel lblAverageGrade;
	private JLabel lblMedianGrade;
	private JLabel lblNewLabel_1;
	private JPanel panel;
	private JTextField averageGradeTextField;
	private JTextField medianGradeTextField;
	private JTextField studentGradeTextField;
	private JPanel panel_1;
	private JTextField status;

	/**
	 * Create the frame.
	 */
	public GradesToolGUI() {
		
		setTitle("CS6300 GRADING TOOL");
		setBounds(100, 100, 500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(5, 5));
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(infoPanel, BorderLayout.CENTER);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		studentInformationTextArea = new JTextArea();
		infoPanel.add(studentInformationTextArea);
		
		panel = new JPanel();
		infoPanel.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel navigationPanel = new JPanel();
		panel.add(navigationPanel, BorderLayout.NORTH);
		GridBagLayout gbl_navigationPanel = new GridBagLayout();
		gbl_navigationPanel.columnWeights = new double[]{1.0, 1.0};
		gbl_navigationPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0};
		navigationPanel.setLayout(gbl_navigationPanel);
		
		studentSelectionComboBox = new JComboBox();
		GridBagConstraints gbc_studentSelectionComboBox = new GridBagConstraints();
		gbc_studentSelectionComboBox.gridwidth = 2;
		gbc_studentSelectionComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_studentSelectionComboBox.gridx = 0;
		gbc_studentSelectionComboBox.gridy = 1;
		navigationPanel.add(studentSelectionComboBox, gbc_studentSelectionComboBox);
		studentSelectionComboBox.addActionListener(this);
		studentSelectionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Select A Student"}));
		
		saveButton = new JButton("Save");
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.gridwidth = 2;
		gbc_saveButton.insets = new Insets(0, 0, 5, 0);
		gbc_saveButton.gridx = 0;
		gbc_saveButton.gridy = 2;
		navigationPanel.add(saveButton, gbc_saveButton);
		saveButton.addActionListener(this);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		navigationPanel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("Formula:");
		panel_1.add(lblNewLabel, BorderLayout.WEST);
		
		formulaTextField = new JTextField();
		panel_1.add(formulaTextField);
		formulaTextField.setColumns(10);
		
		lblAverageGrade = new JLabel("Average Class Grade:");
		GridBagConstraints gbc_lblAverageGrade = new GridBagConstraints();
		gbc_lblAverageGrade.anchor = GridBagConstraints.EAST;
		gbc_lblAverageGrade.insets = new Insets(0, 0, 5, 5);
		gbc_lblAverageGrade.gridx = 0;
		gbc_lblAverageGrade.gridy = 5;
		navigationPanel.add(lblAverageGrade, gbc_lblAverageGrade);
		
		averageGradeTextField = new JTextField();
		averageGradeTextField.setEditable(false);
		GridBagConstraints gbc_averageGradeTextField = new GridBagConstraints();
		gbc_averageGradeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_averageGradeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_averageGradeTextField.gridx = 1;
		gbc_averageGradeTextField.gridy = 5;
		navigationPanel.add(averageGradeTextField, gbc_averageGradeTextField);
		averageGradeTextField.setColumns(10);
		
		lblMedianGrade = new JLabel("Median Class Grade:");
		GridBagConstraints gbc_lblMedianGrade = new GridBagConstraints();
		gbc_lblMedianGrade.anchor = GridBagConstraints.EAST;
		gbc_lblMedianGrade.insets = new Insets(0, 0, 5, 5);
		gbc_lblMedianGrade.gridx = 0;
		gbc_lblMedianGrade.gridy = 6;
		navigationPanel.add(lblMedianGrade, gbc_lblMedianGrade);
		
		medianGradeTextField = new JTextField();
		medianGradeTextField.setEditable(false);
		GridBagConstraints gbc_medianGradeTextField = new GridBagConstraints();
		gbc_medianGradeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_medianGradeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_medianGradeTextField.gridx = 1;
		gbc_medianGradeTextField.gridy = 6;
		navigationPanel.add(medianGradeTextField, gbc_medianGradeTextField);
		medianGradeTextField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Student Grade:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 7;
		navigationPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		studentGradeTextField = new JTextField();
		studentGradeTextField.setEditable(false);
		GridBagConstraints gbc_studentGradeTextField = new GridBagConstraints();
		gbc_studentGradeTextField.insets = new Insets(0, 0, 0, 5);
		gbc_studentGradeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_studentGradeTextField.gridx = 1;
		gbc_studentGradeTextField.gridy = 7;
		navigationPanel.add(studentGradeTextField, gbc_studentGradeTextField);
		studentGradeTextField.setColumns(10);
		
		status = new JTextField();
		status.setEditable(false);
		infoPanel.add(status, BorderLayout.SOUTH);
		status.setColumns(10);

		//Data initialization
		initView();
	}

	private JComboBox getStudentSelectionComboBox() {
		return studentSelectionComboBox;
	}
	public JTextArea getStudentInformationTextArea() {
		return studentInformationTextArea;
	}

	private JFileChooser getFileChooser() {
		if(fileChooser == null) {
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		}
		return fileChooser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == studentSelectionComboBox) {
			if(studentSelectionComboBox.getSelectedIndex() > 0) {
				status.setText("");
				Student student = database.getStudentByName((String)studentSelectionComboBox.getSelectedItem()) ;
				getStudentInformationTextArea().setText(database.getStudentInfo(student.getName()));
				String formula = formulaTextField.getText();
				if(formula != null && !formula.trim().equals("")) {
					database.setFormula(formula);
					averageGradeTextField.setText(getFormattedNumber(database.getAverageGrade()));
					medianGradeTextField.setText(getFormattedNumber(database.getMedianGrade()));
					studentGradeTextField.setText(getFormattedNumber(database.getStudentGrade(student)));
				}
			}
		} else if(e.getSource() == saveButton) {
			if(studentSelectionComboBox.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "Select a student to save!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				FileWriter writer = null;
				try {
					String filePath = ((String)studentSelectionComboBox.getSelectedItem()).replaceAll(" ", "_")+".txt";
					writer = new FileWriter(filePath);
					writer.write(getStudentInformationTextArea().getText());
					status.setText("Student information successfully saved to "+filePath);						
				} catch(IOException ioe) {
					ioe.printStackTrace();
				} finally {
					try {
						if(writer != null)
							writer.close();
					}catch(IOException ioe2) {
						ioe2.printStackTrace();
					}
				}
			}
		}
	}

	/* Non-GUI code here */
	
	public void initView() {
		database = new GradesDB("DB/GradesDatabase6300.xlsx");
		students = database.getStudents();
		((DefaultComboBoxModel)getStudentSelectionComboBox().getModel()).removeAllElements();
		((DefaultComboBoxModel)getStudentSelectionComboBox().getModel()).addElement("Select A Student");
		
		for(Student student: students.toArray(new Student[0])) {
			((DefaultComboBoxModel)getStudentSelectionComboBox().getModel()).addElement(student.getName());
		}
		
		saveButton.setName("save");
		studentSelectionComboBox.setName("combo");
 		studentInformationTextArea.setName("text");
		
		
	}
	
	public int studentCount(){
		return database.getNumStudents();
		
	}
	public HashSet<Student> studentSet(){
		return database.getStudents();
		
	}
	public  Student getStudentByName(String student){
	return database.getStudentByName(student);
	}
	
	public String getStudentInfo(String student){
		return database.getStudentInfo(student);
	}
	
	public String getFormattedNumber(double number) {
		return String.format("%.2f", number);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GradesToolGUI frame = new GradesToolGUI();
					//GradesToolGUIController controller = new GradesToolGUIController(frame, database);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}

