package com.erp.mastro.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@PropertySource("classpath:MastroFileDB.properties")
public class MastroFileStoreAutowiredPipes {

    public static MastroFileStoreAutowiredPipes instance;
    @Autowired
    private Environment env;

    @PostConstruct
    private void init() {
        MastroFileStoreAutowiredPipes.instance = this;
    }

    public Environment getEnv() {
        return env;
    }

}
