package gamesearch.dao;

import gamesearch.core.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class GameDAO {

	private Connection myConn;

	public GameDAO() throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("kursach.properties"));

		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		myConn = DriverManager.getConnection(dburl, user, password);

		System.out.println("GameDAO: DB connection successful to: " + dburl);
	}

	public List<Game> getAllGames() throws Exception {
		List<Game> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from игры");

			while (myRs.next()) {
				Game tempGame = convertRowToGame(myRs);
				list.add(tempGame);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	private Game convertRowToGame(ResultSet myRs) throws SQLException {

		Game game = new Game();

		game.setId(myRs.getInt("номер игры"));
		game.setName(myRs.getString("название"));
		game.setType(myRs.getString("вид игры"));
		game.setPrice(myRs.getInt("цена"));
		game.setVersion(myRs.getString("издание"));
		game.setReleaseDate(myRs.getDate("дата выхода"));
		game.setAgeRating(myRs.getString("возрастная категория"));
		game.setPublisher(myRs.getString("издатель"));

		return game;

	}

	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		try {
			if (myStmt != null)
				myStmt.close();
			if (myRs != null)
				myRs.close();
		} catch (Exception e) {
		}
		;
	}

	public void addGame(Game theGame) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("insert into игры " + "values (NULL, ?, ?, ?, ?, ?, ?, ?)");

			myStmt.setString(1, theGame.getName());
			myStmt.setString(2, theGame.getType());
			myStmt.setInt(3, theGame.getPrice());
			myStmt.setString(4, theGame.getVersion());
			myStmt.setDate(5, (Date) theGame.getReleaseDate());
			myStmt.setString(6, theGame.getAgeRating());
			myStmt.setString(7, theGame.getPublisher());

			myStmt.executeUpdate();
		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			myStmt.close();
		}
	}

	public void deleteGame(int gameId) throws SQLException {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("DELETE FROM `lab5_schema`.`audit history` WHERE (`game id` = ?)");
			
			myStmt.setInt(1, gameId);

			myStmt.executeUpdate();

			myStmt.close();
			
			myStmt = myConn.prepareStatement("DELETE FROM `lab5_schema`.`игры` WHERE (`номер игры` = ?)");

			myStmt.setInt(1, gameId);

			myStmt.executeUpdate();
		} finally {
			myStmt.close();
		}
	}

	public void updateGame(Game theGame, int userId) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("update `игры`set `название`=?, "
					+ "`вид игры`=?, `цена`=?, `издание`=?, `дата выхода`=?, `возрастная категория`=?, `издатель`=? "
					+ "where `номер игры`=?");
			myStmt.setString(1, theGame.getName());
			myStmt.setString(2, theGame.getType());
			myStmt.setInt(3, theGame.getPrice());
			myStmt.setString(4, theGame.getVersion());
			myStmt.setDate(5, (Date) theGame.getReleaseDate());
			myStmt.setString(6, theGame.getAgeRating());
			myStmt.setString(7, theGame.getPublisher());
			myStmt.setInt(8, theGame.getId());

			myStmt.executeUpdate();
			myStmt.close();

			myStmt = myConn.prepareStatement("insert into `audit history` "
					+ "(`user id`, `game id`, `action`, `action date`) " + "values (?,?,?,?)");

			myStmt.setInt(1, userId);
			myStmt.setInt(2, theGame.getId());
			myStmt.setString(3, "Updated game.");
			myStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

			myStmt.executeUpdate();
		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			myStmt.close();
		}
	}
	
	public List<Game> filterByPrice(int thePrice) throws SQLException{
		List<Game> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from `игры` where `цена` <= " + thePrice);

			while (myRs.next()) {
				Game tempGame = convertRowToGame(myRs);
				list.add(tempGame);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}
}
