package school.functions;

import java.util.Scanner;

import school.jiemian.Function;
import school.jiemian.Source;

/**
 * 显示现有的景点列表
 */
public class DisplayJingdian implements Function {

	@Override
	public int doFunction(Source scr, Scanner scan) {
		if (scr.jd.isEmpty()) {
			System.out.println("没有景点!");
			return 0;
		}
		System.out.println("所有景点: ");
		for (Jingdian i : scr.jd)
			System.out.println(i);
		return 0;
	}

}
