package dvd_store.helpers;

public class Utils {

	private Utils() {}

	public static String cause(Throwable e) {
		StringBuilder sb = new StringBuilder("--->\nEXCEPTION:::::MSG\n"
			+ "=================\n");
		for (Throwable t = e; t != null; t = t.getCause())
			sb.append(t.getClass().getSimpleName()).append(":::::")
				.append(t.getMessage()).append("\n");
		sb.append("FIN\n\n");
		return sb.toString();
	}
}
