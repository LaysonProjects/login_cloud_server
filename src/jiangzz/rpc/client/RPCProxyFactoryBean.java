package jiangzz.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import jiangzz.rpc.model.MethodInvokeMeta;
import jiangzz.rpc.util.WrapMethodUtils;

public class RPCProxyFactoryBean extends AbstractFactoryBean<Object> implements InvocationHandler{
    private Class interfaceClass;
    private NettyClient client;
	public void setClient(NettyClient client) {
		this.client = client;
	}
	public void setInterfaceClass(Class interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		final MethodInvokeMeta mim = WrapMethodUtils.readMethod(interfaceClass,method, args);
		return client.remoteCall(mim, 0);
	}
	@Override
	protected Object createInstance() throws Exception {
		// TODO Auto-generated method stub
		 return Proxy.newProxyInstance(interfaceClass.getClassLoader(), 
				new Class[]{interfaceClass}, this);
	}
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return interfaceClass;
	}
}
