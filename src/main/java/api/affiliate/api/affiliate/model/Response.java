package api.affiliate.api.affiliate.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class Response {

    private final Date timestamp = new Date();

    public Object ok(String message, String key, Object value) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);

        Map<Object, Object> data = new HashMap<>();

        data.put(key, value);
        res.put("data", data);

        return res;
    }

    public Object success(String message) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);

        return res;


    }

    public Object failed(String message) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.EXPECTATION_FAILED.value());
        res.put("message", message);

        return res;
    }

}
