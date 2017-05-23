package temps.bigInteger;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * <b>����Ӧ�ý��������ĸ���ģ�ͣ�Ȼ����ǽ����������ݽṹ���ӿڣ���Ȼ���ٽ�����������ݽṹ�������ӿ�ʱ����Ҫ˼��������ʵ���������� <b/>
 * 
 * ֻ֧�������� ��ģ����������û�п��ǵ�����ģ�͵ı߽��Ժ������ǲ�һ�µģ�ֻ�ܱ�ʾ��λΪ{0....9}�����������һ����ڣ� 0000���������ݣ���
 * 
 * ����Ԫ���趨�����ּ��ԺͿ�����ԣ�����ϳɸ����Ĳ����͹��ܣ� ��ͬ��Ƽ����ָ���
 * 
 * ���㲻������������ļ��Ը���Ķ����ʵ��(�ڲ��ṹ��ƣ�)
 * 
 * ˼ά���Ͻ����������⣩������ԣ�д������Ķ�һ�����ʮ�ֵ��б�Ҫ���ٲ��Ժ͵��ԵĴ��ۡ������ٵͼ���������Ƶ�ʣ���:
 * ������������д����ʱ�ز����٣���ʹʹ����˼������ƺ�coding�����ķ�ʽ��
 * 
 * 
 * �ܽ᣺ ��ģ�Ƚ�����ͬʱ�����ڸ������⣡��===>ֻ֧�������ļ��㣡��Ȼ����ȷ�ģ������ڽ�ģ֮����û��֤�����ģ���ǿ��õġ�
 * 
 * @throws IndexOutOfBoundsException
 *             (����Ҫע�ͣ� ��Ϊ�ڶ��巶Χ�ڣ���Ȼ�׳�����쳣����Ȼ֮�£�����˵��)
 * @author Administrator
 *
 */
public class BigInteger {
	public static enum Endian {
		BigEndian, SmallEndian;
	}

	private List<Byte> value = Lists.newArrayList(); // ��λ��ǰ��byte in {0,1,,,9}

	/**
	 * @throws IllegalArgumentException
	 * @param initBigInteger
	 */
	public BigInteger(String initBigInteger) {
		List<Character> charactorList = getCharacterList(initBigInteger);
		validateBound(charactorList);
		for (Character any : charactorList) {
			getValue().add(charToByte(any.charValue()));
		}
	}

	private List<Byte> getValue() {
		return value;
	}

	private void setValue(List<Byte> value) {
		this.value = value;
	}

	private List<Character> getCharacterList(String initBigInteger) {
		if (initBigInteger == null || initBigInteger.length() == 0) {
			throw new IllegalArgumentException(
					"initBigInteger must have length!");
		}
		List<Character> result = Lists.newArrayList();
		int maxIndex = initBigInteger.length() - 1;
		for (int i = 0; i <= maxIndex; i++) {
			Character charValue = initBigInteger.charAt(i);
			result.add(charValue);
		}

		return result;
	}

	private void validateBound(List<Character> charactorList) {
		for (Character any : charactorList) {
			char value = any.charValue();
			char biggest = '9';
			char smallest = '0';
			if (value < smallest || value > biggest) {
				throw new IllegalArgumentException(
						"char is out of bound {0....9}");
			}
		}
	}

	/**
	 * @throws IllegalArgument
	 * @param initBigInteger
	 */
	public BigInteger(BigInteger initBigInteger) {
		if (initBigInteger == null) {
			throw new IllegalArgumentException("initBigInteger can't be null!");
		}

		String strBigInteger = initBigInteger.toString();
		List<Character> charactorList = getCharacterList(strBigInteger);

		for (Character any : charactorList) {
			getValue().add(new Byte(charToByte(any))); // ���㲻�������ͼ�
		}
	}

