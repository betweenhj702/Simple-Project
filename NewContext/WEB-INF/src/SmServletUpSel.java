package sdo.sv.sm;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class SmServletUpSel extends HttpServlet 
{
	Connection con;
	PreparedStatement pstmt1;

	public void init(){ 
	    String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
		String sql1 = "select * from BOARD where SEQ = ?";
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, "servlet", "java");
			pstmt1 = con.prepareStatement(sql1);
		}catch(ClassNotFoundException cnfe){
		}catch(SQLException se){
		}
	}
	public void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		
		
		int seq = -1;
		String seqStr = req.getParameter("seq");
        if(seqStr != null){
			seqStr = seqStr.trim();
			if(seqStr.length() != 0){
				try{
					seq = Integer.parseInt(seqStr);
				}catch(NumberFormatException ne){
					res.sendRedirect("list.do");
				}
			}else{
				res.sendRedirect("list.do");
			}
		}else{
			res.sendRedirect("list.do");
		}

		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();
		ResultSet rs = null;
		try{
			pstmt1.setInt(1, seq);
			rs = pstmt1.executeQuery();
			rs.next();
			String writer = rs.getString(2);
			String email = rs.getString(3);
			String subject = rs.getString(4);
			String content = rs.getString(5);
			
			//
pw.println("<head>");
pw.println("<meta charset=utf-8>");
pw.println("<title>Simple Board with Servlet</title>");
pw.println("<style>table,th,td{border:1px solid black;border-collapse:collapse}th,td{padding:5px}a{text-decoration:none}</style>");
pw.println("<script src=\"../js/check.js\"></script>");
pw.println("<script language=javascript>function check(){for(var c=0;c<document.input.elements.length;c++){var e=document.input.elements[c];if(e.value==\"\"){alert(\"모든 값을 입력 하셔야 합니다. \");return false}}var a=input.writer.value;a=trim(a);pass=checkByteLen(a,15);if(!pass){alert(\"이름 15바이트이하\");input.writer.focus();return false}var d=input.email.value;d=trim(d);pass=checkByteLen(d,20);if(!pass){alert(\"이메일 20바이트이하\");input.email.focus();return false}var b=input.subject.value;b=trim(b);pass=checkByteLen(b,20);if(!pass){alert(\"제목 20바이트이하\");input.subject.focus();return false}var f=input.content.value;f=trim(f);pass=checkByteLen(f,400);if(!pass){alert(\"글 400바이트이하\");input.writer.focus();return false}document.input.submit()};</script>");
pw.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
pw.println("<script>");
pw.println("function f(){");
pw.println("input.email.value = \"\";");
pw.println("input.subject.value = \"\";");
pw.println("$(\"#ta\").text(\"\");");
pw.println("input.email.focus();");
pw.println("}");
pw.println("</script>");
pw.println("</head>");
pw.println("<body onload=input.writer.focus()>");
pw.println("<font color=gray size=4 face=휴먼편지체>");
pw.println("<center>");
pw.println("<hr width=600 size=2 color=gray noshade>");
pw.println("<h3> Simple Board with Servlet </h3>");
pw.println("<font color=gray size=3 face=휴먼편지체>");
pw.println("<a href=list.do>리스트</a>");		//링크
pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
pw.println("<a href=../>인덱스</a>");		//링크
pw.println("</font>");
pw.println("<hr width=600 size=2 color=gray noshade>");
pw.println("</center>");
pw.println("<form name=input method=post action= update.do>");		//링크
pw.println("<input type=\"hidden\" name=\"seq\" value=\""+seq+"\">");
pw.println("<table border=0 width=600 align=center cellpadding=3 cellspacing=1 bordercolor=gray>");
pw.println("<tr>");
pw.println("<td width=30% align=center>WRITER</td>");
pw.println("<td><input type=text name=writer value=\""+writer+"\" size=60></td>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td align=center>EMAIL</td>");
pw.println("<td><input type=text name=email value=\""+email+"\" size=60></td>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td align=center>SUBJECT</td>");
pw.println("<td><input type=text name=subject value=\""+subject+"\" size=60></td>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td align=center>CONTENT</td>");
pw.println("<td><textarea id=ta name=content style=width:98% rows=8 cols=60>"+content+"</textarea></td>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td colspan=2 align=center>");
pw.println("<input type=button value=수정 onclick=check()>");
pw.println("<input type=button value=\"다시입력\" onclick=f()>");
pw.println("</td>");
pw.println("</tr>");
pw.println("</table>");
pw.println("<hr width=600 size=2 color=gray noshade>");
pw.println("</form>");
pw.println("</font>");
pw.println("</body>");
	//

		}catch(SQLException se){
		}
	}
	public void destroy(){ 
        try{
			if(pstmt1 != null) pstmt1.close();
			if(con != null) con.close();
		}catch(SQLException se){
		}
	}
}