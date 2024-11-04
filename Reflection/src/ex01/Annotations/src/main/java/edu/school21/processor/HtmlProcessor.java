package edu.school21.processor;

import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;
import com.google.auto.service.AutoService;

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;

@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		final Set<? extends Element> allEelements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);	
		
        for (Element element : allEelements) {
            HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
            String fileName = htmlForm.fileName();
            String action = htmlForm.action();
            String method = htmlForm.method();
			
            StringBuilder outString = new StringBuilder();
            outString.append("<form action = \"" + action + "\" method = \"" + method + "\">\n");

            for (Element enclosed : element.getEnclosedElements()) {
                HtmlInput inputAnnotation = enclosed.getAnnotation(HtmlInput.class);
                if (inputAnnotation != null) {
                    outString.append("\t<input type = \"" + inputAnnotation.type() +
									"\" name = \"" + inputAnnotation.name() +
									"\" placeholder = \"" + inputAnnotation.placeholder() + "\">\n");
                }
            }
            outString.append("\t<input type = \"submit\" value = \"Send\">\n");
			outString.append("</form>");
            try {
                FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", fileName);
                System.out.println(fileName);
                try (BufferedWriter writer = new BufferedWriter(fileObject.openWriter())) {
                    writer.write(outString.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
