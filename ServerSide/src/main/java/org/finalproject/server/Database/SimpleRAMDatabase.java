package org.finalproject.server.Database;

import org.finalproject.DataObject.DataObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a simple database not meant to be used in production.
 * It is used to test methods without having to wait for disk calls.
 */
public class SimpleRAMDatabase implements IDataBase {
    List<DataObject> dataObjects = new ArrayList<>();

    @Override
    public void save(DataObject object) {
        System.out.println("saving object " + object.toString());
        if (object.getObjectId()<0){
            dataObjects.add(object);
            long objectId = dataObjects.size()-1;
            System.out.println("setting object id as " + objectId);
            object.setObjectId(objectId);
        }else{
            dataObjects.set((int) object.getObjectId(),object);
            System.out.println("updated object with id " + object.getObjectId());
        }

    }

    @Override
    public <V extends DataObject> List<V> findAll(QueryConstraints<V> queryConstraints) {
        List<V> results = new ArrayList<>();
        for (DataObject dataObject : dataObjects) {
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
        for (DataObject dataObject : dataObjects) {
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
        for (DataObject dataObject : dataObjects) {
            if (dataObject.getObjectId() == objectId) return (V) dataObject;
        }
        return null;
    }
}
