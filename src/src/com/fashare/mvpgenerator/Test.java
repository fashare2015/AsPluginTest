package com.fashare.mvpgenerator;

import com.intellij.openapi.components.ApplicationComponent;

import org.jetbrains.annotations.NotNull;

/**
 * Created by jinliangshan on 2017/9/30.
 */
public class Test implements ApplicationComponent {
    public Test() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "Test";
    }
}
