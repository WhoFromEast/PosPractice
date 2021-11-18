package models;

import java.util.ArrayList;

import beans.GoodsBean;

public class MenuManagement {
	private DataAccessObject dao;

	public MenuManagement() {

	}

	public String backController(String clientData) {
		GoodsBean goods = null;
		String[] data = null;
		String jobCode = null;
		

		if(clientData.indexOf('?') != -1) {
			jobCode = clientData.substring(0, clientData.indexOf('?'));
			data = clientData.substring(
					clientData.indexOf('?') +1).split("&");
			if(data.length>0) {
				goods = new GoodsBean();
				goods.setMenuCode(data[0]);
				if(jobCode.equals("2R")) {
					goods.setMenuName(data[1]);
					goods.setMenuPrice(Integer.parseInt(data[2]));
					goods.setMenuState(data[3].charAt(0));
					goods.setMenuCategory(data[4]);
					goods.setDiscountRate(Integer.parseInt(data[5]));
				}else if(jobCode.equals("2M")) {
					goods.setMenuPrice(Integer.parseInt(data[1]));
					goods.setDiscountRate(Integer.parseInt(data[2]));
				}
			}
		}else {
			jobCode = clientData;
		}
		
		String message = null;
		switch(jobCode) {
		
		case "21": case "22": case "23":
			message = this.ctlReadMenu();
			break;
			
		case "2R":
			message = this.ctlRegMenu(goods);
			break;
			
		case "2M":
			message = this.ctlModMenu(goods);
			break;
			
		case "2D":
			message = this.ctlDelMenu(goods);
			break;
		}
		return message;
	}
	
	
	/* 메뉴읽기 */
	private String ctlRegMenu(GoodsBean data) {
		String menuList = null;
		dao = new DataAccessObject();
		// DAO에 메뉴등록 요청
		if(dao.setMenu(data)) {
			// DAO에 등록된 메뉴 읽기 요청
			menuList = this.toStringFromArray(dao.getMenu());
		}else {
			menuList = "메뉴등록작업이 실패하였습니다. 다시 한번 입력해주세요";
		}
		
		return menuList;
	}
	
    private String ctlModMenu(GoodsBean data) {
		dao = new DataAccessObject();
        ArrayList<GoodsBean> menuList = dao.getMenu();
		
        for(int recordIndex = 0; recordIndex<menuList.size(); recordIndex++) {
        	if(menuList.get(recordIndex).getMenuCode().equals(data.getMenuCode())) {
        		menuList.get(recordIndex).setMenuPrice(data.getMenuPrice());
        		menuList.get(recordIndex).setDiscountRate(data.getDiscountRate());
        		break;
        	}
        }
				
		return (dao.setMenu(menuList))? this.toStringFromArray(dao.getMenu()) : "메뉴수정에 실패하였습니다. 다시 시도해주세요";
	}
    
    private String ctlDelMenu(GoodsBean data) {
    	ArrayList<GoodsBean> menuList;
    	
    	dao = new DataAccessObject();
    	menuList = dao.getMenu();
    	for(int recordIndex=0; recordIndex<menuList.size(); recordIndex++) {
    		if(menuList.get(recordIndex).getMenuCode().equals(data.getMenuCode())) {
    			menuList.remove(recordIndex);
    			break;
    		}
    	}
    	return (dao.setMenu(menuList)) ? this.toStringFromArray(dao.getMenu()) : "메뉴 삭제에 실패하였습니다. 다시 시도하시겠습니까?";
    }
	
	private String ctlReadMenu() {
		dao = new DataAccessObject();

		return this.toStringFromArray(dao.getMenu());
	}
	
	/* 2차원 배열 --> String */
	private String toStringFromArray(ArrayList<GoodsBean> menuList) {
		StringBuffer sb = new StringBuffer();
		
		for(int recordIndex=0; recordIndex<menuList.size(); recordIndex++) {
			sb.append(" ");
			sb.append(menuList.get(recordIndex).getMenuCode());
			sb.append("\t");
			sb.append(menuList.get(recordIndex).getMenuName());
			sb.append(menuList.get(recordIndex).getMenuName().length() <6? "\t\t" : "\t" );
			sb.append(menuList.get(recordIndex).getMenuPrice());
			sb.append("\t");
			sb.append(menuList.get(recordIndex).getMenuState() == '1'? "가능" : "불가");
			sb.append("\t");
			sb.append(menuList.get(recordIndex).getMenuCategory());
			sb.append("\t");
			sb.append(menuList.get(recordIndex).getDiscountRate());

			sb.append("\n");
		}
		return sb.toString();
	}
	
	/* 메뉴등록 */
	
	/* 굿즈등록 */
	private void ctlRegGoods() {

	}

	/* 굿즈정보수정 */
	private void ctlModGoods() {

	}

	/* 굿즈 삭제 */
	private void ctlDelGoods() {

	}
}
