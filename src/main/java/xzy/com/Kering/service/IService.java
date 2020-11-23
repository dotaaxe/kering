package xzy.com.Kering.service;

import xzy.com.Kering.GlobalConfig;
import xzy.com.Kering.api.BaseAPI;
import xzy.com.Kering.util.SpringContextUtils;

/**
 * @author xzy.ajiu
 * Created by on @date 2020/11/23 9:56
 */
public interface IService {

    default BaseAPI getBaseApi() {
        String apiName = SpringContextUtils.getBean(GlobalConfig.class).getApi();
        return SpringContextUtils.getBean(apiName, BaseAPI.class);
    }
}
