package com.summer.isnow.shior;

import com.summer.icommon.utils.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author liudongting
 * @date 2019/7/2 17:58
 *     * 凭证匹配器
 */
public class DBCredentialsMatcher extends HashedCredentialsMatcher {

    private String hashAlgorithm;
    private int hashIterations;
    private boolean hashSalted;
    private boolean storedCredentialsHexEncoded;

    public DBCredentialsMatcher(String hashAlgorithm,int hashIterations) {
        this.hashAlgorithm = hashAlgorithm;
        this.hashSalted = true;
        this.hashIterations = hashIterations;
        this.storedCredentialsHexEncoded = true;
        super.setHashAlgorithmName(hashAlgorithm);
        super.setHashIterations(hashIterations);
        super.setStoredCredentialsHexEncoded(true);
        super.setHashSalted(true);
    }



    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

//        Object accountCredentials = getCredentials(info);
//        Object stored = authcToken.getPrincipal().
//        String salt = stored.toString();
//        Object tokenCredentials = encrypt(String.valueOf(token.getPassword()),salt);
        //       boolean matches = equals(tokenCredentials, accountCredentials);
        boolean matches = super.doCredentialsMatch(authcToken, info);
        if (!matches) {
            //todo  密码错误次数加一
        }
        return matches;
    }

    /**
     * MD5加密---md5转为大写
     */
    private String encrypt(String data,String salt) {
        if (StringUtils.isBlank(data)) {
            return StringUtils.EMPTY;
        }
        return   new Md5Hash(data,salt,1).toHex();
    }

    public static void main(String[] args) {

    }
}
