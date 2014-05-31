package at.lvmaster3000.gui;

public class NavDrawerItem {

	private String title;
	private int icon;
	private int count;
	private boolean isCounterVisible;
	
	public NavDrawerItem(){}
	
    public NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }
     
    public NavDrawerItem(String title, int icon, boolean isCounterVisible, int count){
        this.title = title;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getIcon() {
		return icon;
	}
	
	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}	
	
	public boolean getCounterVisibility() {
		return isCounterVisible;
	}

	public void setCounterVisibility(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}
}
