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
package net.pureessence.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author developer.sid@gmail.com
 *
 */
public class DaemonThreadFactory implements ThreadFactory {
   private AtomicInteger counter;
   
   public DaemonThreadFactory() {
      this.counter=new AtomicInteger(0);
   }
   
   public Thread newThread(Runnable r) {
      Thread thread=new Thread(r);
      
      //if you wanted you can make this class generic by having the constructor take arguments that can be used to configure the following
      thread.setDaemon(true); //need it to be daemon so the JVM will die even if there is a thread running
      thread.setName("Daemon Thread: " + counter.incrementAndGet()); //you don't have to give it a name, but I always do.
      
      return thread;
   }
}
