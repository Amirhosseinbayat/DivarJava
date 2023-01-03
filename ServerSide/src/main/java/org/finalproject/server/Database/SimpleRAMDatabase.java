package org.finalproject.server.Database;

import org.finalproject.DataObject.DataObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is a simple database not meant to be used in production.
 * It is used to test methods without having to wait for disk calls.
 */
public class SimpleRAMDatabase implements IDataBase {
    Set<DataObject> dataObjectList = new HashSet<>();

    @Override
    public void save(DataObject object) {
        if (dataObjectList.add(object)) object.setObjectId(dataObjectList.size()-1);
    }

    @Override
    public <V extends DataObject> List<V> findAll(QueryConstraints<V> queryConstraints) {
        List<V> results = new ArrayList<>();
        for (DataObject dataObject : dataObjectList) {
            try {
                @SuppressWarnings("unchecked") //trust me intellij, I'm an engineer!
                V object = (V) dataObject;
                if (queryConstraints.test(object)) {
                    results.add(object);
                }
            } catch (ClassCastException ignored) {
            }
        }
        return results;
    }

    @Override
    public <V extends DataObject> V findOne(QueryConstraints<V> queryConstraints) {
        for (DataObject dataObject : dataObjectList) {
            try {
                @SuppressWarnings("unchecked")
                V object = (V) dataObject;
                if (queryConstraints.test(object)) {
                    return object;
                }
            } catch (ClassCastException ignored) {
            }
        }
        return null;
    }

    @Override
    public <V extends DataObject> V getObjectWithId(long objectId) throws IOException {
        for (DataObject dataObject : dataObjectList) {
            if (dataObject.getObjectId() == objectId) return (V) dataObject;
        }
        return null;
    }
}
