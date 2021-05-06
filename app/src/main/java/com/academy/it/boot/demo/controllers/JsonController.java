package com.academy.it.boot.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonController extends HttpServlet {
    protected void toJson(Object obj, HttpServletResponse resp) throws ServletException, IOException {
        obj = (obj instanceof Optional) ? ((Optional<?>) obj).orElse(null) : obj;
        ObjectMapper mapper = new ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String json = mapper.writeValueAsString(obj);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    @SneakyThrows
    protected  <T> T toObject(Class<T> clazz, HttpServletRequest req) {
        String json = req.getReader().lines().collect(Collectors.joining());
//        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator
//                .builder()
//                .allowIfSubType(Map.class)
//                .allowIfSubType(List.class)
//                .allowIfSubType(Set.class)
//                .allowIfSubType(Number.class)
//                .build();
        ObjectMapper mapper = new ObjectMapper();
//        JsonMapper.builder()
//                .activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
//                .build();

        return mapper.readValue(json, clazz);
    }
}
