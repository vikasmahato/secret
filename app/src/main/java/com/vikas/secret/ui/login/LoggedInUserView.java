package com.vikas.secret.ui.login;

public class LoggedInUserView {
    private String displayName;
    private String email;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }
}
