package org.wangwei.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NioClientIOChannel implements ISeckillClientIOChannel {
    private static Logger log = LoggerFactory.getLogger(NioClientIOChannel.class);
    private IOChannelConfig config;
    private SocketChannel sockChannel;
    private Selector selector;
    private static int BLOCK_SIZE = 512;
    private Map<Long, String> responseMap = new ConcurrentHashMap<Long, String>();
    private BlockingQueue<SecKillRequest> reqQueue = new LinkedBlockingDeque<>();
    private ExecutorService executor = Executors.newCachedThreadPool();

    public NioClientIOChannel(IOChannelConfig config) {
        this.config = config;
        try {
            sockChannel = this.initiateConnection();
            Thread mainThread = new Thread() {
                @Override
                public void run() {
                    startChannel();
                }
            };
            mainThread.start();
        }
        catch (IOException ex) {
            log.error("NioClientIOChannel", ex);
        }
    }

    private void startChannel() {
        Set<SelectionKey> selectionKeys;
        Iterator<SelectionKey> iterator;
        while (true) {
            try {
                selector.select();
                selectionKeys = selector.selectedKeys();
                iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    final SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isConnectable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        if (client.isConnectionPending()) {
                            client.finishConnect();
                        }
                        send(selectionKey);
                        // client.write(ByteBuffer.wrap(new String("123").getBytes()));
                        client.register(selector, SelectionKey.OP_READ);
                        // client.register(selector, SelectionKey.OP_WRITE);
                    }
                    else if (selectionKey.isReadable()) {
                        read(selectionKey);
                    }
                }
                selectionKeys.clear();
            }
            catch (Exception e) {
                log.error("NioClientIOChannel::start", e);
                close();
                break;
            }
        }
    }

    private void send(final SelectionKey selectionKey) throws InterruptedException {
        final ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK_SIZE);
        final SecKillRequest req = reqQueue.take();
        final String response = responseMap.get(req.getRequestId());
        final SocketChannel client = (SocketChannel) selectionKey.channel();
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                try {
                    sendRequest(selectionKey, sendbuffer, req);
                    // client.register(selector, SelectionKey.OP_WRITE);
                }
                catch (Exception e) {
                    // response.setResultCode(SecKillResponse.RESPONSE_CODE_IOFAIL);
                    close();
                }
            }
        };
        executor.submit(runable);
    }

    private void read(final SelectionKey selectionKey) throws IOException {
        final ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK_SIZE);
        final SocketChannel client = (SocketChannel) selectionKey.channel();
        final int count = client.read(receivebuffer);
        if (count > 0) {
            Runnable runable = new Runnable() {
                @Override
                public void run() {
                    try {
                        parseResponse(receivebuffer, selectionKey, client, count);
                        // client.register(selector, SelectionKey.OP_READ);
                        client.register(selector, SelectionKey.OP_WRITE);
                    }
                    catch (ClosedChannelException e) {
                        close();
                    }
                }
            };
            executor.submit(runable);
        }
    }

    private void sendRequest(final SelectionKey selectionKey, ByteBuffer sendbuffer, SecKillRequest req)
            throws IOException {
        SocketChannel client = (SocketChannel) selectionKey.channel();
        // sendbuffer.put(req.generateRequestStream());
        sendbuffer.put(new String("100").getBytes());
        sendbuffer.flip();
        client.write(sendbuffer);
    }

    private void parseResponse(ByteBuffer receivebuffer, SelectionKey selectionKey, SocketChannel client, int count)
            throws ClosedChannelException {

        if (count == SecKillResponse.LENGTH_RESPONSE) {
            byte[] readBytes = new byte[count];
            receivebuffer.get(readBytes);
            deSerialize(readBytes);
        }
        else {
            byte[] preBytes = (byte[]) selectionKey.attachment();
            byte[] curBytes = new byte[count];
            receivebuffer.get(curBytes);
            if (preBytes != null) {
                byte[] tmpBytes = new byte[preBytes.length + count];
                System.arraycopy(preBytes, 0, tmpBytes, 0, preBytes.length);
                System.arraycopy(curBytes, 0, tmpBytes, preBytes.length, curBytes.length);
                if (count + preBytes.length < SecKillResponse.LENGTH_RESPONSE) {
                    selectionKey.attach(tmpBytes);
                }
                else {
                    this.deSerialize(tmpBytes);
                }
            }
            else {
                preBytes = new byte[count];
                selectionKey.attach(preBytes);
            }
        }
    }

    private void deSerialize(byte[] readBytes) {
        SecKillResponse tmpResponse = SecKillResponse.createTmpSecKillResponse();
        tmpResponse.deSerialize(readBytes);
        responseMap.get(tmpResponse.getRequestId());
        responseMap.remove(tmpResponse.getRequestId());
    }

    private SocketChannel initiateConnection() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.socket().setTcpNoDelay(true);
        socketChannel.configureBlocking(false);
        selector = this.initSelector();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(config.getIp(), config.getPort()));
        return socketChannel;
    }

    private Selector initSelector() throws IOException {
        return SelectorProvider.provider().openSelector();
    }

    public void close() {
        responseMap.clear();
        if (sockChannel != null) {
            try {
                sockChannel.close();
            }
            catch (IOException ex) {
                log.error("close", ex);
            }
        }
    }

    @Override
    public SecKillResponse transferRequest(SecKillRequest request) {
        SecKillResponse response = new SecKillResponse(this.config.getReadTimeout());
        if (!this.sockChannel.isOpen()) {
            response.setResultCode(SecKillResponse.RESPONSE_CODE_IOFAIL);
        }
        else {
            reqQueue.add(request);
        }
        responseMap.put(request.getRequestId(), "100");
        return response;
    }

}
