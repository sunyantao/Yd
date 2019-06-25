package com.fr.io;

import com.fr.base.FRContext;
import com.fr.base.Parameter;
import com.fr.dav.LocalEnv;
import com.fr.general.ModuleContext;
import com.fr.io.exporter.EmbeddedTableDataExporter;
import com.fr.io.exporter.ImageExporter;
import com.fr.main.impl.WorkBook;
import com.fr.main.workbook.ResultWorkBook;
import com.fr.report.module.EngineModule;
import com.fr.stable.WriteActor;

import java.io.File;
import java.io.FileOutputStream;


public class ExportApi {
    public static void main(String[] args) {
        // 定义报表运行环境,才能执行报表
        String envpath = "D:\\soft\\FineReport_9.0\\WebReport\\WEB-INF";
        FRContext.setCurrentEnv(new LocalEnv(envpath));
        ModuleContext.startModule(EngineModule.class.getName());
        try {
            // 未执行模板工作薄
            WorkBook workbook = (WorkBook) TemplateWorkBookIO
                    .readTemplateWorkBook(FRContext.getCurrentEnv(),
                            "\\doc\\Primary\\Parameter\\Parameter.cpt");
            // 获取报表参数并设置值，导出内置数据集时数据集会根据参数值查询出结果从而转为内置数据集
            Parameter[] parameters = workbook.getParameters();
            parameters[0].setValue("华东");
            // 定义parametermap用于执行报表，将执行后的结果工作薄保存为workBook
            java.util.Map parameterMap = new java.util.HashMap();
            for (int i = 0; i < parameters.length; i++) {
                parameterMap.put(parameters[i].getName(), parameters[i]
                        .getValue());
            }
            // 定义输出流
            FileOutputStream outputStream;
            // 将未执行模板工作薄导出为内置数据集模板
//            outputStream = new FileOutputStream(new File("D:\\EmbExport.cpt"));
//            EmbeddedTableDataExporter templateExporter = new EmbeddedTableDataExporter();
//            templateExporter.export(outputStream, workbook);
//            // 将模板工作薄导出模板文件，在导出前您可以编辑导入的模板工作薄，可参考报表调用章节
//            outputStream = new FileOutputStream(new File("D:\\TmpExport.cpt"));
//            (workbook).export(outputStream);
            //将结果工作薄导出为image文件
            outputStream = new FileOutputStream(new File("D:\\PngExport.png"));
            ImageExporter ImageExport = new ImageExporter();
            ImageExport.setSuffix("png");
            ImageExport.export(outputStream, workbook.execute(parameterMap,new WriteActor()));
            outputStream.close();
            ModuleContext.stopModules();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}