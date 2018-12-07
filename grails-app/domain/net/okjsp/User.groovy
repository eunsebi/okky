package net.okjsp

import grails.util.Environment

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired = false
	boolean accountLocked = false
	boolean passwordExpired = false
	boolean withdraw = false

    Date lastPasswordChanged = new Date()

    Date dateCreated
    Date lastUpdated
	Date dateWithdraw

    Person person
    Avatar avatar

    String createIp
    String lastUpdateIp

	String dateJoined

	String oid

    static hasMany = [loggedIns: LoggedIn, oAuthIDs: OAuthID]

	static transients = ['springSecurityService']

	static constraints = {
		username(blank: false, unique: true, size: 4..15, matches: /[a-z0-9]{4,15}/, validator: {
            if(disAllowUsernameFilter(it)) return ['default.invalid.disallow.message']
        })
		password blank: false, minSize: 4, matches: /^(?=.*[0-9])(?=.*[a-zA-Z]).*$/
        person unique: true
        avatar unique: true
        enabled bindable: true
        accountExpired bindable: false
        accountLocked bindable: false
        passwordExpired bindable: false
        lastPasswordChanged bindable: false
        loggedIns bindable: false
        createIp bindable: false, nullable: true
        lastUpdateIp nullable: true, bindable: false
		dateWithdraw bindable: false, nullable: true
		withdraw bindable: false, nullable: true
		oid bindable: false, nullable: true
	}

	static mapping = {
		password column: '`password`'

		loggedIns sort:'id', order:'desc'

		if (Environment.current == Environment.DEVELOPMENT)
			//dateJoined formula: "FORMATDATETIME(date_created, 'yyyy-MM-dd')"
			dateJoined formula: "DATE_FORMAT(date_created, '%Y-%m-%d')"

		if (Environment.current == Environment.PRODUCTION)
			dateJoined formula: "DATE_FORMAT(date_created, '%Y-%m-%d')"
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

    static boolean disAllowUsernameFilter(username) {
        return ['root','administrator','administration','rootadmin','adminroot'
            ,'system','tomcatroot','sysadmin','sysroot','manager','management','manage'].contains(username)
    }

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
