package service;

public class DeleteGroup extends Tools {

	public DeleteGroup() {
		super();
	}
	
	public int work(String uid, int gid, int allFlag) {
		json.put("uid", uid);
		json.put("gid", gid);
		json.put("allFlag", allFlag);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new DeleteGroup().work("a17dbbc0bb2641e19754e41ed3a63d68", 3, 0));
	}

}
