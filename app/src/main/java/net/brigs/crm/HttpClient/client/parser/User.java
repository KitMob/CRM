package net.brigs.crm.HttpClient.client.parser;

public class User {
    private  boolean success;
    private String id;
    private String hash;



    public User(boolean success, String id, String hash) {
        this.success = success;
        this.id = id;
        this.hash = hash;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "User{" +
                "success=" + success +
                ", id='" + id + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}

