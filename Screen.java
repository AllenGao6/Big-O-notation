import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image; 
import java.util.ListIterator;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
 
public class Screen extends JPanel implements ActionListener {
    private JTextArea textArea;
	private ArrayList<Student> studentList;
	private ListIterator<Student> itStudent;
	
    private String firstName = "";
	private String lastName = "";
	private int age;
	
	private boolean added;
	private String text;
	
	private JButton sequential, binary, add, remove, Submit;
	private JTextField search, first,last, ageStudent;
	
	private int found;
	private int passes;
 
    public Screen() {
        this.setLayout(null);
        this.setFocusable(true);
         
        //TextArea
        textArea = new JTextArea(200,300); //sets the location and size
        textArea.setText(text);
         
        //JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10,10,200,300);
		this.add(scrollPane);
        
		studentList = new ArrayList<Student>();
		itStudent = studentList.listIterator();
		
		try{
			Scanner scan = new Scanner(new File("names.txt"));
            while (scan.hasNext()){
               firstName = scan.next();
               lastName = scan.next();
               age = (int)(Math.random()*5) + 14;
               itStudent = studentList.listIterator();
               added = false;
               
               while(itStudent.hasNext()){
               		if(lastName.compareTo(itStudent.next().getLast()) <= 0){
               			itStudent.previous();
               			itStudent.add(new Student(firstName, lastName, age));
               			added = true;
               			break;
               		}
               }
               if(added == false){
               		itStudent.add(new Student(firstName, lastName, age));
               }
            }     
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		found = -1;
		passes = 0;
		
		search = new JTextField(50);
        search.setBounds(300, 50, 200, 30);
        this.add(search);
		
		first = new JTextField(50);
        first.setBounds(510, 50, 200, 30);
        first.setText(" Input First Name");
        this.add(first);
		first.setVisible(false);
		
		last = new JTextField(50);
        last.setBounds(510, 100, 200, 30);
        last.setText("Input Last Name");
        this.add(last);
		last.setVisible(false);
		
		ageStudent = new JTextField(50);
        ageStudent.setBounds(510, 150, 200, 30);
        ageStudent.setText("Input Age");
        this.add(ageStudent);
        ageStudent.setVisible(false);
		
		sequential = new JButton("Sequential Search");
        sequential.setBounds(300, 200, 150, 40);
        sequential.addActionListener(this);
        this.add(sequential);
		
		binary = new JButton("Binary Search");
        binary.setBounds(300, 300, 150, 40);
        binary.addActionListener(this);
        this.add(binary);
		
		add = new JButton("Add");
        add.setBounds(510, 200, 150, 40);
        add.addActionListener(this);
        this.add(add);
        
        Submit = new JButton("Submit");
        Submit.setBounds(510, 200, 150, 40);
        Submit.addActionListener(this);
        this.add(Submit);
		Submit.setVisible(false);
		remove = new JButton("Remove");
        remove.setBounds(510, 300, 150, 40);
        remove.addActionListener(this);
        this.add(remove);
	}
 
    public Dimension getPreferredSize() {
        //Sets the size of the panel
        return new Dimension(800,600);
    }
 
    public void paintComponent(Graphics g) {
        //draw background
        g.setColor(Color.white);
        g.fillRect(0,0,800,600);
        

		Font font = new Font("Arial", Font.PLAIN, 20);
        g.setFont(font);
        g.setColor(Color.black); 
        g.drawString("Search by last name", 300, 25);
    	text = "";
    	itStudent = studentList.listIterator();
		
		int index = 0;
        while(itStudent.hasNext())
        {
			index++;
        	text += index + ") " + itStudent.next().toString() + "\n";
        }
		
		if (found > 0) {
		    g.setColor(Color.red);
            g.drawString("found :" + found + "th", 100, 350);
            g.drawString("Passes: " + passes + " times", 100, 400);
        } else {
            g.drawString("Not Found ", 100, 350);
        }
        
        textArea.setText(text);
        Font fontLarge = new Font("Courier New",Font.BOLD,40);
        g.setFont(fontLarge);
        g.drawString("Binary Search ", 300, 400);
    }
	
	public void actionPerformed(ActionEvent e) 
	{
		passes = 0;
		if (e.getSource() == binary){
            found = binary(search.getText(), 0, studentList.size()-1) + 1; 
		} if(e.getSource() == sequential){
			found = sequential(search.getText()) + 1; 
		} if(e.getSource() == add){
		    ageStudent.setVisible(true);
		    first.setVisible(true);
		    last.setVisible(true);
		    add.setVisible(false);
		    Submit.setVisible(true);
			//studentList.add(new Student(first.getText(),last.getText(),Integer.parseInt(ageStudent.getText())));
		} 
		if (e.getSource() == remove){
			remove(search.getText());
		}
		if(e.getSource() == Submit){
		    studentList.add(new Student(first.getText(),last.getText(),Integer.parseInt(ageStudent.getText())));
		    ageStudent.setVisible(false);
		    first.setVisible(false);
		    last.setVisible(false);
		    add.setVisible(true);
		    Submit.setVisible(false);
		}
		
		repaint();
	}
	
	public int sequential(String find) {
		for (int i = 0; i<studentList.size(); i++) {
			passes ++;
			if (studentList.get(i).getLast().equals(find)) {
				return i;
			}
		}
		return -1;
	}
	
	public int binary(String find, int start, int end) {
        if (start<=end) {
			passes++;
            int middle = (end + start) / 2;
            if (studentList.get(middle).getLast().compareTo(find) == 0) {
                return middle;
            }
            else if (studentList.get(middle).getLast().compareTo(find) > 0) {
                end = middle -1;
                return binary(find, start, end);
            }
            else if (studentList.get(middle).getLast().compareTo(find) < 0) {
                start = middle + 1;
                return binary(find, start, end);
            }
        }
        return -1;
    }
	
	public void add(Student a) {
        ListIterator<Student> it = studentList.listIterator();
        boolean added = false;
        while (it.hasNext()) {
            if (it.next().getLast().compareTo(a.getLast()) > 0) {
                it.previous();
                it.add(a);
                added = true;
                break;
            }
        }
        if (!added) {
            it.add(a);
        }
    }
	
	public void remove(String last) {
        ListIterator<Student> it = studentList.listIterator();
        while (it.hasNext()) {
            if (it.next().getLast().equals(last)) {
                it.remove();
            }
        }
    }
	
}