package org.finalproject.DataObject;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * represents a user and his/her profile information.
 */
@XmlType(name = "1")
@XmlRootElement
public class User extends DataObject {

    static final long serialVersionUID = 1L;
    final String name;
    final String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
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
