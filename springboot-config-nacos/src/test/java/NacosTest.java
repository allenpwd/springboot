import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingMaintainService;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.ListView;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * 报错
 * type=Forbidden, status=403
 * 如果开启了密码验证（nacos.core.auth.enabled=true），则要指定账号密码
 *
 * 拓展 TODO 这个怎么用的
 * 资源权限的resources字段格式
 * PWD*:*:*
 * 最前面是命名空间，支持通配符，上面的例子表示可以访问PWD开头的命名空间
 * 中间的表示：TODO
 * 最后的表示：TODO
 *
 * @author pwdan
 * @create 2021-09-03 15:05
 **/
public class NacosTest {

	private static Properties properties;

	static {
		String serverAddr = null, namespace = null, username = null, password = null, accessKey = null;

		serverAddr = "127.0.0.1:8848";
		namespace = "";
		username = "nacos";
		password = "nacos";


		properties = new Properties();
		// nacos服务器地址，127.0.0.1:8848
		properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
		// 配置中心的命名空间id
		properties.put(PropertyKeyConst.NAMESPACE, namespace);
		if (accessKey != null) {
			properties.put(PropertyKeyConst.ACCESS_KEY, accessKey);
		} else {
			if (username != null) {
				properties.put(PropertyKeyConst.USERNAME, username);
			}
			if (password != null) {
				properties.put(PropertyKeyConst.PASSWORD, password);
			}
		}
	}

	/**
	 * 获取配置信息
	 * @throws NacosException
	 */
    @Test
    public void config() throws NacosException, InterruptedException {
		ConfigService configService = NacosFactory.createConfigService(properties);
		// 根据dataId、group定位到具体配置文件，获取其内容. 方法中的三个参数分别是: dataId, group, 超时时间
		String content = configService.getConfig("yaml_config", "DEFAULT_GROUP", 3000L);
		System.out.println(content);

		// 另启线程监控配置文件的变更
		configService.addListener("json_config", "DEFAULT_GROUP", new Listener() {
			@Override
			public Executor getExecutor() {
				return Executors.newSingleThreadExecutor();
			}

			@Override
			public void receiveConfigInfo(String configInfo) {
				System.out.println(configInfo);
			}
		});

		while (true) {
			Thread.sleep(2000);
		}
    }

	//<editor-fold desc="服务发现">
	@Test
    public void maintain() throws NacosException, InterruptedException {
		NamingMaintainService maintainService = NacosFactory.createMaintainService(properties);
		maintainService.createService("my.Service", "MY_GROUP");

		while (true) {
			Thread.sleep(2000);
		}
	}
	@Test
	public void naming() throws NacosException {
		NamingService namingService = NacosFactory.createNamingService(properties);
		ListView<String> servicesOfServer = namingService.getServicesOfServer(1, 10);
		System.out.println(servicesOfServer);
	}
	//</editor-fold>
}
