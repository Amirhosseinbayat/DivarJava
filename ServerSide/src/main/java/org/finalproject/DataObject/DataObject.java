package org.finalproject.DataObject;

import java.io.*;
import java.util.Objects;

/**
 * Base class
 * DataObjects will contain data that either has to be transmitted over network or saved in database.
 * These objects will be serializable.
 */
public class DataObject implements Serializable, Cloneable {

    public static final int RECORD_LIMIT = 1024;
    //should be increased when serialized versions of older objects can not be deserialized to the new class...
    static final long serialVersionUID = 1L;

    /**
     * Creates a subclass of SerializableDataObject from the byte array provided.
     * V is the classType of the returned object, it is determined by the context in which this method is used.
     * for example:
     * TestClass test = SerializableDataObject.createFromByteArray(bytes);
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

    long objectId = -1; //must not be 0 at first because 0 itself is a valid objectId.

    public static byte[] toByteArray(Serializable serializable) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(serializable);
            out.flush();
            out.close();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] toByteArray() {
        return toByteArray(this);
    }

    public long getObjectId() {
        return objectId;
    }

    long createdAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataObject that)) return false;
        return getObjectId() == that.getObjectId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObjectId());
    }

    long updatedAt;

    public void setObjectId(long objectId) {
        if (objectId<0) throw new RuntimeException("invalid object id");
        this.objectId = objectId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
        setUpdatedAt(createdAt);
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public DataObject clone() {
        try {
            return (DataObject) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
