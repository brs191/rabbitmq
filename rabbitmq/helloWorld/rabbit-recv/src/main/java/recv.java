import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class recv {

  private final static String QUEUE_NAME = "rajaQ";

  public static void main (String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println("[*] Waiting for messages. To exit press Ctrl-C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        try {
          String message = new String(body, "UTF-8");
          System.out.println(" [x] Received '" + message + "'");
        } catch (UnsupportedEncodingException ex) {
          System.out.println("Caught exception  " + ex.toString());
        }
      }
    };

    channel.basicConsume(QUEUE_NAME, true, consumer);
  }
}
