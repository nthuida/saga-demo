package com.huida.learn.saga.builder;

import com.huida.learn.saga.journal.handler.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 构造接入处理链
 * @author huidamao
 * @date: 2024/3/18
 */
@Configuration
public class ChainBuilder {

    private final ApplicationContext applicationContext;

    @Value("${chain.xml.path}")
    private String chainXmlPath;

    @Autowired
    public ChainBuilder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public Handler chainHandler() {
        List<Handler> handlers = new ArrayList<>();
        Set<String> handlerMap = new HashSet<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(chainXmlPath);

            Element root = doc.getDocumentElement();
            NodeList handlerNodes = root.getElementsByTagName("handler");

            for (int i = 0; i < handlerNodes.getLength(); i++) {
                Element handlerElement = (Element) handlerNodes.item(i);
                String beanId = handlerElement.getAttribute("id");
                handlerMap.add(beanId);
            }

            for (String beanId : handlerMap) {
                Handler handler = (Handler) applicationContext.getBean(beanId);
                handlers.add(handler);
            }

            for (int i = 0; i < handlers.size() - 1; i++) {
                Handler currentHandler = handlers.get(i);
                Handler nextHandler = handlers.get(i + 1);
                currentHandler.setNextHandler(nextHandler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return handlers.isEmpty() ? null : handlers.get(0);
    }
}
