package ru.agavshin.entity;

public class Demo {

    private String name;

    private int number;

    public Demo() {

    }

    public Demo(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static Demo createSample() {
        int number = (int) (1 + Math.random() * 10);
        return new Demo("Demo_" + number, number);
    }

    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
