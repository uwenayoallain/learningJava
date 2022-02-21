public class DaemonDemo extends Thread {
    @Override
    public void run(){
        String name = Thread.currentThread().getName();
        System.out.println(name);
        if(Thread.currentThread().isDaemon()){
            System.out.println("executing:"+name);
        }
    }
    public static void main (String[] args){
        DaemonDemo thread = new DaemonDemo();
        DaemonDemo thread1 = new DaemonDemo();
        DaemonDemo thread2 = new DaemonDemo();
        thread.setName("Closable");
        thread.setDaemon(true);
        thread1.setName("Thread");
        thread1.setDaemon(true);
        thread2.setName("Thread 2");
        thread.start();
        thread1.start();
        thread2.start();
    }
}
