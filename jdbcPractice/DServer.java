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
			//System.out.println("DB에 접속 성공");
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
	void selectInit(){	//프로그램 켜질 때 테이블에 출력할 데이터 
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
	
	void selectSDEPTNO(String search){	// 키워드에 따른 데이터(부서번호로 검색)
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
	void selectSDNAME(String search){	// 키워드에 따른 데이터(부서이름으로 검색)
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
	void selectSLOC(String search){		// 키워드에 따른 데이터(지역으로 검색)
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
	
	void selectAll() {		//모든 데이터를 뽑는 메소드
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
	
	
	void insertD(int deptno, String dname, String loc) throws SQLException{	//데이터를 새로 입력하는 메소드
		pstmtInsert.setInt(1, deptno);
		pstmtInsert.setString(2, dname);
		pstmtInsert.setString(3, loc);
		pstmtInsert.executeUpdate();
		selectInsert(deptno);
	}
	void selectInsert(int deptno){		//새로 입력한 데이터를 테이블에 출력하기 위한 메소드
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
	
	//변경된 부분의 데이터만 테이블모델에서 바꾸는 방법을 찾아보자
	//DB에서 다시 가져와서 테이블모델에 넣는건 낭비
	void updateD(String dname, String loc, int deptno) throws SQLException{	//데이터 변경 메소드
		pstmtUpdate.setString(1,dname);
		pstmtUpdate.setString(2,loc);
		pstmtUpdate.setInt(3,deptno);
		pstmtUpdate.executeUpdate();
		selectAll();
	}
	void deleteD(int deptno) throws SQLException{	//데이터 삭제 메소드
		pstmtDelete.setInt(1, deptno);
		pstmtDelete.executeUpdate();
		selectAll();	
	}
	
}
