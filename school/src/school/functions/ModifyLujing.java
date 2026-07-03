package school.functions;

import java.util.Scanner;

import school.jiemian.Function;
import school.jiemian.Source;

public class ModifyLujing implements Function {

	@Override
	public int doFunction(Source scr, Scanner scan) {
		int counter = 0;
		do {
			System.out.print("输入起始景点编号: ");
			String start = scan.next();
			System.out.print("输入到达景点编号: ");
			String end = scan.next();

			start = start.trim();
			end = end.trim();
			boolean s = false, e = false;
			for (int i = 0; i < scr.jd.size(); i++) {
				if (scr.jd.get(i).ID.equals(start))
					s = true;
				if (scr.jd.get(i).ID.equals(end))
					e = true;
			}
			if (s && e) {
				System.out.print("输入路径长度(用-1表无路径): ");
				double len = scan.nextDouble();
				if (len < 0) {
					scr.dt.remove(String.format("%s##%s", start, end));
				} else {
					scr.dt.put(String.format("%s##%s", start, end), len);
				}
				return 0;
			}

		} while (counter++ < 3);
		
		return counter;
	}

}
