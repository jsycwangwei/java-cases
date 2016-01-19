package org.wangwei.io.nio;

public class NumberUtils {
	public static long getLong(byte[] bb) {
		return ((((long) bb[0] & 0xff) << 56) | (((long) bb[1] & 0xff) << 48) | (((long) bb[2] & 0xff) << 40)
				| (((long) bb[3] & 0xff) << 32) | (((long) bb[4] & 0xff) << 24) | (((long) bb[5] & 0xff) << 16)
				| (((long) bb[6] & 0xff) << 8) | (((long) bb[7] & 0xff) << 0));
	}

	public static long getLong(byte[] bb, int pos) {
		return ((((long) bb[pos] & 0xff) << 56) | (((long) bb[pos + 1] & 0xff) << 48)
				| (((long) bb[pos + 2] & 0xff) << 40) | (((long) bb[pos + 3] & 0xff) << 32)
				| (((long) bb[pos + 4] & 0xff) << 24) | (((long) bb[pos + 5] & 0xff) << 16)
				| (((long) bb[pos + 6] & 0xff) << 8) | (((long) bb[pos + 7] & 0xff) << 0));
	}

	public static int getInteger(byte[] bb) {
		return ((((int) bb[0] & 0xff) << 24) | (((int) bb[1] & 0xff) << 16) | (((int) bb[2] & 0xff) << 8)
				| (((int) bb[3] & 0xff) << 0));
	}

	public static int getInteger(byte[] bb, int pos) {
		return ((((int) bb[pos] & 0xff) << 24) | (((int) bb[pos + 1] & 0xff) << 16) | (((int) bb[pos + 2] & 0xff) << 8)
				| (((int) bb[pos + 3] & 0xff) << 0));
	}

	public static void long2Byte(byte[] bb, long x) {
		bb[0] = (byte) (x >> 56);
		bb[1] = (byte) (x >> 48);
		bb[2] = (byte) (x >> 40);
		bb[3] = (byte) (x >> 32);
		bb[4] = (byte) (x >> 24);
		bb[5] = (byte) (x >> 16);
		bb[6] = (byte) (x >> 8);
		bb[7] = (byte) (x >> 0);
	}

	public static void int2Byte(byte[] bb, int x) {
		bb[0] = (byte) (x >> 24);
		bb[1] = (byte) (x >> 16);
		bb[2] = (byte) (x >> 8);
		bb[3] = (byte) (x >> 0);
	}
}
