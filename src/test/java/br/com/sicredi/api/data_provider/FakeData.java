package br.com.sicredi.api.data_provider;

import net.datafaker.Faker;
import org.bson.codecs.ObjectIdGenerator;

import java.util.Locale;

public class FakeData {

    private final Faker faker;
    private static FakeData instance;

    private FakeData(Locale locale) {
        this.faker = new Faker(locale);
    }

    private FakeData() {
        this.faker = new Faker(new Locale("pt-br"));
    }

    public static FakeData getInstance() {
        if (instance == null)
            instance = new FakeData();
        return instance;
    }

    public static FakeData getInstance(Locale locale) {
        if (instance == null)
            instance = new FakeData(locale);
        return instance;
    }

    public String generateFunnyName() {
        return faker.funnyName().name();
    }

    public int generateRandomInt(int minValue, int maxValue) {
        return faker.number().numberBetween(minValue, maxValue);
    }

    public int generateRandomInt(int maxValue) {
        return this.generateRandomInt(1, maxValue);
    }

    public String generateCpf(boolean specialChars) {
        return faker.cpf().valid(specialChars);
    }

    public String generateCpf() {
        return generateCpf(false);
    }

    public String generateInvalidCpf(boolean specialChars) {
        return faker.cpf().invalid(specialChars);
    }

    public String generateInvalidCpf() {
        return generateInvalidCpf(false);
    }

    public String generatedId() {
        return new ObjectIdGenerator().generate().toString();
    }
}
