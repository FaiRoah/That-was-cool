package gamesearch.core;

import java.sql.Date;

public class AuditHistory {
	private Integer id;
	private Integer user_id;
	private Integer game_id;
	private String action;
	private Date action_date;
	
	
	
	public AuditHistory() {
		super();
	}

	public AuditHistory(Integer id, Integer user_id, Integer game_id, String action, Date action_date) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.game_id = game_id;
		this.action = action;
		this.action_date = action_date;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getGame_id() {
		return game_id;
	}
	public void setGame_id(Integer game_id) {
		this.game_id = game_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getAction_date() {
		return action_date;
	}
	public void setAction_date(Date action_date) {
		this.action_date = action_date;
	}

}
