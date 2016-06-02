package com.google.appinventor.client.boxes;

import com.google.appinventor.client.explorer.youngandroid.FollowersPage;
import com.google.appinventor.shared.rpc.project.Followers;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Box implementation for user profile.
 *
 * @author thomasoropeza@gmail.com (Thomas Oropeza)
 *
 */
public class FollowersBox extends FlowPanel {

    // Singleton Profile explorer box instance (only one Profile explorer allowed)
    private static final FollowersBox INSTANCE = new FollowersBox();

    // Followers page for young android
    private static FlowPanel pContainer;
    private static FollowersPage fPage;

    public static void loadFollowers(){
        fPage.loadFollowers();
    }

    /**
     * Returns the singleton FollowersBox.
     *
     * @return  ProfileBox box
     */
    public static FollowersBox getFollowersBox() {
        return INSTANCE;
    }

    /**
     * Returns the singleton FollowersBox.
     *
     * @return  ProfileBox box
     */
    public static FollowersPage getFollowersPage() {
        return fPage;
    }

    public static Followers getFollowers(){
        return fPage.getFollowers();
    }
    /**
     * Creates new user followers box.
     */
    private FollowersBox() {
        pContainer = new FlowPanel();
        this.add(pContainer);
        fPage = new FollowersPage();
        pContainer.add(fPage);
    }
}
