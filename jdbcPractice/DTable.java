package dbgui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.table.*;

class DTable extends JFrame{
	Container cp;
	JPanel p1,p2,p3;
	//p2
	DefaultTableModel model;
	Vector<String> column = new Vector<String>();;
	Vector<Vector<String>> data = new Vector<Vector<String>>();;
	JTable jt;
	JScrollPane scroll;
	//p1
	JComboBox<String> combo;
	JTextField textSearch;
	//p3
	JButton bInsert, bUpdate, bDelete;
	JTextField t1,t2,t3;
	
	DServer ds;
	DTHandler dth;
	
	DTable(){
		createUI();
		setUI();
		loadData();
		setEvent();
	}

	void createUI(){
		cp = getContentPane();
		
		p2 = new JPanel();
		cp.add(p2);

		p1 = new JPanel();
		textSearch = new JTextField(20);
		p1.setLayout(new GridLayout(1,2));
		
		
		p3 = new JPanel();
		t1 = new JTextField();
		t2 = new JTextField();
		t3 = new JTextField();
		bInsert = new JButton("추가");
		bUpdate = new JButton("수정");
		bDelete = new JButton("삭제");
		p3.setLayout(new GridLayout(2,3));
		p3.add(t1); p3.add(t2); p3.add(t3);
		p3.add(bInsert); p3.add(bUpdate); p3.add(bDelete);
	
		cp.add(p1, BorderLayout.NORTH);
		cp.add(p3, BorderLayout.SOUTH);
	}
	void setUI() {
		setTitle("JDBC 실습"); 
		setSize(500,500);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	void loadData(){
		ds = new DServer(this);//db로딩
		
		combo = new JComboBox<String>(column);
		p1.add(combo);p1.add(textSearch);
		
		
		p1.revalidate();
		jt = new JTable(model);
		scroll = new JScrollPane(jt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p2.add(scroll);
		p2.revalidate();
	}
	
	void setEvent(){
		dth = new DTHandler(this);
		combo.addActionListener(dth);
		textSearch.addKeyListener(dth);
		bInsert.addActionListener(dth);
		bUpdate.addActionListener(dth);
		bDelete.addActionListener(dth);
	}
	
	
	public static void main(String[] args) {
		DTable dt = new DTable();
	}
}

