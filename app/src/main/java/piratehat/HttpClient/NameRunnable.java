package piratehat.HttpClient;

/**
 * Created by PirateHat on 2019/2/18.
 */

public abstract class NameRunnable implements Runnable {

    private String mName;
    public NameRunnable(String name) {
        this.mName = name;
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        String oldName = thread.getName();
        thread.setName(mName);
        execute();
        thread.setName(oldName);
    }


    public abstract void execute();
}
