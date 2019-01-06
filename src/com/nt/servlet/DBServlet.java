package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.pept.transport.Connection;

public class DBServlet extends HttpServlet {
	java.sql.Connection con;
	PreparedStatement ps;
	public void init(){
		try{
			Class.forName("oracle.JDBC.driver.OracleDriver");
			con=DriverManager.getConnection("JDBC:oracle:thin:@localhost:1521:xe","system","manager");
			ps=con.prepareStatement("select empno,ename,job,sal from emp where empno=?");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
	try{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		int no=Integer.parseInt(req.getParameter("teno"));
		ps.setInt(1, no);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			pw.println("<br>Employee no"+rs.getInt(1));
			pw.println("<br>Employee name"+rs.getString(2));
			pw.println("<br>Employee Desg:"+rs.getString(3));
			pw.println("<br>Employee Salary:"+rs.getFloat(4));
		}
		else{
			pw.println("<br>No Employee Found");
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
}
public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
	doGet(req,res);
}
public void destroy(){
	try{
		if(ps!=null)
			ps.close();
	}
	catch(Exception e){
		e.printStackTrace();
	}
	try{
		if(con!=null)
			con.close();
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
}
