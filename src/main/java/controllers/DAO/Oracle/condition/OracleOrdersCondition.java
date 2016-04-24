package main.java.controllers.DAO.Oracle.condition;

import main.java.controllers.DAO.condition.OrdersCondition;

/**
 * Created by igor on 14.04.16.
 */
public class OracleOrdersCondition extends OrdersCondition {
    private final String table = "orders.";
    @Override
    public String getExpression() {
        StringBuffer expression = new StringBuffer();
        String tmp;
        if((tmp = getId_o()) != null) expression.append(" AND " + table + "id_o=" + tmp);
        if((tmp = getInstance_id()) != null) expression.append(" AND " + table + "instance_id=" + tmp);
        if((tmp = getReader_id()) != null) expression.append(" AND " + table + "reader_id=" + tmp);
        return expression.toString();
    }
}
