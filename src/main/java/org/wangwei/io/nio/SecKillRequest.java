package org.wangwei.io.nio;

import java.util.concurrent.atomic.AtomicLong;

public class SecKillRequest {
    public static final int FIX_LEN = 29;
    private static AtomicLong requestIdGenerator = new AtomicLong(0);
    private long prodId;
    private long activityId;
    private int userId;
    private long requestId;
    // 1 client 2 node
    private byte requesterType = 1;

    private byte[] byteArray;

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getProdId() {
        return prodId;
    }

    public void setProdId(long prodId) {
        this.prodId = prodId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public SecKillRequest() {
        requestId = requestIdGenerator.incrementAndGet();
    }

    /**
     * @param type
     * @return
     */
    public byte[] generateRequestStream() {
        byte[] stream = new byte[FIX_LEN];
        byte[] tmp = new byte[8];
        byte[] tmpForInt = new byte[4];
        NumberUtils.long2Byte(tmp, activityId);
        System.arraycopy(tmp, 0, stream, 0, 8);
        NumberUtils.long2Byte(tmp, prodId);
        System.arraycopy(tmp, 0, stream, 8, 8);
        NumberUtils.int2Byte(tmpForInt, userId);
        System.arraycopy(tmpForInt, 0, stream, 16, 4);
        NumberUtils.long2Byte(tmp, requestId);
        System.arraycopy(tmp, 0, stream, 20, 8);
        stream[FIX_LEN - 1] = requesterType;
        byteArray = stream;
        return stream;
    }

    public byte getRequesterType() {
        return requesterType;
    }

    public void setRequesterType(byte requesterType) {
        this.requesterType = requesterType;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public static SecKillRequest deSerialize(byte[] stream) {
        SecKillRequest requst = new SecKillRequest();
        requst.setActivityId(NumberUtils.getLong(stream, 0));
        requst.setProdId(NumberUtils.getLong(stream, 8));
        requst.setUserId(NumberUtils.getInteger(stream, 16));
        requst.setRequestId(NumberUtils.getLong(stream, 20));
        requst.setRequesterType(stream[FIX_LEN - 1]);
        return requst;
    }

    public long getRequestId() {
        return requestId;
    }

    public static void main(String[] args) {
        SecKillRequest req = new SecKillRequest();
        req.setActivityId(123L);
        req.setProdId(1231231L);
        req.setUserId(34423);
        byte[] rs = req.generateRequestStream();
        SecKillRequest tmp = SecKillRequest.deSerialize(rs);
        // resp.deSerialize(rs);

        SecKillResponse resp = SecKillResponse.createTmpSecKillResponse();
        resp.setRequestId(1L);
        resp.setResultCode(312342);
        rs = resp.generateResponseStream();
        resp.deSerialize(rs);
    }
}
