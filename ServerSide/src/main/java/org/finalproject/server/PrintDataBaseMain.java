package org.finalproject.server;

import org.finalproject.DataObject.DataObject;
import org.finalproject.server.Database.QueryConstraints;

import java.io.IOException;
import java.util.List;

public class PrintDataBaseMain {
    public static void main(String[] args) throws IOException {
        QueryConstraints<DataObject> queryConstraints = new QueryConstraints<DataObject>() {
            @Override
            public boolean test(DataObject object) {
                return true;
            }

            @Override
            public int compare(DataObject o1, DataObject o2) {
                return 0;
            }
        };
        System.out.println("started reading file...");
        long before = System.currentTimeMillis();
        List<DataObject> dataObjectList =
                ServerConfiguration.getInstance().getDataBase().findAll(queryConstraints);
        long took = System.currentTimeMillis()-before;
        System.out.println("there are "+dataObjectList.size()+" objects in database. took "+took
                +"ms");
        if (false) for (DataObject dataObject : dataObjectList) {
            System.out.println(dataObject.toString()+" "+dataObject.getObjectId());
        }
        long bef = System.currentTimeMillis();
        DataObject objectWithId =
                ServerConfiguration.getInstance()
                        .getDataBase().getObjectWithId(DataObject.RECORD_LIMIT*40960);
        long took2 = System.currentTimeMillis()-bef;
        System.out.println("finding object with id took "+took2+"ms"+"\n"+objectWithId.toString());
    }
}
