package net.brigs.crm.HttpClient.parser;

public class User {
    private  boolean success;
    private String user;
    private String id;


    public boolean getSuccess() {
        return success;
    }

    public String getUser() {
        return user;
    }


    public User(boolean success, String user) {

        this.success = success;
        this.user = user;

    }

    @Override
    public String toString() {
        return "User{" +
                "success=" + success +
                ", user='" + user + '\'' +
                '}';
    }
}
