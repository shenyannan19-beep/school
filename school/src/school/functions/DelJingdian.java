package school.functions;

import java.util.Scanner;

import school.jiemian.Function;
import school.jiemian.Source;

/**
 * 删除节点功能实现
 */
public class DelJingdian implements Function {

	@Override
	public int doFunction(Source scr, Scanner scan) {
		System.out.print("请输入要删除的景点编号: ");
		String ID = scan.next();
		for (int i = 0; i < scr.jd.size(); i++)
			if (scr.jd.get(i).ID.equals(ID)) {
				System.out.println("删除: " + scr.jd.remove(i));
				return 0;
			}
		System.out.println("未找到景点: " + ID);
		return 0;
	}

}
