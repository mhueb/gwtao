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
import com.gwtao.app.client.DocumentRegistry;
import com.gwtao.app.client.FactoryEntries;
import com.gwtao.app.client.FactoryEntry;
import com.gwtao.app.client.IDocument;

public class DocumentFactory extends Generator {

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
    composer.addImport(IDocument.class.getName());
    composer.addImport(DocumentRegistry.class.getName());
    composer.addImport(GWT.class.getName());
    SourceWriter src = composer.createSourceWriter(context, printWriter);
    FactoryEntries entries = classType.getAnnotation(FactoryEntries.class);
    boolean next = false;

    src.println("  public DocumentRegistry.Entry[] getEntries() {");
    src.println("    return new DocumentRegistry.Entry[] {");

    for (FactoryEntry entry : entries.value()) {
      if (next)
        src.println("    ,");
      else
        next = true;
      src.println("    new DocumentRegistry.Entry() {");
      src.println("      public String getToken() {");
      src.println("        return \"%s\";", entry.token());
      src.println("      }");
      src.println("      public IDocument create() {");
      src.println("        return GWT.create( %s.class );", entry.doc().getName());
      src.println("      }");
      src.println("    }");
    }
    src.println("    };");
    src.println("  }");

    src.commit(logger);
  }

}
