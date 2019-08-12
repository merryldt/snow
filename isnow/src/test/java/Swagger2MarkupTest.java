//import com.summer.isnow.ISnowApplication;
//import com.sun.tools.internal.xjc.Language;
//import io.github.swagger2markup.GroupBy;
//import io.github.swagger2markup.Swagger2MarkupConfig;
//import io.github.swagger2markup.Swagger2MarkupConverter;
//import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
//import io.github.swagger2markup.markup.builder.MarkupLanguage;
//import javafx.application.Application;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * @author liudongting
// * @date 2019/7/15 13:13
// */
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ISnowApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class Swagger2MarkupTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    private static final Logger LOG = LoggerFactory.getLogger(Swagger2MarkupTest.class);
//
//    @Test
//    public void createSpringFoxSwaggerJson() throws Exception {
////        String outputDir = System.getProperty("swaggerOutputDir"); // mvn test
//        MvcResult mvcResult = this.mockMvc.perform(get("/v2/api-docs")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        String swaggerJson = response.getContentAsString();
////        Files.createDirectories(Paths.get(outputDir));
////        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputDir, "swagger.json"), StandardCharsets.UTF_8)){
////            writer.write(swaggerJson);
////        }
//        LOG.info("--------------------swaggerJson create --------------------");
//        convertAsciidoc(swaggerJson);
//        LOG.info("--------------------swagon.json to asciiDoc finished --------------------");
//    }
//
//    /**
//     * 将swagger.yaml或swagger.json转换成漂亮的 AsciiDoc
//     * 访问：http://localhost:9095/v2/api-docs
//     * 将页面结果保存为src/main/resources/swagger.json
//     */
//    private void convertAsciidoc(String swaggerStr) {
////        Path localSwaggerFile = Paths.get(System.getProperty("swaggerOutputDir"), "swagger.json");
//        Path outputFile = Paths.get(System.getProperty("asciiDocOutputDir"));
//        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
//                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
////                .withOutputLanguage(Language.valueOf())
//                .withPathsGroupedBy(GroupBy.TAGS)
//                .withGeneratedExamples()
//                .withoutInlineSchema()
//                .build();
//        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(swaggerStr)
//                .withConfig(config)
//                .build();
//        converter.toFile(outputFile);
//    }
//}