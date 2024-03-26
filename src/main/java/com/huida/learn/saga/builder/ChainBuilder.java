package com.huida.learn.saga.builder;
import com.huida.learn.saga.journal.handler.Handler;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huidamao
 * @date: 2024/3/18
 */
@Component
public class ChainBuilder {
    public Handler buildChainFromXML(String xmlConfigFile) {
        List<Handler> handlers = new ArrayList<>();

        try {
            // 创建DOM解析器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 创建DOM解析器
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 解析XML文件
            Document doc = builder.parse(xmlConfigFile);

            // 获取根元素
            Element root = doc.getDocumentElement();

            // 获取所有handler元素
            NodeList handlerNodes = root.getElementsByTagName("handler");

            // 遍历handler元素，创建责任链节点
            for (int i = 0; i < handlerNodes.getLength(); i++) {
                Node handlerNode = handlerNodes.item(i);
                if (handlerNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element handlerElement = (Element) handlerNode;
                    String handlerClassName = handlerElement.getAttribute("class");
                    // 根据类名反射创建责任链节点实例
                    Class<?> handlerClass = Class.forName(handlerClassName);
                    Handler handler = (Handler) handlerClass.getDeclaredConstructor().newInstance();
                    handlers.add(handler);
                }
            }

            // 将责任链节点按照顺序连接起来
            for (int i = 0; i < handlers.size() - 1; i++) {
                Handler currentHandler = handlers.get(i);
                Handler nextHandler = handlers.get(i + 1);
                currentHandler.setNextHandler(nextHandler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返回责任链的头部节点
        return handlers.isEmpty() ? null : handlers.get(0);
    }
}
