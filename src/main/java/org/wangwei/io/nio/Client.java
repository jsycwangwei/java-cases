package org.wangwei.io.nio;

public class Client {
    public static void main(String[] args) {
        IOChannelConfig config = new IOChannelConfig();
        config.setIp("127.0.0.1");
        config.setPort(8000);
        new NioClientIOChannel(config).transferRequest(new SecKillRequest());
    }
}
