package com.sky.jSimple.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class Session {
     private DataSource dataSource;
     
     private Connection connection;
     
     private SessionFactory sessionFactory;
     
     public void setAutoCommit(boolean autoCommit) throws SQLException
     {
    	 if(connection!=null)
    	 {
    		 connection.setAutoCommit(autoCommit);
    	 }
     }
     
     public void commit() throws SQLException
     {
    	 if(connection!=null)
    	 {
    	     connection.commit();
    	 }
     }
     
     public void close() throws SQLException
     {
    	  if(connection!=null&&!connection.isClosed())
    	  {
    		  connection.close();
    	  }
     }
     
     public void rollback() throws SQLException
     {
    	 if(connection!=null)
    	 {
    	     connection.rollback();
    	 }
     }

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
     
}
