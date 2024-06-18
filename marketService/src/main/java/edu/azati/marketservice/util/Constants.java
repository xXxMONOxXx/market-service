package edu.azati.marketservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public final String BROKER_NAME_TOPIC = "TOPIC";

    public final String ENTITY_CREATED = "Entity created";

    public final String ENTITY_DELETED = "Entity deleted";

    public final String ENTITY_UPDATED = "Entity updated";

    public final String ENTITY_FOUND = "Entity found";

    public final String PAGE_CREATED = "Page of entities created";

    public final String PAYMENT_SUCCESS = "Payment was successful";

    public final String FOUND_ALL = "Got a full list of entities";

    public final String IMAGE_ADDED = "Image was added";

    public final String IMAGE_DELETED = "Image was deleted";

    public final String IMAGE_RESOURCE_PATH = "/";

    public final String GROUPS = "groups";

    public final String REALM_ACCESS_CLAIM = "realm_access";

    public final String ROLES_CLAIM = "roles";

    public final String ROLES_PATH = "/roles*";

    public final String STATUSES_PATH = "/statuses*";

    public final String DEFAULT_PATH = "/*";

    public final String ADDRESSES_PATH = "/addresses*";

    public final String CATEGORIES_PATH = "/categories*";

    public final String PAYMENTS_PATH = "/payments*";

    public final String PRODUCTS_PATH = "/products*";

    public final String PRODUCT_ORDERS_PATH = "/products-orders*";

    public final String PRODUCT_REVIEWS_PATH = "/products-orders*";

    public final String SERVICES_PATH = "/services*";

    public final String SERVICE_ORDERS_PATH = "/service-orders*";

    public final String SERVICE_REVIEWS_PATH = "/service-reviews*";

    public final String USERS_PATH = "/users*";

    public final String PDF_ORDER_CONVERTER_PATH = "/pdf/order/*";

    public final String OAUTH_SCHEME_NAME = "oAuth (OAuth2, authorizationCode)";

    public final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    public final String FAILED_VERIFICATION_MESSAGE = "Verification failed";

    public final String SUCCESS_VERIFICATION_MESSAGE = "Verification success";

    public final String ACCESS_TOKEN_CREATED = "Access token created";
}
