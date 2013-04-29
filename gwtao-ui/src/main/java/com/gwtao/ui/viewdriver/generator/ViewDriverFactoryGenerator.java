/* 
 * Copyright 2012 Matthias Huebner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtao.ui.viewdriver.generator;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.editor.rebind.model.ModelUtils;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtao.ui.viewdriver.client.IViewDriver;
import com.gwtao.ui.viewdriver.client.ViewDriver;
import com.gwtao.ui.viewdriver.client.ViewDriverFactory;
import com.gwtao.ui.viewdriver.client.adapter.CheckBoxAdapter;
import com.gwtao.ui.viewdriver.client.adapter.DateBoxAdapter;
import com.gwtao.ui.viewdriver.client.adapter.IDPickerAdapter;
import com.gwtao.ui.viewdriver.client.adapter.IFieldAdapter;
import com.gwtao.ui.viewdriver.client.adapter.IFieldAdapter.WidgetType;
import com.gwtao.ui.viewdriver.client.adapter.PasswordTextBoxAdapter;
import com.gwtao.ui.viewdriver.client.adapter.TextAreaAdapter;
import com.gwtao.ui.viewdriver.client.adapter.ValueBoxBaseAdapter;
import com.gwtao.ui.viewdriver.client.adapter.ValueListBoxAdapter;

public class ViewDriverFactoryGenerator extends Generator {

  private static Map<String, String> adapterMap;

  public ViewDriverFactoryGenerator() {
  }

  private Map<String, String> initAdapterList(TypeOracle typeOracle) {
    String[] types = {
        ValueBoxBaseAdapter.class.getName(),
        ValueListBoxAdapter.class.getName(),
        DateBoxAdapter.class.getName(),
        TextAreaAdapter.class.getName(),
        PasswordTextBoxAdapter.class.getName(),
        CheckBoxAdapter.class.getName(),
        IDPickerAdapter.class.getName() };

    Map<String, String> adapterMap = new HashMap<String, String>();

    for (String type : types) {
      JClassType fieldType = typeOracle.findType(type);
      if (fieldType != null) {
        WidgetType annotation = fieldType.getAnnotation(IFieldAdapter.WidgetType.class);
        if (annotation != null) {
          for (Class<? extends Widget> w : annotation.value())
            adapterMap.put(w.getName(), type);
        }
      }
    }

    return adapterMap;
  }

  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
    try {
      if (adapterMap == null)
        adapterMap = initAdapterList(context.getTypeOracle());
      JClassType classType = context.getTypeOracle().getType(typeName);
      String packageName = classType.getPackage().getName();
      String simpleName = classType.getName().replace('.', '_') + "Impl";
      PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
      if (printWriter != null) {
        generate(context, printWriter, logger, classType, packageName, simpleName);
      }
      return packageName + "." + simpleName;
    }
    catch (NotFoundException e) {
      logger.log(Type.ERROR, "Type " + typeName + " not found", e);
      throw new UnableToCompleteException();
    }
  }

  private List<JField> getUiFields(JClassType viewType) {
    List<JField> uiFields = new ArrayList<JField>();
    JField[] fields = viewType.getFields();
    for (JField field : fields) {
      if (field.isAnnotationPresent(UiField.class) && !field.isAnnotationPresent(Ignore.class)) {
        uiFields.add(field);
      }
    }
    return uiFields;
  }

  private void generate(GeneratorContext context, PrintWriter printWriter, TreeLogger logger, JClassType classType, String packageName, String simpleName) throws UnableToCompleteException, NotFoundException {
    JClassType[] interfaces = classType.getImplementedInterfaces();
    if (interfaces.length != 1) {
      logger.log(Type.ERROR, "too many interfaces in " + classType);
      throw new UnableToCompleteException();
    }
    JClassType intfType = context.getTypeOracle().getType(ViewDriverFactory.class.getName()).isInterface();
    JClassType[] parameters = ModelUtils.findParameterizationOf(intfType, classType);
    assert parameters.length == 2 : "Unexpected number of type parameters";
    JClassType modelType = parameters[0];
    JClassType viewType = parameters[1];
    ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);

    composer.addImplementedInterface(classType.getQualifiedSourceName());

    composer.addImport(List.class.getName());
    composer.addImport(ArrayList.class.getName());
    composer.addImport(IFieldAdapter.class.getName());
    composer.addImport(IViewDriver.class.getName());
    composer.addImport(ViewDriver.class.getName());
    composer.addImport(modelType.getQualifiedSourceName());
    composer.addImport(viewType.getQualifiedSourceName());

    SourceWriter src = composer.createSourceWriter(context, printWriter);

    String modelName = modelType.getSimpleSourceName();
    String viewName = viewType.getSimpleSourceName();

    List<JField> uiFields = getUiFields(viewType);
    for (JField uiField : uiFields) {
      generateAdapterClasses(src, logger, uiField, modelType, context.getTypeOracle());
    }

    src.println("public IViewDriver<%s> generateDriver(%s view) {", modelName, viewName );
    src.println("  List<IFieldAdapter<%s,%s>> adapterList = new ArrayList<IFieldAdapter<%s,%s>>();", modelName, viewName, modelName, viewName);
    src.println();

    src.println("  ViewDriver<%s> mgr = new ViewDriver<%s>();", modelName, modelName);

    for (JField uiField : uiFields) {
      src.println("  mgr.add( new %s(), view.%s );", WordUtils.capitalize(uiField.getName()) + "Adapter", uiField.getName());
    }

    src.println("  return mgr;");
    src.println("}");
    src.commit(logger);
  }

  private void generateAdapterClasses(SourceWriter src, TreeLogger logger, JField uiField, JClassType modelType, TypeOracle typeOracle) throws UnableToCompleteException {
    String uiFieldName = WordUtils.capitalize(uiField.getName());

    String modelFieldName;
    if (uiFieldName.endsWith("Editor"))
      modelFieldName = uiFieldName.substring(0, uiFieldName.length() - 6);
    else
      modelFieldName = uiFieldName;

    JType[] arr = new JType[0];
    JMethod field;
    try {
      field = modelType.getMethod("get" + modelFieldName, arr);
    }
    catch (NotFoundException e1) {
      try {
        modelFieldName = WordUtils.uncapitalize(modelFieldName);
        field = modelType.getMethod("get" + modelFieldName, arr);
      }
      catch (NotFoundException e2) {
        logger.log(Type.ERROR, e1.getMessage());
        throw new UnableToCompleteException();
      }
    }
    String valueType = field.getReturnType().getQualifiedSourceName();

    src.println("  private static class %s extends %s<%s,%s,%s> {", uiFieldName + "Adapter", getAdapterName(typeOracle, uiField, modelType, logger), modelType.getQualifiedSourceName(), valueType, uiField.getType().getParameterizedQualifiedSourceName());
    src.println("    public void updateView(%s model) {", modelType.getSimpleSourceName());
    src.println("      setValue( model.get%s() );", modelFieldName);
    src.println("    }");
    src.println();
    src.println("    public void updateModel(%s model) {", modelType.getSimpleSourceName());
    src.println("      model.set%s( getValue() );", modelFieldName);
    src.println("    }");
    src.println("  }");
    src.println();
  }

  private Object getAdapterName(TypeOracle typeOracle, JField uiField, JClassType modelType, TreeLogger logger) throws UnableToCompleteException {
    String widgetTypeName = uiField.getType().getQualifiedSourceName();
    String adapterName = adapterMap.get(widgetTypeName);
    if (adapterName != null)
      return adapterName;
    logger.log(Type.ERROR, "No adapter found for ui field type '" + widgetTypeName + "'");
    throw new UnableToCompleteException();
  }
}
