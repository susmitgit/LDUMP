package com.qnap.ldap.dump;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LdapDumpMysql {

	public LdapDumpMysql() {
		// TODO Auto-generated constructor stub

	}
	public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
	
	public boolean inserRecord( String Sql) throws UnsupportedEncodingException {
		
		PrintStream sysout = new PrintStream(System.out, true, "UTF-8");
		String url = "jdbc:mysql://127.0.0.1:3306/qdesk?useUnicode=true&amp;characterEncoding=UTF8";
		String username = "qdesk";
		String password = "qdesk";
		System.out.println("Connecting database...");
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("MySQL Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new IllegalStateException("MySQL Cannot find the driver in the classpath!", e);
		}
		try (Connection con = DriverManager.getConnection(url, username, password)) {
		    System.out.println("MySQL Database connected!");
			Statement st;
			st =  con.createStatement();
			//sysout("SQL -- >  "+Sql);
			
			st.executeUpdate("TRUNCATE TABLE `import_excel`");
			
			System.out.println("Cleaned");
			
			st.executeUpdate(Sql);
			sysout.println("SQL -- >  "+Sql);
			return true;
		} catch (SQLException e) {
		    throw new IllegalStateException("MySQL Cannot connect the database!", e);
		}
	}
	

}
