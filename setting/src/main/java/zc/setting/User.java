package zc.setting;

/**
 * @作者 zhouchao
 * @日期 2019/9/12
 * @描述
 */
public class User {

    /**
     * id : 1
     * token : 056603b6e5e3d50fe7cb833fce44f9b3
     * roleName : OPERATOR
     * userName : 13121080560
     * roleRemark : 运营人员
     */

    private int id;
    private String token;
    private String roleName;
    private String userName;
    private String roleRemark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }
}
