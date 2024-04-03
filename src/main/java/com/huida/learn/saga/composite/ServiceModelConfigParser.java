package com.huida.learn.saga.composite;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务编排模型解析
 * @author: huida
 * @date: 2024/3/19
 **/

@Slf4j
@Component
public class ServiceModelConfigParser implements InitializingBean {

    @Value("${model.json.path}")
    private String configFile;

    private Map<String, List<Step>> models = new HashMap<>();


    @Override
    public void afterPropertiesSet()  {
        JSONParser parser = new JSONParser();

        try {
            InputStream inputStream = ServiceModelConfigParser.class.getClassLoader().getResourceAsStream(configFile);
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONArray configArray = (JSONArray) obj;

            for (Object modelObj : configArray) {
                JSONObject model = (JSONObject) modelObj;
                String serviceName = (String) model.get("serviceName");
                JSONArray stepsArray = (JSONArray) model.get("steps");
                List<Step> steps = new ArrayList<>();

                for (Object stepObj : stepsArray) {
                    JSONObject step = (JSONObject) stepObj;
                    String service = (String) step.get("service");
                    JSONObject argsObject = (JSONObject) step.get("args");
                    Map<String, String> args = new HashMap<>();
                    for (Object key : argsObject.keySet()) {
                        args.put((String) key, (String) argsObject.get(key));
                    }
                    String compensate = (String) step.get("compensate");
                    String condition = (String) step.get("condition");
                    steps.add(new Step(service, args, compensate, condition));
                }

                models.put(serviceName, steps);
            }
        } catch (IOException | ParseException e) {
            log.error("解析服务编排模型失败", e);
        }
    }

    public List<Step> getModelByServiceName(String serviceName) {
        return models.get(serviceName);
    }

}
