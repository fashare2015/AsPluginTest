package com.fashare.mvpgenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * Created by jinliangshan on 16/11/23.
 */
public class GeneratorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Messages.showMessageDialog("Hello World !", "Information", Messages.getInformationIcon());
    }
}
