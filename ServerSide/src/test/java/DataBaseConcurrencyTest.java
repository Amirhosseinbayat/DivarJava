import org.finalproject.DataObject.DataObject;
import org.finalproject.DataObject.User;
import org.finalproject.server.Database.IDataBase;
import org.finalproject.server.ServerConfiguration;

import java.io.IOException;
import java.util.UUID;

public class DataBaseConcurrencyTest {
    static final Object lock = new Object();
    static IDataBase iDataBase;
    static volatile int threadsDone = 0;

    static synchronized void increment() {
        threadsDone++;
        System.out.println(Thread.currentThread().getName()+" done. index "+threadsDone);
        if (threadsDone == 10) {
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        iDataBase = ServerConfiguration.getInstance().getDataBase();
        for (int x = 0; x != 10; x++) {
            new Thread(() -> {
                for (int y = 0; y != 10000; y++) {
                    try {
                        iDataBase.save(createSampleObject());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                increment();
            }).start();
        }
        synchronized (lock) {
            lock.wait();
        }
        System.out.println("done!");
    }

    public static DataObject createSampleObject() {
        return new User(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }


}
