package jiangzz.rpc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 对象序列化
 * @author Administrator
 */
public class ObjectSerializerUtils {
	/**
	 * 序列化对象
	 * @param obj
	 * @return
	 */
	public static byte[] serilizer(Object obj){
		if(obj!=null){
			try {
				ByteArrayOutputStream bos=new ByteArrayOutputStream();
				ObjectOutputStream oos=new ObjectOutputStream(bos);
				oos.writeObject(obj);
				oos.flush();
				oos.close();
				return bos.toByteArray();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}else{
			return null;
		}
	}
	/**
	 * 反序列化
	 * @param data
	 * @return
	 */
	public static Object deSerilizer(byte[] data){
		if(data!=null || data.length>0){
			try {
				ByteArrayInputStream bis=new ByteArrayInputStream(data);
				ObjectInputStream ois=new ObjectInputStream(bis);
				return ois.readObject();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}else{
			return null;
		}
	}
}
