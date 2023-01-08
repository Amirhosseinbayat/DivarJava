package org.finalproject.server.Database;

import org.finalproject.DataObject.DataObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class DataBase implements IDataBase {

    ReentrantLock reentrantLock = new ReentrantLock();
    File file;
    RandomAccessFile randomAccessFile;


    public DataBase(File file) throws FileNotFoundException {
        this.file = file;
        this.randomAccessFile = new RandomAccessFile(file, "rwd");
    }

    @Override
    public <V extends DataObject> List<V> findAll(QueryConstraints<V> queryConstraints) throws IOException {
        List<V> list = new ArrayList<>();
        byte[] chunk = new byte[DataObject.RECORD_LIMIT];
        long remainingBytes = file.length();

        reentrantLock.lock();
        randomAccessFile.seek(0);
        while (remainingBytes != 0) {
            int read = randomAccessFile.read(chunk, 0, (int) Math
                    .min(remainingBytes, DataObject.RECORD_LIMIT));
            remainingBytes -= read;
            try {
                V obj = DataObject.createFromByteArray(chunk);
                if (queryConstraints.test(obj)) list.add(obj);
            } catch (ClassCastException e) {
                //the object does not match the classType requested. we will simply ignore it...
            } catch (Exception e) {
                System.out.println("error on restoring object : "+e.getMessage());
            }
        }
        reentrantLock.unlock();

        return list;
    }

    @Override
    public <V extends DataObject> V findOne(QueryConstraints<V> queryConstraints) throws IOException {
        byte[] chunk = new byte[DataObject.RECORD_LIMIT];
        int remainingBytes = (int) file.length();
        reentrantLock.lock();
        randomAccessFile.seek(0);
        while (remainingBytes != 0) {
            int read = randomAccessFile.read(chunk, 0, Math.min(remainingBytes, DataObject.RECORD_LIMIT));
            remainingBytes -= read;
            try {
                V obj = DataObject.createFromByteArray(chunk);
                if (queryConstraints.test(obj)) {
                    reentrantLock.unlock();
                    return obj;
                }
            } catch (ClassCastException e) {
                //the object does not match the classType requested. we will simply ignore it...
            }
        }
        reentrantLock.unlock();
        return null;
    }

    public void close() throws IOException {
        reentrantLock.lock(); //waits for any ongoing read/write to be completed.
        randomAccessFile.close();
        //reentrantLock.unlock();
        // as we have closed the database,
        //we will not release the lock to prevent any new operation and avoid crash due to exception.
    }

    public void save(DataObject dataObject) throws IOException {
        reentrantLock.lock(); //this lock is needed for ID assignment of the first object to be thread-safe.
        if (dataObject.getObjectId()<0) {
            long fileLength = file.length();
            dataObject.setObjectId(fileLength);
        }
        saveObjectAt(dataObject.getObjectId(), dataObject);
        reentrantLock.unlock();

    }


    private void saveObjectAt(long startByteIndex, DataObject dataObject) throws IOException {
        byte[] bytesObject = dataObject.toByteArray();
        if (bytesObject.length>DataObject.RECORD_LIMIT) throw new IOException("object size exceeds db record limit.");
        byte[] bytesFilling = new byte[DataObject.RECORD_LIMIT-bytesObject.length];
        reentrantLock.lock();
        randomAccessFile.seek(startByteIndex);
        randomAccessFile.write(bytesObject);
        randomAccessFile.write(bytesFilling);
        reentrantLock.unlock();
    }


    public <V extends DataObject> V getObjectWithId(long objectId) throws IOException {
        if (objectId%DataObject.RECORD_LIMIT != 0) {
            throw new RuntimeException("not a valid object id");
        }
        reentrantLock.lock();
        randomAccessFile.seek(objectId);
        byte[] chunk = new byte[DataObject.RECORD_LIMIT];
        randomAccessFile.read(chunk, 0, DataObject.RECORD_LIMIT);
        reentrantLock.unlock();
        return DataObject.createFromByteArray(chunk);
    }


}
