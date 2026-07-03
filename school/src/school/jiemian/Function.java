package school.jiemian;

import java.util.Scanner;

/**
 * 操作同一调用接口
 */
public interface Function {
	/**
	 * 操作功能接口 提供了统一的数据操作调用接口
	 * 
	 * @param scr  共享数据资源对象
	 * @param scan 共享键盘输入对象
	 * @return 运行结果状态 0:运行正常 非0:运行异常
	 */
	public int doFunction(Source scr, Scanner scan);
}
