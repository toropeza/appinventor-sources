package com.google.appinventor.client.explorer.youngandroid;

import com.google.appinventor.client.GalleryGuiFactory;
import com.google.appinventor.client.Ode;
import com.google.appinventor.client.boxes.FollowersBox;
import com.google.appinventor.shared.rpc.project.Followers;
import com.google.appinventor.shared.rpc.user.User;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Toolbar for the Followers Page, contains the search followers TextBox
 * @author thomasoropeza@gmail.com (Thomas Oropeza)
 *
 */
public class FollowersListToolbar extends Composite{

    public static List<FollowersListToolbar> allSearchToolbars = new ArrayList<FollowersListToolbar>();  //store the reference of all creating toolbar
    private static final int SEARCHTAB = 4;
    final TextBox searchText;
    final Image profileAvatar;
    final Label name;
    final Image followerListIcon;
    final GalleryGuiFactory guiFactory = new GalleryGuiFactory();

    /**
     * Initializes and assembles all commands into buttons in the toolbar.
     */
    public FollowersListToolbar() {
        allSearchToolbars.add(this);
        final User user = Ode.getInstance().getUser();
        String username = user.getUserName();
        final String userId = user.getUserId();

        HorizontalPanel toolbar = new HorizontalPanel();
        toolbar.setWidth("100%");
        toolbar.setStylePrimaryName("ya-GalleryToolbar");

        FlowPanel actionPanel = new FlowPanel();
        searchText = new TextBox();
        searchText.getElement().setPropertyString("placeholder", "Search Followers...");
        searchText.addStyleName("gallery-search-textarea");
        profileAvatar = new Image();
        profileAvatar.addStyleName("gallery-toolbar-icon");
        profileAvatar.addStyleName("gallery-avatar-wrapper");
        if (username.length() > User.USERNAME_MAX){
            username = username.substring(0, User.USERNAME_MAX-1) + "...";
        }
        followerListIcon = new Image();
        followerListIcon.setResource(Ode.getImageBundle().followersIcon());
        followerListIcon.addStyleName("gallery-toolbar-icon");
        name = new Label(username);
        name.addStyleName("gallery-toolbar-name");
        actionPanel.add(name);
        actionPanel.add(profileAvatar);
        actionPanel.add(followerListIcon);
        actionPanel.add(searchText);
        actionPanel.addStyleName("gallery-toolbar");
        toolbar.add(actionPanel);
        followerListIcon.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Ode.getInstance().switchToUserFollowersView();
            }
        });

        //display search results as user is typing
        searchText.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                String text = searchText.getText();
                Followers results = new Followers();
                for (User follower: FollowersBox.getFollowers()){
                    if (follower.getUserName().contains(text)){
                        results.addFollower(follower);
                    }
                }
                guiFactory.generateFollowersList(results, FollowersBox.getFollowersPage().followersPanel, true);
            }
        });
        profileAvatar.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                //switch to public view of profile
                Ode.getInstance().switchToUserProfileView(userId, ProfilePage.PUBLIC);
            }
        });
        name.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Ode.getInstance().switchToUserProfileView(userId, ProfilePage.PUBLIC);
            }
        });
        initWidget(toolbar);
    }

    public void setUserAvatar(String url){
        profileAvatar.setUrl(url);
        profileAvatar.addErrorHandler(new ErrorHandler() {
            @Override
            public void onError(ErrorEvent event) {
                profileAvatar.setResource(Ode.getImageBundle().profileIcon());
            }
        });
    }

    /**
     * Updates the username of the toolbar
     *  @param newUserName The new username to replace existing
     * */
    public void setUserName(String newUserName){
        name.setText(newUserName);
    }
}
