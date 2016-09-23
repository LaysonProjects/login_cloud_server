package jiangzz.rpc.model;

import java.io.Serializable;

public class NullWritable implements Serializable {
	private static  NullWritable instance=new NullWritable();
	private NullWritable(){}
	public static NullWritable nullWritable(){
		return instance;
	}
}
