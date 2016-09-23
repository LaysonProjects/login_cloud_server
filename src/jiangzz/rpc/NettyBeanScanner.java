package jiangzz.rpc;


import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import jiangzz.rpc.util.PackageClassUtils;
import jiangzz.rpc.client.RPCProxyFactoryBean;
/**
 * 动态加载代理bean到Springbean工厂
 * @author Administrator
 *
 */
public class NettyBeanScanner implements BeanFactoryPostProcessor {
	private DefaultListableBeanFactory beanFactory;  
	private String basePackage;
	private String clientName;
	public NettyBeanScanner(String basePackage, String clientName) {
		this.basePackage = basePackage;
		this.clientName = clientName;
	}
	/**
	 * 注册Bean到Spring的bean工厂
	 */
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		this.beanFactory = (DefaultListableBeanFactory)beanFactory;  
		
		List<String> resolverClass = PackageClassUtils.resolver(basePackage);
		for (String clazz : resolverClass) {
			String simpleName="";
			if(clazz.lastIndexOf('.')!=-1){
				simpleName=clazz.substring(clazz.lastIndexOf('.')+1);
			}else{
				simpleName=clazz;
			}
			BeanDefinitionBuilder gd = BeanDefinitionBuilder.genericBeanDefinition(RPCProxyFactoryBean.class);  
			gd.addPropertyValue("interfaceClass", clazz);
			gd.addPropertyReference("client", clientName);
			this.beanFactory.registerBeanDefinition(simpleName, gd.getRawBeanDefinition());
		}
	}
}
