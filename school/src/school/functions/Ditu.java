package school.functions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import school.jiemian.Source;

public class Ditu implements Serializable {
	private static final long serialVersionUID = 1075965289112114120L;

	public Ditu(Source s) {
		this.id2jd = new TreeMap<String, Jingdian>();
		this.id2idx = new TreeMap<String, Integer>();
		this.idx2id = new TreeMap<Integer, String>();

		for (Jingdian i : s.jd) {
			this.id2jd.put(i.ID, i);
			if (!this.id2idx.containsKey(i.ID)) {
				int idx = this.id2idx.size();
				this.id2idx.put(i.ID, idx);
				this.idx2id.put(idx, i.ID);
			}
		}
		this.N = this.id2jd.size();
		this.m = new double[this.N][this.N];
		for (int i = 0; i < this.m.length; i++)
			for (int j = 0; j < this.m[i].length; j++)
				if (i == j) {
					this.m[i][j] = 0; // 每个节点到自己的距离为0
				} else {
					this.m[i][j] = Double.POSITIVE_INFINITY;
				}

		// 初始化邻接矩阵，确保所有连接都是双向的
		for (String i : s.dt.keySet()) {
			String ss[] = i.trim().split("##");
			int from = this.id2idx.get(ss[0]);
			int to = this.id2idx.get(ss[1]);
			double distance = s.dt.get(i);
			// 设置正向连接
			this.m[from][to] = distance;
			// 设置反向连接，确保图是无向的
			this.m[to][from] = distance;
		}
	}

	public Map<String, Jingdian> id2jd; // 景点编号到景点对象的映射表
	public Map<String, Integer> id2idx; // 景点编号到邻接矩阵索引号
	public Map<Integer, String> idx2id; // 邻接矩阵索引号到景点编号
	private double[][] m;
	private int N;

	/**
	 * 单源最短路径算算法
	 * 
	 * @param startJdId 出发景点编号
	 * @return 最短路径前驱查询表Map key为目标景点 value为目标前驱景点
	 */
	public Map<String, String> Dijkstra(String startJdId) {
		int start = this.id2idx.get(startJdId);
		Set<Integer> s = new TreeSet<Integer>(), u = new TreeSet<Integer>();
		double dist[] = new double[this.N];
		int path[] = new int[this.N];
		
		// 正确初始化距离数组和路径数组
		for (int i = 0; i < this.N; i++) {
			if (i == start) {
				dist[i] = 0; // 起始点距离为0
				path[i] = start;
				s.add(i);
			} else {
				dist[i] = this.m[start][i];
				if (Double.isFinite(dist[i]))
					path[i] = start;
				else
					path[i] = -1;
				u.add(i);
			}
		}

		while (!u.isEmpty()) {
			int min_n = -1;
			for (int i : u)
				if (-1 == min_n || dist[i] < dist[min_n])
					min_n = i;

			s.add(min_n);
			u.remove(min_n);

			for (int i : u)
				if (dist[min_n] + this.m[min_n][i] < dist[i]) {
					dist[i] = dist[min_n] + this.m[min_n][i];
					path[i] = min_n;
				}
		}

		Map<String, String> pm = new TreeMap<String, String>();
		for (int i = 0; i < path.length; i++) {
			if (path[i] != -1) {
				pm.put(this.idx2id.get(i), this.idx2id.get(path[i]));
			} else {
				// 如果没有前驱节点（无法到达的节点），则不添加到映射表中
				// 或者可以添加为null，让调用者处理
				pm.put(this.idx2id.get(i), null);
			}
		}
		return pm;
	}

	public static Map<String, Double> readDituFile(String path) {
		Map<String, Double> ditu = new TreeMap<String, Double>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			for (String i = br.readLine(); null != i; i = br.readLine()) {
				i = i.trim();
				if (i.length() == 0)
					continue;
				String s[] = i.split("\\s+");
				if (2 != s.length)
					continue;
				ditu.put(s[0], Double.parseDouble(s[1]));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ditu;
	}

	// public static void main(String[] argc) {
	// Ditu dt = new Ditu(Source.factory());
	// for (Entry<String, String> i : dt.Dijkstra("0001").entrySet()) {
	// System.out.println(i.getKey() + "\t" + i.getValue());
	// }
	//
	// }
}
