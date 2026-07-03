package school.jiemian;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import school.functions.Ditu;
import school.functions.Jingdian;

/**
 * 共享数据对象类 在这里建立数据存储对象的初始化
 */
public class Source implements Serializable { // 存放共享数据对象
	/**
	 * 序列化版本ID
	 */
	private static final long serialVersionUID = 7050802188297317494L;

	/**
	 * 景点列表
	 */
	public List<Jingdian> jd = new LinkedList<Jingdian>();
	/**
	 * 景点之间的路径长度 键采用“景点索引##景点索引”的方式存放 没有存放的认为不存在路径
	 */
	public Map<String,Double> dt = new TreeMap<String, Double>();

	/**
	 * 构造函数 初始化默认数据集
	 */
	private Source() {
		// jd.add(new Jingdian("001", "修德楼", "修德楼也称为主楼，是学校行政机构和多个学院的驻地。"));
		jd.addAll(Jingdian.read("data/Jingdian.txt"));
		dt.putAll(Ditu.readDituFile("data/ditu.txt"));				
	}

	/**
	 * 默认数据存储路径
	 */
	private static final String filename = "data/source.cls";

	public static Source factory() {
		File f = new File(filename);
		File jdFile = new File("data/Jingdian.txt");
		File dtFile = new File("data/ditu.txt");
		
		// 如果序列化文件存在，检查文本文件是否更新
		if (f.exists() && jdFile.exists() && dtFile.exists()) {
			long serialTime = f.lastModified();
			long jdTime = jdFile.lastModified();
			long dtTime = dtFile.lastModified();
			
			// 如果文本文件比序列化文件新，则从文本文件重新加载
			if (jdTime > serialTime || dtTime > serialTime) {
				System.out.println("检测到数据文件已更新，重新加载数据...");
				// 删除旧的序列化文件，强制从文本文件加载
				f.delete();
			} else {
				// 文本文件未更新，使用序列化文件
				try {
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
					Object obj = ois.readObject();
					ois.close();
					if (obj instanceof Source)
						return Source.class.cast(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return new Source();
	}

	public void saveSource() {
		File f = new File(filename);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(this);
			oos.flush();
			oos.close();
			System.out.println("数据已保存: " + f.getAbsolutePath());
		} catch (Exception e) {
			if (f.exists() && (!f.delete()))
				f.deleteOnExit();
			e.printStackTrace();
		}
	}
}
