package org.example.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DataSource {

    Configuration configuration;
    SessionFactory sessionFactory;
    //Session session;


    public DataSource(){
        this.configuration = new Configuration().configure();
        this.sessionFactory = configuration.buildSessionFactory();
    }

    public Session openSession(){
        return sessionFactory.openSession();
    }
}
