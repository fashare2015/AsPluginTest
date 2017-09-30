package com.fashare.mvpgenerator;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.JBColor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Component;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

/**
 * Created by jinliangshan on 2017/9/30.
 */
public class TestLineMarkerProvider extends RelatedItemLineMarkerProvider {
    private static final String TAG = "TestLineMarkerProvider: ";

    public static final Icon ICON = IconLoader.getIcon("/icons/icon.png");

    public static final Set<String> LITERAL_CACHE = new HashSet<>();

    private static GutterIconNavigationHandler<PsiElement> SHOW_SENDERS = (e, psiElement) -> {
        if (psiElement instanceof PsiMethod) {
            Project project = psiElement.getProject();
            JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
            PsiClass eventBusClass = javaPsiFacade.findClass("de.greenrobot.event.EventBus", GlobalSearchScope.allScope(project));
            PsiMethod postMethod = eventBusClass.findMethodsByName("post", false)[0];
            PsiMethod method = (PsiMethod) psiElement;
            PsiClass eventClass = ((PsiClassType) method.getParameterList().getParameters()[0].getTypeElement().getType()).resolve();
//                        new ShowUsagesAction(new SenderFilter(eventClass)).startFindUsages(postMethod, new RelativePoint(e), PsiUtilBase.findEditor(psiElement), MAX_USAGES);
        }
    };

    @Nullable
    @Override
    public RelatedItemLineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
//        if(psiElement instanceof PsiAnnotation) {
//            PsiAnnotation psiAnnotation = ((PsiAnnotation) psiElement);
//            System.out.println(TAG + "-------------- begin ------------");
//
//            System.out.println(TAG + psiElement.getClass().getSimpleName() + "-> " + psiAnnotation.getText() + ", " + psiAnnotation.findAttributeValue("path"));
//
//            System.out.println(TAG + "-------------- end ------------");
//
//            if(psiAnnotation.getText().startsWith("@Route")) {
//                PsiAnnotationMemberValue tmp = psiAnnotation.findAttributeValue("path");
//                if(tmp == null || !(tmp instanceof PsiLiteralExpression))
//                    return null;
//                LITERAL_CACHE.add(((PsiLiteralExpression) tmp).getValue().toString());
//
//                System.out.println(LITERAL_CACHE);
//                return new RelatedItemLineMarkerInfo<>(psiElement, psiElement.getTextRange(), new MyIcon(1), Pass.UPDATE_ALL, null, null, GutterIconRenderer.Alignment.LEFT, Collections.emptyList());
//            }
//        }
//
//        if(psiElement instanceof PsiLiteralExpression){
//            PsiLiteralExpression psiLiteralExpression = ((PsiLiteralExpression) psiElement);
//            System.out.println(TAG + "-------------- begin ------------");
//
//            System.out.println(TAG + psiElement.getClass().getSimpleName() + "-> " + psiLiteralExpression.getValue());
//
//            System.out.println(TAG + "-------------- end ------------");
//
//            for (PsiReference psiReference : ReferencesSearch.search(psiLiteralExpression)) {
//                System.out.println(TAG + "search -> " + psiLiteralExpression.getText() + " -> " + psiReference.resolve().getText());
//            }
//
//            System.out.println(LITERAL_CACHE);
//            if(LITERAL_CACHE.contains(psiLiteralExpression.getValue().toString()))
//                return new RelatedItemLineMarkerInfo<>(psiElement, psiElement.getTextRange(), new MyIcon(2), Pass.UPDATE_ALL, null, null, GutterIconRenderer.Alignment.LEFT, Collections.emptyList());
//        }

        return null;
    }


    private static class MyIcon extends com.intellij.util.ui.EmptyIcon {

        private int count;
        private int length;

        MyIcon(int count) {
            super(8, 8);
            this.count = count;
            int temp = count;
            length ++;
            while (temp / 10 != 0) {
                length ++;
                temp /= 10;
            }
        }

        @Override
        public void paintIcon(Component c, Graphics g, int i, int j) {
            if (count == -1) {
                return;
            }
            g.setColor(JBColor.BLACK);
//            g.setColor(count <= 0 ? PropertiesUtils.getZeroColor() : count == 1 ? PropertiesUtils.getOneColor() : PropertiesUtils.getOtherColor());
            g.drawString(String.valueOf(count), i, (int)(j + getIconHeight() + 1.5));
        }

        @Override
        public int getIconWidth() {
            return length * 5;
        }

        @Override
        public int getIconHeight() {
            return 8;
        }
    }

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if(element instanceof PsiAnnotation) {
            PsiAnnotation psiAnnotation = ((PsiAnnotation) element);

            if(psiAnnotation.getText().startsWith("@Route")) {
                PsiAnnotationMemberValue psiAnnotationMember = psiAnnotation.findAttributeValue("path");

                if (psiAnnotationMember instanceof PsiLiteralExpression) {
                    PsiLiteralExpression literalExpression = (PsiLiteralExpression) psiAnnotationMember;

                    System.out.println(TAG + "-------------- begin ------------");

                    System.out.println(TAG + literalExpression.getClass().getSimpleName() + "-> " + literalExpression.getValue());

                    System.out.println(TAG + "-------------- end ------------");

                    String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
                    Project project = element.getProject();
                    final List<PsiElement> properties = SimpleUtil.findProperties(project, value, PsiElement.class);

                    System.out.println(TAG + properties);

                    if (properties.size() > 0) {
                        result.add(NavigationGutterIconBuilder.create(new MyIcon(666))
                                .setTargets(properties)
                                .setTooltipText("Navigate to a simple property")
                                .createLineMarkerInfo(element));
                    }
                }
            }
        }
    }
}
