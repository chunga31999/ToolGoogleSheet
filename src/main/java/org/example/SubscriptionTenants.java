package org.example;
public class SubscriptionTenants {

    private int Id;
    private String key;
    private int value;

    public SubscriptionTenants(){}
    public SubscriptionTenants(int id, String key, int value) {
        Id = id;
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
