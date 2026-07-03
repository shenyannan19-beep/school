package school.jiemian;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import school.functions.AddJingdian;
import school.functions.DelJingdian;
import school.functions.DisplayJingdian;
import school.functions.DisplayLujing;
import school.functions.FindPath;
import school.functions.ModifyLujing;

/**
 * 程序运行入口类 1.完成运行对象初始化 2.主函数运行框架
 */
public class Run {
	/**
	 * 键盘输入对象
	 */
	public static Scanner scan = new Scanner(System.in);
	/**
	 * 功能列表对象
	 */
	public static Map<Integer, Function> funTable = new TreeMap<Integer, Function>();
	/**
	 * 数据资源对象
	 */
	public static Source scr = Source.factory(); // 资源对象整体初始化

	static {// 静态代码 在这里填入功能编号和功能入口映射表
		funTable.put(1, new DisplayJingdian());
		funTable.put(2, new AddJingdian());
		funTable.put(3, new DelJingdian());
		funTable.put(4, new DisplayLujing());
		funTable.put(5, new ModifyLujing());
		funTable.put(6, new FindPath());
	}

	/**
	 * 输出行分割线
	 */
	private static void printLine() {
		System.out.println("-------------------------------------------");
	}

	/**
	 * 打印功能列表
	 */
	private static void printFunTable() { // 在这里根据funTable填写输出提示信息
		System.out.println("\t\t功能表");
		System.out.println("0:退出程序\t\t1:显示景点");
		System.out.println("2:添加景点\t\t3:删除景点");
		System.out.println("4:显示地图\t\t5:修改地图");
		System.out.println("6:查询路径\t\t");
	}

	/**
	 * 清空CMD控制框 在IDE内无效
	 */
	private static void clearScr() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * 程序主函数
	 * 
	 * @param args 无效
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		while (true) { // 循环提示信息和用户输入流程
			clearScr();
			printLine(); // 输出功能表提示信息
			printFunTable();
			printLine();
			System.out.print("请输入功能序号:"); // 用户输入功能号
			int fun = scan.nextInt(); // 读取用户输入功能号
			if (0 == fun) { // 退出程序
				System.out.println("是否保存当前数据?(Y/N): ");
				int c = System.in.read();
				if ('Y' == c || 'y' == c)
					scr.saveSource();

				System.out.println("退出程序!");
				System.exit(0);
			} else { // 非退出程序
				if (funTable.containsKey(fun)) {// 调用功能存在 读取功能表并调用
					funTable.get(fun).doFunction(scr, scan);
				} else // 调用功能不存在 输出提示信息
					System.out.println("功能无效!");
			}
			System.out.print("『回车』回到主界面...");
			System.in.read();
		}
	}

}
