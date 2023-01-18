package pl.ilias.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ilias.shop.report.FileType;
import pl.ilias.shop.report.generic.GenericFactory;
import pl.ilias.shop.report.generic.strategy.report.FileStrategy;
import pl.ilias.shop.report.regular.ReportFactory;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final ReportFactory reportFactory;
    private final GenericFactory<FileType, FileStrategy> genericFactory;

    @GetMapping
    void generateReport(@RequestParam FileType fileType) {
        reportFactory.getStrategyByType(fileType).generate();
    }

    @GetMapping("generic")
    ResponseEntity<byte[]> generateGenericReport(@RequestParam FileType fileType) {
        byte[] file = genericFactory.getStrategyByType(fileType).generate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report." + fileType);
        return ResponseEntity.ok().headers(httpHeaders).body(file);
    }
}
