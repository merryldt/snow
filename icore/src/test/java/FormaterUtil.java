import com.summer.icore.model.*;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;


public class FormaterUtil<T> {


    public static String convertPropertyToColmun(String property) {
        char[] cs = property.toCharArray();
        StringBuilder stringBuffer = new StringBuilder();
        for (char c : cs) {
            if (c >= 'A' && c <= 'Z') {
                stringBuffer.append("_");
            }
            stringBuffer.append(Character.toLowerCase(c));
        }
        return stringBuffer.toString();
    }

    public static String getJdbcType(Field field) {
        String fieldType = field.getType().getName();
        if (fieldType.endsWith("String")) {
            return "VARCHAR";
        } else if (fieldType.endsWith("Date")) {
            return "TIMESTAMP";
        } else {
            return "DECIMAL";
        }
    }
//    public static void main(String[] args) throws ParserConfigurationException {
//       createXml("H:\\myself\\snow\\snow\\icore\\src\\main\\resources\\mapper\\UserAdminMapper.xml",
//               "H:\\myself\\snow\\snow\\icore\\src\\main\\resources\\mapper\\UserAdminMapper.xml",
//               UserAdmin.class,
//               "user_member",
//               "s_user_member",
//               "com.summer.icore.",
//               "com.summer.icore.model");
//    }
    public static void main(String[] args) throws ParserConfigurationException {
        createXml("D:\\study\\snow\\icore\\src\\main\\resources\\mapper\\UserRolePermissionRelationMapper.xml",
                "D:\\study\\snow\\icore\\src\\main\\resources\\mapper\\UserRolePermissionRelationMapper.xml",
                UserRolePermissionRelation.class,
                "user_role_permission_relation",
                "s_user_role_permission_relation",
                "com.summer.icore.",
                "com.summer.icore.model");
    }
    /**
     * @param <T>
     * @param fileName          mapper.xml目标文件
     * @param entityClass       待生成.xml的entity的class
     * @param tableName         对应entity的表名
     * @param tableAlias        表的别名
     * @param mapperPackageName 对应interface-mapper.java的包名
     * @throws ParserConfigurationException
     *
     */
    public static <T> void createXml(String mapperXmlName,String fileName,Class<T> entityClass, String tableName, String tableAlias, String mapperPackageName,String entityPackageName) throws ParserConfigurationException {
        String entityName = entityClass.getSimpleName();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        //mapper.xml头与根节点
        Element root = document.createElement("mapper");
        root.setAttribute("namespace", mapperPackageName + "dao"+"." + entityName + "Mapper");
        document.appendChild((Node) root);
        //resultMap节点
        Element resultMap = (Element) document.createElement("resultMap");
        resultMap.setAttribute("id", "BaseResultMap");
        resultMap.setAttribute("type", entityPackageName + "."+entityName);
        resultMap.appendChild(document.createTextNode("\n\t"));
        resultMap.appendChild(document.createTextNode("\n\t"));
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("serialVersionUID")) {
                String fieldName = field.getName();
                Element result=null;
                if(fieldName.equals("id")){
                    result=(Element) document.createElement("id");
                }else {
                    result = (Element) document.createElement("result");
                }
                result.setAttribute("column", convertPropertyToColmun(fieldName));
                result.setAttribute("property", fieldName);
                resultMap.appendChild(document.createTextNode("\n\t"));
                resultMap.appendChild(result);
            }
        }
        root.appendChild(resultMap);

        //base_column_list sql
        Element baseColumnList = (Element) document.createElement("sql");
        baseColumnList.setAttribute("id", "Base_Column_List");
        StringBuilder buffer = new StringBuilder();
        baseColumnList.appendChild(document.createTextNode("\n\t"));
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("serialVersionUID")) {
                buffer.append(convertPropertyToColmun(field.getName())).append(",");
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        ((Node) baseColumnList).appendChild(document.createTextNode(buffer.toString()));
        baseColumnList.appendChild(document.createTextNode("\n"));
        root.appendChild(baseColumnList);


        //findListForMap
//        Element mapSelect = document.createElement("select");
//        mapSelect.setAttribute("id", "findListForMap");
//        mapSelect.setAttribute("parameterType", "map");
//        mapSelect.setAttribute("resultMap", "BaseResultMap");
//        mapSelect.appendChild(document.createTextNode("\n\tselect\n\t"));
//        Element incluse5 = (Element) document.createElement("include");
//        incluse5.setAttribute("refid", "Base_Column_List");
//        mapSelect.appendChild(incluse5);
//        mapSelect.appendChild(document.createTextNode("\n\tfrom " + tableName + " " + tableAlias + "\n\t"));
//        Element incluse6 = (Element) document.createElement("include");
//        incluse6.setAttribute("refid", "common_query_condition");
//        mapSelect.appendChild(incluse6);
//        root.appendChild(mapSelect);

        //common_query_condition
//        Element condition = document.createElement("sql");
//        condition.setAttribute("id", "common_query_condition");
//        condition.appendChild(document.createTextNode("\n\t"));
//        Element where = document.createElement("where");
//        where.appendChild(document.createTextNode("\n\t\t"));
//        Element ifElement = document.createElement("if");
//        ifElement.setAttribute("test", "id != null");
//        ifElement.appendChild(document.createTextNode("\n\t\t\t"));
//        ifElement.appendChild(document.createTextNode((tableAlias != null ? tableAlias + ".id = " : "id = ") ));
//        where.appendChild(ifElement);
//        where.appendChild(document.createTextNode("\n\t"));
//        condition.appendChild(where);
//        root.appendChild(condition);


        TransformerFactory tf = TransformerFactory.newInstance();

        try {

            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(document);

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//mybatis.org//DTD mapper 3.0//EN");

            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://mybatis.org/dtd/mybatis-3-mapper.dtd");

//		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            PrintWriter pw = new PrintWriter(new FileOutputStream(mapperXmlName));

            StreamResult result = new StreamResult(pw);

            transformer.transform(source, result);

            /* 生成mapper和dao*/
            createMapper(fileName, entityClass, mapperPackageName);
            createService(fileName, entityClass, mapperPackageName);
            createServiceImpl(fileName, entityClass, mapperPackageName);
//            createDao(fileName, entityClass, mapperPackageName);
        } catch (TransformerConfigurationException e) {

            System.out.println(e.getMessage());

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());

        } catch (TransformerException e) {

            System.out.println(e.getMessage());

        } catch (FileNotFoundException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }

    }



    public static <T> void createMapper(String fileName, Class<T> entityClass,String mapperPackageName) throws FileNotFoundException{
        fileName = StringUtils.substringBefore(fileName, "\\resources");
        fileName = fileName+"\\java\\com\\summer\\icore\\dao\\"+entityClass.getSimpleName()+"Mapper.java";
        StringBuilder mapperFile = new StringBuilder("package "+mapperPackageName+"dao"+";");
        mapperFile.append("\n");
        mapperFile.append("import com.baomidou.mybatisplus.mapper.BaseMapper;");
        mapperFile.append("\n");
        mapperFile.append("import "+entityClass.getPackage().getName()+"."+entityClass.getSimpleName()+";");
        mapperFile.append("\n");
        mapperFile.append("public interface "+entityClass.getSimpleName()+ "Mapper" +" extends BaseMapper<"+entityClass.getSimpleName()+"> {");
        mapperFile.append("\n");
        mapperFile.append("}");

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)),true);
        pw.println(mapperFile.toString());
    }


    //创建接口
    public  static <T> void createService(String fileName, Class<T> entityClass,String mapperPackageName) throws FileNotFoundException{

        fileName = StringUtils.substringBefore(fileName, "\\resources");
        fileName = fileName+"\\java\\com\\summer\\icore\\service\\"+entityClass.getSimpleName()+"Service.java";
        String daoPackageFileName = StringUtils.substringBefore(mapperPackageName, ".Mapper");
        StringBuilder daoFile = new StringBuilder("package "+mapperPackageName+"service"+";");
        daoFile.append("\n");
        daoFile.append("import com.baomidou.mybatisplus.service.IService;");
        daoFile.append("\n");
        daoFile.append("import "+entityClass.getPackage().getName()+"."+entityClass.getSimpleName()+";");
        daoFile.append("\n");
        daoFile.append("public interface "+entityClass.getSimpleName()+"Service extends IService<"+entityClass.getSimpleName()+"> {");
        daoFile.append("\n");
        daoFile.append("}");

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)),true);
        pw.println(daoFile.toString());

    }
    //创建接口的实现类
    public  static <T> void createServiceImpl(String fileName, Class<T> entityClass,String mapperPackageName) throws FileNotFoundException{

        fileName = StringUtils.substringBefore(fileName, "\\resources");
        fileName = fileName+"\\java\\com\\summer\\icore\\serviceImpl\\"+entityClass.getSimpleName()+"ServiceImpl.java";
        String daoPackageFileName = StringUtils.substringBefore(mapperPackageName, ".Mapper");
        StringBuilder daoFile = new StringBuilder("package "+mapperPackageName+"serviceImpl"+";");
        daoFile.append("\n");
        daoFile.append("import com.baomidou.mybatisplus.service.impl.ServiceImpl;");
        daoFile.append("\n");
        daoFile.append("import org.springframework.stereotype.Service;");
        daoFile.append("\n");
        daoFile.append("import "+entityClass.getPackage().getName()+"."+entityClass.getSimpleName()+";");
        daoFile.append("\n");
        daoFile.append("import  com.summer.icore.service"+"."+entityClass.getSimpleName()+"Service;");
        daoFile.append("\n");
        daoFile.append("import  com.summer.icore.dao"+"."+entityClass.getSimpleName()+"Mapper;");
        daoFile.append("\n");
        daoFile.append("@Service");
        daoFile.append("\n");
        daoFile.append("public class "+entityClass.getSimpleName()+"ServiceImpl extends ServiceImpl<" +entityClass.getSimpleName()+ "Mapper" + ","+entityClass.getSimpleName()+ "> implements "+entityClass.getSimpleName()+"Service"+"{");
        daoFile.append("\n");
        daoFile.append("}");

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)),true);
        pw.println(daoFile.toString());

    }
}
