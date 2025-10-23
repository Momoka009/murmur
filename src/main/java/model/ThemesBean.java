package model;

import java.io.Serializable;

public class ThemesBean implements Serializable {
	private int theme_id;
	private String text;
	
	public int getTheme_id() {
		return theme_id;
	}
	public void setTheme_id(int theme_id) {
		this.theme_id = theme_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
