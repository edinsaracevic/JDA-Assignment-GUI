package code.model;

public class Person {

    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private String address;
    private Integer salary;

    public Person() {
        super();
    }

    public Person(String name, String surname, Integer age, String address, Integer salary) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    public Person(Integer id, String name, String surname, Integer age, String address, Integer salary) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.salary = salary;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id +", "+
                "\t" + name + ",   " +
                "\t" + surname + ",   " +
                "\t" + age + ",   " +
                "\t" + address + ",    " +
                "\t" + salary;
    }

}
