package com.masion.net;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class ADTest {

	public static final String BASE_DN = "dc=sugon,dc=cn";
	private Hashtable<String, String> env;
	private static String appName = "coreplatform";
	private static String userName = "zhangjfbycode";
	private static String passwd = "Dawning;123";

	public ADTest() {
		env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://10.0.31.58:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "administrator@sugon.cn");
		env.put(Context.SECURITY_CREDENTIALS, "dawning@123");
	}

	public LdapContext getConnnection() {

		LdapContext conn = null;
		try {
			conn = new InitialLdapContext(env, null);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
		}
		return conn;
	}

	//用户信息验证，主要是验证用户名称和密码
	public boolean authCfSecurityUser() throws Exception {
		boolean exist = false;
		//获取AD连接
		LdapContext ldapContext = getConnnection();
		//设置查询控制器
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		//首先查找AD中是否存在用户信息，防止用户信息在AD中删除而在数据库中没有删除时抛出异常
		String userDN = this.getUserDN();
		String filter = "(&(objectclass=person)(cn=" + userName + ")(userPassword=" + passwd + "))";
		NamingEnumeration<SearchResult> existUser = ldapContext.search(userDN, filter, sc);
		if (existUser.hasMoreElements()) {//说明AD中存在该用户信息，进行信息删除，否则不执行任何操作
			exist = true;
		}
		//关闭AD连接
		ldapContext.close();
		return exist;
	}

	//用户AD信息删除
	public void delCfSecurityUser() throws Exception {
		//获取AD连接
		LdapContext ldapContext = getConnnection();
		//设置查询控制器
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		//首先查找AD中是否存在用户信息，防止用户信息在AD中删除而在数据库中没有删除时抛出异常
		String userDN = this.getUserDN();
		NamingEnumeration<SearchResult> existUser = ldapContext.search(userDN, "(objectclass=person)", sc);
		if (existUser.hasMoreElements()) {//说明AD中存在该用户信息，进行信息删除，否则不执行任何操作
			ldapContext.destroySubcontext(getUserDN());
		}
		//如果该用户所属应用组中没有用户，也删除该组
		String appOuDN = "ou=" + appName + "," + BASE_DN;
		NamingEnumeration<SearchResult> answer = ldapContext.search(appOuDN, "(objectclass=person)", sc);
		if (!answer.hasMoreElements()) {//说明该应用组中已经没有用户
			// 删除该组
			ldapContext.destroySubcontext(appOuDN);
		}
		//关闭AD连接
		ldapContext.close();

	}

	public String getUserDN() throws Exception {

		//return "cn=" + userName + ",ou=users,ou=system";
		return "cn=" + userName + "," + "ou=" + appName + "," + BASE_DN;
	}

	public void creatDc() throws Exception {
		LdapContext ldapContext = getConnnection();
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> answer;
		try {
			answer = ldapContext.search(BASE_DN, "(&(objectclass=dcObject)(dc=sugon))", sc);
			System.out.println(answer.next());
		} catch (NamingException e) {
			// 创建DC
			Attributes attrs = new BasicAttributes(true);
			BasicAttribute objectClassAttr = new BasicAttribute("objectclass");
			objectClassAttr.add("dcObject");
			objectClassAttr.add("organization");
			attrs.put(objectClassAttr);
			attrs.put("o", "sugon");
			ldapContext.createSubcontext(BASE_DN,attrs);
		}
		ldapContext.close();

	}

	//添加用户信息
	public void addCfSecurityUser() throws Exception {
		LdapContext ldapContext = getConnnection();
		// 如果应用组不存在，新建应用组
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> answer = ldapContext.search(BASE_DN, "(&(objectclass=organizationalUnit)(ou="
				+ appName + "))", sc);
		if (!answer.hasMoreElements()) {
			// 创建一个组
			Attributes attrs = new BasicAttributes(true);
			attrs.put("objectClass", "organizationalUnit");
			attrs.put("ou", appName);
			ldapContext.createSubcontext("ou=" + appName + "," + BASE_DN, attrs);
		}
		Attributes attrs = new BasicAttributes(true);

		BasicAttribute objectClassAttr = new BasicAttribute("objectclass");
		objectClassAttr.add("organizationalPerson");
		objectClassAttr.add("person");
		objectClassAttr.add("user");
		attrs.put(objectClassAttr);

		attrs.put("cn", userName);
		attrs.put("sn", userName);
//		attrs.put("userPassword", passwd);

		System.out.println(attrs);

		ldapContext.createSubcontext(getUserDN(), attrs);
		ldapContext.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ADTest ldap = new ADTest();
		LdapContext lc = ldap.getConnnection();
		try {
			// System.out.println(ldap.authCfSecurityUser());
				ldap.addCfSecurityUser();

			//			ldap.delCfSecurityUser();

			// ldap.creatDc();
			lc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
