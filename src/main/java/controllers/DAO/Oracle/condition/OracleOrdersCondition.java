package main.java.controllers.DAO.Oracle.condition;

import main.java.controllers.DAO.condition.OrdersCondition;

/**
 * Created by igor on 14.04.16.
 */

/**
 * Class OracleOrdersConditon for finding Orders by condition,
 * @author igor
 */

public class OracleOrdersCondition extends OrdersCondition {
    /**
     * Return part of sql expression for finding orders
     * @return part of sql expression
     */
    @Override
    public String getExpression() {
        StringBuffer expression = new StringBuffer();
        String tmp;
        if((tmp = getId_o()) != null) expression.append(" AND " +  "id_o=" + tmp);
        if((tmp = getInstance_id()) != null) expression.append(" AND " + "instance_id=" + tmp);
        if((tmp = getReader_id()) != null) expression.append(" AND " + "reader_id=" + tmp);
        return expression.toString();
    }
}
