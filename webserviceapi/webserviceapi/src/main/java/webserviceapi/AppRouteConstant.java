package webserviceapi;

import webserviceapi.shared.dto.RouteDto;

import java.util.ArrayList;
import java.util.List;

public class AppRouteConstant {
    public static final String USER = "users";
    public static final String USER_MANAGE = "user_manage";
    public static final String USER_GROUP = "user_group";
    public static final String ROLE = "role";
    public static final String ROLE_GRANT = "grant_role";
    public static final String ROLE_MANAGE = "role_manage";
    public static final String ROLE_AUTHORITY = "role_authority";
    public static final String GROCERY = "grocery";
    public static final String GROCERY_DASHBOARD = "grocery_dashboard";
    public static final String GROCERY_HISTORY = "grocery_history";
    public static final String PRODUCT = "product";
    public static final String PRODUCT_DASHBOARD = "product_dashboard";
    public static final String PRODUCT_HISTORY = "product_history";

    public static final String[] routes = {
            USER,
            USER_MANAGE,
            USER_GROUP,
            ROLE,
            ROLE_GRANT,
            ROLE_MANAGE,
            ROLE_AUTHORITY,
            GROCERY,
            GROCERY_DASHBOARD,
            GROCERY_HISTORY,
            PRODUCT,
            PRODUCT_DASHBOARD,
            PRODUCT_HISTORY
    };

    public static List<RouteDto> routesAuthority = new ArrayList<>();
}
