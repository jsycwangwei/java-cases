package org.wangwei.io.nio;

public class SecKillResponse {
    public static final int FIX_LEN = 12;
    private int resultCode;
    public static final int LENGTH_RESPONSE = 12;

    public static final int RESPONSE_CODE_IOFAIL = -1;
    public static final int RESPONSE_CODE_PENDING = 0;
    public static final int RESPONSE_CODE_SUCCESS = 1;
    public static final int RESPONSE_CODE_NON_SECKILL = 2;
    public static final int RESPONSE_CODE_FAIL = -2;
    public static final int RESPONSE_CODE_SEVER_INTER_ERROR = -3;
    public static final int RESPONSE_CODE_NODE_ISNOT_MASTER = -4;

    private int readTimeout = 5000;
    private long requestId;
    private byte[] byteArray;

    public SecKillResponse(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public SecKillResponse() {

    }

    public static SecKillResponse createTmpSecKillResponse() {
        return new SecKillResponse();
    }

    public int getResultCode() {
        // if (resultCode == RESPONSE_CODE_PENDING) {
        // synchronized (this) {
        // try {
        // wait(readTimeout);
        // } catch (InterruptedException e) {
        // return RESPONSE_CODE_IOFAIL;
        // }
        // }
        // }
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
        // synchronized (this) {
        // notifyAll();
        // }
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public byte[] generateResponseStream() {
        byte[] stream = new byte[12];
        byte[] tmp = new byte[8];
        NumberUtils.long2Byte(tmp, requestId);
        System.arraycopy(tmp, 0, stream, 0, 8);
        NumberUtils.long2Byte(tmp, resultCode);
        System.arraycopy(tmp, 4, stream, 8, 4);
        byteArray = stream;
        return stream;
    }

    public void deSerialize(byte[] stream) {
        requestId = NumberUtils.getLong(stream);
        resultCode = NumberUtils.getInteger(stream, 8);
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    @Override
    public String toString() {
        return "SecKillResponse [resultCode=" + resultCode + ", requestId=" + requestId + "]";
    }

}
