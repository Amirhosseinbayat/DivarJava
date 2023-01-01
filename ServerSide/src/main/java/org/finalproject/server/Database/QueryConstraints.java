package org.finalproject.server.Database;

import org.finalproject.DataObject.DataObject;

/**
 * Database will use QueryConstraints to test objects against the query criteria.
 * results will be sorted according to a comparator using {@link #compare} method..
 */
public interface QueryConstraints<V extends DataObject> {

    boolean test(V object);

    int compare(V o1, V o2);

}
