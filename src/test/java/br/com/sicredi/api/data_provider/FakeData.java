package br.com.sicredi.api.data_provider;

import net.datafaker.Faker;
import org.bson.codecs.ObjectIdGenerator;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class FakeData {

    private final Faker faker;

    public FakeData(Locale locale) {
        this.faker = new Faker(locale);
    }

    public FakeData() {
        this.faker = new Faker(new Locale("pt-br"));
    }

    public String generateFunnyName() {
        return faker.funnyName().name();
    }

    public int generateRandomInt(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    public int generateRandomInt(int maxValue) {
        return this.generateRandomInt(1, maxValue);
    }

    public String generateCpf(boolean specialChars) {
        return faker.cpf().valid(specialChars);
    }

    public String generateCpf() {
        return faker.cpf().valid(false);
    }

    public String generateInvalidCpf(boolean specialChars) {
        return faker.cpf().invalid(specialChars);
    }

    public String generateInvalidCpf() {
        return faker.cpf().invalid(false);
    }

    public String generatedId() {
        return new ObjectIdGenerator().generate().toString();
    }
}
