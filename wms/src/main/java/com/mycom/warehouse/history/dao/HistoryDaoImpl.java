package com.mycom.warehouse.history.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycom.warehouse.history.vo.HistoryVO;
import com.mycom.warehouse.stock.vo.StockVO;

@Repository("historyDao")
public class HistoryDaoImpl implements HistoryDao {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	StockVO stockVO;

	@Override
	public String insertNewHistory(HistoryVO historyVO) throws DataAccessException {
		stockVO = sqlSession.selectOne("mapper.history.selectStock", historyVO);//���˻�
		String history_sort = historyVO.getHistory_sort1();
		String prev_quantity;
		String present_quantity;
		String stock_quantity_40kg;
		String[] quantity;
		BigDecimal num1, num2;
		int unit;
		double bag;
		String msg;
		switch(history_sort)
		{
		case "�԰�":
			if(stockVO==null)//���� �԰�Ǵ� ���(â�� ���� ������ ��� �԰�)
			{
				historyVO.setStock_prev("0");
				sqlSession.insert("mapper.history.insertNewStock", historyVO);
				stockVO = new StockVO();
				stockVO.setStock_seq_num(historyVO.getStock_seq_num());
			}
			else//���� ��� �߰� �԰�Ǵ� ���
			{
				historyVO.setStock_prev(stockVO.getStock_quantity_40kg());
				historyVO.setStock_seq_num(stockVO.getStock_seq_num());
			}
			prev_quantity = historyVO.getStock_prev();
			num1 = new BigDecimal(prev_quantity);
			num2 = new BigDecimal(historyVO.getHistory_quantity());
			present_quantity = num1.add(num2).toString();
			quantity = present_quantity.split("\\.");
			if(quantity.length>1)
			{
				if(quantity[1].length()==1&&Integer.parseInt(quantity[1])>=4)
				{
					num1 = new BigDecimal(present_quantity);
					num2 = new BigDecimal("0.6");
					present_quantity = num1.add(num2).toString();
				}
				else if(quantity[1].length()==2&&Integer.parseInt(quantity[1])>=40)
				{
					num1 = new BigDecimal(present_quantity);
					num2 = new BigDecimal("0.6");
					present_quantity = num1.add(num2).toString();
				}
			}
			historyVO.setStock_present(present_quantity);
			stockVO.setStock_quantity_40kg(present_quantity);
			
			//�ܷ��� ���� ���� ��� ����
			stock_quantity_40kg = stockVO.getStock_quantity_40kg();
			quantity = stock_quantity_40kg.split("\\."); //¥���� Ȯ�� quantity[0]�� 40kg���밳��, quantity[1]�� ¥����(kg)
			unit = Integer.parseInt(historyVO.getStock_unit());
			
			if(quantity==null||quantity.length<2)//������ ������ ���(¥������ �������)
			{
				bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
				bag = Math.ceil(bag);
			}
			else //������ �Ǽ��ϰ�� (¥������ �������)
			{
				double total;
				total = (Double.parseDouble(quantity[0])*40)+Double.parseDouble(quantity[1]);//��kg
				bag = total/(double)unit;
				bag = Math.ceil(bag);
			}
			stockVO.setStock_quantity_bag(Integer.toString((int)bag));
			//�ܷ��� ���� ���� ��� ��
			
			sqlSession.update("mapper.history.updateStock", stockVO);
			sqlSession.insert("mapper.history.insertNewHistory", historyVO);
			msg = "��� ������ ���ƽ��ϴ�. ��� ���� ���� â���� �̵��մϴ�.";
			break;
		case "���":
			if(stockVO==null)
			{
				msg = "â�� �Է��Ͻ� ��� �����ϴ�. �ٽ� �õ����ּ���.";
				break;
			}
			historyVO.setStock_seq_num(stockVO.getStock_seq_num());
			historyVO.setStock_prev(stockVO.getStock_quantity_40kg());
			prev_quantity = historyVO.getStock_prev();
			num1 = new BigDecimal(prev_quantity);
			num2 = new BigDecimal(historyVO.getHistory_quantity());
			present_quantity = num1.subtract(num2).toString();
			if(Double.parseDouble(present_quantity)<0)
			{
				msg = "�Է��Ͻ� ����� �̿������� Ů�ϴ�. �ٽ� �õ����ּ���.";
				break;
			}
			quantity = present_quantity.split("\\.");
			if(quantity.length>1)
			{
				if(quantity[1].length()==1&&Integer.parseInt(quantity[1])>=4)
				{
					num1 = new BigDecimal(present_quantity);
					num2 = new BigDecimal("0.6");
					present_quantity = num1.subtract(num2).toString();
				}
				else if(quantity[1].length()==2&&Integer.parseInt(quantity[1])>=40)
				{
					num1 = new BigDecimal(present_quantity);
					num2 = new BigDecimal("0.6");
					present_quantity = num1.subtract(num2).toString();
				}
			}
			historyVO.setStock_present(present_quantity);
			stockVO.setStock_quantity_40kg(present_quantity);
			
			//�ܷ��� ���� ���� ��� ����
			stock_quantity_40kg = stockVO.getStock_quantity_40kg();
			quantity = stock_quantity_40kg.split("\\."); //¥���� Ȯ�� quantity[0]�� 40kg���밳��, quantity[1]�� ¥����(kg)
			unit = Integer.parseInt(historyVO.getStock_unit());
			
			if(quantity==null||quantity.length<2)//������ ������ ���(¥������ �������)
			{
				bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
				bag = Math.ceil(bag);
			}
			else //������ �Ǽ��ϰ�� (¥������ �������)
			{
				double total;
				total = (Double.parseDouble(quantity[0])*40)+Double.parseDouble(quantity[1]);//��kg
				bag = total/(double)unit;
				bag = Math.ceil(bag);
			}
			stockVO.setStock_quantity_bag(Integer.toString((int)bag));
			//�ܷ��� ���� ���� ��� ��
			
			sqlSession.update("mapper.history.updateStock", stockVO);
			sqlSession.insert("mapper.history.insertNewHistory", historyVO);
			msg = "��� ������ ���ƽ��ϴ�. ��� ���� ���� â���� �̵��մϴ�.";
			break;
		default : //����
			if(stockVO==null)
			{
				msg = "â�� �Է��Ͻ� ��� �����ϴ�. �ٽ� �õ����ּ���.";
				break;
			}
			if(Double.parseDouble(stockVO.getStock_quantity_40kg())<Double.parseDouble(historyVO.getHistory_quantity()))
			{
				msg = "�Է��Ͻ� �������� �̿������� Ů�ϴ�. �ٽ� �õ����ּ���.";
				break;
			}
			historyVO.setStock_seq_num(stockVO.getStock_seq_num());
			historyVO.setStock_prev(stockVO.getStock_quantity_40kg());
			historyVO.setStock_present(stockVO.getStock_quantity_40kg());
			sqlSession.update("mapper.history.updateStock", stockVO);
			sqlSession.insert("mapper.history.insertNewHistory", historyVO);
			msg = "��� ������ ���ƽ��ϴ�. ��� ���� ���� â���� �̵��մϴ�.";
		}
		return msg;		
	}

