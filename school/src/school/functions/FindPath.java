package school.functions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import school.jiemian.Function;
import school.jiemian.Source;

public class FindPath implements Function {

	@Override
	public int doFunction(Source scr, Scanner scan) {
		Ditu dt = new Ditu(scr);
		System.out.print("输入起始景点编号: ");
		String start = scan.next();
		System.out.print("输入目标景点编号: ");
		String target = scan.next();
		Map<String, String> lj = dt.Dijkstra(start);

		List<String> ls = new LinkedList<String>();
		ls.add(0, target);
		for (String i = ls.get(0); !i.equals(start); i = ls.get(0)) {
			ls.add(0, lj.get(i));
		}
		double len = 0;
		System.out.println("最短路径: \n" + 0.d + ":\t" + dt.id2jd.get(ls.get(0)));
		for (int i = 1; i < ls.size(); i++) {
			double el = scr.dt.get(String.format("%s##%s", ls.get(i - 1), ls.get(i)));
			len += el;
			System.out.println(el + ":\t" + dt.id2jd.get(ls.get(i)));
		}
		System.out.println("总距离: " + len);
		return 0;
	}

}
