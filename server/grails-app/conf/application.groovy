

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'serviceinventory.UserAccount'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'serviceinventory.UserAccountRole'
grails.plugin.springsecurity.authority.className = 'serviceinventory.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/**',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	//[pattern: '/assets/**',      filters: 'none'],
	//[pattern: '/**/js/**',       filters: 'none'],
	//[pattern: '/**/css/**',      filters: 'none'],
	//[pattern: '/**/images/**',   filters: 'none'],
	//[pattern: '/**/favicon.ico', filters: 'none'],
	//[pattern: '/**',             filters: 'JOINED_FILTERS']
	//[pattern: '/api/guest/**', filters: 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor'],
	[pattern: '/api/**',       filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
	[pattern: '/**',           filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]

grails.plugin.springsecurity.rest.token.storage.jwt.secret = 'WaQ+bmwRujmkB!a+=@YkeD7gVbHbh_%b'