	@Override
	public List<HistoryVO> selectList(HistoryVO historyVO) throws DataAccessException {
		return sqlSession.selectList("mapper.history.selectList", historyVO);
	}

	@Override
	public void delete(HistoryVO historyVO) throws DataAccessException {
		historyVO = sqlSession.selectOne("mapper.history.selectHistory", historyVO);
		stockVO.setStock_seq_num(historyVO.getStock_seq_num());
		String history_sort = historyVO.getHistory_sort1();
		BigDecimal num1,num2;
		String stock_quantity_40kg;
		String[] quantity;
		int unit;
		double bag;
		switch(history_sort)
		{
		case "�԰�":
			num2 = new BigDecimal(historyVO.getHistory_quantity());
			num1 = new BigDecimal(historyVO.getStock_present());
			stock_quantity_40kg = num1.subtract(num2).toString();
			quantity = stock_quantity_40kg.split("\\.");
			if(quantity.length>1)
			{
				if(quantity[1].length()==1&&Integer.parseInt(quantity[1])>=4)
				{
					num1 = new BigDecimal(stock_quantity_40kg);
					num2 = new BigDecimal("0.6");
					stock_quantity_40kg = num1.subtract(num2).toString();
				}
				else if(quantity[1].length()==2&&Integer.parseInt(quantity[1])>=40)
				{
					num1 = new BigDecimal(stock_quantity_40kg);
					num2 = new BigDecimal("0.6");
					stock_quantity_40kg = num1.subtract(num2).toString();
				}
			}
			stockVO.setStock_quantity_40kg(stock_quantity_40kg);
			//�ܷ��� ���� ���� ��� ����
			stock_quantity_40kg = stockVO.getStock_quantity_40kg();
			quantity = stock_quantity_40kg.split("\\."); //¥���� Ȯ�� quantity[0]�� 40kg���밳��, quantity[1]�� ¥����(kg)
			unit = Integer.parseInt(historyVO.getStock_unit());
			
			if(quantity==null||quantity.length<2)//������ ������ ���(¥������ �������)
			{
				bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
				bag = Math.ceil(bag);
			}
			else //������ �Ǽ��ϰ�� (¥������ �������)
			{
				double total;
				total = (Double.parseDouble(quantity[0])*40)+Double.parseDouble(quantity[1]);//��kg
				bag = total/(double)unit;
				bag = Math.ceil(bag);
			}
			stockVO.setStock_quantity_bag(Integer.toString((int)bag));
			//�ܷ��� ���� ���� ��� ��
			sqlSession.update("mapper.history.updateStock", stockVO);
			break;
		case "���":
			num2 = new BigDecimal(historyVO.getHistory_quantity());
			num1 = new BigDecimal(historyVO.getStock_present());
			stock_quantity_40kg = num1.add(num2).toString();
			quantity = stock_quantity_40kg.split("\\.");
			if(quantity.length>1)
			{
				if(quantity[1].length()==1&&Integer.parseInt(quantity[1])>=4)
				{
					num1 = new BigDecimal(stock_quantity_40kg);
					num2 = new BigDecimal("0.6");
					stock_quantity_40kg = num1.add(num2).toString();
				}
				else if(quantity[1].length()==2&&Integer.parseInt(quantity[1])>=40)
				{
					num1 = new BigDecimal(stock_quantity_40kg);
					num2 = new BigDecimal("0.6");
					stock_quantity_40kg = num1.add(num2).toString();
				}
			}
			stockVO.setStock_quantity_40kg(stock_quantity_40kg);
			//�ܷ��� ���� ���� ��� ����
			stock_quantity_40kg = stockVO.getStock_quantity_40kg();
			quantity = stock_quantity_40kg.split("\\."); //¥���� Ȯ�� quantity[0]�� 40kg���밳��, quantity[1]�� ¥����(kg)
			unit = Integer.parseInt(historyVO.getStock_unit());
			
			if(quantity==null||quantity.length<2)//������ ������ ���(¥������ �������)
			{
				bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
				bag = Math.ceil(bag);
			}
			else //������ �Ǽ��ϰ�� (¥������ �������)
			{
				double total;
				total = (Double.parseDouble(quantity[0])*40)+Double.parseDouble(quantity[1]);//��kg
				bag = total/(double)unit;
				bag = Math.ceil(bag);
			}
			stockVO.setStock_quantity_bag(Integer.toString((int)bag));
			//�ܷ��� ���� ���� ��� ��
			sqlSession.update("mapper.history.updateStock", stockVO);
			break;
		}
		sqlSession.delete("mapper.history.deleteHistory", historyVO);
	}

}
