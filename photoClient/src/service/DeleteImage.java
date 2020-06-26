package service;

public class DeleteImage extends Tools {

	public DeleteImage() {
		super();
	}

	public int work(String uid, int gid, long timestamp) {
		json.put("uid", uid);
		json.put("gid", gid);
		json.put("timestamp", timestamp);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new DeleteImage().work("a17dbbc0bb2641e19754e41ed3a63d68", 1, 1593132983112L));
	}

}
