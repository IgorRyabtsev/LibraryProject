package main.java.controllers.DAO.Oracle.condition;


import main.java.controllers.DAO.condition.AuthorCondition;

/**
 * Created by igor on 14.04.16.
 */
public class OracleAuthorCondition extends AuthorCondition {
    @Override
    public String getExpression() {
        StringBuffer expression = new StringBuffer();
        String tmp;
        if((tmp = getName_f()) != null) expression.append(" name_f='" + tmp+"'");
        if((tmp = getName_s()) != null) expression.append(" AND name_s='" + tmp+"'");
        if((tmp = getName_p()) != null) expression.append(" AND name_p='" + tmp+"'");
        if((tmp = getYear_a()) != null) expression.append(" AND year_a='" + tmp+"'");
        return expression.toString();
    }
}
