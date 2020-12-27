package dbgui;

import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

class DTHandler implements ActionListener, KeyListener{
	DTable dt;
	DTHandler(DTable dt){
		this.dt = dt;
	}
	void re() {
		dt.t2.setText("");dt.t3.setText("");
		dt.jt.repaint();dt.jt.revalidate();
		dt.p2.repaint();dt.p2.revalidate();
	}
	//JTable 마우스 이벤트 추가 해줄 부분

	String col="DEPTNO";
	public void actionPerformed(ActionEvent e){	
		Object obj = e.getSource();
		if(obj==dt.combo){
			 col = (String) dt.combo.getSelectedItem();
		}
		if(obj==dt.bInsert){
			try{
				int deptno = Integer.parseInt(dt.t1.getText());
				String dname = dt.t2.getText();
				String loc = dt.t3.getText();
				dt.ds.insertD(deptno, dname, loc);
				re();
			}catch(NumberFormatException ne){
				System.out.println("숫자입력안돼");
			}catch(SQLException se){
				System.out.println("db오류");
			}
		}
		if(obj==dt.bUpdate){
			try{
				int deptno = Integer.parseInt(dt.t1.getText());
				String dname = dt.t2.getText();
				String loc = dt.t3.getText();
				dt.ds.updateD(dname, loc, deptno);
				re();
			}catch(NumberFormatException ne){
				System.out.println("숫자입력안돼");
			}catch(SQLException se){
				System.out.println("db오류");
			}
		}
		if(obj==dt.bDelete){
			try{
				int deptno = Integer.parseInt(dt.t1.getText());
				dt.ds.deleteD(deptno);
				re();
			}catch(NumberFormatException ne){
				System.out.println("숫자입력안돼");
			}catch(SQLException se){
				System.out.println("db오류");
			}
		}
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){
		int keyCode = e.getKeyCode();
		String search = dt.textSearch.getText();
		if(col.equals("DEPTNO")){
			dt.ds.selectSDEPTNO(search);
			re();
		}else if(col.equals("DNAME")){
			dt.ds.selectSDNAME(search);
			re();
		}else if(col.equals("LOC")){
			dt.ds.selectSLOC(search);
			re();
		}
	}
	
	public void keyPressed(KeyEvent e){}
}
