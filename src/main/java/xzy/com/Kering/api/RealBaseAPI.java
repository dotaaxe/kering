package xzy.com.Kering.api;

/**
 * @author xzy.ajiu
 * Created by on @date 2020/11/23 9:54
 */
public class RealBaseAPI implements BaseAPI {
    @Override
    public void getSomeThing(String s) {
        System.out.println("非测试api");
    }
}
