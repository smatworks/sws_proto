/*	
 * $Id$
 * created by    : maninsoft
 * creation-date : 2011. 11. 2.
 * =========================================================
 * Copyright (c) 2011 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.util.id;

import java.io.DataInput;
import java.io.IOException;
import java.io.Serializable;

/**
 * <code>UUID</code> represents a Universally Unique Identifier per IETF
 * Draft specification. For more information regarding the IETF Draft UUID
 * specification
 * <p/>
 * See: http://www.ietf.org/internet-drafts/draft-mealling-uuid-urn-01.txt
 * <p/>
 * This is a lightweight implementation of an UUID, disregarding the different
 * versions of UUIDs.
 */
public class UUID implements UtilConstants, Serializable, Comparable {

	static final long serialVersionUID = 2526142433736157231L;

	/**
	 * the least siginificant 64 bits of the uuid (bytes 8-15)
	 */
	private final long lsb;

	/**
	 * the most siginificant 64 bits of the uuid (bytes 0-7)
	 */
	private final long msb;

	/**
	 * the random number generator
	 */
	private static VersionFourGenerator versionFourGenereator = new VersionFourGenerator();

	/**
	 * Constructs a UUID from a 16 byte array.
	 * 
	 * @param b
	 *            the 16 byte array to construct this UUID from.
	 * @throws IllegalArgumentException
	 *             argument must be 16 bytes
	 */
	public UUID(byte[] b) throws IllegalArgumentException {
		if (b.length != UUID_BYTE_LENGTH) {
			throw new IllegalArgumentException("UUID must be contructed using a 16 byte array.");
		}
		msb = ((((long) b[7]) & 0xFF) + ((((long) b[6]) & 0xFF) << 8) + ((((long) b[5]) & 0xFF) << 16) + ((((long) b[4]) & 0xFF) << 24)
				+ ((((long) b[3]) & 0xFF) << 32) + ((((long) b[2]) & 0xFF) << 40) + ((((long) b[1]) & 0xFF) << 48) + ((((long) b[0]) & 0xFF) << 56));
		lsb = ((((long) b[15]) & 0xFF) + ((((long) b[14]) & 0xFF) << 8) + ((((long) b[13]) & 0xFF) << 16) + ((((long) b[12]) & 0xFF) << 24)
				+ ((((long) b[11]) & 0xFF) << 32) + ((((long) b[10]) & 0xFF) << 40) + ((((long) b[9]) & 0xFF) << 48) + ((((long) b[8]) & 0xFF) << 56));
	}

	/**
	 * Constructs a UUID from a DataInput. Note if 16 bytes are not available
	 * this method will block.
	 * 
	 * @param input
	 *            the datainput with 16 bytes to read in from.
	 * @throws java.io.IOException
	 *             exception if there is an IO problem also argument must
	 *             contain 16 bytes.
	 */
	public UUID(DataInput input) throws IOException {
		msb = input.readLong();
		lsb = input.readLong();
	}

	/**
	 * Constructs a UUID from two long values in most significant byte, and
	 * least significant bytes order.
	 * 
	 * @param mostSignificant
	 *            the most significant 8 bytes of the uuid to be constructed.
	 * @param leastSignificant
	 *            the least significant 8 bytes of the uuid to be constructed.
	 */
	public UUID(long mostSignificant, long leastSignificant) {
		msb = mostSignificant;
		lsb = leastSignificant;
	}

	/**
	 * Constructs a UUID from a UUID formatted String.
	 * 
	 * @param uuidString
	 *            the String representing a UUID to construct this UUID
	 * @throws IllegalArgumentException
	 *             String must be a properly formatted UUID string
	 */
	public UUID(String uuidString) throws IllegalArgumentException {
		// e.g. f81d4fae-7dec-11d0-a765-00a0c91e6bf6
		// 012345678901234567890123456789012345
		int len = uuidString.length();
		if (len != UUID_FORMATTED_LENGTH) {
			throw new IllegalArgumentException();
		}
		long[] words = new long[2];
		int b = 0;
		for (int i = 0; i < UUID_FORMATTED_LENGTH; i++) {
			int c = uuidString.charAt(i) | 0x20; // to lowercase (will lose some
													// error checking)
			if (i == 8 || i == 13 || i == 23) {
				if (c != '-') {
					throw new IllegalArgumentException(String.valueOf(i));
				}
			} else if (i == 18) {
				if (c != '-') {
					throw new IllegalArgumentException(String.valueOf(i));
				}
				b = 1;
			} else {
				byte h = (byte) (c & 0x0f);
				if (c >= 'a' && c <= 'f') {
					h += 9;
				} else if (c < '0' || c > '9') {
					throw new IllegalArgumentException();
				}
				words[b] = words[b] << 4 | h;
			}
		}
		msb = words[0];
		lsb = words[1];
	}

