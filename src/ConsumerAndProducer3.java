import java.util.concurrent.ArrayBlockingQueue;

/**
 * 实现生产者和消费者
 * 阻塞队列BlockingQueue实现
 */
public class ConsumerAndProducer3 {

    private ArrayBlockingQueue integerBlockingDeque = new ArrayBlockingQueue<Integer>(10);

    public static void main(String[] args) {
        ConsumerAndProducer3 consumerAndProducer3 = new ConsumerAndProducer3();
        new Thread(consumerAndProducer3.new Producer()).start();
        new Thread(consumerAndProducer3.new Consumer()).start();
        new Thread(consumerAndProducer3.new Producer()).start();
        new Thread(consumerAndProducer3.new Consumer()).start();
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

                try {
                    integerBlockingDeque.put(1);
                    System.out.println(Thread.currentThread().getName() + "--生产者生产, 当前总量: " + integerBlockingDeque.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

                try {
                    integerBlockingDeque.take();
                    System.out.println(Thread.currentThread().getName() + "--消费者消费, 当前余量: " + integerBlockingDeque.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
