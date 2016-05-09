package main.java.controllers.DAO.condition;

/**
 * Created by igor on 14.04.16.
 */

/**
 * Abstract Class AuthorConditon for finding Author by condition,
 * realization is {@link main.java.controllers.DAO.Oracle.condition.OracleAuthorCondition}
 * @author igor
 */

public abstract class AuthorCondition implements Condition {
    private String id_a;
    private String name_f;
    private String name_s;
    private String name_p;
    private String year_a;

    /**
     * Function returns author_id
     * @return id_a
     */
    public String getId_a() {
        return id_a;
    }

    /**
     * Function set author_id
     * @param id_a - author new id
     */

    public void setId_a(String id_a) {
        this.id_a = id_a;
    }

    /**
     * Get author First name
     * @return author's first name
     */
    public String getName_f() {
        return name_f;
    }

    /**
     * Setting author First name
     * @param name_f
     */
    public void setName_f(String name_f) {
        this.name_f = name_f;
    }

    /**
     * Get author Second name
     * @return author's second name
     */
    public String getName_s() {
        return name_s;
    }

    /**
     * Setting author Second name
     * @param name_s
     */
    public void setName_s(String name_s) {
        this.name_s = name_s;
    }

    /**
     * Get author First name
     * @return author's patronymic
     */
    public String getName_p() {
        return name_p;
    }

    /**
     * Setting author Patronymic name
     * @param name_p
     */
    public void setName_p(String name_p) {
        this.name_p = name_p;
    }

    /**
     * Get authir birth's year
     * @return year_a
     */
    public String getYear_a() {
        return year_a;
    }

    /**
     * Setting author year of birth
     * @param year_a
     */
    public void setYear_a(String year_a) {
        this.year_a = year_a;
    }
}
