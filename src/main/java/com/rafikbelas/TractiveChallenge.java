package com.rafikbelas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static java.util.stream.Collectors.*;

public class TractiveChallenge {

    public static void main(String[] args) throws JsonProcessingException {
        String[] purchasedProducts = {"CVCD", "SDFD", "DDDF", "SDFD"};

        String products = "{\"CVCD\": {\"version\": 1,\"edition\": \"X\"},\"SDFD\": {\"version\": 2,\"edition\":\n" +
                "\"Z\"},\"DDDF\": {\"version\": 1}}\n";

        HashMap<String, HashMap> result = aggregate(products, purchasedProducts);

        System.out.println(result.values());
    }

    private static HashMap<String, HashMap> aggregate(String products, String[] purchasedProducts)
            throws JsonProcessingException {

        HashMap<String, HashMap> productsInfo = convertToMap(products);

        Map<String, Long> purchasedQuantities = convertToProductQuantityMap(purchasedProducts);

        productsInfo.forEach((code, info) -> {
            info.put("quantity", purchasedQuantities.get(code));
        });

        return productsInfo;
    }

    private static Map<String, Long> convertToProductQuantityMap(String[] purchasedProducts) {
        return Arrays.stream(purchasedProducts)
                .collect(groupingBy(s -> s, counting()));
    }

    private static HashMap convertToMap(String products) throws JsonProcessingException {
        return new ObjectMapper().readValue(products, HashMap.class);
    }
}