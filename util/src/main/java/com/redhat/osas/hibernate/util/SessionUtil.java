package com.redhat.osas.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//import org.hibernate.service.ServiceRegistryBuilder;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.jboss.logging.Logger;

public class SessionUtil {
    private static final SessionUtil instance = new SessionUtil();
    private /*-- final --*/ SessionFactory factory;
    private static final String CONFIG_NAME = "/configuration.properties";

    Logger logger = Logger.getLogger(this.getClass());

    private SessionUtil() {
	   // A SessionFactory is set up once for an application!
	   final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		  	.configure() // configures settings from hibernate.cfg.xml
		  	.build();
              
	   try {
	       	factory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
	   }
	   catch (Exception e) {
		  // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
		  // so destroy it manually.
		  StandardServiceRegistryBuilder.destroy( registry );
	   }
    }
/*      Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
        srBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
        factory = configuration.buildSessionFactory(serviceRegistry); 
    }  -- */


    public static Session getSession() {
        return getInstance().factory.openSession();
    }

    private static SessionUtil getInstance() {
        return instance;
    }
}
