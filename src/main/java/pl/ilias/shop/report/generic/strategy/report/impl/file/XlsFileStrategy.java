package pl.ilias.shop.report.generic.strategy.report.impl.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import pl.ilias.shop.model.dao.Product;
import pl.ilias.shop.report.FileType;
import pl.ilias.shop.report.generic.strategy.report.FileStrategy;
import pl.ilias.shop.repository.ProductRepository;
import pl.ilias.shop.security.SecurityUtils;
import pl.ilias.shop.security.UserDetailsServiceImpl;
import pl.ilias.shop.service.MailService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsFileStrategy implements FileStrategy {
    private final ProductRepository productRepository;
    private final MailService mailService;

    @Override
    public FileType getType() {
        return FileType.XLS;
    }

    @Override
    public byte[] generate() {
        log.info("XLS Generated");

        try (Workbook workbook = WorkbookFactory.create(false)) {
            int rowIndex = 0;
            Sheet sheet = workbook.createSheet("product report");
            Row row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("name");
            row.createCell(2).setCellValue("description");
            row.createCell(3).setCellValue("price");
            row.createCell(4).setCellValue("quantity");
            row.createCell(5).setCellValue("createDate");
            row.createCell(6).setCellValue("lastModifiedDate");

            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                row = sheet.createRow(++rowIndex);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getDescription());
                row.createCell(3).setCellValue(product.getPrice().doubleValue());
                row.createCell(4).setCellValue(product.getQuantity());
                row.createCell(5).setCellValue(product.getCreateDate());
                row.createCell(6).setCellValue(product.getLastModifiedDate());
            }

            sheet.setAutoFilter(new CellRangeAddress(0, rowIndex, 0, 6));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            byte[] excelFile = byteArrayOutputStream.toByteArray();
            mailService.sendEmail(SecurityUtils.getCurrentUserEmail(), "Product report",
                    Collections.emptyMap(), excelFile, "Report.xls");
            return excelFile;
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        return new byte[0];
    }
}
