package org.finalproject.DataObject;

/**
 * represents a user and his/her profile information.
 */
public class User extends DataObject {

    static final long serialVersionUID = 1L;
    String name;
    String password;


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{"+
                "name='"+name+'\''+
                ", password='"+password+'\''+
                ", objectId="+objectId+
                '}';
    }
}
