package jiangzz.rpc.model;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Arrays;

public class MethodInvokeMeta implements Serializable {
	private Class<?> interfaceClass;
	private String name;
	private Object[] args;
	private Class<?>[] parameterTypes;
	private Class<?> responseType;
	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}
	public void setInterfaceClass(Class interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public Class getResponseType() {
		return responseType;
	}
	public void setResponseType(Class responseType) {
		this.responseType = responseType;
	}
	public Class[] getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(Class[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	
	
}
