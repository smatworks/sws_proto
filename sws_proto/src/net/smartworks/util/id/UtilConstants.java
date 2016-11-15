/*	
 * $Id$
 * created by    : maninsoft
 * creation-date : 2011. 11. 2.
 * =========================================================
 * Copyright (c) 2011 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.util.id;

public interface UtilConstants {

	// ** Magic number constants
	/**
	 * Bits in a UUID.
	 */
	int UUID_BIT_LENGTH = 128;

	/**
	 * Number of bytes in a UUID.
	 */
	int UUID_BYTE_LENGTH = 16;

	// ** Formatting and validation constants
	/**
	 * Chars in a UUID String.
	 */
	int UUID_UNFORMATTED_LENGTH = 32;

	/**
	 * Chars in a UUID String.
	 */
	int UUID_FORMATTED_LENGTH = 36;

	/**
	 * Token length of '-' separated tokens.
	 */
	int TOKENS_IN_UUID = 5;

	/**
	 * Array to check tokenized UUID's segment lengths
	 */
	int[] TOKEN_LENGTHS = { 8, 4, 4, 4, 12 };

	/**
	 * Insertion point 1 for dashes in the string format
	 */
	int FORMAT_POSITION1 = 8;

	/**
	 * Insertion point 2 for dashes in the string format
	 */
	int FORMAT_POSITION2 = 13;

	/**
	 * Insertion point 3 for dashes in the string format
	 */
	int FORMAT_POSITION3 = 18;

	/**
	 * Insertion point 4 for dashes in the string format
	 */
	int FORMAT_POSITION4 = 23;

	/**
	 * The string prefix for a urn UUID identifier.
	 */
	String URN_PREFIX = "urn:uuid:";

	// ** UUID Variant QueryConstants
	/**
	 * UUID variant bits described in the IETF Draft MSB order, this is the
	 * "Reserved, NCS backward compatibility field" 0 x x with unknown bits as 0
	 */
	int VARIANT_NCS_COMPAT = 0;

	/**
	 * UUID variant bits described in the IETF Draft MSB order, this is the IETF
	 * Draft memo variant field 1 0 x with unknown bits as 0
	 */
	int VARIANT_IETF_DRAFT = 2;

	/**
	 * UUID variant bits described in the IETF Draft MSB order, this is the IETF
	 * Draft "Microsoft Corporation" field variant 1 1 0 x with unknown bits as
	 * 0
	 */
	int VARIANT_MS = (byte) 6;

	/**
	 * UUID variant bits described in the IETF Draft MSB order, this is the
	 * "Future Reserved variant 1 1 1 x with unknown bits as 0
	 */
	int VARIANT_FUTURE = 7;

	// ** UUID Version QueryConstants
	/**
	 * Version one constant for UUID version one of four
	 */
	int VERSION_ONE = 1;

	/**
	 * Version two constant for UUID version two of four
	 */
	int VERSION_TWO = 2;

	/**
	 * Version three constant for UUID version three of four
	 */
	int VERSION_THREE = 3;

	/**
	 * Version four constant for UUID version four of four
	 */
	int VERSION_FOUR = 4;

	// ** Exception message constants
	/**
	 * Message indicating this is not a version one UUID
	 */
	String WRONG_VAR_VER_MSG = "Not a ietf variant 2 or version 1 (time-based UUID)";

	// ** Array positions and lengths of UUID fields ** //
	/**
	 * Byte length of time low field
	 */
	int TIME_LOW_BYTE_LEN = 4;
	/**
	 * Byte length of time low field
	 */
	int TIME_MID_BYTE_LEN = 2;
	/**
	 * Byte length of time low field
	 */
	int TIME_HI_BYTE_LEN = 2;
	/**
	 * Timestamp byte[] position of time low field
	 */
	int TIME_LOW_TS_POS = 4;
	/**
	 * Timestamp byte[] position mid field
	 */
	int TIME_MID_TS_POS = 2;
	/**
	 * Timestamp byte[] position hi field
	 */
	int TIME_HI_TS_POS = 0;
	/**
	 * uuid array position start of time low field
	 */
	int TIME_LOW_START_POS = 0;
	/**
	 * uuid array position start of mid field
	 */
	int TIME_MID_START_POS = 4;
	/**
	 * uuid array position start of hi field
	 */
	int TIME_HI_START_POS = 6;
	/**
	 * Byte position of the clock sequence and reserved field
	 */
	short TIME_HI_AND_VERSION_BYTE_6 = 6;
	/**
	 * Byte position of the clock sequence and reserved field
	 */
	short CLOCK_SEQ_HI_AND_RESERVED_BYTE_8 = 8;

	/**
	 * XXX added by stefan@apache.org: hexdigits for converting numerics to hex
	 */
	char[] hexDigits = "0123456789abcdef".toCharArray();

}