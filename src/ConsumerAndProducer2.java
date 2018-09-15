import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 实现生产者和消费者
 * 使用可重入锁ReentrantLock实现
 */
public class ConsumerAndProducer2 {
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    private int curCount = 0;
    private final int FULL = 10;

    public static void main(String[] args) {
        ConsumerAndProducer2 consumerAndProducer2 = new ConsumerAndProducer2();
        new Thread(consumerAndProducer2.new Producer()).start();
        new Thread(consumerAndProducer2.new Producer()).start();
//        new Thread(consumerAndProducer2.new Consumer()).start();
        new Thread(consumerAndProducer2.new Consumer()).start();
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                try {
                    while (curCount >= FULL) {
                        notFull.wait();
                    }
                    curCount++;
                    System.out.println(Thread.currentThread().getName() + "--生产者生产, 当前总量: " + curCount);
                    notEmpty.signal();
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                try {
                    while (curCount <= 0) {
                        notEmpty.wait();
                    }
                    curCount--;
                    System.out.println(Thread.currentThread().getName() + "--消费者消费, 当前剩余: " + curCount);
                    notFull.signal();
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