	/**
	 * @throws IllegalArgument
	 * @param initBigInteger
	 * @param endian
	 */
	public BigInteger(byte[] initBigInteger, Endian endian) {
		List<Character> charValue = Lists.newArrayList();
		List<Byte> byteValue = Lists.newArrayList();
		for (int i = 0; i < initBigInteger.length; i++) {
			charValue
					.add(new Character((char) ('0' + (char) initBigInteger[i])));
			byteValue.add(initBigInteger[i]);
		}

		validateBound(charValue);

		List<Byte> bigEndianValue = Lists.newArrayList(byteValue);
		if (endian.equals(Endian.BigEndian)) {
			setValue(bigEndianValue);
		} else {
			Collections.reverse(bigEndianValue); // smallEndian
			setValue(bigEndianValue);
		}

	}

	/**
	 * @throws IllegalArgument
	 * @param initBigInteger
	 */
	public BigInteger(byte[] initBigInteger) {
		this(initBigInteger, Endian.BigEndian);

	}

	public BigInteger add(BigInteger bigInterger) {
		int byteCount = value.size();

		List<Byte> toBeAdded = getByteListValue(bigInterger);
		int toAddedByteCount = toBeAdded.size();

		int counting = 0;
		for (int i = (byteCount - 1) - counting; i >= 0; i--) {
			Byte toAdded = toBeAdded.get((toAddedByteCount - 1) - counting);
			add(toAdded, i);
			// counting++; ˼ά���Ͻ�������ԣ�д������Ķ�һ�����ʮ�ֵ��б�Ҫ���ٲ��Ժ͵��ԵĴ��ۡ������ٵͼ���������Ƶ�ʣ���
			counting++;
		}
		return this;
	}

	/**
	 * i == -1 ��ʾvalue��λ������Ҫ��valueǰ�߲���һλ���ݣ�
	 * 
	 * @throws IndexOutOfBoundsException
	 *             (����Ҫע�ͣ� ��Ϊ�ڶ��巶Χ�ڣ���Ȼ�׳�����쳣����Ȼ֮�£�����˵��)
	 * @param toAdded
	 * @param i
	 *            index of value(list)
	 */
	private void add(Byte toAdded, int i) {
		if (i == -1) {
			value.add(0, toAdded);
		} else {
			Byte ivalue = value.get(i);
			Byte result = (byte) (toAdded.byteValue() + ivalue.byteValue()); // 0
																				// ....
																				// 18

			boolean upScale = false;
			if (result.byteValue() > 9) { // 0 && 9 ������Ϊ������г������Ͳ���Ҫд�ܶ��ע���ˣ����ڱ߽��
											// ��
				upScale = true;
			}
			byte cur = (byte) (upScale ? (result.byteValue() - 10) : result
					.byteValue());
			value.set(i, cur);
			if (upScale) {
				add((byte) 1, i - 1);
			}
		}

	}

	private List<Byte> getByteListValue(BigInteger bigInterger) {
		String strBigInteger = bigInterger.toString();
		List<Character> charactorList = getCharacterList(strBigInteger);

		List<Byte> result = Lists.newArrayList();
		for (Character any : charactorList) {
			byte byteValue = charToByte(any);
			result.add(byteValue);
		}

		return result;
	}

	private byte charToByte(Character singlenumber) {
		return (byte) (singlenumber - '0');
	}

	@Override
	public String toString() {
		String strValue = "";
		for (Byte any : value) {
			strValue += any.toString();
			// strValue += '0' +any.toString();
		}
		return strValue;
	}

	public byte[] byteValue(Endian endian) {
		List<Byte> bigEndian = Lists.newArrayList();
		if (endian.equals(Endian.BigEndian)) {
			bigEndian = getValue();
		} else {
			bigEndian.addAll(value);
			Collections.reverse(bigEndian);
		}

		byte[] result = new byte[bigEndian.size()];
		for (int i = 0; i < bigEndian.size(); i++) {
			result[i] = bigEndian.get(i);
		}

		return result;
	}
}
