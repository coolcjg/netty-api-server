package ch9.core;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

import ch9.service.RequestParamException;
import ch9.service.ServiceException;

public abstract class ApiRequestTemplate implements ApiRequest {
    protected Logger logger;

    /**
     * API �슂泥� data
     */
    protected Map<String, String> reqData;

    /**
     * API 泥섎━寃곌낵
     */
    protected JsonObject apiResult;

    /**
     * logger �깮�꽦<br/>
     * apiResult 媛앹껜 �깮�꽦
     */
    public ApiRequestTemplate(Map<String, String> reqData) {
        this.logger = LogManager.getLogger(this.getClass());
        this.apiResult = new JsonObject();
        this.reqData = reqData;

        logger.info("request data : " + this.reqData);
    }

    public void executeService() {
        try {
            this.requestParamValidation();

            this.service();
        }
        catch (RequestParamException e) {
            logger.error(e);
            this.apiResult.addProperty("resultCode", "405");
        }
        catch (ServiceException e) {
            logger.error(e);
            this.apiResult.addProperty("resultCode", "501");
        }
    }

    public JsonObject getApiResult() {
        return this.apiResult;
    }

    @Override
    public void requestParamValidation() throws RequestParamException {
        if (getClass().getClasses().length == 0) {
            return;
        }

        // // TODO �씠嫄� 瑗쇱닔 諛붽퓭�빞 �븯�뒗�뜲..
        // for (Object item :
        // this.getClass().getClasses()[0].getEnumConstants()) {
        // RequestParam param = (RequestParam) item;
        // if (param.isMandatory() && this.reqData.get(param.toString()) ==
        // null) {
        // throw new RequestParamException(item.toString() +
        // " is not present in request param.");
        // }
        // }
    }

    public final <T extends Enum<T>> T fromValue(Class<T> paramClass, String paramValue) {
        if (paramValue == null || paramClass == null) {
            throw new IllegalArgumentException("There is no value with name '" + paramValue + " in Enum "
                    + paramClass.getClass().getName());
        }

        for (T param : paramClass.getEnumConstants()) {
            if (paramValue.equals(param.toString())) {
                return param;
            }
        }

        throw new IllegalArgumentException("There is no value with name '" + paramValue + " in Enum "
                + paramClass.getClass().getName());
    }
}
