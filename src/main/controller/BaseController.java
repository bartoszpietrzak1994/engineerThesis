package controller;

import org.springframework.context.ApplicationContext;

public abstract class BaseController
{
    protected ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }
}
