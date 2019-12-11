package c.g.a.x.applogic_compiler;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import c.g.a.x.applogic_annotation.AppLogic;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"c.g.a.x.applogic_annotation.AppLogic"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AppLogicProcessor extends AbstractProcessor {


    private static final String INTERFACE_IAPPLOGICFULLNAMESLOADER_FULLNAME = "c.g.a.x.applogic_api.base.IAppLogicFullNamesLoader";
    private static final String INTERFACE_IAPPLOGICFULLNAMESLOADER_METHOD = "loadFullNames";


    private static final String INTERFACE_IAPPLOGICFULLNAMESLOADER_IMPL_PACKAGE = "com.android.iapplogicfullnamesloader.impl";


    private Elements elements;
    private Filer filer;

    private String moduleName;

    private final List<String> clsFullNameList = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("====AppLogicProcessor init======" + moduleName);

        elements = processingEnv.getElementUtils();      // Get class meta.
        filer = processingEnv.getFiler();

        Map<String, String> options = processingEnv.getOptions();

        moduleName = options.get("APPLOGIC");

    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("====AppLogicProcessor process======" + moduleName);
        collectInfo(roundEnvironment);
        writeToFile();
        return true;
    }


    private void collectInfo(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(AppLogic.class);
        for (Element element : elements) {

            TypeElement typeElement = (TypeElement) element;
            String classFullName = typeElement.getQualifiedName().toString();

            clsFullNameList.add(classFullName);
        }
    }


    private void writeToFile() {
        try {

            ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                    ClassName.get(List.class),
                    ClassName.get(String.class)
            );

            ParameterSpec parameterSpec = ParameterSpec.builder(parameterizedTypeName, "list").build();

            MethodSpec.Builder methodSpec = MethodSpec.methodBuilder(INTERFACE_IAPPLOGICFULLNAMESLOADER_METHOD)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(parameterSpec);
            for (int i = 0; i < clsFullNameList.size(); i++) {
                methodSpec.addStatement("list.add($S)", clsFullNameList.get(i));
            }

            TypeSpec typeSpec = TypeSpec
                    .classBuilder(moduleName + "$$AppLogicFullNamesLoader")
                    .addSuperinterface(ClassName.get(elements.getTypeElement(INTERFACE_IAPPLOGICFULLNAMESLOADER_FULLNAME)))
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodSpec.build())
                    .build();
            JavaFile.builder(INTERFACE_IAPPLOGICFULLNAMESLOADER_IMPL_PACKAGE, typeSpec)//
                    .build()//
                    .writeTo(filer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
