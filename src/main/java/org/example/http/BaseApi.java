package org.example.http;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.example.config.PropertyUtil.getConfig;

public abstract class BaseApi{

    private final RequestSpecification requestSpecification;

    public BaseApi() {
        var httpConfig = HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", getConfig().connectionTimeout())
                .setParam("http.socket.timeout", getConfig().socketTimeout());
       // RestAssured.config = RestAssured.config().httpClient(httpConfig);
        requestSpecification = RestAssured.given()
                .baseUri(getConfig().baseUrl())
                .filter(new AllureRestAssured())
                .config(RestAssuredConfig.config().httpClient(httpConfig));
    }
    protected BaseApi setRequestBody(Object object) {
        this.requestSpecification.body(object);
        return this;
    }
    protected void setBasePath(String basePath){
        this.requestSpecification.basePath(basePath);
    }
    public BaseApi setContentType(ContentType contentType){
        this.requestSpecification.contentType(contentType);
        return this;
    }
    protected BaseApi setBasicAuth(String username, String password){
        this.requestSpecification.auth().preemptive().basic(username, password);
        return this;
    }
    protected void setPathParam(String paramName, Object paramValue){
        this.requestSpecification.pathParams(paramName, paramValue);
    }
    protected BaseApi logAllRequestData(){
        this.requestSpecification.filter(new RequestLoggingFilter());
        return this;
    }
    public BaseApi logSpecificRequestDetail(LogDetail logDetail){
        this.requestSpecification.filter(new RequestLoggingFilter(logDetail));
        return this;
    }
    public BaseApi logAllResponseData(){
        this.requestSpecification.filter(new ResponseLoggingFilter());
        return this;
    }
    public BaseApi logSpecificResponseDetail(LogDetail logDetail){
        this.requestSpecification.filter(new ResponseLoggingFilter(logDetail));
        return this;
    }
    public void setRedirect(boolean shouldFollowRedirect){
        this.requestSpecification.redirects().follow(shouldFollowRedirect)
                .urlEncodingEnabled(false);
    }

    protected Response sendRequest(Method methodType){
        return switch (methodType){
            case GET -> this.requestSpecification.when().get();
            case POST -> this.requestSpecification.when().post();
            case PUT -> this.requestSpecification.when().put();
            case DELETE -> this.requestSpecification.when().delete();
            case PATCH -> this.requestSpecification.when().patch();
            default -> throw new IllegalArgumentException("Input method type not supported: " + methodType);
        };

    }
}
