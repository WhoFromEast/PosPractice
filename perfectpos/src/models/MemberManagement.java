package models;

public class MemberManagement {
	private DataAccessObject dao;
	public MemberManagement() {

	}

	public String backController(String jobCode) {
		String message = null;
		switch(jobCode) {
		case "31": case "32": case "33":
			message = this.ctlReadMember();
			break;
		}
		return message;
	}
	
	public String backController(String jobCode, String[] data) {
		String message = null;
		switch(jobCode) {
		case "3R":
			message = this.ctlRegMember(data);
			break;
		case "3M":
			message = this.ctlModMember(data);
			break;
		case "3D" :
			message = this.ctlDelMember(data);
		}
		return message;
	}
	
	private String ctlModMember(String[] data) {
		
		dao = new DataAccessObject();
		
        String[][] list = dao.getMemberList();
		
		for(int recordIndex = 0; recordIndex<list.length; recordIndex++ ) {
			
			if(data[0].equals(list[recordIndex][0])) {
				
				list[recordIndex][2] = data[1];
				
				break;
			}
		}
				
		return (dao.setMember(list))? this.toStringFromArray(dao.getMemberList()) : "멤버수정에 실패하였습니다. 다시 시도해주세요";
	}
	
	private String ctlDelMember(String[] data) {
		
		boolean check = true;
		
		String[][] list = null;
		String[][] newList;
		
		dao = new DataAccessObject();
		
		list = dao.getMemberList();
		newList = new String[list.length-1][list[0].length];
		
		for(int recordIndex = 0; recordIndex<list.length; recordIndex++) {
			if(!list[recordIndex][0].equals(data[0])) {
				newList[(check) ? recordIndex : recordIndex-1] = list[recordIndex];
			}else {
				check = false;
			}
		}
		return (dao.setMember(newList))? this.toStringFromArray(dao.getMemberList()) : "회원삭제에 실패하였습니다. 다시 시도하시겠습니까?";
	}

	private String ctlReadMember() {
		dao = new DataAccessObject();
		return this.toStringFromArray(dao.getMemberList());
	}

	private String toStringFromArray(String[][] data) {
		StringBuffer sb = new StringBuffer();

		for(int recordIndex=0; recordIndex<data.length; recordIndex++) {
			sb.append(" ");
			for(int colIndex=0; colIndex<data[recordIndex].length; colIndex++) {
				if(colIndex == 3) {
					sb.append(data[recordIndex][colIndex].equals("1")? "가능": "불가");
				}else {
					sb.append(data[recordIndex][colIndex]);
				}

				if(colIndex != data[recordIndex].length - 1) {
					sb.append("\t");
					if(colIndex == 1 && data[recordIndex][colIndex].length()<6) {
						sb.append("\t");
					}
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/* 회원정보등록 */
	private String ctlRegMember(String[] memberInfo) {
		String message = null;
		dao = new DataAccessObject();
		if(dao.setMember(memberInfo)) {
			message = this.toStringFromArray(dao.getMemberList());
		}else {
			message = "회원등록작업이 실패하였습니다.\n다시 등록해 주시기 바랍니다.";
		}
		
		return message;
	}

	/* 회원정보수정 */
}
