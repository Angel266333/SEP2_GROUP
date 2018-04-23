import java.util.ArrayList;

public class Database {
	private ArrayList<String> data;
	private static Database me;

	private Database() {
		data = new ArrayList<>();

		//Test data
		data.add("John Hansen");
		data.add("Paul Hansen");
		data.add("John Jensen");
		data.add("Carl Larsen");
		data.add("Peter Nielsen");
		data.add("Peter Jensen");
		data.add("Carl Hansen");
		data.add("Lars Jensen");
		data.add("Lars Hansen");
		data.add("John Larsen");
		data.add("John Jensen");
	}

	public static Database getIntance() {
		synchronized(me) {
			if (me == null) {
				me = new Database();
			}
			return me;
		}
	}

	public String[] getMembers() {
		synchronized(me) {
			String[] res = new String[data.size()];
			data.toArray(res);
			return res;
		}
	}
}
