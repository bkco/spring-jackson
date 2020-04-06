package co.bk.task.jackson.dto;

import java.util.List;

public class PojoWithList {

    String name;

    List<String> contacts;

    public PojoWithList(String name, List<String> contacts) {
        this.name = name;
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }
}
