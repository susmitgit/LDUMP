package com.qnap.ldap.dump;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LdapDump {

	public LdapDump() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		String dbuser="helpdesk";
        String dbpassword="zaq1xsw2";		
	    String url ="jdbc:sqlserver://10.8.2.37;databaseName=QEIP";
		Connection conn;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url,dbuser,dbpassword);
			
			System.out.println("Connected");
			
			Statement sta,st; 
			sta = conn.createStatement();
			st = conn.createStatement();
			
			String Sql = "select * from V_HELPDESK_EMPLOYEE";
			
			ResultSet rs = sta.executeQuery(Sql);
			
			ResultSet rsCount = st.executeQuery("select count(*) from V_HELPDESK_EMPLOYEE");
			
			//rsCount = st.executeQuery("SELECT COUNT(*) FROM survey");
			    // get the number of rows from the result set
			rsCount.next();
			int rowCount = rsCount.getInt(1);
			
			
			String sql="";//"TRUNCATE TABLE `import_excel`;";
			sql=sql+"INSERT INTO import_excel(`EMAIL`,`EMPID`,`EMPCNAME`,`EMPENGNAME`,`DERNUMBER`,`SUPERVSR`,`DEPMANGR`,`TOPMANGR`) values";
			
			int rowcount = 0;
			
			//return size;
			while(rs.next()){
				sql=sql+""
						+ "('"+ rs.getString(1)+"',"+ "'"+ rs.getString(2)+"',"+ "'"+ rs.getString(3)+"',"+ "'"+ rs.getString(4)+"',"+ "'"+ rs.getString(5)+"',"
						+ "'"+ rs.getString(6)+"',"+ "'"+ rs.getString(7)+"',"+ "'"+  rs.getString(8)+"'"
						+ ")";			
				//System.out.println(rs.getString(1)+" -- \n");
			rowcount++;
				if(rowcount!=rowCount){
					sql=sql+",";
				}else{
					sql=sql+";";
				}
			}
			
			rs.close();
			st.close();
			sta.close();
			conn.close();
			//System.out.println("Init - >  "+sql);
		//	LdapDumpMysql ldm=new LdapDumpMysql();
			
			
			if(new LdapDumpMysql().inserRecord(sql)){
					System.out.println("Data dumped in MySQL");
				}else{
					System.out.println("Data couldn't dumped in MySQL");
				}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	
		
		
	}

}
