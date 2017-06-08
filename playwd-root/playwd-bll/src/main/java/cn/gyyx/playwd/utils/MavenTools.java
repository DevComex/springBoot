package cn.gyyx.playwd.utils;
import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 删除maven缓存
 */
public class MavenTools {

	private static final String MAVEN_REPO_PATH = "C:\\Users\\Administrator\\.m2\\repository";
	private static final String FILE_SUFFIX = "lastUpdated";
	private static final Log log = LogFactory.getLog(MavenTools.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MavenTools t = new MavenTools();
		t.delLastUpdated(MAVEN_REPO_PATH);
	}
	
	public void delLastUpdated(String repoPath){
		File mavenRep = new File(repoPath);
		if (!mavenRep.exists()) {
			log.warn("Maven repos is not exist.");
			return;
		}
		File[] files = mavenRep.listFiles((FilenameFilter) FileFilterUtils
				.directoryFileFilter());
		delFileRecr(files,null);
		log.info("Clean lastUpdated files finished.");
	}

	private static void delFileRecr(File[] dirs, File[] files) {
		if (dirs != null && dirs.length > 0) {
			for(File dir: dirs){
				File[] childDir = dir.listFiles((FilenameFilter) FileFilterUtils
				.directoryFileFilter());
				File[] childFiles = dir.listFiles((FilenameFilter) FileFilterUtils
				.suffixFileFilter(FILE_SUFFIX));
				delFileRecr(childDir,childFiles);
			}
		}
		if(files!=null&&files.length>0){
			for(File file: files){
				if(file.delete()){
					log.info("File: ["+file.getName()+"] has been deleted.");
				}
			}
		}
	}

}