package xzy.com.Kering.api;

/**
 * @author xzy.ajiu
 * Created by on @date 2020/11/23 9:53
 */
public class TestBaseAPI implements BaseAPI {
    @Override
    public void getSomeThing(String s) {
        System.out.println("测试api接口");
    }
}
