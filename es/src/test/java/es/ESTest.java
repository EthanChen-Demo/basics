package es;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cn.lzg.esdemo.ESUtils;
import cn.lzg.esdemo.Goods;
import cn.lzg.esdemo.GoodsFilter2ES;

public class ESTest {

	@Before
	public void init() {
		ESUtils.host = "localhost";
		ESUtils.port = 9200;
	}
	/**
	 * 生成索引
	 * 
	 * @throws UnknownHostException
	 * @throws JsonProcessingException
	 */
	@Test  
    public void testCreatIndex() throws UnknownHostException, JsonProcessingException{  
        List<Goods> goodsList = new ArrayList<Goods>();  
          
        String[] r123 = {"r1","r2","r3"};  
        String[] r23 = {"r2","r3"};  
        goodsList.add(new Goods(1L, "雀巢咖啡", r123));  
        goodsList.add(new Goods(2L, "雅哈咖啡", r23));  
          
        goodsList.add(new Goods(3L, "星巴克咖啡", r123));  
        goodsList.add(new Goods(4L, "可口可乐", r123));  
          
        ESUtils.createIndex(goodsList);  
    }
	/**
	 * 测试search
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testSearch() throws JsonParseException, JsonMappingException,
			IOException {
		GoodsFilter2ES filter = new GoodsFilter2ES();
		filter.setQueryStr("咖啡");
		filter.setRegionId("r2");
		List<Goods> result = ESUtils.search(filter);
		for (Goods goods : result) {
			System.out.println(goods);
		}
	}

	/**
	 * 测试新增doc
	 * 
	 * @throws UnknownHostException
	 * @throws JsonProcessingException
	 */
	@Test
	public void testAddDoc() throws UnknownHostException,
			JsonProcessingException {
		// test_index 和 goods 在创建索引的时候写死了 所以这样 就传这两个值
		String[] r = { "r2", "r3" };
		Goods goods = new Goods(5L, "新增的咖啡", r);
		ESUtils.addDocument("test_index", "goods", goods);
	}

	/**
	 * 测试修改doc
	 * 
	 * @throws UnknownHostException
	 * @throws JsonProcessingException
	 */
	@Test
	public void testUpdateDoc() throws UnknownHostException,
			JsonProcessingException {
		String[] r = { "r2", "r3" };
		Goods goods = new Goods(5L, "修改啦的咖啡", r);
		ESUtils.updateDocument("test_index", "goods", goods);
	}

	/**
	 * 测试删除doc
	 * 
	 * @throws UnknownHostException
	 * @throws JsonProcessingException
	 */
	@Test
	public void testDelDoc() throws UnknownHostException,
			JsonProcessingException {
		ESUtils.deleteDocument("test_index", "goods", 5L);
	}

}
