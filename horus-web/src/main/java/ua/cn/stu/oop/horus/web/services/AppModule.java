package ua.cn.stu.oop.horus.web.services;

import org.apache.shiro.realm.*;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.*;
import org.tynamo.security.services.impl.SecurityFilterChain;

/**
 *
 * @author alex
 */
@SubModule(value = {SecurityModule.class})
public class AppModule {

    public static void bind(ServiceBinder binder) {
        binder.bind(AuthorizingRealm.class, UserRealm.class).withId(UserRealm.class.getSimpleName());
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {

        configuration.add(SymbolConstants.CHARSET, "UTF-8");
        
        configuration.add(SymbolConstants.START_PAGE_NAME, "index");
        
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,ru");

        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");

        configuration.add(SymbolConstants.FORM_CLIENT_LOGIC_ENABLED, "false");

        configuration.add(SymbolConstants.APPLICATION_VERSION, "0.1");

        configuration.add(SymbolConstants.APPLICATION_CATALOG, "i18n/app.properties");        
        
        configuration.add(SecuritySymbols.LOGIN_URL, "user/login");
    }

    public static void contributeSecurityConfiguration(Configuration<SecurityFilterChain> configuration,
            SecurityFilterChainFactory factory) {
        configuration.add(factory.createChain("user/registration**").add(factory.anon()).build());
        configuration.add(factory.createChain("user/login**").add(factory.anon()).build());
    }

    public static void contributeWebSecurityManager(Configuration<Realm> configuration, @InjectService("UserRealm") AuthorizingRealm userRealm) {
        configuration.add(userRealm);
    }
}
