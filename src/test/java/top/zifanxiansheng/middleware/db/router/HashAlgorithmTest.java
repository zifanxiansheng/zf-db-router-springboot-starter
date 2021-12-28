package top.zifanxiansheng.middleware.db.router;

import org.junit.Test;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 16:53
 **/
public class HashAlgorithmTest {


    @Test
    public void test_hash_index() {


        String key = "rasdsdrrddddsdddds";

        int dbCount = 2, tbCount = 4;
        int size = dbCount * tbCount;
        // 散列
        int idx = (size - 1) & (key.hashCode() ^ (key.hashCode() >>> 16));

        int dbIdx = idx / tbCount + 1;
        int tbIdx = idx - tbCount * (dbIdx - 1);

        System.out.println(dbIdx);
        System.out.println(tbIdx);
    }
}
