package sdo.sv.sm;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class SmServletList extends HttpServlet 
{
	Connection con;
	Statement stmt;

	public void init(){
		System.out.println("init()수행");
	    String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, "servlet", "java");
			stmt = con.createStatement();
		}catch(ClassNotFoundException cnfe){
		}catch(SQLException se){
		}
	}
	public void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException { //요청될 때마다 
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();
		//
		pw.println("<head>");
		pw.println("<meta charset='utf-8'>");
		pw.println("<title>Simple Board with Servlet</title>");
		pw.println("<style>table,th,td{border:1px solid black;border-collapse:collapse}th,td{padding:5px}a{text-decoration:none}</style>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<center>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("<h2>Simple Board with Servlet</h2>");
		pw.println("&nbsp;&nbsp;&nbsp;");
		pw.println("<a href='write.html'>글쓰기</a>");	//링크 서블릿
		pw.println("&nbsp;&nbsp;&nbsp;");
		pw.println("<a href='../index.html'>인덱스</a>");
		pw.println("<hr width='600' size='2' noshade>");
		pw.println("</center>");
		pw.println("<table border='1' width='600' align='center' cellpadding='2'>");
		pw.println("<tr>");
		pw.println("<th align='center' width='10%'>글번호</th>");
		pw.println("<th align='center' width='15%'>작성자</th>");
		pw.println("<th align='center' width='30%'>이메일</th>");
		pw.println("<th align='center' width='30%'>글제목</th>");
		pw.println("<th align='center' width='15%'>날짜</th>");
		pw.println("</tr>");

        ResultSet rs = null;
		String sql = "select * from BOARD order by SEQ desc";
		try{
			rs = stmt.executeQuery(sql);
			boolean flag = false;
			while(rs.next()){
				flag = true;
				int seq = rs.getInt(1);
				String writer = rs.getString(2);
				String email = rs.getString(3);
				String subject = rs.getString(4);
				Date rdate = rs.getDate(6);

				pw.println("<tr>");
				pw.println("<td align='center'>"+seq+"</td>");
				pw.println("<td align='center'>"+writer+"</td>");
				pw.println("<td align='center'>"+email+"</td>");
				pw.println("<td align='center'>");
				pw.println("<a href='sel.do?seq="+seq+"'>"+subject+"</a>");	//링크서블릿
				pw.println("</td>");
				pw.println("<td align='center'>"+rdate+"</td>");
				pw.println("</tr>");
				pw.println("</body>");
			}

			if(!flag){
				pw.println("<tr>");
					pw.println("<td colspan='5' align='center'>데이터 없음</a>");
				pw.println("</tr>");
			}
		}catch(SQLException se){
		}finally{
			try{
				if(rs != null) rs.close();
			}catch(SQLException se){}
		}
		
	}
	public void destroy(){ 
        try{
			if(stmt != null) stmt.close();
			if(con != null) con.close();
		}catch(SQLException se){
		}
	}
}