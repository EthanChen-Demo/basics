package temps.bigInteger;

import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

/**
 * <b>首先应该建立完整的概念模型，然后才是建立抽象数据结构（接口），然后再建立具体的数据结构，构建接口时，需要思考依赖的实现条件！！ <b/>
 * 
 * 只支持正数： 建模不完整——没有考虑到这种模型的边界性和需求是不一致的（只能表示数位为{0....9}的正数！并且还存在： 0000这样的数据！）
 * 
 * 计算元的设定：保持简单性和可组合性（能组合成更过的操作和功能： 如同设计计算机指令！）
 * 
 * 计算不够独立：计算的简单性概念的定义和实现(内部结构设计：)
 * 
 * 思维不严谨（经验问题）：相关性：写完后再阅读一遍代码十分的有必要减少测试和调试的代价——减少低级错误发生的频率！！:
 * 检查这个步骤在写代码时必不可少（即使使用了思考、设计和coding独立的方式）
 * 
 * 
 * 总结： 建模比较慢，同时还存在各种问题！！===>只支持正数的计算！虽然是正确的，但是在建模之初并没有证明这个模型是可用的。
 * 
 * @throws IndexOutOfBoundsException
 *             (不需要注释： 因为在定义范围内，必然抛出这个异常。必然之事，不必说明)
 * @author Administrator
 *
 */
public class BigInteger {
	public static enum Endian {
		BigEndian, SmallEndian;
	}

	private List<Byte> value = Lists.newArrayList(); // 高位在前；byte in {0,1,,,9}

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

	@NotNull
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
			getValue().add(new Byte(charToByte(any))); // 计算不够独立和简单
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
			// counting++; 思维不严谨：相关性：写完后再阅读一遍代码十分的有必要减少测试和调试的代价——减少低级错误发生的频率！！
			counting++;
		}
		return this;
	}

	/**
	 * i == -1 表示value进位（即需要在value前边插入一位数据）
	 * 
	 * @throws IndexOutOfBoundsException
	 *             (不需要注释： 因为在定义范围内，必然抛出这个异常。必然之事，不必说明)
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
			if (result.byteValue() > 9) { // 0 && 9 可以作为概念抽闲出来！就不需要写很多的注视了（关于边界的
											// ）
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
