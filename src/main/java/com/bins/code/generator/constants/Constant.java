package com.bins.code.generator.constants;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.nio.charset.StandardCharsets;

public interface Constant {

    String AMPERSAND = "&";
    String AND = "and";
    String AT = "@";
    String ASTERISK = "*";
    String STAR = "*";
    String BACK_SLASH = "\\";
    String COLON = ":";
    String COMMA = ",";
    String DASH = "-";
    String DOLLAR = "$";
    String DOT = ".";
    String DOTDOT = "..";
    String DOT_CLASS = ".class";
    String DOT_JAVA = ".java";
    String DOT_XML = ".xml";
    String EMPTY = "";
    String EQUALS = "=";
    String FALSE = "false";
    String SLASH = "/";
    String HASH = "#";
    String HAT = "^";
    String LEFT_BRACE = "{";
    String LEFT_BRACKET = "(";
    String LEFT_CHEV = "<";
    String DOT_NEWLINE = ",\n";
    String NEWLINE = "\n";
    String N = "n";
    String NO = "no";
    String NULL = "null";
    String OFF = "off";
    String ON = "on";
    String PERCENT = "%";
    String PIPE = "|";
    String PLUS = "+";
    String QUESTION_MARK = "?";
    String EXCLAMATION_MARK = "!";
    String QUOTE = "\"";
    String RETURN = "\r";
    String TAB = "\t";
    String RIGHT_BRACE = "}";
    String RIGHT_BRACKET = ")";
    String RIGHT_CHEV = ">";
    String SEMICOLON = ";";
    String SINGLE_QUOTE = "'";
    String BACKTICK = "`";
    String SPACE = " ";
    String TILDA = "~";
    String LEFT_SQ_BRACKET = "[";
    String RIGHT_SQ_BRACKET = "]";
    String TRUE = "true";
    String UNDERSCORE = "_";
    String UTF_8 = "UTF-8";
    String US_ASCII = "US-ASCII";
    String ISO_8859_1 = "ISO-8859-1";
    String Y = "y";
    String YES = "yes";
    String ONE = "1";
    String ZERO = "0";
    String DOLLAR_LEFT_BRACE = "${";
    String HASH_LEFT_BRACE = "#{";
    String CRLF = "\r\n";
    String HTML_NBSP = "&nbsp;";
    String HTML_AMP = "&amp";
    String HTML_QUOTE = "&quot;";
    String HTML_LT = "&lt;";
    String HTML_GT = "&gt;";
    String[] EMPTY_ARRAY = new String[0];
    byte[] BYTES_NEW_LINE = "\n".getBytes();

    String MODULE_NAME = "ModuleName";
    String COMMON = "Common";
    String PARENT = "Parent";
    String ENTITY = "Entity";
    String DTO = "Dto";
    String VO = "Vo";
    String SERVICE = "Service";
    String SERVICE_IMPL = "ServiceImpl";
    String MAPPER = "Mapper";
    String XML = "Xml";
    String CONTROLLER = "Controller";
    String ES_ENTITY = "EsEntity";
    String ES_SERVICE = "EsService";
    String ES_SERVICE_IMPL = "EsServiceImpl";
    String PAGE_DTO = "PageDto";
    String CONSTANTS = "Constants";
    String AUTH_UTILS = "AuthUtils";
    String BUSINESS_EXCEPTION = "BusinessException";
    String ERROR_CODE = "ErrorCode";
    String RESULT_BODY = "ResultBody";
    String VALID_GROUPS = "ValidGroups";
    String WEB_ROUTER = "WebRouter";
    String WEB_PAGE = "WebPage";
    String WEB_API = "WebApi";

    String JAVA_TMPDIR = "java.io.tmpdir";
    String UTF8 = StandardCharsets.UTF_8.name();
    String UNDERLINE = "_";

    String JAVA_SUFFIX = StringPool.DOT_JAVA;
    String XML_SUFFIX = ".xml";
    String TS_SUFFIX = ".ts";

    /**
     * 实体模板路径
     */
    String TEMPLATE_ENTITY_JAVA = "/templates/do.java";

    /**
     * DTO模板路径
     */
    String TEMPLATE_DTO_JAVA = "/templates/dto.java";

    /**
     * VO模板路径
     */
    String TEMPLATE_VO_JAVA = "/templates/vo.java";

    /**
     * 控制器模板路径
     */
    String TEMPLATE_CONTROLLER = "/templates/controller.java";

    /**
     * Mapper模板路径
     */
    String TEMPLATE_MAPPER = "/templates/mapper.java";

    /**
     * MapperXml模板路径
     */
    String TEMPLATE_XML = "/templates/mapper.xml";

    /**
     * Service模板路径
     */
    String TEMPLATE_SERVICE = "/templates/service.java";

    /**
     * ServiceImpl模板路径
     */
    String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";

    /**
     * 实体模板路径
     */
    String TEMPLATE_ES_ENTITY_JAVA = "/templates/esDO.java";

    /**
     * Service模板路径
     */
    String TEMPLATE_ES_SERVICE = "/templates/esService.java";

    /**
     * ServiceImpl模板路径
     */
    String TEMPLATE_ES_SERVICE_IMPL = "/templates/esServiceImpl.java";

    /**
     * web router 模板
     */
    String TEMPLATE_WEB_ROUTER_TS = "/templates/webRouter.ts";

    /**
     * web页面模板
     */
    String TEMPLATE_WEB_PAGE_TS = "/templates/webPage.ts";

    /**
     * web api页面模板
     */
    String TEMPLATE_WEB_API_TS = "/templates/webApi.ts";

    String VM_LOAD_PATH_KEY = "file.resource.loader.class";
    String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

    String SUPER_MAPPER_CLASS = "cn.commerce.components.db.mybatis.base.mapper.SuperMapper";
    String SUPER_SERVICE_CLASS = "cn.commerce.components.db.mybatis.base.service.IBaseService";
    String SUPER_SERVICE_IMPL_CLASS = "cn.commerce.components.db.mybatis.base.service.impl.BaseServiceImpl";
}
