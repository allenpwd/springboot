package pwd.allen.nacos.listener;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.api.config.convert.NacosConfigConverter;

import java.util.Map;

/**
 * 自定义nacos的配置内容类型转换器
 * 用法：可以在@NacosConfigListener中指定
 *
 * @see com.alibaba.nacos.api.config.annotation.NacosConfigListener
 *
 * @author 门那粒沙
 * @create 2021-09-04 20:17
 **/
public class DataConfigConverter implements NacosConfigConverter<Map> {

    @Override
    public boolean canConvert(Class<Map> targetType) {
        return true;
    }

    @Override
    public Map convert(String source) {
        return JSONUtil.toBean(source, Map.class);
    }

}
