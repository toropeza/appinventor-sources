// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.explorer.youngandroid;

import java.util.ArrayList;
import java.util.List;

import com.google.appinventor.client.GalleryClient;
import com.google.appinventor.client.Ode;
import com.google.appinventor.client.boxes.GalleryListBox;
import com.google.appinventor.shared.rpc.user.User;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 * The Gallery Toolbar
 * @author Bin Lu blu2@dons.usfca.edu
 *
 */
public class GalleryToolbar extends Composite {
  public static List<GalleryToolbar> allSearchToolbars = new ArrayList<GalleryToolbar>();  //store the reference of all creating toolbar
  final TextBox searchText;
  final Button searchButton;
  final Image profileAvatar;
  final Label name;
  final Image followerListIcon;

  /**
   * Initializes and assembles all commands into buttons in the toolbar.
   */
  public GalleryToolbar() {
    final User user = Ode.getInstance().getUser();
    String username = user.getUserName();
    final String userId = user.getUserId();

    allSearchToolbars.add(this);
    HorizontalPanel toolbar = new HorizontalPanel();
    toolbar.setWidth("100%");
    toolbar.setStylePrimaryName("ya-GalleryToolbar");

    FlowPanel actionPanel = new FlowPanel();
    searchText = new TextBox();
    searchText.addStyleName("gallery-search-textarea");
    searchButton = new Button("Search apps");
    searchButton.addStyleName("search-compontent");
    profileAvatar = new Image();
    profileAvatar.addStyleName("gallery-toolbar-icon");
    profileAvatar.addStyleName("gallery-avatar-wrapper");
    //handle editing usernames larger than User.USERNAME_MAX
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
    actionPanel.add(searchButton);
    actionPanel.addStyleName("gallery-toolbar");
    toolbar.add(actionPanel);
    followerListIcon.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        Ode.getInstance().switchToUserFollowersView();
      }
    });
    searchButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        GalleryClient.getInstance().FindApps(searchText.getText(), 0, GalleryList.NUMAPPSTOSHOW, 0, true);
        searchText.setFocus(true);
        Ode.getInstance().switchToGalleryView();
        GalleryListBox.getGalleryListBox().getGalleryList().setSelectTabIndex(GalleryList.SEARCH_TAB);
        for(GalleryToolbar toolbar : allSearchToolbars){
          toolbar.getSearchText().setText(searchText.getText());
        }
        //TODO in gallerylist.java --> findapps: create a way to grab keyword from this toolbar
        //this is just a temp solution.
        GalleryListBox.getGalleryListBox().getGalleryList().getSearchText().setText(searchText.getText());
      }
    });
    searchText.addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent e) {
        if(e.getNativeKeyCode() == KeyCodes.KEY_ENTER){
          GalleryClient.getInstance().FindApps(searchText.getText(), 0, GalleryList.NUMAPPSTOSHOW, 0, true);
          searchText.setFocus(true);
          Ode.getInstance().switchToGalleryView();
          GalleryListBox.getGalleryListBox().getGalleryList().setSelectTabIndex(GalleryList.SEARCH_TAB);
          for(GalleryToolbar toolbar : allSearchToolbars){
            toolbar.getSearchText().setText(searchText.getText());
          }
          //TODO in gallerylist.java --> findapps: create a way to grab keyword from this toolbar
          //this is just a temp solution.
          GalleryListBox.getGalleryListBox().getGalleryList().getSearchText().setText(searchText.getText());
        }
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

  /**
   * get searchText label
   * @return Label searchText
   */
  public TextBox getSearchText(){
    return searchText;
  }

  /**
   * get getSearchButton button
   * @return Button searchButton
   */
  public Button getSearchButton(){
    return searchButton;
  }

  public void setUserAvatar(String url){
    profileAvatar.setUrl(url);
    //called when user has no profile photo
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
