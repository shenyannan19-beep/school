package school.functions;

import java.util.Scanner;

import school.jiemian.Function;
import school.jiemian.Source;

public class DisplayLujing implements Function {

	@Override
	public int doFunction(Source scr, Scanner scan) {
		for (String i : scr.dt.keySet())
			System.out.println(i.replace("##", " → ") + ": " + scr.dt.get(i));

		return 0;
	}

}
