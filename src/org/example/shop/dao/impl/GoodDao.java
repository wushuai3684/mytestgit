package org.example.shop.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.example.shop.dao.IGoodDao;
import org.example.shop.pojo.Goods;
import org.example.shop.pojo.OrderList;
import org.example.shop.pojo.User;
import org.example.shop.util.DBUtil;
import org.springframework.stereotype.Repository;

@Repository("goodDao")
public class GoodDao implements IGoodDao {
	
	/**
	 * �г�������Ʒ
	 */
	public List<Goods> findAllGoods() {
		// TODO Auto-generated method stub
		SqlSession session = DBUtil.createSession();
		List <Goods> gds = session.selectList(Goods.class.getName()+".find_allgoods",null);	
		DBUtil.closeSession(session);
		return gds;
	}
	
	/**
	 * �û�������Ʒ�󣬸��ݶ����������û�������Ϣ
	 */
	public boolean addGoodToUser(OrderList order) {
		// TODO Auto-generated method stub
		SqlSession session = DBUtil.createSession();
		System.out.println(order.getGoodsserial());
		List<String> goodlist = session.selectList(Goods.class.getName()+".validate_goods", order.getGoodsserial());
		if(!goodlist.iterator().hasNext()){
			session.insert(Goods.class.getName()+".buy_good", order);
			session.commit();	
			DBUtil.closeSession(session);
			return true;
		}
		DBUtil.closeSession(session);
		return false;
	}

	public void addGoods(Goods gd) {
		// TODO Auto-generated method stub
		SqlSession session = DBUtil.createSession();
		
	}

	/**
	 * ��õ�ǰ������Ʒ
	 */
	public List<Goods> findGoodsByType(String type) {
		// TODO Auto-generated method stub
		SqlSession session = DBUtil.createSession();
		List <Goods> gds = session.selectList(Goods.class.getName()+".find_goodsby_type",type);
		DBUtil.closeSession(session);
		return gds;
	}
	
	/**
	 * �����û����кŻ�ö�Ӧ��Ʒ���кţ�Ȼ���ٴ�
	 * t_order�а�����Ʒ���кŻ�ó�������Ʒ����
	 */
	public List<Goods>findGoodsBySerial(String userserial){
		SqlSession session = DBUtil.createSession();
		List<String> goodsserial = session.selectList(Goods.class.getName()+".find_goodidby_user",userserial);
		List<Goods> list = new ArrayList<Goods>();
		Iterator it = goodsserial.iterator();
		while(it.hasNext()){
			Goods gds = session.selectOne(Goods.class.getName()+".find_goodsby_serial",(String) it.next());
			list.add(gds);
		}	
		DBUtil.closeSession(session);
		return list;
	}
	
	
	public void deleteGoodFromUser(OrderList order) {
		// TODO Auto-generated method stub
		SqlSession session = DBUtil.createSession();
		session.delete(Goods.class.getName()+".delete_goodsby_user", order);
		session.commit();	
		DBUtil.closeSession(session);
	}

}
