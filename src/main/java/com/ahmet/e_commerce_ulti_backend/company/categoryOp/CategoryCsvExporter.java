package com.ahmet.e_commerce_ulti_backend.company.categoryOp;


import com.ahmet.e_commerce_ulti_backend.entities.Category;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryCsvExporter extends AbstractExporterCat {

    public void export(List<Category> categories, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response,"text/csv",".csv");

        ICsvBeanWriter csvwriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Category Id", "Name", "Alias","Parent Name", "Enabled"};
        String[] fieldMapping = {"id", "name", "alias", "parent", "enabled"};
        csvwriter.writeHeader(csvHeader);
        for (Category category : categories) {
            csvwriter.write(category, fieldMapping);
        }
        csvwriter.close();
    }
}
