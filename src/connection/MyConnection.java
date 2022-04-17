package connection;

public enum MyConnection {

    URL("jdbc:mysql://localhost:3306/jda?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"),
    USERNAME("root"),
    PASSWORD("root");

    private String value;

    private MyConnection(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
