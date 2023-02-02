package gamesearch.core;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class Game {
	private Integer id;
	private String name;
	private String type;
	private Integer price;
	private String version;
	private Date releaseDate;
	private String ageRating;
	private String publisher;
	
	
	
	public Game() {
		super();
	}

	public Game(int id, String name, String type, Integer price, String version, Date releaseDate, String ageRating,
			String publisher) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.version = version;
		this.releaseDate = releaseDate;
		this.ageRating = ageRating;
		this.publisher = publisher;
	}
	
	public Game(String name, String type, Integer price, String version, Date releaseDate, String ageRating,
			String publisher) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.version = version;
		this.releaseDate = releaseDate;
		this.ageRating = ageRating;
		this.publisher = publisher;
	}
	
	@Override
	public String toString() {
		return id + ", " + name + ", " + type + ", " + price + ", " + version + ", " + releaseDate + ", " + ageRating + ", " + publisher + ")"; 
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date date) {
		this.releaseDate = date;
		}
	public String getAgeRating() {
		return ageRating;
	}
	public void setAgeRating(String ageRating) {
		this.ageRating = ageRating;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
