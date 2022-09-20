package package2;

public class Main {
    public static void main(String[] args) {
        MySynchro s = new MySynchro();
        Thread first = new Thread(new OneClass(s,6));
        Thread second = new Thread(new SecondClass(s,6));
        first.start();
        second.start();
    }
}

class MySynchro {
    boolean aBoolean;

    synchronized void getOneClass(int i, String strNameClass) {
        Thread iAmWaveThread = Thread.currentThread();
        iAmWaveThread.setName("__One__");
        if (aBoolean) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Я поток " + iAmWaveThread.getName() + " Меня прислал - " + strNameClass + " я напечатаю " + i);
        aBoolean = true;
        notify();
    }


    synchronized void getSecondClass(int i, String strNameClass) {
        Thread iAmWaveThread = Thread.currentThread();
        iAmWaveThread.setName("__Second__");
        if (!aBoolean) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Я поток " + iAmWaveThread.getName() + " Меня прислал - " + strNameClass + " я напечатаю " + i);
        aBoolean = false;
        notify();
    }
}

class OneClass extends Thread {
    private int count;
    MySynchro mS;
    String strNameClass;

    public OneClass(MySynchro mS, int count) {
        this.mS = mS;
        this.strNameClass = OneClass.class.getSimpleName();
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 1; i < count; i++) {
            if(i % 2 != 0){
                mS.getOneClass(i, strNameClass);
            }

        }
    }
}

class SecondClass extends Thread {

    private int count;
    MySynchro mS;
    String strNameClass;

    public SecondClass(MySynchro s, int count) {
        this.mS = s;
        this.strNameClass = SecondClass.class.getSimpleName();
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 1; i < count; i++) {
            if(i % 2 == 0){
                mS.getSecondClass(i, strNameClass);
            }
        }
    }
}

