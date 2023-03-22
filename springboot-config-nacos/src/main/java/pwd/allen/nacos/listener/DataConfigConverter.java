package pwd.allen.nacos.listener;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.api.config.convert.NacosConfigConverter;

/**
 * 自定义nacos的配置内容类型转换器
 * 用法：可以在@NacosConfigListener中指定
 *
 * @see com.alibaba.nacos.api.config.annotation.NacosConfigListener
 *
 * @author 门那粒沙
 * @create 2021-09-04 20:17
 **/
public class DataConfigConverter implements NacosConfigConverter<Data> {

    @Override
    public boolean canConvert(Class<Data> targetType) {
        return true;
    }

    @Override
    public Data convert(String source) {
        return JSONUtil.toBean(source, Data.class);
    }

}
