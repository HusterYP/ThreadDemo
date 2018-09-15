/***
 * 实现生产者和消费者
 * 使用wait()和notify()实现
 */
public class ConsumerAndProducer1 {

    private static final Object LOCK = new Object();
    private static final int FULL = 10;
    private static int curCount = 0;

    public static void main(String[] args) {
        ConsumerAndProducer1 consumerAndProducer1 = new ConsumerAndProducer1();
        new Thread(consumerAndProducer1.new Producer()).start();
        new Thread(consumerAndProducer1.new Producer()).start();
        new Thread(consumerAndProducer1.new Consumer()).start();
        new Thread(consumerAndProducer1.new Consumer()).start();
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

                synchronized (LOCK) {
                    while (curCount >= FULL) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    curCount++;
                    System.out.println(Thread.currentThread().getName() + ": 生产者生产, 目前总共有: " + curCount);
                    LOCK.notifyAll();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (LOCK) {
                    while (curCount <= 0) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    curCount--;
                    System.out.println(Thread.currentThread().getName() + ": 消费者消费, 目前剩余: " + curCount);
                    LOCK.notifyAll();
                }
            }
        }
    }
}
