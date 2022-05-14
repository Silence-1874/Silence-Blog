package top.silence;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(DigestUtil.md5Hex("1746946575"));
    }

}
