package model;

public class Person {

    private int personId;
    private String personName;
    private String personSurname;
    private int personAge;
    private String personAddress;
    private int personSalary;

    public Person() {
        super();
    }

    public Person(int personId, String personName, String personSurname, int personAge, String personAddress, int personSalary) {
        this.personId = personId;
        this.personName = personName;
        this.personSurname = personSurname;
        this.personAge = personAge;
        this.personAddress = personAddress;
        this.personSalary = personSalary;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }

    public int getPersonAge() {
        return personAge;
    }

    public void setPersonAge(int personAge) {
        this.personAge = personAge;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public int getPersonSalary() {
        return personSalary;
    }

    public void setPersonSalary(int personSalary) {
        this.personSalary = personSalary;
    }

    @Override
    public String toString() {
        return personId +", "+
                "\t" + personName + ",   " +
                "\t" + personSurname + ",   " +
                "\t" + personAge + ",   " +
                "\t" + personAddress + ",    " +
                "\t" + personSalary;
    }

}
