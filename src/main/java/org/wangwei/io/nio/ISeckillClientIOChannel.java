package org.wangwei.io.nio;

public interface ISeckillClientIOChannel {
    SecKillResponse transferRequest(SecKillRequest request);

    void close();
}
