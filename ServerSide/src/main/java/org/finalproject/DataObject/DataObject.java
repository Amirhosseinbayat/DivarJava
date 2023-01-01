package org.finalproject.DataObject;

import java.io.*;

/**
 * Base class
 * DataObjects will contain data that either has to be transmitted over network or saved in database.
 * These objects will be serializable.
 */
public class DataObject implements Serializable {

    /**
     * Creates a subclass of SerializableDataObject from the byte array provided.
     * V is the classType of the returned object, it is determined by the context in which this method is used.
     * for example:
     * TestClass test = SerializableDataObject.createFromBytes(bytes);
     * is going to cast the return value to a "TestClass" object. because we want to assign
     * the result of this method as a TestObject.
     * <p>
     * (V extends SerializableDataObject) means that context should use subclasses of this class.
     */
    public static <TYPE extends DataObject> TYPE createFromByteArray(byte[] bytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return (TYPE) o;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] toByteArray() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            out.close();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
