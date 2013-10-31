package org.farmer.permission;

import org.farmer.query.GetOp;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-31
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public class Authorize {
    private GetOp get = null;

    public Authorize() throws IOException{
        get = new GetOp(ConstantAuthorication.AUTHORIZE_TABLE);
    }

    /**
     * Is access?
     * @return
     */
    public boolean checkAccess(Authentication user) throws IOException{
        String password = get.fetchOne(user.getUser(),"info","password");
        if(password!=null && password.equals(user.getPassword())){
            return true;
        }else{
            return false;
        }
    }
}
