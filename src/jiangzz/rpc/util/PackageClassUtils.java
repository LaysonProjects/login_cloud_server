package jiangzz.rpc.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * 字节文件加载
 * @author Administrator
 */
public class PackageClassUtils {
	
	public static List<String> resolver(String basepackage){
		List<String> classStrs=new ArrayList<String>();
		String filePath=basepackage.replaceAll("\\.", "/");
		File file = new File(PackageClassUtils.class.getResource("/").getPath()+"/"+filePath);
        File[] listFiles = file.listFiles();
        int i=0;
        for (File file2 : listFiles) {
			if(file2.isFile()){
				String name = file2.getName();
				String fname=basepackage+"."+name.substring(0,name.lastIndexOf('.'));
				classStrs.add(fname);  
			}
		}
        return classStrs;
	}
}
