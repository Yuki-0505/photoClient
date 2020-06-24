package service;

public class CreateGroup extends Tools {
	
	public CreateGroup() {
		super();
	}

	public int work(String uid, String groupName) {
		json.put("uid", uid);
		json.put("name", groupName);
		jsonArr.add(json);
		getJsonArr();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		
	}

}
