package school.functions;

import java.util.Scanner;

import school.jiemian.Function;
import school.jiemian.Source;

/**
 * 添加景点功能实现
 */
public class AddJingdian implements Function {

	@Override
	public int doFunction(Source scr, Scanner scan) {
		try {
			Jingdian jd = new Jingdian(String.format("%04d", scr.jd.size() + 1), "", "", "");
			System.out.println("请输入景点编号:");
			jd.ID = scan.next();
			System.out.println("请输入景点名称:");
			jd.name = scan.next();
			System.out.println("请输入景点校区:");
			jd.section = scan.next();
			System.out.println("请输入景点介绍:");
			jd.introduce = scan.next();

			scr.jd.add(jd);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

}
