package com.cd.LiderIT_test.parsers;

import com.cd.LiderIT_test.exception.AuthorException;
import com.cd.LiderIT_test.exception.BookException;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class Parser {

    public Map<String, String> bookValidate(String request) throws BookException {

        HashMap<String, String> json = new HashMap<String, String>() {{
            List<String> valuesAndKey = new ArrayList<>(Arrays.asList(request.split("&")));
            valuesAndKey.forEach(x -> put(x.substring(0, x.indexOf("=")), x.substring(x.indexOf("=") + 1)));
        }};

        if (request != null) {
            for (String k : json.keySet()) {
                switch (k) {
                    case "name":
                        break;
                    case "author_id":
                        try {
                            Integer.parseInt(json.get(k));
                            break;
                        } catch (Exception e) {
                            throw new BookException("Parsing error for author_id field, field must be integer data type. " +
                                    "Please check the input is correct");
                        }
                    case "availability":
                        this.parseBooleanValue(json.get(k));
                        break;

                    case "number":
                        try {
                            Integer.parseInt(json.get(k));
                            break;
                        } catch (Exception e) {
                            throw new BookException("Parsing error for number field, field must be integer data type. " +
                                    "Please check the input is correct");
                        }
                    default:
                        throw new BookException("An incorrect key is specified, please check the correctness of the entered keys");
                }
            }
            if (json.get("name") == null)
                throw new BookException("The required parameter name was not passed in the request");
            if (json.get("availability") == null)
                throw new BookException("The required parameter name was not passed in the request");
            return json;
        }
        throw new BookException("Bad request");
    }

    public Map<String, String> validateAuthor(String request) throws AuthorException {

        HashMap<String, String> json = new HashMap<String, String>() {{
            List<String> valuesAndKey = new ArrayList<>(Arrays.asList(request.split("&")));
            valuesAndKey.forEach(x -> put(x.substring(0, x.indexOf("=")), x.substring(x.indexOf("=") + 1)));
        }};

        HashMap<String, String> map = new HashMap<>();

        if (request != null) {
            for (String k : json.keySet()) {
                switch (k) {
                    case "name":
                        map.put(k, json.get(k));
                        break;
                    case "patronymic":
                        map.put(k, json.get(k));
                        break;
                    case "surname":
                        map.put(k, json.get(k));
                        break;
                    case "birthday":
                        try {
                            java.sql.Date.valueOf(json.get(k));
                            map.put("birthday", json.get(k));
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new AuthorException("Error parsing the birthday field, the date must be in the Year-Month-Day format. " +
                                    "Please check the correctness of the entry.");
                        }
                    case "biography":
                        map.put(k, json.get(k));
                        break;
                    default:
                        throw new AuthorException("An incorrect key is specified, please check the correctness of the entered keys");
                }
            }
            switch (map.size()) {
                case 4:
                    return map;
                case 5:
                    return map;
            }

        }
        throw new AuthorException("There are not enough keys in the request, please check its integrity");
    }

    public Boolean parseBooleanValue(String value) throws BookException {
        if (value.equals("true")) {
            return true;
        }
        if (value.equals("false")) {
            return false;
        }
        throw new BookException("Parsing error for availability field, field must be boolean data type. " +
                "Please check the input is correct");
    }
}
