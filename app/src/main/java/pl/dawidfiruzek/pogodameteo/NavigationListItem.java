package pl.dawidfiruzek.pogodameteo;

/**
 * Created by fks on 2015-05-19.
 */
public class NavigationListItem {
    private String mTitle;
    private String mSubtitle;
    private int mIcon;

    public NavigationListItem(String title, String subtitle, int icon){
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getIcon() {
        return mIcon;
    }
}
