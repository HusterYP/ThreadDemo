import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 实现生产者和消费者
 * 管道输入输出流PipedInputStream和PipedOutputStream实现
 * 只能适用于一个生产者和一个消费者的时候, 参见ReadMe中博客链接
 */
public class ConsumerAndProducer5 {
    private PipedInputStream pis = new PipedInputStream();
    private PipedOutputStream pos = new PipedOutputStream();

    {
        try {
            pis.connect(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsumerAndProducer5 consumerAndProducer5 = new ConsumerAndProducer5();
        new Thread(consumerAndProducer5.new Producer()).start();
        new Thread(consumerAndProducer5.new Consumer()).start();
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(1000);
                    int num = (int) (Math.random() * 100);
                    System.out.println("生产者生产, 生产数字为: " + num);
                    pos.write(num);
                    pos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    pis.close();
                    pos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(1000);
                    int num = pis.read();
                    System.out.println("消费者消费, 消费数字为: " + num);
                }
            } catch (Exception e) {
            } finally {
                try {
                    pis.close();
                    pos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
