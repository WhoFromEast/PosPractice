package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import beans.OrderBean;

public class SalesManagement {

	DataAccessObject dao;

	public SalesManagement() {}

	public String backController(String request) {
		ArrayList orders = new ArrayList();
		String message = null;
		String jobCode = request.substring(0, request.indexOf("?"));

		String[] data = request.substring(request.indexOf("?")+1).split("&");
		if(data.length == 1) {
			OrderBean ob = new OrderBean(); 
			ob.setGoodsCode(data[0]);
			orders.add(ob);
		}else if(data.length % 2 == 0) {
			for(int recordIndex=0; recordIndex<data.length; recordIndex+=2) {
				OrderBean ob = new OrderBean();
				ob.setGoodsCode(data[recordIndex]);
				ob.setOrderQuantity(Integer.parseInt(data[recordIndex+1]));
				orders.add(ob);
			}
		}else {
			String memberCode = data[data.length-1];
			for(int recordIndex = 0; recordIndex<data.length-1; recordIndex +=2) {

				OrderBean ob = new OrderBean();
				ob.setGoodsCode(data[recordIndex]);
				ob.setOrderQuantity(Integer.parseInt(data[recordIndex+1]));
				if(data.length%2 == 1) {
					ob.setMemberCode(memberCode);
					orders.add(ob);
				}
			}
		}


		switch(jobCode) {
		case "4S":
			message = this.ctlSales(orders);
			break;

		case "4D":
			message = this.ctlOrders(orders);
			break;

		}
		return message;
	}

	/* 판매개시 */
	private String ctlSales(ArrayList<OrderBean> orders) {
		
		OrderBean ob = null;
		dao = new DataAccessObject();
		ob = dao.getGoodsInfo(orders.get(0));
		
		StringBuffer sb = new StringBuffer();
		sb.append(ob.getGoodsCode() + ",");
		sb.append(ob.getGoodsName() + ",");
		sb.append(ob.getGoodsPrice() + ",");
		sb.append(ob.getOrderQuantity() + ",");
		sb.append(ob.getDiscountRate());
		


		return sb.toString();
	}

	private String ctlOrders(ArrayList<OrderBean> orders) {
		String message = null;
		dao = new DataAccessObject();
		
		String date = now();
		for(int recordIndex=0; recordIndex<orders.size(); recordIndex++) {
			(orders.get(recordIndex)).setOrderCode(date);
		}
		
		message = dao.setOrders(orders)? "주문이 완료되었습니다.":"죄송합니다.~~~";
		return message;
	}

	private String now() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = new Date();
		return sdf.format(d);
	}
}
