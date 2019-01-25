package Connexion_Base_Oracle;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;




import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;
import java.sql.DatabaseMetaData;

public class Connexion_BDD {  
  // The recommended format of a connection URL is the long format with the
  // connection descriptor.
  final static String DB_URL= "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521/im2ag";
  // For ATP and ADW - use the TNS Alias name along with the TNS_ADMIN when using 18.3 JDBC driver
  // final static String DB_URL="jdbc:oracle:thin:@wallet_dbname?TNS_ADMIN=/Users/test/wallet_dbname";
  // In case of windows, use the following URL 
  // final static String DB_URL="jdbc:oracle:thin:@wallet_dbname?TNS_ADMIN=C:\\Users\\test\\wallet_dbname";
  final static String DB_USER = "roussys";
  final static String DB_PASSWORD = "fn7DPQxAEB";

 /*
  * The method gets a database connection using 
  * oracle.jdbc.pool.OracleDataSource. It also sets some connection 
  * level properties, such as,
  * OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH,
  * OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES, etc.,
  * There are many other connection related properties. Refer to 
  * the OracleConnection interface to find more. 
  */
  public static void main(String args[]) throws SQLException {
    Properties info = new Properties();     
    info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
    info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);          
    info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");    
  

    OracleDataSource ods = new OracleDataSource();
    ods.setURL(DB_URL);    
    ods.setConnectionProperties(info);

    // With AutoCloseable, the connection is closed automatically.
    try (OracleConnection connection = (OracleConnection) ods.getConnection()) {
      // Get the JDBC driver name and version 
      DatabaseMetaData dbmd = connection.getMetaData();       
      System.out.println("Driver Name: " + dbmd.getDriverName());
      System.out.println("Driver Version: " + dbmd.getDriverVersion());
      // Print some connection properties
      System.out.println("Default Row Prefetch Value is: " + 
         connection.getDefaultRowPrefetch());
      System.out.println("Database Username is: " + connection.getUserName());
      System.out.println();
      // Perform a database operation 
      
      //Requete(connection); //juste les requetes
      
      try {
		Scripts("C:\\Users\\P61SDEMVID2\\Documents\\projets\\ABD -Projet Photo\\ProjetBD-G12-PhotoNum\\Connexion_Base_Oracle\\nettoyageBase.sql",connection);
		System.out.println("coucou");
      } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }//fichier de scripts  
    }   
  }
 /*
  * Displays first_name and last_name from the employees table.
  */
  public static void Requete(Connection connection) throws SQLException {
    // Statement and ResultSet are AutoCloseable and closed automatically. 
    try (Statement statement = connection.createStatement()) {      
      try (ResultSet resultSet = statement
          .executeQuery("DROP TABLE ROUSSYS.ZOO_LESRESPONSABLES")) {
        System.out.println("***");
        System.out.println("---------------------");
        while (resultSet.next())
          System.out.println(resultSet.getString(1) + " ");
            //  + resultSet.getString(2) + " ");       
      }
    }   
  }
  public static boolean Scripts(String aSQLScriptFilePath,Connection connection) throws IOException,SQLException {
	  boolean isScriptExecuted = false;
	  	try {
		  BufferedReader in = new BufferedReader(new FileReader(aSQLScriptFilePath));
		  String str;
		  StringBuffer sb = new StringBuffer();
		  while ((str = in.readLine()) != null) {
			  sb.append(str + "\n ");
		  }
		  in.close();
		  Statement stmt = connection.createStatement();
		  stmt.executeUpdate(sb.toString());
		  isScriptExecuted = true;
	  	} catch (Exception e) {
	  	System.err.println("Failed to Execute " + aSQLScriptFilePath +". The error is "+ e.getMessage());
	  } 
	  return isScriptExecuted;
	}
}

