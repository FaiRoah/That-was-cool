package gamesearch.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import gamesearch.core.AuditHistory;
import gamesearch.core.Game;

public class AuditHistoryDAO {

	private Connection myConn;

	public AuditHistoryDAO() throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("kursach.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		myConn = DriverManager.getConnection(dburl, user, password);

		System.out.println("AuditHistoryDAO: DB connection successful to: " + dburl);
	}

	public List<AuditHistory> getAuditHistory(int gameId) throws Exception {

		List<AuditHistory> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
//			myStmt = myConn.prepareStatement("CREATE TEMPORARY TABLE TempAudHis AS SELECT * FROM lab5_schema.`audit history`;"
//					+ "ALTER TABLE TempAudHis DROP COLUMN `id`;"
//					+ "SELECT * FROM TempAudHis;"
//					+ " where `game id` = ?");
			myStmt = myConn.prepareStatement("select * from `audit history` where `game id` = ?");
			myStmt.setInt(1, gameId);
			myRs = myStmt.executeQuery();	//TODO: may be undesired behavior
			

			while (myRs.next()) {
				AuditHistory tempAuditHistory = convertRowToAuditHistory(myRs);
				list.add(tempAuditHistory);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	private void close(PreparedStatement myStmt, ResultSet myRs) throws SQLException {
		try {
			if (myStmt != null)
				myStmt.close();
			if (myRs != null)
				myRs.close();
		} catch (Exception e) {
		}
		;
	}
	private AuditHistory convertRowToAuditHistory(ResultSet myRs) throws SQLException {
		AuditHistory auditHistory = new AuditHistory();

		auditHistory.setId(myRs.getInt("id"));
		auditHistory.setUser_id(myRs.getInt("user id"));
		auditHistory.setGame_id(myRs.getInt("game id"));
		auditHistory.setAction(myRs.getString("action"));
		auditHistory.setAction_date(myRs.getDate("action date"));
		
		return auditHistory;
	}
}
