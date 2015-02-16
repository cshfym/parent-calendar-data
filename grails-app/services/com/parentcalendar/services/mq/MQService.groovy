package com.parentcalendar.services.mq

import io.iron.ironmq.Client
import io.iron.ironmq.Cloud
import io.iron.ironmq.Queue
import org.springframework.stereotype.Service

@Service
class MQService {

  Client client

  public MQService() { }

  public MQService(String projectId, String token) {
    client = new Client(projectId, token, Cloud.ironAWSUSEast)
  }

  /**
   * Pushes the message parameter on the queue specified by queueName. If the queueName specified does not exist, it will be created.
   * @param message
   * @param queueName
   * @throws IOException
   */
  public void pushMessage(String message, String queueName) throws IOException {

    if (!client) {
      throw new RuntimeException("IronMQ client is null; check configuratoin.");
    }

    Queue queue = client.queue(queueName);

    try {
      queue.push(message);
    } catch (IOException ex) {
      throw ex;
    }
  }
}
