package xzy.com.Kering.service;

import javax.xml.transform.Source;

import xzy.com.Kering.bean.SourceBean;
import xzy.com.Kering.bean.TargetBean;
import xzy.com.Kering.util.BeanUtils;

/**
 * @author xzy.ajiu
 * Created by on @date 2020/11/23 9:56
 */
public class TestService  implements IService{
    /**
     * 根据环境调用不同的接口，一般适用于第三方未提供测试接口，
     * 本地测试时调用模拟第三方接口的数据
     * @param s
     */
    public void changeEnvironment(String s){
        getBaseApi().getSomeThing(s);
    }


    public TargetBean testConvert(SourceBean s, TargetBean t){
         BeanUtils.copyBean(s,t);
         return t;
    }

    public static void main(String[] args) {
        TestService testService =new TestService();
        SourceBean s=new SourceBean();
        s.setAge("143");
        s.setBirthday("20201102");
        s.setId(1);
        s.setName("ss");
        TargetBean t= new TargetBean();
        t =testService.testConvert(s,t);
        System.out.println(t.toString());
    }
}
