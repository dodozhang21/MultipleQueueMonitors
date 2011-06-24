/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.pureessence.worker;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author developer.sid@gmail.com
 *
 */
public class Worker implements Runnable {
   private BlockingQueue<String> finishedQueue;
   private String result;
   private long sleepTime;
   
   public Worker(BlockingQueue<String> finishedQueue, String result, long sleepTime) {
      this.finishedQueue=finishedQueue;
      this.result=result;
      this.sleepTime=sleepTime;
   }
   
   public void run() {
      try {
         TimeUnit.SECONDS.sleep(sleepTime);
         finishedQueue.offer(result); //you have to use offer to get this queue to work.  It will throw an exception if there is something in the queue.
      } catch(InterruptedException e) {
         e.printStackTrace();
      }
   }
}