	/**
	 * Parses a string for a UUID.
	 * 
	 * @param uuidString
	 *            the UUID formatted String to parse.
	 * @return Returns a UUID or <code>null</code> if the formatted string could
	 *         not be parsed.
	 * @throws IllegalArgumentException
	 *             the String must be a properly formatted UUID String.
	 */
	public static UUID fromString(String uuidString) throws IllegalArgumentException {
		return new UUID(uuidString);
	}

	/**
	 * Returns a string representation of the UUID.
	 * 
	 * @return a string representation of the UUID formatted according to the
	 *         specification.
	 */
	public String toString() {
		char[] chars = new char[UUID_FORMATTED_LENGTH];
		for (int i = 60, j = 0; i >= 0; i -= 4) {
			chars[j++] = hexDigits[(int) (msb >> i) & 0x0f];
			if (j == 8 || j == 13 || j == 18) {
				chars[j++] = '-';
			}
		}
		for (int i = 60, j = 19; i >= 0; i -= 4) {
			chars[j++] = hexDigits[(int) (lsb >> i) & 0x0f];
			if (j == 23) {
				chars[j++] = '-';
			}
		}
		return new String(chars);
	}

	public String toStringWithoutDash() {
		char[] chars = new char[UUID_FORMATTED_LENGTH - 4];
		int mynum = 0;
		for (int i = 60, j = 0; i >= 0; i -= 4) {
			chars[mynum++] = hexDigits[(int) (msb >> i) & 0x0f];
			j++;
			if (j == 8 || j == 13 || j == 18) {
				// chars[mynum++] = '-';
				j++;
			}
		}
		for (int i = 60, j = 19; i >= 0; i -= 4) {
			chars[mynum++] = hexDigits[(int) (lsb >> i) & 0x0f];
			if (j == 23) {
				// chars[mynum++] = '-';
				j++;
			}
		}
		return new String(chars);
	}

	/**
	 * Compares two UUID for equality.
	 * 
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof UUID) {
			UUID o = (UUID) obj;
			return o.msb == msb && o.lsb == lsb;
		}
		return false;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		int result;
		result = (int) (lsb ^ (lsb >>> 32));
		result = 29 * result + (int) (msb ^ (msb >>> 32));
		return result;
	}

	/**
	 * Compares two UUID's for equality
	 * 
	 * @see Comparable#compareTo(Object)
	 */
	public int compareTo(Object compareTo) throws ClassCastException {
		final UUID u = (UUID) compareTo;
		return (int) (u.msb == msb ? lsb - u.lsb : msb - u.msb);
	}

	/**
	 * Returns the least significant bits stored in the uuid's internal
	 * structure.
	 * 
	 * @return the least significant bits stored in the uuid's internal
	 *         structure.
	 */
	public long getLeastSignificantBits() {
		return lsb;
	}

	/**
	 * Returns the most significant bits stored in the uuid's internal
	 * structure.
	 * 
	 * @return the most significant bits stored in the uuid's internal
	 *         structure.
	 */
	public long getMostSignificantBits() {
		return msb;
	}

	/**
	 * Returns a copy of the byte values contained in this UUID.
	 * 
	 * @return a copy of the byte values contained in this UUID.
	 */
	public byte[] getRawBytes() {
		byte[] b = new byte[UUID_BYTE_LENGTH];
		long n = msb;
		b[7] = (byte) (n);
		n >>>= 8;
		b[6] = (byte) (n);
		n >>>= 8;
		b[5] = (byte) (n);
		n >>>= 8;
		b[4] = (byte) (n);
		n >>>= 8;
		b[3] = (byte) (n);
		n >>>= 8;
		b[2] = (byte) (n);
		n >>>= 8;
		b[1] = (byte) (n);
		n >>>= 8;
		b[0] = (byte) (n);

		n = lsb;
		b[15] = (byte) (n);
		n >>>= 8;
		b[14] = (byte) (n);
		n >>>= 8;
		b[13] = (byte) (n);
		n >>>= 8;
		b[12] = (byte) (n);
		n >>>= 8;
		b[11] = (byte) (n);
		n >>>= 8;
		b[10] = (byte) (n);
		n >>>= 8;
		b[9] = (byte) (n);
		n >>>= 8;
		b[8] = (byte) (n);

		return b;
	}

	/**
	 * Returns a new version 4 UUID, based upon Random bits.
	 * 
	 * @return a new version 4 UUID, based upon Random bits.
	 */
	public static UUID randomUUID() {
		return (UUID) versionFourGenereator.nextIdentifier();
	}

}
