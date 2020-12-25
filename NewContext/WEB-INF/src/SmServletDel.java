package sdo.sv.sm;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class SmServletDel extends HttpServlet 
{
	Connection con;
	PreparedStatement pstmt;

	public void init(){ //서블릿 로딩 ( by 첫번째 요청) 
		
	    String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
		String sql = "delete from BOARD where SEQ=?";
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, "servlet", "java");
			pstmt = con.prepareStatement(sql);
		}catch(ClassNotFoundException cnfe){
		}catch(SQLException se){
		}
	}
	public void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException { //요청될 때마다 
		
		int seq = -1;
		String seqStr = req.getParameter("seq");
		
        if(seqStr != null){
			seqStr = seqStr.trim();
			if(seqStr.length() != 0){
				try{
					seq = Integer.parseInt(seqStr);
				}catch(NumberFormatException ne){
					res.sendRedirect("sel.do");
				}
			}else{
				res.sendRedirect("sel.do");
			}
		}else{
			res.sendRedirect("sel.do");
		}

		try{
			pstmt.setInt(1, seq);
			pstmt.executeUpdate();
			res.sendRedirect("list.do");
		}catch(SQLException se){
		}
	}

	public void destroy(){ 
        try{
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		}catch(SQLException se){
		}
	}
}

