package com.gwtao.cdi.generator;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class Injector extends Generator {

  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
    try {
      JClassType classType = context.getTypeOracle().getType(typeName);
      String packageName = classType.getPackage().getName();
      String simpleName = classType.getSimpleSourceName() + "Injector";

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

  private void generateInjector(GeneratorContext context, PrintWriter printWriter, TreeLogger logger, JClassType classType, String packageName, String simpleName) {
    ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
    composer.setSuperclass(classType.getName());

    // composer.addImport("com.google.gwt.user.client.ui.RootPanel");

    SourceWriter src = composer.createSourceWriter(context, printWriter);

    src.println("public %s() {", simpleName);

    // more to do...

    src.println("}");
    src.commit(logger);
  }

}
