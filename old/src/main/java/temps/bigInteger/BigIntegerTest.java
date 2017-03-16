package temps.bigInteger;

import temps.bigInteger.BigInteger.Endian;

public class BigIntegerTest {
	public static void main(String[] args) {
		String aBigInteger = "395";
		String bBigInteger = "395";
		BigInteger bigInterger = new BigInteger(aBigInteger);
		BigInteger bbigInterger = new BigInteger(bBigInteger);
		System.out.println(bigInterger.toString());
		System.out.println(bigInterger.byteValue(Endian.BigEndian));
		System.out.println(bigInterger.byteValue(Endian.SmallEndian));

		bigInterger.add(bbigInterger);
		System.out.println(bigInterger.toString());
		System.out.println(bigInterger.byteValue(Endian.BigEndian));
		System.out.println(bigInterger.byteValue(Endian.SmallEndian));
		
	}
}
