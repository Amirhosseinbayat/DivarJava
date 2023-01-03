package org.finalproject.server.Database;

import org.finalproject.DataObject.DataObject;

import java.io.IOException;
import java.util.List;

/**
 * As our software will be used by many of users simultaneously, database needs to be thread safe.
 * we will use java locking system to implement locking.
 * reads can occur simultaneously without a lock, writes have to be done one by one.
 */
public interface IDataBase {

    void save(DataObject object) throws IOException;

    /**
     * Will find all the objects matching the query.
     */
    <V extends DataObject> List<V> findAll(QueryConstraints<V> queryConstraints) throws IOException;

    /**
     * Will find only one object matching query constraints and then will stop scanning.
     * Useful when we are sure that there is only one object with the given criteria,
     * for example, when searching for a user object by its username.
     */
    <V extends DataObject> V findOne(QueryConstraints<V> queryConstraints) throws IOException;

    <V extends DataObject> V getObjectWithId(long objectId) throws IOException;

}
