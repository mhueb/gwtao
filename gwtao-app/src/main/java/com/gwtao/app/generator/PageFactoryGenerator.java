package com.gwtao.app.generator;

import java.io.PrintWriter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtao.app.client.IPage;
import com.gwtao.app.client.PageFactories;
import com.gwtao.app.client.PageFactory;
import com.gwtao.app.client.PageFactoryRegistry;
import com.gwtao.app.client.Pages;

public class PageFactoryGenerator extends Generator {

  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
    try {
      JClassType classType = context.getTypeOracle().getType(typeName);
      String packageName = classType.getPackage().getName();
      String simpleName = classType.getSimpleSourceName() + "Impl";

      PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
      if (printWriter != null) {
        generateInjector(context, printWriter, logger, classType, packageName, simpleName);
      }
      return packageName + "." + simpleName;
    }
    catch (NotFoundException e) {
      logger.log(Type.ERROR, "Type " + typeName + " not found", e);
      throw new UnableToCompleteException();
    }
  }

  private void generateInjector(GeneratorContext context, PrintWriter printWriter, TreeLogger logger, JClassType classType, String packageName, String simpleName) throws NotFoundException {
    ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
    composer.addImplementedInterface(classType.getName());
    composer.addImport(IPage.class.getName());
    composer.addImport(Pages.class.getName());
    composer.addImport(PageFactoryRegistry.class.getName());
    composer.addImport(GWT.class.getName());
    SourceWriter src = composer.createSourceWriter(context, printWriter);
    PageFactories entries = classType.getAnnotation(PageFactories.class);

    src.println("  public %s() {", simpleName);
    for (PageFactory entry : entries.value()) {
      src.println("    PageFactoryRegistry.Entry entry = new PageFactoryRegistry.Entry() {");
      src.println("      public String getToken() { return \"%s\"; }", entry.token());
      src.println("      public IPage create() { return GWT.create( %s.class ); }", entry.doc().getName());
      src.println("    };");
      src.println("    Pages.REGISTRY.register(entry);");
    }
    src.println("  }");

    src.commit(logger);
  }

}
