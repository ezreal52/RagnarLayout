package come.ezreal.RagnarTabLayout;

public class TabEntity  {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public String getTabTitle() {
        return title;
    }

    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
