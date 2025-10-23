package model;

import java.io.Serializable;
import java.time.LocalDate;

public class UsersBean implements Serializable {

	private String userId;
	private String nickname;
	private String password;
	private int currentStreak;
	private LocalDate last_post_date;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCurrentStreak() {
		return currentStreak;
	}
	public void setCurrentStreak(int current_streak) {
		this.currentStreak = current_streak;
	}
	public LocalDate getLast_post_date() {
		return last_post_date;
	}
	public void setLast_post_date(LocalDate last_post_date) {
		this.last_post_date = last_post_date;
	}
	
}
