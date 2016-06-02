package com.google.appinventor.client.explorer.youngandroid;

import com.google.appinventor.client.GalleryGuiFactory;
import com.google.appinventor.client.Ode;
import com.google.appinventor.client.OdeAsyncCallback;
import com.google.appinventor.shared.rpc.project.Followers;
import com.google.appinventor.shared.rpc.user.User;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import static com.google.appinventor.client.Ode.MESSAGES;

/**
 * The Gallery Followers Page
 * @author thomasoropeza@gmail.com (Thomas Oropeza)
 *
 */
public class FollowersPage extends Composite {

    FlowPanel followersPanel = new FlowPanel();
    Label followersLabel = new Label(Ode.getMessages().followingLabel());
    Label numberOfFollowers = new Label();

    // The abstract top-level GUI container
    VerticalPanel followersGUI = new VerticalPanel();
    GalleryGuiFactory guiFactory = new GalleryGuiFactory();

    private final User user;
    private final String userId;
    private Followers followers = new Followers();

    public void loadFollowers(){
        final OdeAsyncCallback<Followers> getFollowersCallback = new OdeAsyncCallback<Followers>() {
            @Override
            public void onSuccess(Followers returnedFollowers) {
                followers = returnedFollowers;
                numberOfFollowers.setText(String.valueOf("Following " + returnedFollowers.getTotalFollowers() + " users"));
                guiFactory.generateFollowersList(followers, followersPanel, true);
            }
        };
        Ode.getInstance().getGalleryService().getFollowers(userId, getFollowersCallback);
    }

    public Followers getFollowers(){
        return followers;
    }

    public FollowersPage(){
        user = Ode.getInstance().getUser();
        userId = user.getUserId();

        // profileGUI is just the abstract top-level GUI container
        followersGUI.addStyleName("ode-UserProfileWrapper");
        followersGUI.addStyleName("gallery");
        initWidget(followersGUI);

        followersLabel.addStyleName("followers-label");
        followersGUI.add(followersLabel);
        followersGUI.add(numberOfFollowers);

        followersGUI.add(followersPanel);
    }
}
