/*
 * Copyright 2015 Focus Technology, Co., Ltd. All rights reserved.
 */
package org.wangwei.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * KryoTest.java
 *
 * @author wangwei-ww
 */
public class HessianKryoTest {
    public static void main(String[] args) throws IOException {
        // Log.TRACE();
        int time = 100000;
        System.out.printf("%s objs Test:\n", time);
        ExecutorService executorService = Executors.newCachedThreadPool();
        HessianKryoTest t = new HessianKryoTest();
        executorService.execute(t.new TestHessian(time));
        executorService.execute(t.new TestKryo(time));
    }

    static public class TestKryoData implements Serializable {
        public String aaa = "aaa";
        // public String bbb = "bbb";
        public String ccc = "ccc";
        // public String ddd = bbb;
        public String ddd = "ddd";
    }

    static public class TestHessainData implements Serializable {
        public String aaa = "aaa";
        // public String bbb = "bbb";
        public String ccc = "ccc";
        // public String ddd = bbb;
        public String ddd = "ddd";
    }

    private class TestHessian implements Runnable {
        int t;

        public TestHessian(int time) {
            t = time;
        }

        public void run() {
            long s = System.currentTimeMillis();
            Hessian2Output output = new Hessian2Output();
            Hessian2Input input = new Hessian2Input();
            for (int i = 0; i < t; i++) {
                try {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    output.init(os);
                    output.writeObject(new TestHessainData());
                    output.flush();
                    // System.out.println("hessian body size :" + os.toByteArray().length);
                    ByteArrayInputStream ios = new ByteArrayInputStream(os.toByteArray());
                    input.resetBuffer();
                    input.init(ios);
                    TestHessainData dataWrapper = (TestHessainData) input.readObject(TestHessainData.class);
                    input.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("hessian cost:" + (System.currentTimeMillis() - s));

        }

    }

    private class TestKryo implements Runnable {
        int t;

        public TestKryo(int time) {
            t = time;
        }

        public void run() {
            long s = System.currentTimeMillis();

            Kryo kryo = new Kryo();
            Output output = new Output(1024, -1);
            Input input = null;
            // kryo.setReferences(false);
            // kryo.setRegistrationRequired(false);
            for (int i = 0; i < t; i++) {
                try {
                    output.clear();
                    kryo.writeObject(output, new TestKryoData());

                    // System.out.println("kryo body size :" + output.toBytes().length);
                    input = new Input(output.getBuffer());
                    TestKryoData dataWrapper = kryo.readObject(input, TestKryoData.class);
                }
                finally {
                    output.close();
                    input.close();
                }
            }

            System.out.println("kryo cost:" + (System.currentTimeMillis() - s));
        }

    }

}
