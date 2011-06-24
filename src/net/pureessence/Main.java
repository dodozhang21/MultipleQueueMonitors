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
package net.pureessence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

import net.pureessence.factory.DaemonThreadFactory;
import net.pureessence.worker.Worker;

/**
 * @author developer.sid@gmail.com
 *
 */
public class Main {
   public static void main(String[] args) throws InterruptedException {
      ExecutorService service=Executors.newFixedThreadPool(2, new DaemonThreadFactory());
      SynchronousQueue<String> queue=new SynchronousQueue<String>(); //this queue can hold 1 element at a time, so basically the first thread to finish will be the one to successfully put the element in the queue
      //create the runnables before hand so that extra time isn't spent instantiating the runnables at submission time.
      Runnable runnable1=new Worker(queue, "Runnable 1", 4);
      Runnable runnable2=new Worker(queue, "Runnable 2", 3);
      
      service.execute(runnable1);
      service.execute(runnable2);
      
      System.out.println("before queue");
      System.out.println(queue.take());
      System.out.println("after queue");
      
      service.shutdown(); 
   }
}
