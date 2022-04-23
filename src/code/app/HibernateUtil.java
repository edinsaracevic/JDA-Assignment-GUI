package code.app;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory SESSION_FACTORY = createSessionFactory();

    public static SessionFactory createSessionFactory(){
        if(SESSION_FACTORY == null){
            try{
                Configuration configuration = new Configuration();
                configuration.configure();
                SESSION_FACTORY = configuration.buildSessionFactory();
            }catch(Throwable e){
                System.err.println("SessionFactory creation failed" + e.getMessage());
                throw new ExceptionInInitializerError(e.getMessage());
            }
        }
        return SESSION_FACTORY;
    }
    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }

    public static void close(){
        getSessionFactory().close();
    }

}
