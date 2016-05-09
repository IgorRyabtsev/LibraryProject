package main.java.controllers.DAO.Oracle.condition;

import main.java.controllers.DAO.condition.BookCondition;

/**
 * Created by igor on 14.04.16.
 */

/**
 * Class OracleBookConditon for finding Book by condition,
 * @author igor
 */

public class OracleBookCondition extends BookCondition {
    /**
     * Return part of sql expression for finding book
     * @return part of sql expression
     */
    @Override
    public String getExpression() {
        StringBuffer expression = new StringBuffer();
        String tmp;
        if((tmp = getName_b()) != null) expression.append("name_b='" + tmp+"'");
        return expression.toString();
    }
}
