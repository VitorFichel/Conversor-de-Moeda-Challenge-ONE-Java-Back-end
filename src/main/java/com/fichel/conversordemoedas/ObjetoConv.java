package com.fichel.conversordemoedas;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class ObjetoConv {

    @SerializedName("base_code")
    private String baseCode;

    @SerializedName("conversion_rates")
    private Map<String, Double> conversionRates;

    public String getBaseCode() {
        return baseCode;
    }

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public double converterPara(String currencyCode, double valor) {
        Double taxa = conversionRates.get(currencyCode);
        if (taxa == null) {
            throw new IllegalArgumentException("Moeda não encontrada: " + currencyCode);
        }
        return valor * taxa;
    }
}
