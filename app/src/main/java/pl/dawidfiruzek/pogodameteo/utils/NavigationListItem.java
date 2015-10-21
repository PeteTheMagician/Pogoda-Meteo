package pl.dawidfiruzek.pogodameteo.utils;

public class NavigationListItem {
    private String title;
    private String subtitle;
    private int icon;

    public NavigationListItem(String title, String subtitle, int icon){
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }
}
