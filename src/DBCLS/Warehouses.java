package DBCLS;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.lang.*;

public class Warehouses {	

	//==================== Header ====================

	private Connection conn;
	public boolean holdConnection = false;

	public boolean openConnection() {
		try {
			if(host != null || InitConnectionConfiguration()) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
				return true;
				
			}else{
				return false;
				
			}
			
		}catch(Exception e) {
			return false;
			
		}
	}
	
	public boolean isConnectionValid() {
		try {
			return conn.isValid(0);
			
		} catch (SQLException e) {
			return false;
			
		}
	}
	
	public boolean isConnectionClose() {
		try {
			return conn.isClosed();
			
		}catch(SQLException e) {
			return false;
			
		}
	}
	
	public boolean closeConnection() {
		try {
			conn.close();
			return true;
			
		}catch(Exception e) {
			return false;
			
		}		
	}	

	//@Connection info.
	private Properties props = new Properties();
	private String host;
	private String port;
	private String database;
	private String user;
	private String password;
	
	//InternalVariable
	private String _loc_id;
	private String _status;
	private String _remark;
	
	public final String relName = "warehouses";
	public final String columns = "loc_id, status, remark";
	public final String columnsArr[] = {"loc_id", "status", "remark"};

	//Initialize
	private boolean InitConnectionConfiguration() {
		
		String dbconf = "dbconf.conf";
		try (InputStream inputStream = new FileInputStream(dbconf)) {
			 
			// Loading the properties.
			props.load(inputStream);
 
			// Getting properties 
			host = props.getProperty("host");
			port = props.getProperty("port");
			database = props.getProperty("database");
			user = props.getProperty("user");
			password = props.getProperty("password");
			/*
			System.out.println("Host = " + host);
			System.out.println("Port = " + port);
			System.out.println("Database = " + database);
			System.out.println("User = " + user);
			System.out.println("Password = " + password);
			*/
			return true;
			
		} catch (IOException ex) {
			System.out.println("Problem occurs when reading file !");
			ex.printStackTrace();
			return false;
			
		}
	}

	//Constructor
	public Warehouses() {
		if(host == null) InitConnectionConfiguration();
	}

	public Warehouses(String loc_id) {

		if(host != null || InitConnectionConfiguration()) {
			try{ 
				if(conn == null || conn.isClosed()){
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
				}

				Statement stmt = conn.createStatement();
				String SQL = "SELECT * FROM "+relName+" WHERE loc_id = '"+loc_id+"'";
				ResultSet rs = stmt.executeQuery(SQL);
				while(rs.next()) {
				this._loc_id = rs.getString("loc_id");
				this._status = rs.getString("status");
				this._remark = rs.getString("remark");
				}
			
			if(!holdConnection) conn.close();
			
			}catch(Exception e){ 
				System.out.println(e);
			
			}					
		}
	}

	//ClassInfo DataType
	public class WarehousesInfo {
		String loc_id;
		String status;
		String remark;
	}

	//==================== Properties ====================
	public String loc_id() {
		return this._loc_id;
	}

	public Integer loc_id(String value) {

		int rs = 0;
		try{
			if(conn == null || conn.isClosed()){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
			}

			String SQL = "UPDATE "+relName+" SET loc_id = '"+value+"' WHERE loc_id = '"+this._loc_id+"'";
			Statement stm = conn.createStatement();
			rs = stm.executeUpdate(SQL);

			if(!holdConnection) conn.close();
			
		}catch(Exception e){ 
			System.out.println(e);

		}
		
		return rs;
		
	}

	public String status() {
		return this._status;
	}

	public Integer status(String value) {

		int rs = 0;
		try{
			if(conn == null || conn.isClosed()){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
			}

			String SQL = "UPDATE "+relName+" SET status = '"+value+"' WHERE loc_id = '"+this._loc_id+"'";
			Statement stm = conn.createStatement();
			rs = stm.executeUpdate(SQL);

			if(!holdConnection) conn.close();
			
		}catch(Exception e){ 
			System.out.println(e);

		}
		
		return rs;
		
	}

	public String remark() {
		return this._remark;
	}

	public Integer remark(String value) {

		int rs = 0;
		try{
			if(conn == null || conn.isClosed()){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
			}

			String SQL = "UPDATE "+relName+" SET remark = '"+value+"' WHERE loc_id = '"+this._loc_id+"'";
			Statement stm = conn.createStatement();
			rs = stm.executeUpdate(SQL);

			if(!holdConnection) conn.close();
			
		}catch(Exception e){ 
			System.out.println(e);

		}
		
		return rs;
		
	}

	//==================== Basic Function ====================
		
	//List
	public Warehouses[] List() {
		return List("","");
	}	
	public Warehouses[] List(String Condition) {
		return List(Condition,"");
	}	
	public Warehouses[] List(String Condition, String SortOrder) {
		
		//Init Parameter
		if(Condition != "") {Condition = " WHERE " +Condition;}
		if(SortOrder != "") {SortOrder = " ORDER BY " +SortOrder;}
		
		Warehouses[] rt = null;
		String SQL;
		String loc_id;
		int rc, i = 0;
		try{
			if(conn == null || conn.isClosed()){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
			}

			Statement stm = conn.createStatement();
			SQL = "SELECT COUNT(*) FROM " +relName+Condition+SortOrder;
			ResultSet rs = stm.executeQuery(SQL);
			rs.next();
			rc = rs.getInt(1);
			
			SQL = "SELECT loc_id FROM " +relName+Condition+SortOrder;
			stm = conn.createStatement();
			rs = stm.executeQuery(SQL);
			rt = new Warehouses[rc];
			while(rs.next()){
				loc_id = rs.getString("loc_id");
				rt[i] = new Warehouses(loc_id);
				i++;
			}			
			
			if(!holdConnection) conn.close();
			
		}catch(Exception e){ 
			System.out.println(e);

		}
		
		return rt;

	}

	//Add
	public int Add( WarehousesInfo[] Items) {
		int rt = 0;
		try{
			String SQL;
			String ItemList = "";
			if(Items.length > 0) {
				for(WarehousesInfo A : Items) {
					if(ItemList != "") {ItemList += ",";}
					
					ItemList += "('";
					ItemList += A.loc_id +"','";
					ItemList += A.status +"','";
					ItemList += A.remark;
					ItemList += "')";
					
				}
				ItemList += ";";

			}
			
			if(conn == null || conn.isClosed()){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
			}

			Statement stm = conn.createStatement();
			SQL = "INSERT INTO " +relName+ "(loc_id, status, remark)";
			SQL += " VALUES"+ItemList;
			rt = stm.executeUpdate(SQL);

			if(!holdConnection) conn.close();
			
		}catch(Exception e){ 
			System.out.println(e);

		}
		
		return rt;
	}

	//Delete
	public int Remove() {
		int rt = 0;
		try{
			String SQL;
			
			if(conn == null || conn.isClosed()){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
			}

			Statement stm = conn.createStatement();
			SQL = "DELETE FROM " +relName+ " WHERE loc_id = '"+this._loc_id+"'";
			rt = stm.executeUpdate(SQL);
			
			if(!holdConnection) conn.close();
			
		}catch(Exception e){ 
			System.out.println(e);
		}
		
		return rt;
	}

		public int Remove(String Condition) {
		int rt = 0;
		try{
			String SQL;
			
			if(conn == null || conn.isClosed()){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
			}

			Statement stm = conn.createStatement();
			SQL = "DELETE FROM " +relName+ " WHERE " +Condition;
			rt = stm.executeUpdate(SQL);

			if(!holdConnection) conn.close();
			
		}catch(Exception e){ 
			System.out.println(e);
			
		}
		
		return rt;
	}

	public String dblLine(int length) {

		if(length < 1) length = 1;
		String buff = "";
		for(int i = 0; i < length; i++) {
			buff += "=";
		}
		
		return buff;
		
	}

	public String ReportTable() {
		return ReportTable("","");
	}
	
	public String ReportTable(String Condition) {
		return ReportTable(Condition,"");
	}

	public String ReportTable(String Condition, String SortOption) {

		Date d1, d2;		
		int dblLineLength = 200;
		int recordCount = 0;
		
		String buff = "", tmp = "";
		
		d1 = new Date();

		Warehouses CLSs[] = List(Condition, SortOption);		
		
		tmp += String.format("%s\n", dblLine(dblLineLength));
		tmp += String.format("%s\n", columns.replace(",", "\t"));
		tmp += String.format("%s\n", dblLine(dblLineLength));
		buff += tmp;
		tmp = "";
		
		if(CLSs != null && CLSs.length > 0) {
			recordCount = CLSs.length;
			for(Warehouses cls : CLSs) {
				try{
					tmp = String.format("%s\t %s\t %s\n", cls.loc_id(), cls.status(), cls.remark());
					buff += tmp;

				}catch(Exception e){
					buff += "Error found : " +e.getMessage()+ "\n";

				}				
			}
		}
		
		d2 = new Date();
		
		tmp += String.format("%s\n",dblLine(dblLineLength));		
		tmp += String.format("%s\n",recordCount+ " item(s) found.");
		tmp += String.format("%s\n",dblLine(dblLineLength));
		tmp += String.format("%s\n","[Report started] "+d1);
		tmp += String.format("%s\n","[Report finished] "+d2);
		tmp += String.format("%s\n",dblLine(dblLineLength));
		buff += tmp;

		return buff;
	
	}
}

