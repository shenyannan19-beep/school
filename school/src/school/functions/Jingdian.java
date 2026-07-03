package school.functions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 景点对象类 按经典编号字符串排序作为比较依据
 */
public class Jingdian implements Comparable<Jingdian>, Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 7777783297478283479L;
	/**
	 * 景点编号
	 */
	public String ID, name, introduce, section;

	/**
	 * 构造函数
	 * 
	 * @param ID 景点编号
	 */
	public Jingdian(String ID) {
		this.ID = ID;
	}

	/**
	 * 构造函数
	 * 
	 * @param ID   景点编号
	 * @param name 景点名称
	 */
	public Jingdian(String ID, String name) {
		this(ID);
		this.name = name;
	}

	/**
	 * 构造函数
	 * 
	 * @param ID        景点编号
	 * @param name      景点名称
	 * @param introduce 景点介绍
	 */
	public Jingdian(String ID, String name, String introduce) {
		this(ID, name);
		this.introduce = introduce;
	}

	/**
	 * 
	 * @param ID        经典编号
	 * @param name      景点名称
	 * @param section   所在校区
	 * @param introduce 景点介绍
	 */
	public Jingdian(String ID, String name, String section, String introduce) {
		this(ID, name, introduce);
		this.section = section;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (this == obj)
			return true;
		if (obj instanceof Jingdian)
			return this.ID.equals(Jingdian.class.cast(obj).ID);
		return false;
	}

	@Override
	public int hashCode() {
		return this.ID.hashCode();
	}

	@Override
	public String toString() {
		return String.format("[%s]%s:%s, %s", this.ID, this.name, this.section, this.introduce);
	}

	@Override
	public int compareTo(Jingdian arg0) {
		return this.ID.compareTo(arg0.ID);
	}

	/**
	 * 读取景点结介绍文件 静态方法 <br/>
	 * 每行为一个景点 ID name section introduce 空白分割四个部分
	 * 
	 * @param path 文件路径
	 * @return 读取景点列表对象
	 */
	public static Collection<Jingdian> read(String path) {
		List<Jingdian> lst = new LinkedList<Jingdian>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			for (String i = br.readLine(); null != i; i = br.readLine()) {
				String[] s = i.trim().split("\\s+");
				if (4 == s.length) {
					lst.add(new Jingdian(s[0], s[1], s[2], s[3]));
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
}
