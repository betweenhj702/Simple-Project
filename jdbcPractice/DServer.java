package dbgui;

import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


class DServer
{
	Connection con;
	String url= "jdbc:oracle:thin:@localhost:1521:JAVA";
	String user= "scott";
	String pwd= "tiger";
	Statement stmt;
	PreparedStatement pstmtDEPTNO,pstmtDNAME,pstmtLOC;
	PreparedStatement pstmtInsert, pstmtUpdate, pstmtDelete;
	
	DTable dt;
	DServer(DTable dt){
		this.dt = dt;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,user,pwd);
			//System.out.println("DB�� ���� ����");
			stmt = con.createStatement();
			
			String sqlSDEPTNO = "select * from DEPT where DEPTNO like ? order by DEPTNO";
			String sqlSDNAME = "select * from DEPT where DNAME like ? order by DEPTNO";
			String sqlSLOC = "select * from DEPT where LOC like ? order by DEPTNO";
			pstmtDEPTNO = con.prepareStatement(sqlSDEPTNO);
			pstmtDNAME = con.prepareStatement(sqlSDNAME);
			pstmtLOC = con.prepareStatement(sqlSLOC);
			
			String sqlInsert = "insert into DEPT values(?,?,?)";
			String sqlUpdate = "update DEPT set DNAME= ?, LOC= ? where DEPTNO=?";
			String sqlDelete = "delete from DEPT where DEPTNO = ?";
			pstmtInsert = con.prepareStatement(sqlInsert);
			pstmtUpdate = con.prepareStatement(sqlUpdate);
			pstmtDelete = con.prepareStatement(sqlDelete);

		}catch(ClassNotFoundException cnfe){
			System.out.println("1");
		}catch(SQLException se){
			System.out.println("2");
		}
		selectInit();
	}

	int numberOfCol;
	void selectInit(){	//���α׷� ���� �� ���̺� ����� ������ 
		String sql = "select * from DEPT order by DEPTNO";
		ResultSet rs = null; 
		ResultSetMetaData rsmd= null;
		try{
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			numberOfCol = rsmd.getColumnCount();
			for(int i=1;i<=numberOfCol;i++){
				dt.column.add(rsmd.getColumnName(i));
			}
			while(rs.next()){
				Vector<String> v = new Vector<String>();
				for(int i=1;i<=numberOfCol;i++){
					v.add(rs.getString(i));
				}
				dt.data.add(v);
			}
			dt.model = new DefaultTableModel(dt.data, dt.column);
			
		}catch(SQLException se){
		}finally{
			try{
				if(rs!=null) rs.close();
			}catch(SQLException se){}
		}
	}
	
	void selectSDEPTNO(String search){	// Ű���忡 ���� ������(�μ���ȣ�� �˻�)
		ResultSet rs = null;
		dt.data.clear();
		try{	
			pstmtDEPTNO.setString(1, "%"+search+"%");
			rs = pstmtDEPTNO.executeQuery();
			while(rs.next()){
				Vector<String> v = new Vector<String>();
				for(int i=1;i<=numberOfCol;i++){
					v.add(rs.getString(i));
				}
				//for(String str: v) System.out.print(str);
				dt.data.add(v);
			}
			dt.model = new DefaultTableModel(dt.data, dt.column);
			dt.model.fireTableDataChanged();
		}catch(SQLException se){
		}finally{
			try{
				if(rs!=null) rs.close();
			}catch(SQLException se){}
		}
	}
	void selectSDNAME(String search){	// Ű���忡 ���� ������(�μ��̸����� �˻�)
		ResultSet rs = null;
		dt.data.clear();
		try{	
			pstmtDNAME.setString(1, "%"+search+"%");
			rs = pstmtDNAME.executeQuery();
			while(rs.next()){
				Vector<String> v = new Vector<String>();
				for(int i=1;i<=numberOfCol;i++){
					v.add(rs.getString(i));
				}
				//for(String str: v) System.out.print(str);
				dt.data.add(v);
			}
			dt.model = new DefaultTableModel(dt.data, dt.column);
			dt.model.fireTableDataChanged();
		}catch(SQLException se){
		}finally{
			try{
				if(rs!=null) rs.close();
			}catch(SQLException se){}
		}
	}
	void selectSLOC(String search){		// Ű���忡 ���� ������(�������� �˻�)
		ResultSet rs = null;
		dt.data.clear();
		try{	
			pstmtLOC.setString(1, "%"+search+"%");
			rs = pstmtLOC.executeQuery();
			while(rs.next()){
				Vector<String> v = new Vector<String>();
				for(int i=1;i<=numberOfCol;i++){
					v.add(rs.getString(i));
				}
				//for(String str: v) System.out.print(str);
				dt.data.add(v);
			}
			dt.model = new DefaultTableModel(dt.data, dt.column);
			dt.model.fireTableDataChanged();
		}catch(SQLException se){
		}finally{
			try{
				if(rs!=null) rs.close();
			}catch(SQLException se){}
		}
	}
	
	void selectAll() {		//��� �����͸� �̴� �޼ҵ�
		String sql = "select * from DEPT order by DEPTNO";
		ResultSet rs = null; 
		dt.data.clear();
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Vector<String> v = new Vector<String>();
				for(int i=1;i<=numberOfCol;i++){
					v.add(rs.getString(i));
				}
				dt.data.add(v);
			}
			dt.model = new DefaultTableModel(dt.data, dt.column);
			dt.model.fireTableDataChanged();
		}catch(SQLException se){
		}finally{
			try{
				if(rs!=null) rs.close();
			}catch(SQLException se){}
		}
	}
	
	
	void insertD(int deptno, String dname, String loc) throws SQLException{	//�����͸� ���� �Է��ϴ� �޼ҵ�
		pstmtInsert.setInt(1, deptno);
		pstmtInsert.setString(2, dname);
		pstmtInsert.setString(3, loc);
		pstmtInsert.executeUpdate();
		selectInsert(deptno);
	}
	void selectInsert(int deptno){		//���� �Է��� �����͸� ���̺� ����ϱ� ���� �޼ҵ�
		String sql = "select * from DEPT where DEPTNO = "+deptno;
		ResultSet rs = null; 
		try{
			rs = stmt.executeQuery(sql);
			rs.next();
			Vector<String> v = new Vector<String>();
			for(int i=1;i<=numberOfCol;i++){
				v.add(rs.getString(i));
			}
			dt.model.insertRow(dt.data.size(),v);
			dt.model.fireTableDataChanged();
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
			try{
				if(rs!=null) rs.close();
			}catch(SQLException se){}
		}
	}
	
	//����� �κ��� �����͸� ���̺�𵨿��� �ٲٴ� ����� ã�ƺ���
	//DB���� �ٽ� �����ͼ� ���̺�𵨿� �ִ°� ����
	void updateD(String dname, String loc, int deptno) throws SQLException{	//������ ���� �޼ҵ�
		pstmtUpdate.setString(1,dname);
		pstmtUpdate.setString(2,loc);
		pstmtUpdate.setInt(3,deptno);
		pstmtUpdate.executeUpdate();
		selectAll();
	}
	void deleteD(int deptno) throws SQLException{	//������ ���� �޼ҵ�
		pstmtDelete.setInt(1, deptno);
		pstmtDelete.executeUpdate();
		selectAll();	
	}
	
}
