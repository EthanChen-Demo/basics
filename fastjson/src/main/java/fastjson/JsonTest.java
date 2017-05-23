package fastjson;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 
 * untested
 * 
 * @author ethan
 *
 */
public class JsonTest {
	@Test
	public void test() {
		// String jsonSting = "[+
		// {
		// "num": {
		// "name": "哪些城市来合肥看车的人最多",
		// "data": [
		// 6.42,
		// 6.23,
		// 6.14,
		// 5.86,
		// 5.44,
		// 4.69,
		// 4.5,
		// 4.31,
		// 3.33,
		// 3
		// ]
		// },
		// "num2": {
		// "name": "来看我店铺车的人所在城市占比",
		// "data": [
		// 11.11,
		// 2.78,
		// 8.33,
		// 5.56,
		// 0,
		// 8.33,
		// 5.56,
		// 2.78,
		// 5.56,
		// 2.78
		// ]
		// },
		// "category": [
		// "六安",
		// "滁州",
		// "安庆",
		// "宿州",
		// "亳州",
		// "淮南",
		// "蚌埠",
		// "阜阳",
		// "芜湖",
		// "淮北"
		// ]
		// },
		// {
		// "num": {
		// "name": "来合肥看车的外地用户都爱看什么车",
		// "data": [
		// 2.39,
		// 2.3,
		// 2.2,
		// 1.78,
		// 1.73,
		// 1.5,
		// 1.36,
		// 1.27,
		// 1.22,
		// 1.12
		// ]
		// },
		// "num2": {
		// "name": "来我这里看车的外地用户都爱看什么车",
		// "data": [
		// 0,
		// 0,
		// 0,
		// 2.78,
		// 0,
		// 0,
		// 0,
		// 0,
		// 0,
		// 0
		// ]
		// },
		// "category": [
		// "宝马5系",
		// "奥迪A6L",
		// "POLO",
		// "思域",
		// "科鲁兹",
		// "比亚迪F0",
		// "福克斯",
		// "帕萨特",
		// "奥迪A4L",
		// "高尔夫"
		// ]
		// },
		// [
		// {
		// "city": "六安",
		// "list": [
		// {
		// "num": 1,
		// "name": "科鲁兹",
		// "clue": 8.03,
		// "onsale": 2.94,
		// "cluedealer": 0,
		// "onsaledealer": 3.66
		// },
		// {
		// "num": 2,
		// "name": "POLO",
		// "clue": 4.38,
		// "onsale": 1.47,
		// "cluedealer": 0,
		// "onsaledealer": 0.73
		// }
		// ]
		// },
		// {
		// "city": "滁州",
		// "list": [
		// {
		// "num": 1,
		// "name": "朗动",
		// "clue": 3.76,
		// "onsale": 0.75,
		// "cluedealer": 0,
		// "onsaledealer": 0
		// },
		// {
		// "num": 2,
		// "name": "宝马X5",
		// "clue": 3.76,
		// "onsale": 0.46,
		// "cluedealer": 2.78,
		// "onsaledealer": 1.39
		// }
		// ]
		// }
		// ],
		// [ ]

	}

	@Test
	public void testJsonArray() {

		String userInfo = "[{	\"num\":{\"name\":\"name\",\"data\":\"john\"},\"num2\":{\"name\":\"name\",\"data\":\"john2\"},\"catetory\":[1,2]},{	\"num\":{\"name\":\"name\",\"data\":\"merry\"},\"num2\":{\"name\":\"name\",\"data\":\"merry2\"},\"catetory\":[1,2]},[1,2]]";
		JSONArray userJB = JSON.parseArray(userInfo);
		// 通过正常的方式可以获取指定 键的值
		System.out.println("fromCity: " + userJB.get(1));
		System.out.println("carLiked: " + userJB.get(1));
		for (Object no : userJB) {
			System.out.println(no.toString());
		}

	}
}
