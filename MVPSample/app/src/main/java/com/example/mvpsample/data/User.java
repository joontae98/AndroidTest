package com.example.mvpsample.data;

import java.util.ArrayList;

public class User {
    private String id;
    private String pw;

    public interface Callback {
        void onSuccess();

        void onFail();
    }

    public User(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public void findUser(User user, Callback callback) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("1@1.1","1"));
        users.add(new User("1@2.1","2"));
        users.add(new User("1@3.1","3"));

        for (int i = 0; i < users.size(); i++) {
            User data = users.get(i);
            if (user.getId().equals(data.getId()) && user.getPw().equals(data.getPw())) {
                callback.onSuccess();
                return;
            }
            callback.onFail();
            return;
        }
    }
}
